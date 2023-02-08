package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation.GenerateAWayToIrrigateTheBox;

import java.util.*;
//TODO : changer car class action n'existe plus'

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
        this.objectives = getObjectives();
        System.out.println();
        switch (meteo) {
            case VENT:
                //2 fois la même action possible
                if (arg.equals("demo")) System.out.println("Le dé a choisi : VENT");
                launchAction(arg);
                resetPossibleAction();
                launchAction(arg);
                break;
            case PLUIE:
                //peut faire pousser sur une parcelle irriguée
                if (arg.equals("demo")) System.out.println("Le dé a choisi : PLUIE");
                growBambooRain();
                launchAction(arg);
                launchAction(arg);
                break;
            case NUAGES:
                //peut prendre un aménagement
                if (arg.equals("demo")) System.out.println("Le dé a choisi : PLUIE");
                //TODO
                launchAction(arg);
                launchAction(arg);
                break;
            case ORAGE:
                //peut placer le panda n'importe où et manger un bambou
                movePandaStorm();
                launchAction(arg);
                launchAction(arg);
                break;
            case SOLEIL:
                //3 actions possible
                launchAction(arg);
                launchAction(arg);
                launchAction(arg);
                break;
            case HASARD:

        }
        if (this.getScore() > this.currentScore) {
            this.currentScore = this.getScore();
            this.objectivesInHand--;
        }
        resetPossibleAction();
    }

    protected void launchAction(String arg) {
        if (objectivesInHand < 5 && !(gestionObjectives.getParcelleObjectifs().isEmpty() || gestionObjectives.getJardinierObjectifs().isEmpty() || gestionObjectives.getPandaObjectifs().isEmpty())) {
            drawObjective(arg);
        }
        else if (choseMoveForPanda() == null) {
            doAction(arg);
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

    protected List<HexagoneBoxPlaced> hexagoneBoxWithBamboos() {
        List<HexagoneBoxPlaced> hexagoneBoxWithBamboos = new ArrayList<>();
        for (HexagoneBoxPlaced box : this.board.getAllBoxPlaced()) {
            if (box.getHeightBamboo() != 0) {
                hexagoneBoxWithBamboos.add(box);
            }
        }
        return hexagoneBoxWithBamboos;
    }

    protected int[] choseMoveForPanda() {
        List<HexagoneBoxPlaced> hexagoneBoxWithBamboos = hexagoneBoxWithBamboos();
        List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(this.board, board.getPandaCoords());
        for (int[] possiblePandaCoords : possibleMoves) {
            for (HexagoneBoxPlaced boxWithBamboos : hexagoneBoxWithBamboos) {
                if (Arrays.equals(possiblePandaCoords, boxWithBamboos.getCoordinates())) {
                    return possiblePandaCoords;
                }
            }
        }
        return new int[0];
    }

    protected void doAction(String arg) {
        if (choseMoveForPanda().length == 0) {
            PossibleActions action = chooseAction();
            logInfoDemo.displayTextAction(action);
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
                case TAKE_IRRIGATION:
                    if (arg.equals("demo")) System.out.println("Le bot a choisi : PrendreIrrigation");
                    this.nbIrrigation++;
                    placeIrrigation();
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
        List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords());
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
            List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(board, board.getPandaCoords());
            board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())),this);
        }
        logInfoDemo.displayMovementPanda(arg,board);
    }


    protected void placeIrrigation() {
        if(random.nextInt(0,2) == 0) {
            List<GenerateAWayToIrrigateTheBox> tmp = new ArrayList<>();
            GenerateAWayToIrrigateTheBox temp;
            for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
                if (!box.isIrrigate()) {
                    try {
                        temp = new GenerateAWayToIrrigateTheBox(box);
                        if (temp.getPathToIrrigation().size() <= this.nbIrrigation)
                            tmp.add(temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(!tmp.isEmpty()) {
                temp = tmp.get(random.nextInt(0, tmp.size()));
                for (ArrayList<Crest> path : temp.getPathToIrrigation()) {
                    System.out.println("Le bot a placé une irrigation en " + Arrays.toString(path.get(0).getCoordinates()));
                    board.placeIrrigation(path.get(0));
                    nbIrrigation--;
                }
            }
        }
    }

    protected ArrayList<Objective> getObjectivesOfType(TypeObjective typeObj) {
        ArrayList<Objective> objectivesOfType = new ArrayList<>();
        for (Objective obj : this.objectives) {
            if (obj.getType().equals(typeObj)) {
                objectivesOfType.add(obj);
            }
        }
        return objectivesOfType;
    }


    public int movePandaStorm() {
        List<Objective> pandaObjectives = getObjectivesOfType(TypeObjective.PANDA);
        List<HexagoneBoxPlaced> boxWithBamboos = hexagoneBoxWithBamboos();
        if (!pandaObjectives.isEmpty()) {
            for (HexagoneBoxPlaced box : boxWithBamboos) {
                for (Objective obj : pandaObjectives) {
                    for (Color c : obj.getColors()) {
                        if (box.getColor().equals(c) && box.getHeightBamboo() > 0 && this.bambooEaten.get(c) < obj.getPattern().getHauteurBambou()) {
                            board.setPandaCoords(box.getCoordinates(), this);
                            return 0;
                        }
                    }
                }
            }
        }
        else if (!boxWithBamboos.isEmpty()) {
            board.setPandaCoords(boxWithBamboos.get(random.nextInt(0, boxWithBamboos.size())).getCoordinates(),this);
        }
        return 0;
    }

    public HexagoneBoxPlaced getHighestBamboosOfColor(Color color) {
        int highest = 0;
        HexagoneBoxPlaced highestBox = null;
        for (HexagoneBoxPlaced box : this.board.getAllBoxPlaced()) {
            if (box.getColor().equals(color) && box.getHeightBamboo() < 4 && box.getHeightBamboo() > highest) {
                highest = box.getHeightBamboo();
                highestBox = box;
            }
        }
        return highestBox;
    }

    public int growBambooRain() {
        List<Objective> gardenerObj = getObjectivesOfType(TypeObjective.JARDINIER);
        for (Objective obj : gardenerObj) {
            for (Color c : obj.getColors()) {
                HexagoneBoxPlaced box = getHighestBamboosOfColor(c);
                if (box != null) {
                    box.growBamboo();
                    return 0;
                }
            }
        }
        List<HexagoneBoxPlaced> allBox = this.board.getAllBoxPlaced();
        if (!allBox.isEmpty()) {
            HexagoneBoxPlaced box = allBox.get(random.nextInt(0,allBox.size()));
            box.growBamboo();
        }
        return 0;
    }

}


