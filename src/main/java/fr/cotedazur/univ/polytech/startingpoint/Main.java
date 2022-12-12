package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static String hello() {
        return "Hello World!";
    }

    public static void printBoardState(Board board) {
        HashMap<Integer, Integer> placedBox = new HashMap<>();
        placedBox = board.getPlacedBox();
        for (Map.Entry tile : placedBox.entrySet()) {
            System.out.println("Il y a une tuile ici : " + tile.getValue());
        }
    }

    public static void main(String... args) {
        System.out.println("Que la partie commence !");
        Board board = new Board();
        Bot bot1 = new Bot();
        Bot bot2 = new Bot();
        int turn = 0;
        boolean playing = true;

        while (playing) {
            if (turn == 0) {
                bot1.playAndPrintMove(board);
            }
            else {
                bot2.playAndPrintMove(board);
            }
            printBoardState(board);
            turn = 1 - turn;
            if (board.getNumberBoxPlaced() == 10) {playing = false;}
        }

    }

}
