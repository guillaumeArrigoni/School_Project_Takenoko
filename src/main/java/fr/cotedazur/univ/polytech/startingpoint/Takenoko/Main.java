package fr.cotedazur.univ.polytech.startingpoint.Takenoko;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.ElementOfTheGame;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

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
        Board board = new Board(retrieving);
        Random random = new Random();
        ElementOfTheGame elementOfTheGame = new ElementOfTheGame();
        GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
        gestionnaire.initialize();
        MeteoDice meteoDice = new MeteoDice();
        Bot bot1 = new BotRandom("Bot1",board,random, meteoDice,gestionnaire, retrieving);
        Bot bot2 = new BotRandom("Bot2",board,random, meteoDice,gestionnaire, retrieving);

        boolean playing = true;


        gestionnaire.rollParcelleObjective(bot1);
        gestionnaire.rollParcelleObjective(bot2);
        gestionnaire.rollJardinierObjective(bot1);
        gestionnaire.rollJardinierObjective(bot2);
        /* not implemented yet
        gestionnaire.rollPandaObjective(bot1);
        gestionnaire.rollPandaObjective(bot2);
         */
        System.out.println("Que la partie commence !");
        while (playing) {
            bot1.playTurn();
            gestionnaire.checkObjectives(bot1);
            bot2.playTurn();
            gestionnaire.checkObjectives(bot2);
            printBoardState(board);
            if (board.getNumberBoxPlaced() > 20) {
                playing = false;
                System.out.println("Score du bot 1 : " + bot1.getScore());
                System.out.println("Score du bot 2 : " + bot2.getScore());
                gestionnaire.printWinner(bot1,bot2);
            }
            System.out.println("------------------------------------------");
        }
    }


}
