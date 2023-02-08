package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;


/**
 * This class is the bot that will play the game
 */
public class BotRandom extends Bot {

    /**
     * The random generator
     */
    private final Random random;

    public BotRandom(String name, Board board, Random random, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color,Integer> bambooEated) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated);
        this.random = random;
    }




    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg){
        possibleActions = PossibleActions.getAllActions();
        switch (meteo){
            case VENT -> {
                //Deux fois la même action autorisé
                if (arg.equals("demo")) System.out.println("Le dé a choisi : VENT");
                doAction(arg);
                resetPossibleAction();
                doAction(arg);
            }
            case PLUIE -> {
                //Le joueur peut faire pousser une tuile irriguée
                //TODO c pas implémenté dans la classe hexagoneBox
                if (arg.equals("demo")) System.out.println("Le dé a choisi : PLUIE");
                doAction(arg);
                doAction(arg);
            }

        }
    }

    @Override
    protected void doAction(String arg){
        PossibleActions action = chooseAction();
        switch (action){
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



    protected PossibleActions chooseAction(){
        PossibleActions acp = possibleActions.get(random.nextInt(possibleActions.size()));
        //Check if the action is possible
        if (isObjectiveIllegal(acp))
            return chooseAction();
        possibleActions.remove(acp);
        return acp;
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
    }

    @Override
    public void movePanda(String arg){
        List<int[]> possibleMoves = Action.possibleMoveForGardenerOrPanda(board, board.getPandaCoords());
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())),this);
        if (arg.equals("demo")) System.out.println(this.name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()));
    }

    @Override
    public TypeObjective choseTypeObjectiveToRoll(String arg){
        int i = random.nextInt(0,3) ;
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

}

