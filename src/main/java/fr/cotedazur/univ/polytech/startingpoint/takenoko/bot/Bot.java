package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.tree.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.TakenokoException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.DeletingBotBambooException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import java.util.*;

/**
 * The `Bot` abstract class represents a bot player in a board game.
 * It includes attributes like the bot's name, score, objectives, and so on,
 * as well as methods for playing a turn, moving the panda, and performing actions.
 */
public abstract class Bot {
    //ATTRIBUTES
    /** The name of the bot */
    protected final String name;
    /** The game board */
    protected final Board board;
    /** The bot's score */
    protected int score;
    /** The bot's panda score */
    protected int scorePanda;
    /** The possible actions that the bot can take */
    protected List<PossibleActions> possibleActions;
    /** The objectives that the bot has */
    protected List<Objective> objectives;
    /** A logger of the game */
    protected LogInfoDemo logInfoDemo;
    /** The number of objectives that the bot has completed */
    protected int numberObjectiveDone;
    /** The number of irrigation tiles the bot has */
    protected int nbIrrigation;
    /** A class that manages the objectives of the game */
    protected final GestionObjectives gestionObjectives;
    /** A class that retrieves the box ID*/
    protected final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    /** A map of the bamboo eaten by color */
    protected final Map<Color, Integer> bambooEaten;


    protected int[] lastCoordGardener;
    protected int[] lastCoordPanda;
    protected int[] lastBoxPlaced;

    public void setLastBoxPlaced(int[] lastBoxPlaced) {
        this.lastBoxPlaced = lastBoxPlaced;
    }

    public void setLastCoordGardener(int[] lastCoordGardener) {
        this.lastCoordGardener = lastCoordGardener;
    }

    public void setLastCoordPanda(int[] lastCoordPanda) {
        this.lastCoordPanda = lastCoordPanda;
    }

    public int[] getLastCoordGardener() {
        return lastCoordGardener;
    }

    public int[] getLastCoordPanda() {
        return lastCoordPanda;
    }

    public int[] getLastBoxPlaced() {
        return lastBoxPlaced;
    }

    //CONSTRUCTOR
    /**
     * Constructs a new `Bot` instance with the given parameters.
     *
     * @param name The name of the bot
     * @param board The game board
     * @param gestionObjectives A class that manages the objectives of the game
     * @param retrieveBoxIdWithParameters A class that retrieves the box ID
     * @param bambooEaten A map of the bamboo eaten by color
     * @param logInfoDemo A logger of the game
     */
    protected Bot(String name, Board board, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color, Integer> bambooEaten, LogInfoDemo logInfoDemo) {
        this.name = name;
        this.board = board;
        this.score = 0;
        this.scorePanda = 0;
        this.objectives = new ArrayList<>();
        this.gestionObjectives = gestionObjectives;
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.bambooEaten = bambooEaten;
        this.bambooEaten.put(Color.ROUGE, 0);
        this.bambooEaten.put(Color.JAUNE, 0);
        this.bambooEaten.put(Color.VERT, 0);
        this.bambooEaten.put(Color.LAC, 0);
        this.nbIrrigation = 0;
        this.logInfoDemo = logInfoDemo;
        this.numberObjectiveDone = 0;
        this.lastBoxPlaced = new int[]{};
        this.lastCoordGardener = new int[]{};
        this.lastCoordPanda = new int[]{};
        resetPossibleAction();
    }

    /**
     * Creates a `BotSimulator` instance for this bot.
     *
     * @param instructions The actions that the bot will take in the simulation
     * @return A `BotSimulator` instance for this bot
     */
    public BotSimulator createBotSimulator(ActionLog ... instructions) {
        Board tmpBoard = new BoardSimulation(this.board,this.board.getElementOfTheBoard(),this.board.getNumberOfPlayers());
        RetrieveBoxIdWithParameters tmp = tmpBoard.getRetrieveBoxIdWithParameters();
        List<ActionLog> instructionsList = new ArrayList<>(List.of(instructions));
        ActionLog inst = null;
        if(!instructionsList.isEmpty())
            inst = instructionsList.get(0);
        return new BotSimulator(this,
                tmpBoard,
                this.gestionObjectives.copy(tmpBoard, tmp),
                new ArrayList<>(this.objectives),
                tmp,
                new EnumMap<>(this.getBambooEaten()),
                inst);
    }

