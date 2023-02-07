package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;

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
        ArrayList<HexagoneBoxPlaced> placedBox = board.getAllBoxPlaced();
        for (HexagoneBoxPlaced box : placedBox) {
            System.out.println(Arrays.toString(box.getCoordinates()) + " : bamboo de hauteur " + box.getHeightBamboo());
        }
        System.out.println(" ");
    }

    public int play(GestionObjectives gestionnaire, String arg) {
        gestionnaire.initialize(
                gestionnaire.ListOfObjectiveParcelleByDefault(),
                gestionnaire.ListOfObjectiveJardinierByDefault(),
                gestionnaire.ListOfObjectivePandaByDefault()
        );
        for (Bot bot : this.playerList) {
            gestionnaire.rollParcelleObjective(bot, arg);
            gestionnaire.rollJardinierObjective(bot, arg);
        }
        int numberPlayer = this.playerList.size();
        MeteoDice.Meteo meteo = MeteoDice.Meteo.NO_METEO;
        while (playing) {
            if (arg.equals("demo")) {
                System.out.println("Tour n°" + turnNumber + " :");
                if (turnNumber == 2) System.out.println("Deuxième tour, la météo entre en jeu !");
            }
            if (turnNumber != 1) meteo = meteoDice.roll();
            Bot playingBot = this.playerList.get(turn);
            playingBot.playTurn(meteo, arg);
            gestionnaire.checkObjectives(playingBot, arg, numberPlayer);
            if (arg.equals("demo")) printBoardState(board);
            if (board.getNumberBoxPlaced() > 20) {
                playing = false;
                if (arg.equals("demo")) {
                    for (Bot bot : playerList) {
                        System.out.println("Score de " + bot.getName() + " : " + bot.getScore());
                    }
                }
                if (arg.equals("demo")) gestionnaire.printWinner(gestionnaire.getWinner(playerList));
            }
            if (arg.equals("demo")) System.out.println("------------------------------------------");
            turn = (turn + 1)%numberPlayer;
            this.turnNumber++;
        }
        if(gestionnaire.getWinner(playerList).size()>1){
            return 0;
        }
        else {
            for (int i = 0; i < numberPlayer; i++) {
                if (gestionnaire.getWinner(playerList).contains(playerList.get(i))) {
                    return i + 1;
                }
            }
        }
        return 0;
    }
}