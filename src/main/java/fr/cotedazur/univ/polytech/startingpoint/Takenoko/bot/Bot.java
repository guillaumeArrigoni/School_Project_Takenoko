package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Bot {
    private Board board;

    public Bot(Board board){
        this.board = board;
    }



    public void placeRandomTile(HashMap<Integer, Integer> availableTiles){
     List<HexagoneBox> list = new ArrayList<>();
     List<Integer> availablreTilesList = availableTiles.keySet().stream().toList();
     for(int i = 0; i < 3; i++)
         list.add(Action.drawTile());
     HexagoneBox placedTile = list.get(ThreadLocalRandom.current().nextInt(0, 3));
     int placedTilecCoords = availablreTilesList.get(ThreadLocalRandom.current().nextInt(0, availablreTilesList.size()));
     board.p

    }
}

