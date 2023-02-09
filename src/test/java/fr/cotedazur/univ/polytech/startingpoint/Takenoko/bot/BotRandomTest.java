package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BotRandomTest {
    BotRandom botRandom;
    Board board;
    Random r;
    MeteoDice meteoDice;
    RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    final String arg = "demo";

    GestionObjectives gestionObjectives;

    @BeforeEach
    void setUp() {
        LogInfoDemo logInfoDemo = new LogInfoDemo(true);
        this.retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters, 1, 2);
        gestionObjectives = new GestionObjectives(board, retrieveBoxIdWithParameters);
        gestionObjectives.initialize(
                gestionObjectives.ListOfObjectiveParcelleByDefault(),
                gestionObjectives.ListOfObjectiveJardinierByDefault(),
                gestionObjectives.ListOfObjectivePandaByDefault());
        r = mock(Random.class);
        meteoDice = mock(MeteoDice.class);
        board.getElementOfTheBoard().getStackOfBox().getStackOfBox().add(0, new HexagoneBox(Color.Jaune, Special.Classique));
        botRandom = new BotRandom("testBot", board, r,  gestionObjectives, retrieveBoxIdWithParameters, new HashMap<Color,Integer>(), logInfoDemo);
    }

    @Test
    void placeFirstTileDrawn() {
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        assertEquals(1, board.getPlacedBox().size());
        botRandom.placeTile(arg);
        verify(r, times(2)).nextInt(anyInt(),anyInt());
        assertEquals(2, board.getAllBoxPlaced().size());
    }

    @Test
    void placeSecondTileDrawn(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,1,1);
        assertEquals(1, board.getPlacedBox().size());
        botRandom.placeTile(arg);
        assertEquals(2, board.getPlacedBox().size());
    }

    @Test
    void placeThirdTileDrawn(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,2,2);
        assertEquals(1, board.getPlacedBox().size());
        botRandom.placeTile(arg);
        assertEquals(2, board.getPlacedBox().size());
    }

    @Test
    void playTurnWindPlaceBoxTwoTimes(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0,1,2,1,1);
        when(r.nextInt(anyInt())).thenReturn(0,0);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(5)).nextInt(anyInt(),anyInt());
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(3, board.getAllBoxPlaced().size());
    }

    @Test
    void playTurnWindPlaceBoxMoveGardener(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0);
        when(r.nextInt(anyInt())).thenReturn(0,1);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(4)).nextInt(anyInt(),anyInt());
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, board.getAllBoxPlaced().size());
        assertNotEquals(new int[]{0,0,0}, board.getGardenerCoords());

    }

    @Test
    void playTurnMoveGardenerImpossible(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        when(r.nextInt(anyInt())).thenReturn(1,0);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(3)).nextInt(anyInt());
        assertEquals(3, board.getAllBoxPlaced().size());
    }

    @Test
    void playTurnMovePandaImpossible(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0,1,2,0,0,0);
        when(r.nextInt(anyInt())).thenReturn(3,0);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(3)).nextInt(anyInt());
        assertEquals(3, board.getAllBoxPlaced().size());
    }

    @Test
    void playTurnDrawParcelObjective(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0);
        when(r.nextInt(anyInt())).thenReturn(2);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, botRandom.getObjectives().size());
        assertEquals(TypeObjective.PARCELLE, botRandom.getObjectives().get(0).getType());
        assertEquals(TypeObjective.PARCELLE, botRandom.getObjectives().get(1).getType());
    }

    @Test
    void playTurnDrawGardenerObjective(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(1);
        when(r.nextInt(anyInt())).thenReturn(2);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, botRandom.getObjectives().size());
        assertEquals(TypeObjective.JARDINIER, botRandom.getObjectives().get(0).getType());
        assertEquals(TypeObjective.JARDINIER, botRandom.getObjectives().get(1).getType());
    }

    @Test
    void playTurnDrawPandaObjective(){
        when(r.nextInt(anyInt(), anyInt())).thenReturn(2);
        when(r.nextInt(anyInt())).thenReturn(2);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, botRandom.getObjectives().size());
        assertEquals(TypeObjective.PANDA, botRandom.getObjectives().get(0).getType());
        assertEquals(TypeObjective.PANDA, botRandom.getObjectives().get(1).getType());
    }


    @Test
    void playTurnDrawRandomObjectiveTwice(){
        when(r.nextInt(anyInt())).thenReturn(2);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(2)).nextInt(anyInt());
        assertEquals(2, botRandom.getObjectives().size());
    }

    @Test
    void playTurnDrawRandomObjectiveTooMuch(){
        when(r.nextInt(anyInt())).thenReturn(2,2,2,2,2,2,0);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(7)).nextInt(anyInt());
        assertEquals(5, botRandom.getObjectives().size());
    }

    @Test
    void playTurnSun(){
        when(r.nextInt(anyInt())).thenReturn(0,1,3,0);
        botRandom.playTurn(MeteoDice.Meteo.SOLEIL, arg);
        verify(r, times(4)).nextInt(anyInt());
        assertTrue(board.isCoordinateInBoard(new int[]{1,-1,0}));
        assertEquals(1, board.getGardenerCoords()[0]);
        assertEquals(-1, board.getGardenerCoords()[1]);
        assertEquals(0, board.getGardenerCoords()[2]);
        assertEquals(1, botRandom.getObjectives().size());
    }

    @Test
    void playTurnRain(){
        when(r.nextInt(anyInt())).thenReturn(0,3,1,0,0);
        when(r.nextInt(anyInt(), anyInt())).thenReturn(0);
        botRandom.playTurn(MeteoDice.Meteo.SOLEIL, arg);
        botRandom.playTurn(MeteoDice.Meteo.PLUIE, arg);
        assertEquals(0, board.getGardenerCoords()[0]);
        assertEquals(-1, board.getGardenerCoords()[1]);
        assertEquals(1, board.getGardenerCoords()[2]);
    }

    @Test
    void playTurnThunder(){
        when(r.nextInt(anyInt())).thenReturn(0,3,1,0,0,3,1);
        botRandom.playTurn(MeteoDice.Meteo.SOLEIL, arg);
        botRandom.playTurn(MeteoDice.Meteo.ORAGE, arg);
        assertEquals(1, board.getPandaCoords()[0]);
        assertEquals(-1, board.getPandaCoords()[1]);
        assertEquals(0, board.getPandaCoords()[2]);
    }

    @Test
    void playTurnCloud(){
        when(r.nextInt(anyInt())).thenReturn(0,3,1,0,0,3,1);
        botRandom.playTurn(MeteoDice.Meteo.SOLEIL, arg);
        assertEquals(Special.Classique,board.getPlacedBox().get(1009901).getSpecial());
        botRandom.playTurn(MeteoDice.Meteo.NUAGES, arg);
        assertEquals(Special.Protéger, board.getPlacedBox().get(1009901).getSpecial());
    }

    @Test
    void movePanda(){
        when(r.nextInt(anyInt())).thenReturn(0,3);
        botRandom.playTurn(MeteoDice.Meteo.VENT, arg);
        verify(r, times(2)).nextInt(anyInt());
        assertTrue(board.isCoordinateInBoard(new int[]{1,-1,0}));
        assertEquals(1, board.getPandaCoords()[0]);
        assertEquals(-1, board.getPandaCoords()[1]);
        assertEquals(0, board.getPandaCoords()[2]);
    }


}