package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Bot {
    public String name;
    public Bot(String name){
        this.name = name;
    }

    public HexagoneBox placeRandomTile(Board board){
        List<HexagoneBox> list = new ArrayList<>();
        List<int[]> availableTilesList = board.getAvailableBox().keySet().stream().toList();
        for(int i = 0; i < 3; i++)
            list.add(Action.drawTile());
        HexagoneBox placedTile = list.get(ThreadLocalRandom.current().nextInt(0, 3));
        int[] placedTileCoords = availableTilesList.get(ThreadLocalRandom.current().nextInt(0, availableTilesList.size()));
        placedTile.setCoordinates(placedTileCoords);
        
        placedTile = new HexagoneBox(placedTileCoords[0],
                                     placedTileCoords[1],
                                     placedTileCoords[2],
                                     placedTile.getColor(),
                                     placedTile.getSpecial());
        board.addBox(placedTile);
        return placedTile;
    }

    public void playAndPrintMove(Board board) {
        HexagoneBox placedTile = placeRandomTile(board);
        System.out.println(this.name + " a placé une tuile " + placedTile.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
    }
}

