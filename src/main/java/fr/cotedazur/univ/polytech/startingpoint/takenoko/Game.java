package fr.cotedazur.univ.polytech.startingpoint.takenoko;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;

import java.util.*;

public class Game {
    private List<Bot> playerList;
    private Board board;
    private int turn;
    private int turnNumber;
    private boolean playing;
    private MeteoDice meteoDice;
    private LogInfoDemo logInfoDemo;

    public Game(List<Bot> playerList, Board board, LogInfoDemo logInfoDemo) {
        this.playerList = playerList;
        this.board = board;
        this.turn = 0;
        this.turnNumber = 1;
        this.playing = true;
        this.meteoDice = new MeteoDice();
        this.logInfoDemo = logInfoDemo;
    }

    public void printBoardState(Board board) {
        logInfoDemo.printBoardState(board);
    }

    public int play(GestionObjectives gestionnaire, String arg) throws CloneNotSupportedException {
        gestionnaire.initialize(
                gestionnaire.ListOfObjectiveParcelleByDefault(),
                gestionnaire.ListOfObjectiveJardinierByDefault(),
                gestionnaire.ListOfObjectivePandaByDefault()
        );
        for (Bot bot : this.playerList) {
            gestionnaire.rollParcelleObjective(bot);
            gestionnaire.rollJardinierObjective(bot);
            gestionnaire.rollPandaObjective(bot);
            logInfoDemo.displaySeparator();
        }
        int numberPlayer = this.playerList.size();
        MeteoDice.Meteo meteo = MeteoDice.Meteo.NO_METEO;
        while (playing) {
            logInfoDemo.displayTurn(turnNumber);
            if (turnNumber == 2) logInfoDemo.addLog("Deuxième tour, la météo entre en jeu !");
            if (turnNumber != 1) meteo = meteoDice.roll();
            Bot playingBot = this.playerList.get(turn);
            playingBot.playTurn(meteo, arg);
            gestionnaire.checkObjectives(playingBot, arg, numberPlayer);
            printBoardState(board);
            if (gestionnaire.doesABotHaveEnoughObjectivesDone() || turnNumber>= 200) {
                playing = false;
                for (Bot bot : playerList) {
                    logInfoDemo.displayScore(bot);
                }
                logInfoDemo.printWinner(gestionnaire.getWinner(playerList));
            }
            logInfoDemo.displaySeparator();
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