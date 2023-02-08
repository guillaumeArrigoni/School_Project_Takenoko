package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;

public class BotRuleBased extends Bot {

    int currentScore;
    int irrigationInHand;
    int objectivesInHand;
    Random random;

    public BotRuleBased(String name, Board board, Random random, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color,Integer> bambooEated, LogInfoDemo logInfoDemo) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated,logInfoDemo);
        this.random = random;
        this.irrigationInHand = 0;
        this.objectivesInHand = 0;
        this.currentScore = 0;
    }

    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg) {
        possibleActions = PossibleActions.getAllActions();
        switch (meteo) {
            case VENT:
                if (arg.equals("demo")) System.out.println("Le dé a choisi : VENT");
                if (objectivesInHand < 5 && !(gestionObjectives.getParcelleObjectifs().isEmpty() || gestionObjectives.getJardinierObjectifs().isEmpty() || gestionObjectives.getPandaObjectifs().isEmpty())) {
                    drawObjective(arg);
                }
                else launchAction(arg);
                resetPossibleAction();
                launchAction(arg);
                break;
            case PLUIE:
                if (arg.equals("demo")) System.out.println("Le dé a choisi : PLUIE");
                if (objectivesInHand < 5 && !(gestionObjectives.getParcelleObjectifs().isEmpty() || gestionObjectives.getJardinierObjectifs().isEmpty() || gestionObjectives.getPandaObjectifs().isEmpty())) {
                    drawObjective(arg);
                }
                launchAction(arg);
                break;
        }
        if (this.getScore() > this.currentScore) {
            this.currentScore = this.getScore();
            this.objectivesInHand--;
        }
        resetPossibleAction();
    }

    @Override
    protected void launchAction(String arg) {
        if (choseMoveForPanda() == null) {
            PossibleActions action = chooseAction();
            displayTextAction(action);
            doAction(arg,action);
        } else {
            movePanda(arg);
        }

    }

    protected PossibleActions chooseAction(){
        PossibleActions acp = possibleActions.get(random.nextInt(possibleActions.size()));
        //Check if the action is possible
        if (isObjectiveIllegal(acp))
            return chooseAction();
        possibleActions.remove(acp);
        return acp;
    }

    protected List<int[]> hexagoneBoxWithBamboos() {
        List<int[]> hexagoneBoxWithBamboos = new ArrayList<>();
        for (HexagoneBoxPlaced box : this.board.getAllBoxPlaced()) {
            if (box.getHeightBamboo() != 0) {
                hexagoneBoxWithBamboos.add(box.getCoordinates());
            }
        }
        return hexagoneBoxWithBamboos;
    }

    protected int[] choseMoveForPanda() {
        List<int[]> hexagoneBoxWithBamboos = hexagoneBoxWithBamboos();
        List<int[]> possibleMoves = Action.possibleMoveForGardenerOrPanda(this.board, board.getPandaCoords());
        for (int[] possiblePandaCoords : possibleMoves) {
            for (int[] boxWithBamboos : hexagoneBoxWithBamboos) {
                if (Arrays.equals(possiblePandaCoords, boxWithBamboos)) {
                    return possiblePandaCoords;
                }
            }
        }
        return new int[0];
    }

    protected void doAction(String arg) {
        if (choseMoveForPanda().length == 0) {
            PossibleActions action = chooseAction();
            switch (action) {
                case DRAW_AND_PUT_TILE:
                    if (arg.equals("demo")) System.out.println("Le bot a choisi : PiocherPoserTuile");
                    placeTile(arg);
                    break;
                case MOVE_GARDENER:
                    if (arg.equals("demo")) System.out.println("Le bot a choisi : BougerJardinier");
                    moveGardener(arg);
                    break;
                case DRAW_OBJECTIVE:
                    if (arg.equals("demo")) System.out.println("Le bot a choisi : PiocherObjectif");
                    drawObjective(arg);
                    break;
                case MOVE_PANDA:
                    if (arg.equals("demo")) System.out.println("Le bot a choisi : BougerPanda");
                    movePanda(arg);
                    break;
            }
        }
        else {
            movePanda(arg);
        }
    }

    @Override
    protected void placeTile(String arg){
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Get all the available coords
        List<int[]> availableTilesList = board.getAvailableBox().stream().toList();
        //Draw three tiles
        for(int i = 0; i < 3; i++)
            list.add(board.getElementOfTheBoard().getStackOfBox().getFirstBox());
        //Choose a random tile from the tiles drawn
        int placedTileIndex = random.nextInt(0, 3);
        HexagoneBox tileToPlace = list.get(placedTileIndex);
        //Choose a random available space
        int[] placedTileCoords = availableTilesList.get(random.nextInt(0, availableTilesList.size()));
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0],placedTileCoords[1],placedTileCoords[2],tileToPlace,retrieveBoxIdWithParameters,board);
        //Add the tile to the board
        board.addBox(placedTile);
        if (arg.equals("demo")) System.out.println(this.name + " a placé une tuile " + tileToPlace.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 1) % 3));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 2) % 3));

    }

    @Override
    protected void moveGardener(String arg){
        List<int[]> possibleMoves = Action.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords());
        board.setGardenerCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())));
        if (arg.equals("demo")) System.out.println(this.name + " a déplacé le jardinier en " + Arrays.toString(board.getGardenerCoords()));
    }

    @Override
    public void drawObjective(String arg){
        gestionObjectives.rollObjective(this, arg);
        this.objectivesInHand++;
        this.possibleActions.remove(PossibleActions.DRAW_OBJECTIVE);
    }

    @Override
    public TypeObjective choseTypeObjectiveToRoll(String arg){
        int i = random.nextInt(0,3);
        switch (i) {
            case 1 -> {
                if (arg.equals("demo")) System.out.println("Le bot a choisi : Piocher un objectif de jardinier");
                return TypeObjective.JARDINIER;
            }
            case 2 -> {
                if (arg.equals("demo")) System.out.println("Le bot a choisi : Piocher un objectif de panda");
                return TypeObjective.PANDA;
            }
            default -> {
                if (arg.equals("demo")) System.out.println("Le bot a choisi : Piocher un objectif de parcelle");
                return TypeObjective.PARCELLE;
            }
        }
    }

    @Override
    public void movePanda(String arg){
        if (choseMoveForPanda() != null) {
            board.setPandaCoords(choseMoveForPanda(),this);
        }
        else {
            List<int[]> possibleMoves = Action.possibleMoveForGardenerOrPanda(board, board.getPandaCoords());
            board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())),this);
        }
        logInfoDemo.displayMovementPanda(arg,board);
    }
}
