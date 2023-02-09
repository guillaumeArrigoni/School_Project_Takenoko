package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;

public class GameState {

    private BotSimulator botSimulator;


    private final int score;

    private MeteoDice.Meteo meteo;

    public GameState(BotSimulator botSimulator, MeteoDice.Meteo meteo) {
        this.botSimulator = botSimulator;
        this.meteo = meteo;
        this.score = calculateScore();
    }

    public GameState(){
        score = -999;
    }

    private int calculateScore() {
        if(botSimulator.isLegal())
            return botSimulator.getScore();
        else
            return -999;
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