    //ABSTRACT METHODS

    /**
     * Allows the bot to play a turn
     * @param meteo The weather of the turn
     * @param arg  A string argument for the logger
     */
    public abstract void playTurn(MeteoDice.Meteo meteo, String arg);

    /**
     * Allows the bot to initiate an action
     * @param arg A string argument for the logger
     */
    protected abstract void launchAction(String arg);
    /**
     * Allows the bot to place a tile
     * @param arg A string argument for the logger
     */
    protected abstract void placeTile(String arg);

    /**
     * Allows the bot to move the gardener
     * @param arg A string argument for the logger
     */
    protected abstract void moveGardener(String arg);

    /**
     * Allows the bot to move the panda
     * @param arg A string argument for the logger
     */
    protected abstract void movePanda(String arg);
    /**
     * Allows the bot to grow bamboo if the weather is the rain
     * @param arg A string argument for the logger
     */
    protected abstract void growBambooRain(String arg);

    /**
     * Allows the bot to draw an objective
     * @param arg A string argument for the logger
     */
    protected abstract void drawObjective(String arg);

    /**
     * Allows the bot to place an irrigation
     * @param arg A string argument for the logger
     */
    protected abstract void placeIrrigation(String arg);

    /**
     * Allows the bot to place an augment
     * @param arg A string argument for the logger
     */
    protected abstract void placeAugment(String arg);

    //METHODS

    /**
     * Plays a turn for this bot.
     *
     * @param meteo The weather for the turn
     * @param arg A string argument for the logger
     */


    /**
     * Return a TypeObjective based on the number given in parameter
     * @param nb the number of the type of objective
     * @return the type of objective
     */
    public TypeObjective choseTypeObjectiveToRoll(int nb) {
        switch (nb) {
            case 1 -> {
                logInfoDemo.displayPickGardenerObj(this.name);
                return TypeObjective.JARDINIER;
            }
            case 2 -> {
                logInfoDemo.displayPickPandaObj(this.name);
                return TypeObjective.PANDA;
            }
            default -> {
                logInfoDemo.displayPickPatternObj(this.name);
                return TypeObjective.PARCELLE;
            }
        }
    }




    /**
     * Allow the Bot to do an action
     * @param arg A string argument for the logger
     * @param action the action to do
     */
    protected void doAction(String arg,PossibleActions action){
        switch (action){
            case DRAW_AND_PUT_TILE -> placeTile(arg);
            case MOVE_GARDENER -> moveGardener(arg);
            case DRAW_OBJECTIVE -> drawObjective(arg);
            case TAKE_IRRIGATION -> nbIrrigation++;
            case PLACE_IRRIGATION -> placeIrrigation(arg);
            case GROW_BAMBOO -> growBambooRain(arg);
            case ADD_AUGMENT -> placeAugment(arg);
            default -> movePanda(arg);
        }
    }

    /**
     * Allows the bot to log an action
     * @param action the action to log
     */
    protected void displayTextAction(PossibleActions action){
        logInfoDemo.displayTextAction(action);
    }

    /**
     * Add the score of an objective to the score of the bot
     * @param objective the objective to add
     */
    public void addScore(Objective objective) {
        this.score += objective.getValue();
    }

    /**
     * Add the score of a panda objective to the score of the bot
     * @param objective the objective to add
     */
    public void addScorePanda(Objective objective) {
        this.scorePanda += objective.getValue();
    }

    /**
     * Add a bamboo eaten to the bot
     * @param colorAte the color of the bamboo eaten
     */
    public void addBambooEaten(Color colorAte) {
        int nbAte = bambooEaten.get(colorAte) + 1;
        bambooEaten.put(colorAte, nbAte);
    }

    /**
     * Augment the number of objective done by the bot
     */
    public void incrementNumberObjectiveDone() {
        this.numberObjectiveDone++;
    }

