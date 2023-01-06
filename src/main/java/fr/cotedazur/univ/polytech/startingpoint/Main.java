package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static String hello() {
        return "Hello World!";
    }

    public static void printBoardState(Board board) {
        int nbLigne = 5;
        HashMap<int[], HexagoneBox> placedBox = board.getPlacedBox();
        for (Map.Entry tile : placedBox.entrySet()) {
            System.out.print(Arrays.toString((int[])tile.getKey()) + " ");
        }
        System.out.println(" ");
    }

    public static void main(String... args) {
        Board board = new Board();
        RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
        Bot bot1 = new Bot("Bot1",board, retrieving);
        Bot bot2 = new Bot("Bot2",board, retrieving);
        MeteoDice meteoDice = new MeteoDice();
        System.out.println("Que la partie commence !");
        boolean playing = true;
        int turn = 0;

        while (playing) {
            MeteoDice.Meteo meteo = meteoDice.roll();
            System.out.println("Le dé a choisi : " + meteo);
            if (turn == 0) {
                bot1.placeRandomTile();
                bot1.placeRandomTile();
            }
            else {
                bot2.placeRandomTile();
                bot2.placeRandomTile();
            }
            turn = 1 - turn;
            if (board.getNumberBoxPlaced() == 11) {playing = false;}
            System.out.println("------------------------------------------");
        }

        printBoardState(board);
    }
}
