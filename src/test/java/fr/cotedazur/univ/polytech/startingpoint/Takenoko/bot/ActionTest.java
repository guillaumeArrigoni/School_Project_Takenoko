package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;



import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBoxPlaced;

import java.util.Random;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActionTest {
    static Random r;

    static Board board;
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    @BeforeEach
    void setUp() {
        this.retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        r = mock(Random.class);
        board = new Board(retrieveBoxIdWithParameters);
    }

    //DrawTile
    @Test
    void drawYellowTile() {
        when(r.nextInt(0, 3)).thenReturn(0);
        HexagoneBox hexagoneBox = Action.drawTile(r,retrieveBoxIdWithParameters,board);
        assertEquals(Color.Jaune, hexagoneBox.getColor());
    }

    @Test
    void drawGreenTile() {
        when(r.nextInt(0, 3)).thenReturn(1);
        HexagoneBox hexagoneBox = Action.drawTile(r, retrieveBoxIdWithParameters,board);
        assertEquals(Color.Vert, hexagoneBox.getColor());
    }

    @Test
    void drawRedTile() {
        when(r.nextInt(0, 3)).thenReturn(2);
        HexagoneBox hexagoneBox = Action.drawTile(r, retrieveBoxIdWithParameters,board);
        assertEquals(Color.Rouge, hexagoneBox.getColor());
    }

    //GetMovesForGardenerOrPanda
    @Test
    void getMovesForGardenerOrPandaTopRight() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(1,-1,0,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(1,coords[0]);
        assertEquals(-1,coords[1]);
        assertEquals(0,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaRight() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(1,0,-1,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(1,coords[0]);
        assertEquals(0,coords[1]);
        assertEquals(-1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaBottomRight() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(0,1,-1,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(0,coords[0]);
        assertEquals(1,coords[1]);
        assertEquals(-1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaBottomLeft() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(-1,1,0,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(-1,coords[0]);
        assertEquals(1,coords[1]);
        assertEquals(0,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaLeft() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(-1,0,1,Color.Jaune, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
        assertEquals(-1,coords[0]);
        assertEquals(0,coords[1]);
        assertEquals(1,coords[2]);
    }

    @Test
    void getMovesForGardenerOrPandaTopLeft() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(0,-1,1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced);
        assertEquals(1,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
        int[] coords = Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).get(0);
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
        board.addBox(hexagoneBoxPlaced);
        board.addBox(hexagoneBoxPlaced2);
        board.addBox(hexagoneBoxPlaced3);
        board.addBox(hexagoneBoxPlaced4);
        board.addBox(hexagoneBoxPlaced5);
        board.addBox(hexagoneBoxPlaced6);
        assertEquals(6,Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).size());
    }
}
