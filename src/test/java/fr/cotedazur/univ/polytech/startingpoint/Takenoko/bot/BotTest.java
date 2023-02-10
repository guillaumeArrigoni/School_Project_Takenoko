package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;



import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.util.HashMap;
import java.util.Random;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BotTest {
    static Random r;

    static Board board;
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static BotRandom bot;

    @BeforeEach
    void setUp() {
        this.retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        r = mock(Random.class);
        board = new Board(retrieveBoxIdWithParameters, 1, 2,new LoggerSevere(true));
        bot = new BotRandom("bot",board,new Random(),new GestionObjectives(board,retrieveBoxIdWithParameters,new LoggerError(true)),retrieveBoxIdWithParameters,new HashMap<>(),new LogInfoDemo(true));

    }


    //GetMovesForGardenerOrPanda
    @Test
    void getMovesForGardenerOrPandaTopRight() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(1,-1,0,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced, bot);
        assertEquals(1,Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(1,coords[0]);
        assertEquals(-1,coords[1]);
        assertEquals(0,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaRight() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(1,0,-1,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced, bot);
        assertEquals(1,Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(1,coords[0]);
        assertEquals(0,coords[1]);
        assertEquals(-1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaBottomRight() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(0,1,-1,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced, bot);
        assertEquals(1,Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(0,coords[0]);
        assertEquals(1,coords[1]);
        assertEquals(-1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaBottomLeft() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(-1,1,0,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced, bot);
        assertEquals(1,Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(-1,coords[0]);
        assertEquals(1,coords[1]);
        assertEquals(0,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaLeft() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(-1,0,1,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced, bot);
        assertEquals(1,Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(-1,coords[0]);
        assertEquals(0,coords[1]);
        assertEquals(1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaTopLeft() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(0,-1,1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced, bot);
        assertEquals(1,Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(0,coords[0]);
        assertEquals(-1,coords[1]);
        assertEquals(1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaGlobal() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(1,-1,0,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced2 = new HexagoneBoxPlaced(1,0,-1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced3 = new HexagoneBoxPlaced(0,1,-1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced4 = new HexagoneBoxPlaced(-1,1,0,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced5 = new HexagoneBoxPlaced(-1,0,1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced6 = new HexagoneBoxPlaced(0,-1,1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced, bot);
        board.addBox(hexagoneBoxPlaced2, bot);
        board.addBox(hexagoneBoxPlaced3, bot);
        board.addBox(hexagoneBoxPlaced4, bot);
        board.addBox(hexagoneBoxPlaced5, bot);
        board.addBox(hexagoneBoxPlaced6, bot);
        assertEquals(6,Bot.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
    }
}
