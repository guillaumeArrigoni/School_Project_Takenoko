package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;

/**
 * Class that represents a game state
 */
public class GameState {
    /**
     * The bot simulator
     */
    private BotSimulator botSimulator;

    /**
     * The score of the game state
     */
    private final int score;
    /**
     * The meteo of the game state
     */
    private MeteoDice.Meteo meteo;

    public GameState(BotSimulator botSimulator, MeteoDice.Meteo meteo) {
        this.botSimulator = botSimulator;
        this.meteo = meteo;
        this.score = calculateScore();
    }

    public GameState(){
        score = -999;
    }

    /**
     * Calculate the score of the game state
     * @return the score of the game state or -999 if the game state is illegal
     */
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
