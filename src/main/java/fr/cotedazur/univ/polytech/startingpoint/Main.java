package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.*;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs.GestionObjectifs;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Action;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static String hello() {
        return "Hello World!";
    }

    public static void printBoardState(Board board) {
        int nbLigne = 5;
        HashMap<int[], HexagoneBox> placedBox = board.getPlacedBox();
        for (Map.Entry tile : placedBox.entrySet()) {
            System.out.print(Arrays.toString((int[])tile.getKey()));
        }
        System.out.println(" ");
    }

    public static void main(String... args) {
        RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
        Board board = new Board();
        Random random = new Random();
        ElementOfTheGame elementOfTheGame = new ElementOfTheGame();
        UniqueObjectCreated.setElementOfTheGame(elementOfTheGame);
        UniqueObjectCreated.setBoard(board);
        UniqueObjectCreated.setRetrieveBoxIdWithParameters(retrieving);
        Bot bot1 = new Bot("Bot1",board,random);
        Bot bot2 = new Bot("Bot2",board,random);
        MeteoDice meteoDice = new MeteoDice();
        GestionObjectifs gestionnaire = new GestionObjectifs();
        gestionnaire.initialize();
        System.out.println("Que la partie commence !");
        boolean playing = true;
        int turn = 0;

        while (playing) {
            MeteoDice.Meteo meteo = meteoDice.roll();
            System.out.println("Le dé a choisi : " + meteo);
            if (turn == 0) {
                if(gestionnaire.checkIfBotCanDrawAnObjective(bot1)){
                    gestionnaire.rollObjective(bot1);
                }
                gestionnaire.checkObjectives(bot1);
                bot1.placeRandomTile();
                bot1.placeRandomTile();
            }
            else {
                if(gestionnaire.checkIfBotCanDrawAnObjective(bot2)){
                    gestionnaire.rollObjective(bot2);
                }
                gestionnaire.checkObjectives(bot2);
                bot2.placeRandomTile();
                bot2.placeRandomTile();
            }
            turn = 1 - turn;
            if (board.getNumberBoxPlaced() == 11) {
                playing = false;
                gestionnaire.printWinner(bot1, bot2);
            }
            System.out.println("------------------------------------------");
        }

        printBoardState(board);
        System.out.println(Action.possibleMoveForGardener(board, board.getGardenerCoords()));
    }
}
