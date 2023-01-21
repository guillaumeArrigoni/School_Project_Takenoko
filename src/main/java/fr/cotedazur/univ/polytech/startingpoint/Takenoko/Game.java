package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Action;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.*;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Action;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.*;

public class Game {
    private List<Bot> playerList;
    private Board board;
    int turn;
    int turnNumber;
    boolean playing;
    MeteoDice meteoDice;

    public Game(List<Bot> playerList, Board board) {
        this.playerList = playerList;
        this.board = board;
        this.turn = 0;
        this.turnNumber = 1;
        this.playing = true;
        this.meteoDice = new MeteoDice();
    }

    public static void printBoardState(Board board) {
        System.out.println("Voici l'état du board : ");
        ArrayList<HexagoneBox> placedBox = board.getAllBoxPlaced();
        for (HexagoneBox box : placedBox) {
            System.out.println(Arrays.toString(box.getCoordinates()) + " : bamboo de hauteur " + box.getHeightBamboo());
        }
        System.out.println(" ");
    }

    public void play(GestionObjectives gestionnaire) {
        gestionnaire.initialize(
                gestionnaire.ListOfObjectiveParcelleByDefault(),
                gestionnaire.ListOfObjectiveJardinierByDefault(),
                gestionnaire.ListOfObjectivePandaByDefault()
        );
        for (Bot bot : this.playerList) {
            gestionnaire.rollParcelleObjective(bot);
            gestionnaire.rollJardinierObjective(bot);
        }
        int numberPlayer = this.playerList.size();
        while (playing) {
            System.out.println("Tour n°" + turnNumber + " :");
            if (turnNumber == 2) System.out.println("Deuxième tour, la météo entre en jeu !");
            if (turnNumber != 1) meteoDice.roll();
            Bot playingBot = this.playerList.get(turn);
            playingBot.playTurn();
            gestionnaire.checkObjectives(playingBot);
            printBoardState(board);
            if (board.getNumberBoxPlaced() > 20) {
                playing = false;
                for (int i = 0; i < numberPlayer; i++) {
                    System.out.println("Score de " + this.playerList.get(i).getName() + " : " + this.playerList.get(i).getScore());
                }
                //A changer pour avoir un nombre indéfini de joueur
                Bot bot1 = this.playerList.get(0);
                Bot bot2 = this.playerList.get(1);
                gestionnaire.printWinner(bot1,bot2);
            }
            System.out.println("------------------------------------------");
            turn = (turn + 1)%numberPlayer;
            this.turnNumber++;
        }
    }
}