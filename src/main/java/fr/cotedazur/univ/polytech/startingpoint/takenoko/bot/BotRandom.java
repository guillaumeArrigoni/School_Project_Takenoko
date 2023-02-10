package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.TakenokoException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.pathirrigation.GenerateAWayToIrrigateTheBox;

import java.util.*;

/**
 * The `BotRandom` class represents a random bot player in a game.
 * It implements the abstract methods from the `Bot` class to make decisions and perform actions in the game.
 */
public class BotRandom extends Bot {

    /**
     * The random generator
     */
    protected final Random random;

    /**
     * Constructs a new `BotRandom` instance with the given parameters.
     *
     * @param name The name of the bot
     * @param board The game board
     * @param gestionObjectives A class that manages the objectives of the game
     * @param retrieveBoxIdWithParameters A class that retrieves the box ID
     * @param bambooEaten A map of the bamboo eaten by color
     * @param logInfoDemo A logger of the game
     * @param random The random generator
     */
    public BotRandom(String name, Board board, Random random, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color, Integer> bambooEaten, LogInfoDemo logInfoDemo) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEaten, logInfoDemo);
        this.random = random;
    }


    //METHODS

    /**
     * Launches the action of the bot.
     * @param arg A string argument for the logger
     */
    @Override
    protected void launchAction(String arg){
        PossibleActions action = chooseAction();
        placeIrrigationBase(arg);
        displayTextAction(action);
        doAction(arg,action);
    }

    /**
     * Chooses a legal action randomly.
     * @return The action chosen
     */
    protected PossibleActions chooseAction() {
        PossibleActions acp = possibleActions.get(random.nextInt(possibleActions.size()));
        //Check if the action is possible
        if (isObjectiveIllegal(acp) ||
                (acp != PossibleActions.DRAW_OBJECTIVE && acp != PossibleActions.DRAW_AND_PUT_TILE && acp != PossibleActions.MOVE_GARDENER && acp != PossibleActions.MOVE_PANDA && acp != PossibleActions.TAKE_IRRIGATION))
            return chooseAction();
        possibleActions.remove(acp);
        return acp;
    }

    /**
     * Allow the bot to place a tile on the board.
     * @param arg A string argument for the logger
     */
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
        board.addBox(placedTile,this);
        logInfoDemo.addLog(this.name + " a placé une tuile " + tileToPlace.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 2) % 3));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 1) % 3));
    }

    /**
     * Allow the bot to move the gardener
     * @param arg A string argument for the logger
     */
    @Override
    protected void moveGardener(String arg) {
        List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords());
        board.setGardenerCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())),this);
        super.logInfoDemo.displayMovementGardener(this.name,board);
    }

    /**
     * Allow the bot to move the panda
     * @param arg A string argument for the logger
     */
    @Override
    public void movePanda(String arg) {
        List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(board, board.getPandaCoords());
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())), this);
        super.logInfoDemo.displayMovementPanda(this.name,board);
    }

    /**
     * Allow the bot grow bamboo after during the rain.
     * @param arg A string argument for the logger
     */
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

    /**
     * Allow the bot to draw an objective
     * @param arg A string argument for the logger
     */
    @Override
    public void drawObjective(String arg) {
        int i = random.nextInt(0, 3);
        gestionObjectives.rollObjective(this, arg,i);
    }

    /**
     * Allow the bot to place an irrigation
     * @param arg A string argument for the logger
     */
    @Override
    public void placeIrrigationBase(String arg) {
        if (random.nextInt(0, 4) == 0) {
            List<GenerateAWayToIrrigateTheBox> tmp = new ArrayList<>();
            GenerateAWayToIrrigateTheBox temp;
            for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
                if (!box.isIrrigate()) {
                    try {
                        temp = new GenerateAWayToIrrigateTheBox(box);
                        if (temp.getPathToIrrigation().size() <= this.nbIrrigation)
                            tmp.add(temp);
                    } catch (TakenokoException | CloneNotSupportedException e) {
                        new LoggerSevere(true).logErrorTitle(e);
                    }
                }
            }
            placeIrrigation(tmp);
        }
    }

    private void placeIrrigation(List<GenerateAWayToIrrigateTheBox> tmp) {
        GenerateAWayToIrrigateTheBox temp;
        if (!tmp.isEmpty()) {
            temp = tmp.get(random.nextInt(0, tmp.size()));
            for (ArrayList<Crest> path : temp.getPathToIrrigation()) {
                logInfoDemo.addLog("Le bot a placé une irrigation en " + Arrays.toString(path.get(0).getCoordinates()));
                board.placeIrrigation(path.get(0));
                nbIrrigation--;
            }
        }
    }

    /**
     * Allow the bot to place an augment
     * @param arg A string argument for the logger
     */
    protected void placeAugment(String arg) {
        int rdm = random.nextInt(1, 4);
        Special special = null;
        boolean x = board.getElementOfTheBoard().getNbJetonSpecial().get(Special.SOURCE_EAU) > 0 ||
                board.getElementOfTheBoard().getNbJetonSpecial().get(Special.ENGRAIS) > 0 ||
                board.getElementOfTheBoard().getNbJetonSpecial().get(Special.PROTEGER) > 0;
        while (x) {
            switch (rdm) {
                case 1 -> special = Special.SOURCE_EAU;
                case 2 -> special = Special.ENGRAIS;
                default -> special = Special.PROTEGER;
            }
            x = !board.getElementOfTheBoard().pickSpecial(special);
            rdm = ((rdm + 1) % 3) + 1;
        }

        if (special != null) {
            List<HexagoneBoxPlaced> tmp = new ArrayList<>();
            for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
                if (box.getSpecial() == Special.CLASSIQUE) {
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

    /**
     * Allow the bot to move the panda during the storm
     */
    public void movePandaStorm() {
        List<int[]> possibleMoves = new ArrayList<>();
        for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
            possibleMoves.add(box.getCoordinates());
        }
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())), this);
        logInfoDemo.addLog(this.name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()) + " grâce à l'orage");
    }

    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg){
        possibleActions = PossibleActions.getAllActions();
        this.objectives = getObjectives();
        logInfoDemo.displayTextMeteo(meteo);
        switch (meteo){
            case VENT -> {
                launchAction(arg);
                resetPossibleAction();
                launchAction(arg);
            }
            case PLUIE -> {
                growBambooRain(arg);
                launchAction(arg);
                launchAction(arg);
            }
            case NUAGES -> {
                placeAugment(arg);
                launchAction(arg);
                launchAction(arg);
            }
            case ORAGE -> {
                movePandaStorm();
                launchAction(arg);
                launchAction(arg);
            }
            default/*SOLEIL*/ -> {
                launchAction(arg);
                launchAction(arg);
                launchAction(arg);
            }
        }
    }
}

