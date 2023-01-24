package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
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

    public BotRandom(String name, Board board, Random random, MeteoDice meteoDice, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color,Integer> bambooEated) {
        super(name, board, random, meteoDice, gestionObjectives, retrieveBoxIdWithParameters, bambooEated);
    }


    @Override
    public void playTurn(){
        possibleActions = PossibleActions.getAllActions();
        switch (meteoDice.roll()){
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


        }
    }

    @Override
    protected void doAction(){
        PossibleActions action = chooseAction();
        switch (action){
            case DRAW_AND_PUT_TILE:
                System.out.println("Le bot a choisi : PiocherPoserTuile");
                placeTile();
                break;
            case MOVE_GARDENER:
                System.out.println("Le bot a choisi : BougerJardinier");
                moveGardener();
                break;
            case DRAW_OBJECTIVE:
                System.out.println("Le bot a choisi : PiocherObjectif");
                drawObjective();
                break;
        }

    }

    @Override
    protected PossibleActions chooseAction(){
        PossibleActions acp = possibleActions.get(random.nextInt(possibleActions.size()));
        //Check if the action is possible
        if ((acp == PossibleActions.MOVE_GARDENER &&  Action.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords()).isEmpty()) ||
                (acp == PossibleActions.DRAW_OBJECTIVE && objectives.size() == 5))
            return chooseAction();
        possibleActions.remove(acp);
        return acp;
    }
    @Override
    protected void resetPossibleAction(){
        possibleActions = PossibleActions.getAllActions();
    }

    @Override
    protected void placeTile(){
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Get all the available coords
        List<int[]> availableTilesList = board.getAvailableBox().stream().toList();
        //Draw three tiles
        for(int i = 0; i < 3; i++)
            list.add(Action.drawTile(random, retrieveBoxIdWithParameters,board));
        //Choose a random tile from the tiles drawn
        HexagoneBox tileToPlace = list.get(random.nextInt(0, 3));
        //Choose a random available space
        int[] placedTileCoords = availableTilesList.get(random.nextInt(0, availableTilesList.size()));
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0],placedTileCoords[1],placedTileCoords[2],tileToPlace,retrieveBoxIdWithParameters,board);
        //Add the tile to the board
        board.addBox(placedTile);
        System.out.println(this.name + " a placé une tuile " + tileToPlace.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
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
    public TypeObjective chooseTypeObjectiveToRoll(){
        int i = random.nextInt(0,3) ;
        return switch (i){
            case 0 -> TypeObjective.PARCELLE;
            case 1 -> TypeObjective.JARDINIER;
            case 2 -> TypeObjective.PANDA;
            default -> TypeObjective.PARCELLE;
        };
    }


}