    /**
     * Check if an objective is illegal
     * @param actions the action to check
     * @return true if the objective is illegal
     */
    public boolean isObjectiveIllegal(PossibleActions actions) {
        return ((actions == PossibleActions.MOVE_GARDENER && Bot.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords()).isEmpty()) ||
                (actions == PossibleActions.MOVE_PANDA && Bot.possibleMoveForGardenerOrPanda(board, board.getPandaCoords()).isEmpty()) ||
                (actions == PossibleActions.DRAW_OBJECTIVE && objectives.size() >= 5) ||
                (actions == PossibleActions.DRAW_AND_PUT_TILE && board.getElementOfTheBoard().getStackOfBox().size() < 3) ||
                (actions == PossibleActions.DRAW_OBJECTIVE && (gestionObjectives.getParcelleObjectives().isEmpty() || gestionObjectives.getJardinierObjectives().isEmpty() || gestionObjectives.getPandaObjectives().isEmpty())));
    }

    /**
     * Check all the move possible for the gardener or the panda
     * @param board the board
     * @param coord the coord of the gardener or the panda
     * @return a list of all the possible move
     */
    public static List<int[]> possibleMoveForGardenerOrPanda(Board board, int[] coord) {
        int x = coord[0];
        int y = coord[1];
        int z = coord[2];
        ArrayList<int[]> possibleMove = new ArrayList<>();
        boolean possible = true;
        int count = 1;
        int[] newCoord;
        for (int i = 0; i < 6; i++) {
            while (possible) {
                newCoord = switch (i) {
                    case 0 -> new int[]{x, y + count, z - count};
                    case 1 -> new int[]{x, y - count, z + count};
                    case 2 -> new int[]{x + count, y, z - count};
                    case 3 -> new int[]{x - count, y, z + count};
                    case 4 -> new int[]{x - count, y + count, z};
                    case 5 -> new int[]{x + count, y - count, z};
                    default -> new int[]{0, 0, 0};
                };

                if (!board.isCoordinateInBoard(newCoord)) possible = false;
                else {
                    possibleMove.add(newCoord);
                    count++;
                }
            }
            possible = true;
            count = 1;
        }
        return possibleMove;
    }

    /**
     * Delete the bamboo eaten by the bot
     * @param listBambooToDelete the list of bamboo to delete
     * @throws DeletingBotBambooException if the bot try to delete a bamboo that he doesn't have
     */
    public void deleteBambooEaten(List<Color> listBambooToDelete) throws DeletingBotBambooException {
        ArrayList<Color> errorImpossibleToDeleteTheseBamboo = new ArrayList<>();
        for (Color color : listBambooToDelete) {
            int nbBambooOfOneColorAte = bambooEaten.get(color);
            if (nbBambooOfOneColorAte > 0) {
                bambooEaten.put(color, nbBambooOfOneColorAte - 1);
                try {
                    board.getElementOfTheBoard().giveBackBamboo(color);
                } catch (TakenokoException e) {
                    System.err.println("\n  -> An error has occurred : " + e.getErrorTitle() + "\n");
                }
            } else {
                errorImpossibleToDeleteTheseBamboo.add(color);
            }
        }
        if (!errorImpossibleToDeleteTheseBamboo.isEmpty()) {
            throw new DeletingBotBambooException(errorImpossibleToDeleteTheseBamboo);
        }
    }

    /**
     * Reset the possible actions of the bot
     */
    public void resetPossibleAction(){
        possibleActions = PossibleActions.getAllActions();
    }

    //GETTER
    public int getScore() {
        return score;
    }

    public int getScorePanda() {
        return this.scorePanda;
    }

    public Map<Color, Integer> getBambooEaten() {
        return this.bambooEaten;
    }

    public int getNumberObjectiveDone() {
        return numberObjectiveDone;
    }

    public int getNbIrrigation() {
        return nbIrrigation;
    }

    public String getName() {
        return name;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public LogInfoDemo getLogInfoDemo() {
        return logInfoDemo;
    }

    public Board getBoard() {
        return board;
    }

    public GestionObjectives getGestionObjectives() {
        return gestionObjectives;
    }

    //SETTERS
    public void setScore(int score) {
        this.score = score;
    }

    public void setScorePanda(int scorePanda) {
        this.scorePanda = scorePanda;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }
}
