package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatternTest {

    private static Pattern pattern1;
    private static Pattern pattern2;
    private static Pattern pattern3;
    private static Pattern pattern4;

    @BeforeAll
    public static void setUp(){
        pattern1 = Pattern.PATTERNJARDINIER1;
        pattern2 = Pattern.PATTERNJARDINIER14;
        pattern3 = Pattern.PATTERNPARCELLE1;
        pattern4 = Pattern.PATTERNPARCELLE5;
    }

    @Test
    void getForme() {
        assertNull(pattern1.getForme());
        assertNull(pattern2.getForme());
        assertEquals("TRIANGLE", pattern3.getForme());
        assertEquals("LIGNE", pattern4.getForme());
    }

    @Test
    void getNbBambou() {
        assertEquals(1, pattern1.getNbBambou());
        assertEquals(3, pattern2.getNbBambou());
        assertEquals(0, pattern3.getNbBambou());
        assertEquals(0, pattern4.getNbBambou());
    }

    @Test
    void getHauteurBambou() {
        assertEquals(4, pattern1.getHauteurBambou());
        assertEquals(3, pattern2.getHauteurBambou());
        assertEquals(0, pattern3.getHauteurBambou());
        assertEquals(0, pattern4.getHauteurBambou());
    }

    @Test
    void getSpecial() {
        assertEquals(Special.SourceEau, pattern1.getSpecial());
        assertNull(pattern2.getSpecial());
        assertNull(pattern3.getSpecial());
        assertNull(pattern4.getSpecial());
    }
}