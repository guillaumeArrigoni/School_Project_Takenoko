package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ActionTest {
    static Random r;


    @BeforeAll
    static void setUp() {
        r = mock(Random.class);
    }

    @Test
    void drawYellowTile() {
        when(r.nextInt(0, 3)).thenReturn(0);
        HexagoneBox hexagoneBox = Action.drawTile(r);
        assertEquals(Color.Jaune, hexagoneBox.getColor());
        assertEquals(Special.Classique, hexagoneBox.getSpecial());
    }

    @Test
    void drawGreenTile() {
        when(r.nextInt(0, 3)).thenReturn(1);
        HexagoneBox hexagoneBox = Action.drawTile(r);
        assertEquals(Color.Vert, hexagoneBox.getColor());
        assertEquals(Special.Classique, hexagoneBox.getSpecial());
    }

    @Test
    void drawRedTile() {
        when(r.nextInt(0, 3)).thenReturn(2);
        HexagoneBox hexagoneBox = Action.drawTile(r);
        assertEquals(Color.Rouge, hexagoneBox.getColor());
        assertEquals(Special.Classique, hexagoneBox.getSpecial());
    }

}
