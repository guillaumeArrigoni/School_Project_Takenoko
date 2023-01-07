package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.*;

import java.util.*;

public class Main {

    public static String hello() {
        return "Hello World!";
    }

    public static void printBoardState(Board board) {
        System.out.println("Voici l'état du board : ");
        ArrayList<HexagoneBox> placedBox = board.getAllBoxPlaced();
        for (HexagoneBox box : placedBox) {
            System.out.println(Arrays.toString(box.getCoordinates()) + " : bamboo de hauteur " + box.getHeightBamboo());
        }
        System.out.println(" ");
    }

    public static void main(String... args) {
        RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
        UniqueObjectCreated.setRetrieveBoxIdWithParameters(retrieving);
        Board board = new Board();
        Random random = new Random();
        ElementOfTheGame elementOfTheGame = new ElementOfTheGame();
        UniqueObjectCreated.setElementOfTheGame(elementOfTheGame);
        UniqueObjectCreated.setBoard(board);
        GestionObjectives gestionnaire = new GestionObjectives();
        gestionnaire.initialize();
        MeteoDice meteoDice = new MeteoDice();
        Bot bot1 = new Bot("Bot1",board,random, meteoDice);
        Bot bot2 = new Bot("Bot2",board,random, meteoDice);
        System.out.println("Que la partie commence !");
        boolean playing = true;
        int turn = 0;


        while (playing) {
            meteoDice.roll();
            if (turn == 0) {
                if(gestionnaire.checkIfBotCanDrawAnObjective(bot1)){
                    gestionnaire.rollObjective(bot1);
                }
                gestionnaire.checkObjectives(bot1);
                bot1.playTurn();
            }
            else {
                if(gestionnaire.checkIfBotCanDrawAnObjective(bot2)){
                    gestionnaire.rollObjective(bot2);
                }
                gestionnaire.checkObjectives(bot2);
                bot2.playTurn();
            }
            turn = 1 - turn;
            printBoardState(board);
            if (board.getNumberBoxPlaced() > 10) {
                playing = false;
                System.out.println("Score du bot 1 : " + bot1.getScore());
                System.out.println("Score du bot 2 : " + bot2.getScore());
            }
            System.out.println("------------------------------------------");
        }
    }


}
