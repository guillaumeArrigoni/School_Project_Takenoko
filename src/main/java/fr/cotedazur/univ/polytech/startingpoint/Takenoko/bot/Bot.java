package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.CoordinateMethod.separateID;

public class Bot {
    public Bot(){}

    public HexagoneBox placeRandomTile(Board board){
        List<HexagoneBox> list = new ArrayList<>();
        List<Integer> availableTilesList = board.getAvailableBox().keySet().stream().toList();
        for(int i = 0; i < 3; i++)
            list.add(Action.drawTile());
        HexagoneBox placedTile = list.get(ThreadLocalRandom.current().nextInt(0, 3));
        int placedTileCoords = availableTilesList.get(ThreadLocalRandom.current().nextInt(0, availableTilesList.size()));
        placedTile.setId(placedTileCoords);
        
        placedTile = new HexagoneBox(separateID(placedTileCoords)[0],
                                     separateID(placedTileCoords)[1],
                                     separateID(placedTileCoords)[2],
                                     placedTile.getColor(),
                                     placedTile.getSpecial());
        board.addBox(placedTile);
        return placedTile;
    }

    public void playAndPrintMove(Board board) {
        HexagoneBox placedTile = placeRandomTile(board);
        System.out.println("Une tuile " + placedTile.getColor() + " a été placée ici : " + placedTile.getCoordinates().toString());
    }
}

