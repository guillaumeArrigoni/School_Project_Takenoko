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

    public BotRandom(String name, Board board, Random random, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color,Integer> bambooEated) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated);
        this.random = random;
    }




    @Override
    public void playTurn(MeteoDice.Meteo meteo){
        possibleActions = PossibleActions.getAllActions();
        switch (meteo){
            case VENT -> {
                //Deux fois la même action autorisé
                System.out.println("Le dé a choisi : VENT");
                doAction();
                resetPossibleAction();
                doAction();
            }
            case PLUIE -> {
                //Le joueur peut faire pousser une tuile irriguée
                //TODO c pas implémenté dans la classe hexagoneBox
                System.out.println("Le dé a choisi : PLUIE");
                doAction();
                doAction();
            }
            case NUAGES -> {
                System.out.println("Le dé a choisi : NUAGES");
                //TODO 
                doAction();
                doAction();
            }
            case ORAGE -> {
                System.out.println("Le dé a choisi : ORAGE");
                movePandaStorm();
                doAction();
                doAction();
            }
            default/*SOLEIL*/ -> {
                System.out.println("Le dé a choisi : SOLEIL");
                doAction();
                doAction();
                doAction();
            }


        }
    }

    @Override
    protected void doAction(){
        PossibleActions action = chooseAction();
        switch (action) {
            case DRAW_AND_PUT_TILE -> {
                System.out.println("Le bot a choisi : PiocherPoserTuile");
                placeTile();
            }
            case MOVE_GARDENER -> {
                System.out.println("Le bot a choisi : BougerJardinier");
                moveGardener();
            }
            case DRAW_OBJECTIVE -> {
                System.out.println("Le bot a choisi : PiocherObjectif");
                drawObjective();
            }
            case MOVE_PANDA -> {
                System.out.println("Le bot a choisi : BougerPanda");
                movePanda();
            }
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
    protected void placeTile(){
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
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 1) % 3));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 2) % 3));
        System.out.println(this.name + " a placé une tuile " + placedTile.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
    }

    @Override
    protected void moveGardener(){
        List<int[]> possibleMoves = Action.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords());
        board.setGardenerCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())));
        System.out.println(this.name + " a déplacé le jardinier en " + Arrays.toString(board.getGardenerCoords()));
    }

    @Override
    public void drawObjective(){
        gestionObjectives.rollObjective(this);
    }

    @Override
    public void movePanda(){
        List<int[]> possibleMoves = Action.possibleMoveForGardenerOrPanda(board, board.getPandaCoords());
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())),this);
        System.out.println(this.name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()));
    }

    @Override
    public void movePandaStorm(){
        List<int[]> possibleMoves = new ArrayList<>();
        for(HexagoneBoxPlaced box : board.getPlacedBox().values()){
            possibleMoves.add(box.getCoordinates());
        }
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())),this);
        System.out.println(this.name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()) + " grâce à l'orage");
    }



    public TypeObjective chooseTypeObjectiveToRoll(){
        int i = random.nextInt(0,3) ;
        switch (i) {
            case 1 -> {
                System.out.println("Le bot a choisi : Piocher un objectif de jardinier");
                return TypeObjective.JARDINIER;
            }
            case 2 -> {
                System.out.println("Le bot a choisi : Piocher un objectif de panda");
                return TypeObjective.PANDA;
            }
            default -> {
                System.out.println("Le bot a choisi : Piocher un objectif de parcelle");
                return TypeObjective.PARCELLE;
            }
        }
    }

}

