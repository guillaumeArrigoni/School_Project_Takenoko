package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.TakenokoException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.DeletingBotBambooException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import java.util.*;

public abstract class Bot {
    //ATTRIBUTES
    /**
     * Name of the bot
     */
    protected final String name;
    /**
     * The board of the game
     */
    protected final Board board;
    /**
     * The score of the bot
     */
    protected int score;

    protected int scorePanda;

    /**
     * The list of possible actions
     */
    protected List<PossibleActions> possibleActions;
    /**
     * The list of objectives of the bot
     */
    protected List<Objective> objectives;

    protected LogInfoDemo logInfoDemo;
    /**
     *
     */

    protected int numberObjectiveDone;

    protected int nbIrrigation;
    protected final GestionObjectives gestionObjectives;
    protected final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    protected final Map<Color, Integer> bambooEaten;


    //CONSTRUCTOR
    /**
     * Constructor of the bot
     *
     * @param name  the name of the bot
     * @param board the board of the game
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
        this.bambooEaten.put(Color.Rouge, 0);
        this.bambooEaten.put(Color.Jaune, 0);
        this.bambooEaten.put(Color.Vert, 0);
        this.bambooEaten.put(Color.Lac, 0);
        this.nbIrrigation = 0;
        this.logInfoDemo = logInfoDemo;
        this.numberObjectiveDone = 0;
        resetPossibleAction();
    }

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

    /**
     * This method is called at the beginning of the turn
     */
    public void playTurn(MeteoDice.Meteo meteo, String arg){
        possibleActions = PossibleActions.getAllActions();
        this.objectives = getObjectives();
        logInfoDemo.displayTextMeteo(meteo);
        switch (meteo){
            case VENT -> {
                //Deux fois la même action autorisé
                launchAction(arg);
                resetPossibleAction();
                launchAction(arg);
            }
            case PLUIE -> {
                //Le joueur peut faire pousser une tuile irriguée
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

    public TypeObjective choseTypeObjectiveToRoll(String arg, int nb) {
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

    public abstract void movePandaStorm();

    protected abstract void launchAction(String arg);

    /**
     * This method is called to do an action
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

    protected void displayTextAction(PossibleActions action){
        logInfoDemo.displayTextAction(action);
    }

    //ABSTRACT METHODS
    protected abstract void placeTile(String arg);
    protected abstract void moveGardener(String arg);
    protected abstract void movePanda(String arg);
    protected abstract void growBambooRain(String arg);
    protected abstract void drawObjective(String arg);
    protected abstract void placeIrrigation(String arg);
    protected abstract void placeAugment(String arg);

    //METHODS
    public void addScore(Objective objective) {
        this.score += objective.getValue();
    }

    public void addScorePanda(Objective objective) {
        this.scorePanda += objective.getValue();
    }

    public void addBambooEaten(Color colorAte) {
        int nbAte = bambooEaten.get(colorAte) + 1;
        bambooEaten.put(colorAte, nbAte);
    }

    public void incrementNumberObjectiveDone() {
        this.numberObjectiveDone++;
    }

    public boolean isObjectiveIllegal(PossibleActions actions) {
        return ((actions == PossibleActions.MOVE_GARDENER && Bot.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords()).isEmpty()) ||
                (actions == PossibleActions.MOVE_PANDA && Bot.possibleMoveForGardenerOrPanda(board, board.getPandaCoords()).isEmpty()) ||
                (actions == PossibleActions.DRAW_OBJECTIVE && objectives.size() >= 5) ||
                (actions == PossibleActions.DRAW_AND_PUT_TILE && board.getElementOfTheBoard().getStackOfBox().size() < 3) ||
                (actions == PossibleActions.DRAW_OBJECTIVE && (gestionObjectives.getParcelleObjectifs().isEmpty() || gestionObjectives.getJardinierObjectifs().isEmpty() || gestionObjectives.getPandaObjectifs().isEmpty())));
    }

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
                    throw new RuntimeException();
                }
            } else {
                errorImpossibleToDeleteTheseBamboo.add(color);
            }
        }
        if (!errorImpossibleToDeleteTheseBamboo.isEmpty()) {
            throw new DeletingBotBambooException(errorImpossibleToDeleteTheseBamboo);
        }
    }

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
