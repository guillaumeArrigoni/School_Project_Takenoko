package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

public class GameState {

    private BotSimulator botSimulator;


    private int score;

    private MeteoDice.Meteo meteo;

    public GameState(BotSimulator botSimulator, MeteoDice.Meteo meteo) {
        this.botSimulator = botSimulator;
        this.meteo = meteo;
        this.score = calculateScore();
    }

    private int calculateScore() {
        return botSimulator.getScore();
    }

    public BotSimulator getBotSimulator() {
        return botSimulator;
    }

    public int getScore() {
        return score;
    }

    public MeteoDice.Meteo getMeteo() {
        return meteo;
    }

    public Board getBoard() {
        return botSimulator.getBoard();
    }
}
