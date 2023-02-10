package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.tree;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameStateTest {

    BotRandom botRandom;
    Board board;
    Random r;
    MeteoDice meteoDice;
    RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    BotSimulator bot;
    GestionObjectives gestionObjectives;

    @BeforeEach
    void setUp() {
        LogInfoDemo logInfoDemo = new LogInfoDemo(true);
        this.retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters, 1, 2, new LoggerSevere(true));
        gestionObjectives = new GestionObjectives(board, retrieveBoxIdWithParameters, new LoggerError(true));
        gestionObjectives.initialize(
                gestionObjectives.listOfObjectiveParcelleByDefault(),
                gestionObjectives.listOfObjectiveJardinierByDefault(),
                gestionObjectives.listOfObjectivePandaByDefault());
        r = mock(Random.class);
        meteoDice = mock(MeteoDice.class);
        botRandom = new BotRandom("testBot", board, r,  gestionObjectives, retrieveBoxIdWithParameters, new HashMap<Color,Integer>(), logInfoDemo);
        board.getElementOfTheBoard().getStackOfBox().getStackOfBox().clear();
        for(int i = 0; i < 15; i++){
            board.getElementOfTheBoard().getStackOfBox().getStackOfBox().add(new HexagoneBox(Color.VERT, Special.CLASSIQUE));
        }
        bot = botRandom.createBotSimulator();
    }

    @Test
    void testScoreLegal(){
        bot.setInstructions(new ActionLog(PossibleActions.DRAW_AND_PUT_TILE, 1,-1,0, 0));
        bot.playTurn(MeteoDice.Meteo.VENT, "demo");
        GameState gameState = new GameState(bot, MeteoDice.Meteo.NO_METEO);
        assertEquals(0, gameState.getScore());
        assertEquals(-999,new GameState().getScore());
    }

    @Test
    void testScoreIllegal(){
        bot.setInstructions(new ActionLog(PossibleActions.MOVE_PANDA, 1,-1,0));
        bot.playTurn(MeteoDice.Meteo.NO_METEO, "demo");
        GameState gameState = new GameState(bot, MeteoDice.Meteo.NO_METEO);
        assertEquals(-999, gameState.getScore());

    }
}
