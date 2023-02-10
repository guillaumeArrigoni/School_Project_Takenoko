package fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatternTest {
    private static GestionObjectives gestionnaire;
    private static Board board;
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static Pattern pattern1;
    private static Pattern pattern2;
    private static Pattern pattern3;
    private static Pattern pattern4;
    private static Pattern pattern5;
    private static Pattern pattern6;
    private static LoggerError loggerError;

    @BeforeAll
    public static void setUp(){
        loggerError = new LoggerError(true);
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters, 1, 2,new LoggerSevere(true));
        gestionnaire = new GestionObjectives(board, retrieveBoxIdWithParameters,loggerError);
        pattern1 = gestionnaire.planterSurSourceEau;
        pattern2 = gestionnaire.planterTroisJaunes;
        pattern3 = gestionnaire.poserTriangle;
        pattern4 = gestionnaire.poserLigne;
        pattern5 = gestionnaire.mangerDeuxBambous;
        pattern6 = gestionnaire.mangerTroisBambous;
    }

    @Test
    void getForme() {
        assertNull(pattern1.getForme());
        assertNull(pattern2.getForme());
        assertEquals("TRIANGLE", pattern3.getForme());
        assertEquals("LIGNE", pattern4.getForme());
        assertNull(pattern5.getForme());
        assertNull(pattern6.getForme());
    }

    @Test
    void getNbBambou() {
        assertEquals(1, pattern1.getNbBambou());
        assertEquals(3, pattern2.getNbBambou());
        assertEquals(0, pattern3.getNbBambou());
        assertEquals(0, pattern4.getNbBambou());
        assertEquals(2, pattern5.getNbBambou());
        assertEquals(3, pattern6.getNbBambou());
    }

    @Test
    void getHauteurBambou() {
        assertEquals(4, pattern1.getHauteurBambou());
        assertEquals(3, pattern2.getHauteurBambou());
        assertEquals(0, pattern3.getHauteurBambou());
        assertEquals(0, pattern4.getHauteurBambou());
        assertEquals(1, pattern5.getHauteurBambou());
        assertEquals(1, pattern6.getHauteurBambou());
    }

    @Test
    void getSpecial() {
        assertEquals(Special.SOURCE_EAU, pattern1.getSpecial());
        assertNull(pattern2.getSpecial());
        assertNull(pattern3.getSpecial());
        assertNull(pattern4.getSpecial());
        assertNull(pattern5.getSpecial());
        assertNull(pattern6.getSpecial());
    }
}