package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.DeletingBotBambooException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;
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

    /**
     * The list of possible actions
     */
    protected List<PossibleActions> possibleActions;
    /**
     * The list of objectives of the bot
     */
    protected ArrayList<Objective> objectives;
    /**
     *
     */
    public GestionObjectives gestionObjectives;
    public RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private AbstractMap <Color,Integer> bambooEated;


    //CONSTRUCTOR

    /**
     * Constructor of the bot
     * @param name the name of the bot
     * @param board the board of the game
     */
    protected Bot(String name, Board board, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color,Integer> bambooEated) {
        this.name = name;
        this.board = board;
        this.score = 0;
        this.objectives = new ArrayList<>();
        this.gestionObjectives = gestionObjectives;
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.bambooEated = bambooEated;
        this.bambooEated.put(Color.Rouge,0);
        this.bambooEated.put(Color.Jaune,0);
        this.bambooEated.put(Color.Vert,0);
        resetPossibleAction();
    }

    public BotSimulator createBotSimulator(ActionLog instructions){
        RetrieveBoxIdWithParameters tmp = this.retrieveBoxIdWithParameters.copy();
        Board tmpBoard = this.board.copy(tmp);
        return new BotSimulator(this.name,
                tmpBoard,
                this.gestionObjectives.copy(tmpBoard, tmp),
                tmp,
                new HashMap<>(this.getBambooEated()),
                instructions);
    }

    public BotSimulator createBotSimulator(){
        RetrieveBoxIdWithParameters tmp = this.retrieveBoxIdWithParameters.copy();
        Board tmpBoard = this.board.copy(tmp);
        return new BotSimulator(this.name,
                tmpBoard,
                this.gestionObjectives.copy(tmpBoard, tmp),
                tmp,
                new HashMap<>(this.getBambooEated()),
                null);
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
    public abstract void playTurn(MeteoDice.Meteo meteo);

    /**
     * This method is called to do an action
     */
    protected abstract void doAction();

    //Gestion Actions possibles


    protected void resetPossibleAction(){
        possibleActions = PossibleActions.getAllActions();
    }
    /**
     * This method place a tile on the board
     */
    protected abstract void placeTile();
    /**
     * This method move the gardener
     */
    protected abstract void moveGardener();

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

    public void addScore(Objective objective){
        this.score += objective.getValue();
        System.out.println(objective.toString() + ", a été réalisé");
    }
    public abstract void drawObjective();

    public boolean isObjectiveLegal(PossibleActions actions){
        return ((actions == PossibleActions.MOVE_GARDENER &&  Action.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords()).isEmpty()) ||
                (actions == PossibleActions.DRAW_OBJECTIVE && objectives.size() == 5) ||
                (actions == PossibleActions.DRAW_AND_PUT_TILE && board.getCardDeck().size() < 3) ||
                (actions == PossibleActions.DRAW_OBJECTIVE && (gestionObjectives.getParcelleObjectifs().isEmpty() || gestionObjectives.getJardinierObjectifs().isEmpty() || gestionObjectives.getPandaObjectifs().isEmpty())));
    }

    public String getName() {
        return name;
    }

    public void addBambooAte(Color colorAte){
        int nbAte = bambooEated.get(colorAte) + 1;
        bambooEated.put(colorAte,nbAte);
    }

    public void deleteBambooAte (ArrayList<Color> listBambooToDelete) throws DeletingBotBambooException {
        ArrayList<Color> errorImpossibleToDeleteTheseBamboo = new ArrayList<>();
        for (int i=0;i<listBambooToDelete.size();i++){
            int nbBambooOfOneColorAte = bambooEated.get(listBambooToDelete.get(i));
            if (nbBambooOfOneColorAte>0){
                bambooEated.put(listBambooToDelete.get(i),nbBambooOfOneColorAte-1);
            } else {
                errorImpossibleToDeleteTheseBamboo.add(listBambooToDelete.get(i));
            }
        }
        if (errorImpossibleToDeleteTheseBamboo.size()!=0){
            throw new DeletingBotBambooException(errorImpossibleToDeleteTheseBamboo);
        }

    }

    public AbstractMap<Color,Integer> getBambooEated(){
        return this.bambooEated;
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
}
