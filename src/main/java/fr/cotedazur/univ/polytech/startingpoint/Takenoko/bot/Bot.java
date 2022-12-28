package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs.Objectives;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * This class is the bot that will play the game
 */
public class Bot {
    /**
     * Name of the bot
     */
    private final String name;
    private final Board board;
    private ArrayList<Objectives> objectives;

    private final Random random;

    /**
     * Constructor of the bot
     * @param name : name of the bot
     */
    public Bot(String name, Board board, Random random) {
        this.name = name;
        this.board = board;
        this.random = random;
        this.objectives = new ArrayList<>();
    }

    /**
     * This method is used to place a random tile on the board
     */
    public void placeRandomTile(){
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Get all the available coords
        List<int[]> availableTilesList = board.getAvailableBox().keySet().stream().toList();
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

    public ArrayList<Objectives> getObjectives() {
        return objectives;
    }
}

