package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs.Objectives;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot.ActionPossible.BougerJardinier;

/**
 * This class is the bot that will play the game
 */
public class Bot {

    //ATTRIBUTES
    /**
     * Name of the bot
     */
    private List<ActionPossible> actionPossibles;
    private final String name;
    private final Board board;
    private final Random random;
    private int score;
    private ArrayList<Objectives> objectives;
    private final MeteoDice meteoDice;

    //CONSTRUCTOR
    /**
     * Constructor of the bot
     * @param name : name of the bot
     */
    public Bot(String name, Board board, Random random, MeteoDice meteoDice) {
        this.name = name;
        this.board = board;
        this.random = random;
        this.meteoDice = meteoDice;
        this.score = 0;
        this.objectives = new ArrayList<>();
        resetActionsPossible();
    }

    //METHODS
    //Joueur fait les dés;
    //fait action 2 actions
    //placertuile
    //prendre et poser s'il veut une irrigation
    //Déplacer le jardinier
    //Déplacer le panda
    //piocher une carte objectif

    public void playTurn(){
        actionPossibles = ActionPossible.getAllActions();
        switch (meteoDice.roll()){
            case VENT -> {
                //Deux fois la même action autorisé
                System.out.println("Le dé a choisi : VENT");
                faireActionAleatoire();
                resetActionsPossible();
                faireActionAleatoire();
            }
            case PLUIE -> {
                //Le joueur peut faire pousser une tuile irriguée
                //TODO c pas implémenté dans la classe hexagoneBox
                System.out.println("Le dé a choisi : PLUIE");
                faireActionAleatoire();
                faireActionAleatoire();
            }


        }
    }

    private void faireActionAleatoire(){
        ActionPossible action = choisirActionAleatoire();
        switch (action){
            case PiocherPoserTuile:
                System.out.println("Le bot a choisi : PiocherPoserTuile");
                placeRandomTile();
                break;
            case BougerJardinier:
                System.out.println("Le bot a choisi : BougerJardinier");
                moveGardenerRandomly();
                break;
        }

    }

    //Gestion Actions possibles
    private ActionPossible choisirActionAleatoire(){
        ActionPossible acp = actionPossibles.get(random.nextInt(actionPossibles.size()));
        if (acp == BougerJardinier &&  Action.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords()).isEmpty())
            return choisirActionAleatoire();
        actionPossibles.remove(acp);
        return acp;
    }
    private void resetActionsPossible(){
        actionPossibles = ActionPossible.getAllActions();
    }

    //Actions
    /**
     * This method is used to place a random tile on the board
     */
    protected void placeRandomTile(){
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Get all the available coords
        List<int[]> availableTilesList = board.getAvailableBox().stream().toList();
        //Draw three tiles
        for(int i = 0; i < 3; i++)
            list.add(Action.drawTile(random));
        //Choose a random tile from the tiles drawn
        HexagoneBox placedTile = list.get(random.nextInt(0, 3));
        //Choose a random available space
        int[] placedTileCoords = availableTilesList.get(random.nextInt(0, availableTilesList.size()));
        //Set the coords of the tile
        placedTile.setCoordinates(placedTileCoords);
        //Add the tile to the board
        board.addBox(placedTile);
        System.out.println(this.name + " a placé une tuile " + placedTile.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
    }

    /**
     * This method move the gardener randomly on the board
     */
    private void moveGardenerRandomly(){
        List<int[]> possibleMoves = Action.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords());
        board.setGardenerCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())));
        System.out.println(this.name + " a déplacé le jardinier en " + Arrays.toString(board.getGardenerCoords()));
    }


    //Enum ActionPossibles
    enum ActionPossible{
        PiocherPoserTuile (1),
        BougerJardinier (2);

        ActionPossible(int i) {
        }

        //Return a non immutable list of all the actions possible
        private static List<ActionPossible> getAllActions(){
            return new ArrayList<>(Arrays.asList(ActionPossible.values()));
        }

        private static List<ActionPossible> getActionsPossible(){
            return List.of(ActionPossible.values());
        }
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Objectives> getObjectives() {
        return objectives;
    }
}

