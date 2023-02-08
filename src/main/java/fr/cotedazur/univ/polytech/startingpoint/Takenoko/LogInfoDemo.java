package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class LogInfoDemo extends Log{

    public LogInfoDemo(boolean IsOn) {
        super(Level.INFO,LogInfoDemo.class.getName());
        if (!IsOn){
            this.setOff();
        }
    }

    public void printBoardState(Board board) {
        logger.log(Level.INFO ,"Voici l'état du board : ");
        ArrayList<HexagoneBoxPlaced> placedBox = board.getAllBoxPlaced();
        for (HexagoneBoxPlaced box : placedBox) {
            logger.log(Level.INFO, Arrays.toString(box.getCoordinates()) + " : bamboo de hauteur " + box.getHeightBamboo());
        }
        logger.log(Level.INFO," ");
    }
}
