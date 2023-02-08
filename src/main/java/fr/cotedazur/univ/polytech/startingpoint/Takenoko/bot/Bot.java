package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
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
    protected ArrayList<Objective> objectives;

    protected LogInfoDemo logInfoDemo;
    /**
     *
     */

    protected int numberObjectiveDone;

    protected int nbIrrigation;
    protected final GestionObjectives gestionObjectives;
    protected final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    protected final Map <Color,Integer> bambooEaten;



    //CONSTRUCTOR

    /**
     * Constructor of the bot
     * @param name the name of the bot
     * @param board the board of the game
     */
    protected Bot(String name, Board board, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color,Integer> bambooEaten, LogInfoDemo logInfoDemo) {
        this.name = name;
        this.board = board;
        this.score = 0;
        this.scorePanda = 0;
        this.objectives = new ArrayList<>();
        this.gestionObjectives = gestionObjectives;
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.bambooEaten = bambooEaten;
        this.bambooEaten.put(Color.Rouge,0);
        this.bambooEaten.put(Color.Jaune,0);
        this.bambooEaten.put(Color.Vert,0);
        this.bambooEaten.put(Color.Lac,0);
        this.nbIrrigation = 0;
        this.logInfoDemo= logInfoDemo;
        this.numberObjectiveDone = 0;
        resetPossibleAction();
    }

    public BotSimulator createBotSimulator(ActionLog instructions){
        RetrieveBoxIdWithParameters tmp = this.retrieveBoxIdWithParameters.copy();
        Board tmpBoard = this.board.copy(tmp);
        return new BotSimulator(this.name,
                tmpBoard,
                this.gestionObjectives.copy(tmpBoard, tmp),
                new ArrayList<>(this.objectives),
                tmp,
                new HashMap<>(this.getBambooEaten()),
                instructions,
                this.nbIrrigation,
                this.logInfoDemo,
                this.numberObjectiveDone);
    }

    public BotSimulator createBotSimulator(){
        RetrieveBoxIdWithParameters tmp = this.retrieveBoxIdWithParameters.copy();
        Board tmpBoard = this.board.copy(tmp);
        return new BotSimulator(this.name,
                tmpBoard,
                this.gestionObjectives.copy(tmpBoard, tmp),
                new ArrayList<>(this.objectives),
                tmp,
                new HashMap<>(this.getBambooEaten()),
                null,
                this.nbIrrigation,
                this.logInfoDemo,
                this.numberObjectiveDone);
    }

    //METHODS
    //Joueur fait les dés;
    //fait action 2 actions
    //placertuile
    //prendre et poser s'il veut une irrigation
    //Déplacer le jardinier
    //Déplacer le panda
    //piocher une carte objectif


    /**
     * This method is called at the beginning of the turn
     */
    public abstract void playTurn(MeteoDice.Meteo meteo, String arg);

    /**
     * This method is called to do an action
     */
    protected abstract void doAction(String arg);

    //Gestion Actions possibles


    protected void resetPossibleAction(){
        possibleActions = PossibleActions.getAllActions();
    }
    /**
     * This method place a tile on the board
     */
    protected abstract void placeTile(String arg);
    /**
     * This method move the gardener
     */
    protected abstract void moveGardener(String arg);

    protected abstract void movePanda(String arg);




    //Score and objectives
    public int getScore() {
        return score;
    }

    public ArrayList<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(ArrayList<Objective> objectives) {
        this.objectives = objectives;
    }

    public void addScore(Objective objective, String arg){
        this.score += objective.getValue();
    }
    public void addScorePanda(Objective objective){
        this.scorePanda += objective.getValue();
    }
    public abstract void drawObjective(String arg);

    public boolean isObjectiveIllegal(PossibleActions actions){
        return ((actions == PossibleActions.MOVE_GARDENER &&  Bot.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords()).isEmpty()) ||
                (actions == PossibleActions.MOVE_PANDA && Bot.possibleMoveForGardenerOrPanda(board,board.getPandaCoords()).isEmpty()) ||
                (actions == PossibleActions.DRAW_OBJECTIVE && objectives.size() == 5) ||
                (actions == PossibleActions.DRAW_AND_PUT_TILE && board.getElementOfTheBoard().getStackOfBox().size() < 3) ||
                (actions == PossibleActions.DRAW_OBJECTIVE && (gestionObjectives.getParcelleObjectifs().isEmpty() || gestionObjectives.getJardinierObjectifs().isEmpty() || gestionObjectives.getPandaObjectifs().isEmpty())) ||
                (actions == PossibleActions.PLACE_IRRIGATION));
    }


    public static ArrayList<int[]> possibleMoveForGardenerOrPanda(Board board, int[] coord) {
        int x = coord[0];
        int y = coord[1];
        int z = coord[2];
        ArrayList<int[]> possibleMove = new ArrayList<>();
        boolean possible = true;
        int count = 1;
        int[] newCoord;
        for (int i=0;i<6;i++) {
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

                if (!board.isCoordinateInBoard(newCoord)) possible=false;
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

    public String getName() {
        return name;
    }
    public int getScorePanda() {
        return this.scorePanda;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScorePanda(int scorePanda) {
        this.scorePanda = scorePanda;
    }

    public void addBambooEaten(Color colorAte){
        int nbAte = bambooEaten.get(colorAte) + 1;
        bambooEaten.put(colorAte,nbAte);
    }

    public void deleteBambooEaten(ArrayList<Color> listBambooToDelete) throws DeletingBotBambooException {
        ArrayList<Color> errorImpossibleToDeleteTheseBamboo = new ArrayList<>();
        for (Color color : listBambooToDelete){
            int nbBambooOfOneColorAte = bambooEaten.get(color);
            if (nbBambooOfOneColorAte>0){
                bambooEaten.put(color,nbBambooOfOneColorAte-1);
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
        if (errorImpossibleToDeleteTheseBamboo.size()!=0){
            throw new DeletingBotBambooException(errorImpossibleToDeleteTheseBamboo);
        }

    }

    public Map<Color,Integer> getBambooEaten(){
        return this.bambooEaten;
    }

    public Board getBoard() {
        return board;
    }

    public List<PossibleActions> getPossibleActions() {
        return possibleActions;
    }

    public GestionObjectives getGestionObjectives() {
        return gestionObjectives;
    }

    public RetrieveBoxIdWithParameters getRetrieveBoxIdWithParameters() {
        return retrieveBoxIdWithParameters;
    }

    public LogInfoDemo getLogInfoDemo() {
        return logInfoDemo;
    }

    public void IncrementNumberObjectiveDone() {
        this.numberObjectiveDone++;
    }

    public int getNumberObjectiveDone() {
        return numberObjectiveDone;
    }

    public int getNbIrrigation() {
        return nbIrrigation;
    }

}
