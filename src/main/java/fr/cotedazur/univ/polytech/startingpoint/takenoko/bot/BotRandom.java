package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.pathIrrigation.GenerateAWayToIrrigateTheBox;

import java.util.*;


/**
 * This class is the bot that will play the game
 */
public class BotRandom extends Bot {

    /**
     * The random generator
     */
    protected final Random random;

    public BotRandom(String name, Board board, Random random, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color, Integer> bambooEated, LogInfoDemo logInfoDemo) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated, logInfoDemo);
        this.random = random;
    }


    //METHODS




    @Override
    protected void launchAction(String arg){
        PossibleActions action = chooseAction();
        placeIrrigation(arg);
        displayTextAction(action);
        doAction(arg,action);
    }


    protected PossibleActions chooseAction() {
        PossibleActions acp = possibleActions.get(random.nextInt(possibleActions.size()));
        //Check if the action is possible
        if (isObjectiveIllegal(acp) ||
                (acp != PossibleActions.DRAW_OBJECTIVE && acp != PossibleActions.DRAW_AND_PUT_TILE && acp != PossibleActions.MOVE_GARDENER && acp != PossibleActions.MOVE_PANDA && acp != PossibleActions.TAKE_IRRIGATION))
            return chooseAction();
        possibleActions.remove(acp);
        return acp;
    }


    @Override
    protected void placeTile(String arg) {
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Get all the available coords
        List<int[]> availableTilesList = board.getAvailableBox().stream().toList();
        //Draw three tiles
        for (int i = 0; i < 3; i++)
            list.add(board.getElementOfTheBoard().getStackOfBox().getFirstBox());
        //Choose a random tile from the tiles drawn
        int placedTileIndex = random.nextInt(0, 3);
        HexagoneBox tileToPlace = list.get(placedTileIndex);
        //Choose a random available space
        int[] placedTileCoords = availableTilesList.get(random.nextInt(0, availableTilesList.size()));
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0], placedTileCoords[1], placedTileCoords[2], tileToPlace, retrieveBoxIdWithParameters, board);
        //Add the tile to the board
        board.addBox(placedTile);
        logInfoDemo.addLog(this.name + " a placé une tuile " + tileToPlace.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 2) % 3));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 1) % 3));
    }

    @Override
    protected void moveGardener(String arg) {
        List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords());
        board.setGardenerCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())));
        super.logInfoDemo.displayMovementGardener(this.name,board);
    }

    @Override
    public void movePanda(String arg) {
        List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(board, board.getPandaCoords());
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())), this);
        super.logInfoDemo.displayMovementPanda(this.name,board);
    }

    @Override
    public void growBambooRain(String arg) {
        List<HexagoneBoxPlaced> tmp = new ArrayList<>();
        for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
            if (box.isIrrigate() && box.getHeightBamboo() < 4) {
                tmp.add(box);
            }
        }
        if (!tmp.isEmpty()) {
            HexagoneBoxPlaced box = tmp.get(random.nextInt(0, tmp.size()));
            board.growAfterRain(box);
            logInfoDemo.addLog(this.name + " a fait pousser du bambou grâce à la pluie en " + Arrays.toString(box.getCoordinates()));
        }
    }

    @Override
    public void drawObjective(String arg) {
        int i = random.nextInt(0, 3);
        gestionObjectives.rollObjective(this, arg,i);
    }

    @Override
    public void placeIrrigation(String arg) {
        if (random.nextInt(0, 4) == 0) {
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
            if (!tmp.isEmpty()) {
                temp = tmp.get(random.nextInt(0, tmp.size()));
                for (ArrayList<Crest> path : temp.getPathToIrrigation()) {
                    logInfoDemo.addLog("Le bot a placé une irrigation en " + Arrays.toString(path.get(0).getCoordinates()));
                    board.placeIrrigation(path.get(0));
                    nbIrrigation--;
                }
            }
        }
    }

    protected void placeAugment(String arg) {
        int rdm = random.nextInt(1, 4);
        Special special = null;
        boolean x = board.getElementOfTheBoard().getNbJetonSpecial().get(Special.SourceEau) > 0 ||
                board.getElementOfTheBoard().getNbJetonSpecial().get(Special.Engrais) > 0 ||
                board.getElementOfTheBoard().getNbJetonSpecial().get(Special.Protéger) > 0;
        while (x) {
            switch (rdm) {
                case 1 -> special = Special.SourceEau;
                case 2 -> special = Special.Engrais;
                default -> special = Special.Protéger;
            }
            x = !board.getElementOfTheBoard().pickSpecial(special);
            rdm = ((rdm + 1) % 3) + 1;
        }

        if (special != null) {
            List<HexagoneBoxPlaced> tmp = new ArrayList<>();
            for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
                if (box.getSpecial() == Special.Classique) {
                    tmp.add(box);
                }
            }
            if (!tmp.isEmpty()) {
                HexagoneBoxPlaced box = tmp.get(random.nextInt(0, tmp.size()));
                box.setSpecial(special);
                logInfoDemo.addLog(this.name + " a placé une " + special + " en " + Arrays.toString(box.getCoordinates()));
            }
        }
    }

    public void movePandaStorm() {
        List<int[]> possibleMoves = new ArrayList<>();
        for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
            possibleMoves.add(box.getCoordinates());
        }
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())), this);
        logInfoDemo.addLog(this.name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()) + " grâce à l'orage");
    }
}

