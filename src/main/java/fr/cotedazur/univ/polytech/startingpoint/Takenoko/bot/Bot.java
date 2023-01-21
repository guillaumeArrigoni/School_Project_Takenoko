package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.DeletingBotBambooException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
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
    /**
     * The random generator
     */
    protected final Random random;
    /**
     * The meteo dice
     */
    protected final MeteoDice meteoDice;
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
    private AbstractMap <Color,Integer> bambooEaten;


    //CONSTRUCTOR

    /**
     * Constructor of the bot
     * @param name the name of the bot
     * @param board the board of the game
     * @param random the random generator
     * @param meteoDice the meteo dice
     */
    protected Bot(String name, Board board, Random random, MeteoDice meteoDice, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color,Integer> bambooEated) {
        this.name = name;
        this.board = board;
        this.random = random;
        this.meteoDice = meteoDice;
        this.score = 0;
        this.objectives = new ArrayList<>();
        this.gestionObjectives = gestionObjectives;
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.bambooEaten = bambooEated;
        this.bambooEaten.put(Color.Rouge,0);
        this.bambooEaten.put(Color.Jaune,0);
        this.bambooEaten.put(Color.Vert,0);
        resetPossibleAction();
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
    public abstract void playTurn();

    /**
     * This method is called to do an action
     */
    protected abstract void doAction();

    //Gestion Actions possibles
    /**
     * This method choose an action
     */
    protected abstract PossibleActions chooseAction();

    /**
     * This method reset the list of possible actions
     */
    protected abstract void resetPossibleAction();
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
    }
    public abstract void drawObjective();
    public abstract TypeObjective chooseTypeObjectiveToRoll();


    public String getName() {
        return name;
    }

    /**
     * List of all the possible actions
     */
    protected enum PossibleActions {
        DRAW_AND_PUT_TILE(0),
        MOVE_GARDENER(1),
        DRAW_OBJECTIVE(2);

        /**
         * The value of the action
         */
        PossibleActions(int i) {
        }

        /**
         * This method return the list of all the possible actions
         * @return the list of all the possible actions
         */
        static List<PossibleActions> getAllActions() {
            return new ArrayList<>(Arrays.asList(PossibleActions.values()));
        }
    }

    public void addBambooEaten(Color colorAte){
        int nbAte = bambooEaten.get(colorAte) + 1;
        bambooEaten.put(colorAte,nbAte);
    }

    public void deleteBambooEaten(ArrayList<Color> listBambooToDelete) throws DeletingBotBambooException {
        ArrayList<Color> errorImpossibleToDeleteTheseBamboo = new ArrayList<>();
        for (int i=0;i<listBambooToDelete.size();i++){
            int nbBambooOfOneColorAte = bambooEaten.get(listBambooToDelete.get(i));
            if (nbBambooOfOneColorAte>0){
                bambooEaten.put(listBambooToDelete.get(i),nbBambooOfOneColorAte-1);
            } else {
                errorImpossibleToDeleteTheseBamboo.add(listBambooToDelete.get(i));
            }
        }
        if (errorImpossibleToDeleteTheseBamboo.size()!=0){
            throw new DeletingBotBambooException(errorImpossibleToDeleteTheseBamboo);
        }

    }

    public AbstractMap<Color,Integer> getBambooEaten(){
        return this.bambooEaten;
    }

}
