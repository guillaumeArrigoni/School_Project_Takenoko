package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int profondeur;
    private Node parent;
    private GameState value;
    private ActionLog instructions;
    private List<Node> children;

    public Node(BotSimulator bot, int profondeur, MeteoDice.Meteo meteo, String arg) {
        this.profondeur = profondeur;
        this.value = new GameState(bot, meteo);
        this.children = new ArrayList<>();
        this.parent = null;
        this.instructions = null;
        createChildren(arg);
    }

    public Node(){
        this.profondeur = 0;
        this.value = new GameState();
        this.children = new ArrayList<>();
        this.parent = null;
        this.instructions = null;
    }

    protected Node(BotSimulator bot, int profondeur, Node parent, MeteoDice.Meteo meteo) {
        this.profondeur = profondeur-1;
        this.value = new GameState(bot, meteo);
        this.children = new ArrayList<>();
        this.parent = parent;
        this.instructions = bot.getInstructions();
    }

    public void createChildren(String arg) {
        if(profondeur == 3){
            switch(value.getMeteo()){
                case ORAGE -> {activateBotSimulator(arg,createPandaStormInstructions());}
               /* case PLUIE -> {profondeur -= 1;} // Pas encore implémenté
                case NUAGES -> {profondeur -= 1;}// Idem*/
                default /*SOLEIL*/ -> {
                    List<ActionLog> instruction = generateInstruction();
                    activateBotSimulator(arg, instruction);
                    if(this.getBestChild().getValue().getScore() <= this.getValue().getScore()) {
                        createChildrenIfNoGoodOption(arg);
                    }
                }
            }
        } else if(profondeur > 1 ) {
            List<ActionLog> instruction;
            if(value.getMeteo() == MeteoDice.Meteo.SOLEIL) {
                instruction = generateInstruction(instructions.getAction());
                activateBotSimulator(arg, instruction);
                if(this.getBestChild().getValue().getScore() <= this.getValue().getScore()) {
                    createChildrenIfNoGoodOption(arg,instructions.getAction());
                }
            }else{
                instruction = generateInstruction();
                activateBotSimulator(arg, instruction);
                if(this.getBestChild().getValue().getScore() <= this.getValue().getScore()) {
                    createChildrenIfNoGoodOption(arg);
                }
            }

        } else if (profondeur == 1) {
            List<ActionLog> instruction;
            if(value.getMeteo() == MeteoDice.Meteo.SOLEIL) {
                instruction = generateInstruction(instructions.getAction(), parent.instructions.getAction());
                activateBotSimulator(arg, instruction);
                if(this.getBestChild().getValue().getScore() <= this.getValue().getScore()) {
                    createChildrenIfNoGoodOption(arg,instructions.getAction(), parent.instructions.getAction());
                }
            }else{
                instruction = generateInstruction(instructions.getAction());
                activateBotSimulator(arg, instruction);
                if(this.getBestChild().getValue().getScore() <= this.getValue().getScore()) {
                    createChildrenIfNoGoodOption(arg,instructions.getAction());
                }
            }
        }if(profondeur > 1)
            this.getBestChild().createChildren(arg);
    }



    private void createChildrenIfNoGoodOption(String arg, PossibleActions ... actionPre) {
        List<PossibleActions> tmp = List.of(actionPre);
        if (this.getValue().getBotSimulator().getObjectives().size() < 5 && (value.getMeteo() == MeteoDice.Meteo.VENT || !tmp.contains(PossibleActions.DRAW_OBJECTIVE))) {
            simulateObjectivePicking(arg);
        }else if(value.getMeteo() == MeteoDice.Meteo.VENT || !tmp.contains(PossibleActions.DRAW_AND_PUT_TILE)) {
            simulateDrawAndPutTile(arg);
        }
        if(this.children.isEmpty() && (value.getMeteo() == MeteoDice.Meteo.VENT || !tmp.contains(PossibleActions.DRAW_AND_PUT_TILE)))
            simulateDrawAndPutTile(arg);
        else if(this.children.get(children.size()-1).getValue().getScore() < 0 && (value.getMeteo() == MeteoDice.Meteo.VENT || !tmp.contains(PossibleActions.DRAW_AND_PUT_TILE))){
            simulateDrawAndPutTile(arg);
        }
    }

    public List<ActionLog> createDrawAndPutTileInstructions(){
        List<ActionLog> drawAndPutTileInstructions = new ArrayList<>();
        int[] parameters;
        for (int i = 0; i < 3; i++) {
            for(int[] coords : this.getValue().getBotSimulator().getBoard().getAvailableBox()){
                parameters = new int[]{coords[0], coords[1], coords[2], i};
                drawAndPutTileInstructions.add(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, parameters));
            }
        }
        return drawAndPutTileInstructions;
    }

    public List<ActionLog> createPandaStormInstructions(){
        List<ActionLog> pandaStormInstructions = new ArrayList<>();
        for (HexagoneBoxPlaced boxpossible : value.getBoard().getPlacedBox().values()) {
            pandaStormInstructions.add(new ActionLog(PossibleActions.MOVE_PANDA, boxpossible.getCoordinates()));
        }
        return pandaStormInstructions;
    }


    public List<ActionLog> generateInstruction(PossibleActions ... actionPre){
        List<PossibleActions> tmp = List.of(actionPre);
        List<ActionLog> secondInstruction = new ArrayList<>();
        if(value.getMeteo() == MeteoDice.Meteo.VENT ) {
            for (PossibleActions action : PossibleActions.getAllActions()) {
                secondInstruction.addAll(createInstruction(action));
            }
        }else{
            for (PossibleActions action : PossibleActions.getAllActions()) {
                if(!tmp.contains(action)) {
                    secondInstruction.addAll(createInstruction(action));
                }
            }

        }
        return secondInstruction;
    }

    private List<ActionLog> createInstruction(PossibleActions action) {
        List<ActionLog> tmp = new ArrayList<>();
        switch (action) {
            case MOVE_GARDENER -> {
                for (int[] coords : Bot.possibleMoveForGardenerOrPanda(value.getBoard(), value.getBoard().getGardenerCoords())) {
                    tmp.add(new ActionLog(action, coords));
                }
            }
            case MOVE_PANDA -> {
                for (int[] coords : Bot.possibleMoveForGardenerOrPanda(value.getBoard(), value.getBoard().getPandaCoords())) {
                    tmp.add(new ActionLog(action, coords));
                }
            }
        }
        return tmp;
    }

    private void simulateObjectivePicking(String arg){
        List<Objective> objectives = value.getBotSimulator().getObjectives();
        int parcelObjective = 0;
        int pandaObjective = 0;
        int gardenerObjective = 0;
        ActionLog actionlog ;
        for (Objective objective : objectives) {
            switch (objective.getType()) {
                case PARCELLE -> parcelObjective++;
                case PANDA -> pandaObjective++;
                case JARDINIER -> gardenerObjective++;
            }
        }
        if (parcelObjective <= pandaObjective && parcelObjective <= gardenerObjective) {
            actionlog = new ActionLog(PossibleActions.DRAW_OBJECTIVE, 0);
        } else if (pandaObjective <= parcelObjective && pandaObjective <= gardenerObjective) {
            actionlog = new ActionLog(PossibleActions.DRAW_OBJECTIVE, 1);
        } else {
            actionlog = new ActionLog(PossibleActions.DRAW_OBJECTIVE, 2);
        }
        activateBotSimulator(arg, List.of(actionlog));
        if(children.get(children.size()-1).getValue().getScore() >= 0){
            children.subList(0, children.size() - 1).clear();
        }
    }

    private void activateBotSimulator(String arg, List<ActionLog> instructions) {
        for (ActionLog actionLog : instructions) {
            BotSimulator botSimulator = value.getBotSimulator().createBotSimulator(actionLog);
            botSimulator.playTurn(value.getMeteo(), arg);
            botSimulator.getGestionObjectives().checkObjectives(botSimulator, arg, 2);
            children.add(new Node(botSimulator, profondeur, this, value.getMeteo()));
        }
    }

    private void simulateDrawAndPutTile(String arg){
        List<ActionLog> tmp = createDrawAndPutTileInstructions();
        activateBotSimulator(arg, tmp);
        if(children.get(children.size()-tmp.size()).getValue().getScore() >= 0){
            children.subList(0, children.size() - tmp.size()).clear();
        }
    }



    public Node getBestChild(){
        Node bestChild = new Node();
        //Check if the childrens are good choices
        if(!children.isEmpty()) {
            bestChild = children.get(0);
            for (Node child : children) {
                if (child.getValue().getScore() > bestChild.getValue().getScore()) {
                    bestChild = child;
                }
            }
        }
        return bestChild;
    }
public List<ActionLog> getBestInstruction(){
    List<ActionLog> bestInstruction = new ArrayList<>();
    Node bestChild = getBestChild();
    bestInstruction.add(bestChild.getInstructions());
    while(bestChild.profondeur >= 1){
        bestChild = bestChild.getBestChild();
        bestInstruction.add(bestChild.getInstructions());
    }
    return bestInstruction;
}
    private Node getParent() {
        return parent;
    }

    private ActionLog getInstructions() {
        return instructions;
    }

    private GameState getValue() {
        return value;
    }
}
