package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Action;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Node {
    private int profondeur;
    private Node parent;
    private GameState value;
    private ActionLog instruction;
    private List<Node> children;

    public Node(BotSimulator bot, int profondeur, MeteoDice.Meteo meteo, String arg) {
        this.profondeur = profondeur;
        this.value = new GameState(bot, meteo);
        this.children = new ArrayList<>();
        this.parent = null;
        this.instruction = null;
        createChildren(arg);
    }

    public Node(){
        this.profondeur = 0;
        this.value = new GameState();
        this.children = new ArrayList<>();
        this.parent = null;
        this.instruction = null;
    }

    protected Node(BotSimulator bot, int profondeur, Node parent, MeteoDice.Meteo meteo) {
        this.profondeur = profondeur-1;
        this.value = new GameState(bot, meteo);
        this.children = new ArrayList<>();
        this.parent = parent;
        this.instruction = bot.getInstructions();
    }

    public void createChildren(String arg) {
        if(profondeur > 0 ) {
            List<ActionLog> firstIntruction = createFirstInstruction();
            activateBotSimulator(arg, firstIntruction);
            if(this.getBestChild().getValue().getScore() <= this.getValue().getScore()) {
                createChildrenIfNoGoodOption(arg);
            }
        } else if (profondeur == 0) {
            List<ActionLog> secondIntruction = createSecondInstruction(instruction.getAction());
            activateBotSimulator(arg, secondIntruction);
            if(this.getBestChild().getValue().getScore() <= this.getValue().getScore()) {
                createChildrenIfNoGoodOption(arg);
            }
        }if(profondeur > 0)
            this.getBestChild().createChildren(arg);
    }



    private void createChildrenIfNoGoodOption(String arg) {
        if (this.getValue().getBotSimulator().getObjectives().size() < 5) {
            simulateObjectivePicking(arg);
        }else {
            simulateDrawAndPutTile(arg);
        }
        if(this.children.isEmpty())
            simulateDrawAndPutTile(arg);
        else if(this.children.get(children.size()-1).getValue().getScore() < 0){
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

    public List<ActionLog> createFirstInstruction(){
        List<ActionLog> firstInstruction = new ArrayList<>();
        for (PossibleActions action : PossibleActions.getAllActions()) {
            firstInstruction.addAll(createInstruction(action));
        }
        return firstInstruction;
    }

    public List<ActionLog> createSecondInstruction(PossibleActions actionPre){
        if(value.getMeteo() == MeteoDice.Meteo.VENT) {
            return createFirstInstruction();
        }else{
            List<ActionLog> secondInstruction = new ArrayList<>();
            for (PossibleActions action : PossibleActions.getAllActions()) {
                if(action != actionPre) {
                    secondInstruction.addAll(createInstruction(action));
                }
            }
            return secondInstruction;

        }
    }

    private List<ActionLog> createInstruction(PossibleActions action) {
        List<ActionLog> tmp = new ArrayList<>();
        switch (action) {
            case MOVE_GARDENER -> {
                for (int[] coords : Action.possibleMoveForGardenerOrPanda(value.getBoard(), value.getBoard().getGardenerCoords())) {
                    tmp.add(new ActionLog(action, coords));
                }
            }
            case MOVE_PANDA -> {
                for (int[] coords : Action.possibleMoveForGardenerOrPanda(value.getBoard(), value.getBoard().getPandaCoords())) {
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
            botSimulator.getGestionObjectives().checkObjectives(botSimulator, arg);
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
        while(bestChild.profondeur > 0){
            bestChild = bestChild.getBestChild();
        }
        bestInstruction.add(bestChild.getInstruction());
        while(bestChild.getParent() != null){
            bestChild = bestChild.getParent();
            bestInstruction.add(bestChild.getInstruction());
        }
        return bestInstruction;
    }

    private Node getParent() {
        return parent;
    }

    private ActionLog getInstruction() {
        return instruction;
    }

    private GameState getValue() {
        return value;
    }
}
