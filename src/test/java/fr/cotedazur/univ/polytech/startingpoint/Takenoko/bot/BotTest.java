package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BotTest {
    Bot bot;
    Board board;
    Random r;
    MeteoDice meteoDice;
    @BeforeEach
    void setUp() {
        r = mock(Random.class);
        board = new Board();
        meteoDice = mock(MeteoDice.class);
        bot = new Bot("testBot", board, r, meteoDice);
    }

    @Test
    void placeFirstTileDrawn() {
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        assertEquals(1, board.getPlacedBox().size());
        bot.placeRandomTile();
        verify(r, times(5)).nextInt(anyInt(),anyInt());
        assertEquals(2, board.getPlacedBox().size());
    }

    @Test
    void placeSecondTileDrawn(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,1,1);
        assertEquals(1, board.getPlacedBox().size());
        bot.placeRandomTile();
        assertEquals(2, board.getPlacedBox().size());
    }

    @Test
    void placeThirdTileDrawn(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,2,2);
        assertEquals(1, board.getPlacedBox().size());
        bot.placeRandomTile();
        assertEquals(2, board.getPlacedBox().size());
    }

    @Test
    void playTurnWindPlaceBoxTwoTimes(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0,1,2,1,1);
        when(r.nextInt(anyInt())).thenReturn(0,0);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        bot.playTurn();
        verify(r, times(10)).nextInt(anyInt(),anyInt());
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(3, board.getPlacedBox().size());
    }

    @Test
    void playTurnWindPlaceBoxMoveGardener(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        when(r.nextInt(anyInt())).thenReturn(0,1);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        bot.playTurn();
        verify(r, times(6)).nextInt(anyInt(),anyInt());
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, board.getPlacedBox().size());
        assertNotEquals(new int[]{0,0,0}, board.getGardenerCoords());

    }

    @Test
    void playTurnMoveGardenerImpossible(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        when(r.nextInt(anyInt())).thenReturn(1,0);
        when(meteoDice.roll()).thenReturn(MeteoDice.Meteo.VENT);
        bot.playTurn();
        verify(r, times(3)).nextInt(anyInt());
        assertEquals(3, board.getPlacedBox().size());
    }
}