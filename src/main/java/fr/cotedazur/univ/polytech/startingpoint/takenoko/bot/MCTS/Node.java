package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.pathIrrigation.GenerateAWayToIrrigateTheBox;


import java.util.ArrayList;
import java.util.List;

public class Node {
    private int profondeur;
    private final Node parent;
    private final GameState value;
    private final ActionLog instructions;
    private final List<Node> children;

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
        if(profondeur == 4 || (profondeur == 3 && (value.getMeteo() == MeteoDice.Meteo.NO_METEO || value.getMeteo() == MeteoDice.Meteo.VENT))){
            List<ActionLog> instruction = generateIrrigationInstructions();
            activateBotSimulator(arg, instruction);
            if(this.getBestChild().getValue().getScore() <= this.getValue().getScore()) {
                children.clear();
                profondeur --;
            }else{
                this.getBestChild().createChildren(arg);
                return;
            }
        }
        if(profondeur == 3){
            switch(value.getMeteo()){
                case ORAGE -> activateBotSimulator(arg,createPandaStormInstructions());
                case NUAGES -> {
                    activateBotSimulator(arg,generateSpecialInstruction());
                    if (children.isEmpty()){
                        profondeur --;
                        createChildren(arg);
                    }
                }
                case PLUIE -> {
                    activateBotSimulator(arg,generateRainInstruction());
                    if (children.isEmpty()){
                        profondeur --;
                        createChildren(arg);
                    }
                }
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

    private List<ActionLog> generateIrrigationInstructions(){
        List<ActionLog> irrigationInstructions = new ArrayList<>();
        GenerateAWayToIrrigateTheBox temp;
        for (HexagoneBoxPlaced box : value.getBoard().getPlacedBox().values()) {
            if (!box.isIrrigate()) {
                try {
                    temp = new GenerateAWayToIrrigateTheBox(box);
                    if (temp.getPathToIrrigation().size() <= this.getValue().getBotSimulator().getNbIrrigation())
                        irrigationInstructions.add(new ActionLogIrrigation(PossibleActions.PLACE_IRRIGATION, temp.getPathToIrrigation()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return irrigationInstructions;
    }

    private int getNbSpecial(Special special){
        return getValue().getBoard().getElementOfTheBoard().getNbJetonSpecial().get(special);
    }

    private List<ActionLog> generateSpecialInstruction(){
        List<ActionLog> specialInstructions = new ArrayList<>();
        if(getNbSpecial(Special.SourceEau) > 0){
            for (HexagoneBoxPlaced box : getValue().getBoard().getPlacedBox().values()) {
                if (box.getSpecial() == Special.Classique) {
                    specialInstructions.add(new ActionLog(PossibleActions.ADD_AUGMENT, box.getId(), 1));
                }
            }
        }
        if(getNbSpecial(Special.Engrais) > 0){
            for (HexagoneBoxPlaced box : getValue().getBoard().getPlacedBox().values()) {
                if (box.getSpecial() == Special.Classique) {
                    specialInstructions.add(new ActionLog(PossibleActions.ADD_AUGMENT, box.getId(), 2));
                }
            }
        }
        if(getNbSpecial(Special.Protéger) > 0){
            for (HexagoneBoxPlaced box : getValue().getBoard().getPlacedBox().values()) {
                if (box.getSpecial() == Special.Classique) {
                    specialInstructions.add(new ActionLog(PossibleActions.ADD_AUGMENT, box.getId(), 3));
                }
            }
        }
        return specialInstructions;
    }



    public List<ActionLog> generateRainInstruction(){
        List<ActionLog> rainInstructions = new ArrayList<>();
        for(HexagoneBoxPlaced box : getValue().getBoard().getPlacedBox().values()){
            if(box.isIrrigate() && box.getHeightBamboo() < 4){
                rainInstructions.add(new ActionLog(PossibleActions.GROW_BAMBOO, box.getId()));
            }
        }
        return rainInstructions;
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
        if(action == PossibleActions.MOVE_GARDENER || action == PossibleActions.MOVE_PANDA) {
            if (action == PossibleActions.MOVE_GARDENER) {
                for (int[] coords : Bot.possibleMoveForGardenerOrPanda(value.getBoard(), value.getBoard().getGardenerCoords())) {
                    tmp.add(new ActionLog(action, coords));
                }
            } else { /*MOVE_PANDA*/
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
            botSimulator.getGestionObjectives().checkObjectives(botSimulator, arg, botSimulator.getBoard().getNumberOfPlayers());
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

    private ActionLog getInstructions() {
        return instructions;
    }

    private GameState getValue() {
        return value;
    }
}
