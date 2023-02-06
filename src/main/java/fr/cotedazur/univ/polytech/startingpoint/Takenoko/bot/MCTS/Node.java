package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Action;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int profondeur;
    private Node parent;
    private GameState value;
    private ActionLog instruction;
    private List<Node> children;

    public Node(BotSimulator bot, int profondeur, MeteoDice.Meteo meteo) {
        this.profondeur = profondeur*2;
        this.value = new GameState(bot, meteo);
        this.children = new ArrayList<>();
        this.parent = null;
        this.instruction = null;
        createChildren();
    }

    protected Node(BotSimulator bot, int profondeur, Node parent, MeteoDice.Meteo meteo) {
        this.profondeur = profondeur-1;
        this.value = new GameState(bot, meteo);
        this.children = new ArrayList<>();
        this.parent = parent;
        this.instruction = bot.getInstructions();
    }

    public void createChildren() {
        if(profondeur > 0 && profondeur % 2 == 0) {
            List<ActionLog> firstIntruction = createFirstInstruction();
            for (ActionLog actionLog : firstIntruction) {
                BotSimulator botSimulator = value.getBotSimulator().createBotSimulator(actionLog);
                botSimulator.playTurn(value.getMeteo());
                botSimulator.getGestionObjectives().checkObjectives(botSimulator);
                children.add(new Node(botSimulator, profondeur, this, value.getMeteo()));
            }
        } else if (profondeur > 0) {
            List<ActionLog> secondIntruction = createSecondInstruction(instruction.getAction());
            for (ActionLog actionLog : secondIntruction) {
                BotSimulator botSimulator = value.getBotSimulator().createBotSimulator(actionLog);
                botSimulator.playTurn(value.getMeteo());
                botSimulator.getGestionObjectives().checkObjectives(botSimulator);
                children.add(new Node(botSimulator, profondeur, this, value.getMeteo()));
            }
        }if(profondeur > 1)
            this.getBestChild().createChildren();
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

    private void simulateObjectivePicking(){
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
        BotSimulator botSimulator = value.getBotSimulator().createBotSimulator(actionlog);
        botSimulator.playTurn(value.getMeteo());
        botSimulator.getGestionObjectives().checkObjectives(botSimulator);
        children.add(new Node(botSimulator, profondeur, this, value.getMeteo()));
    }

    private void simulateDrawAndPutTile(){
        ActionLog actionlog;
        actionlog = new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, value.getBoard().getAvailableBox().get(0));
        BotSimulator botSimulator = value.getBotSimulator().createBotSimulator(actionlog);
        botSimulator.playTurn(value.getMeteo());
        botSimulator.getGestionObjectives().checkObjectives(botSimulator);
        children.add(new Node(botSimulator, profondeur, this, value.getMeteo()));
    }



    public Node getBestChild(){
        Node bestChild = null;
        boolean goodChild = false;
        if(!children.isEmpty()) {
            bestChild = children.get(0);
            for (Node child : children) {
                if (child.getValue().getScore() > bestChild.getValue().getScore()) {
                    bestChild = child;
                }
            }
            if(bestChild.getValue().getScore() > this.value.getScore()){
                goodChild = true;
            }
        }
        if(!goodChild && this.getValue().getBotSimulator().getObjectives().size() < 5){
            simulateObjectivePicking();
            if(children.get(children.size()-1).getValue().getScore() >= 0){
                bestChild = children.get(children.size()-1);
                goodChild = true;
            }
        }if(!goodChild){
            simulateDrawAndPutTile();
            if(children.get(children.size()-1).getValue().getScore() >= 0) {
                bestChild = children.get(children.size() - 1);
            }
        }
        return bestChild;
    }

    public List<ActionLog> getBestInstruction(){
        List<ActionLog> bestInstruction = new ArrayList<>();
        Node bestChild = getBestChild();
        while(bestChild.profondeur > 1){
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
