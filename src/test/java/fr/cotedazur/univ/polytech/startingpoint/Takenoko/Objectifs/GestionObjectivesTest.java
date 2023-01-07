package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.*;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GestionObjectivesTest {


    private static HexagoneBox Greenbox1;
    private static HexagoneBox Greenbox2;
    private static HexagoneBox Greenbox3;
    private static HexagoneBox Greenbox4;
    private static HexagoneBox Greenbox5;
    private static Objective triangleVert;
    private static Objective ligneVert;
    private static Objective courbeVert;
    private static Board board;
    private static GestionObjectives gestionObjectives;
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static ElementOfTheGame elementOfTheGame;

    @BeforeAll
    public static void setupBox() {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        UniqueObjectCreated.setRetrieveBoxIdWithParameters(retrieveBoxIdWithParameters);
        elementOfTheGame = new ElementOfTheGame();
        board = new Board();
        UniqueObjectCreated.setElementOfTheGame(elementOfTheGame);
        UniqueObjectCreated.setBoard(board);
        gestionObjectives = new GestionObjectives();
        Greenbox1 = new HexagoneBox(-1, 1, 0, Color.Vert, Special.Classique);
        Greenbox2 = new HexagoneBox(0, 1, -1, Color.Vert, Special.Classique);
        Greenbox3 = new HexagoneBox(-1, 2, -1, Color.Vert, Special.Classique);
        Greenbox4 = new HexagoneBox(-1, 0, 1, Color.Vert, Special.Classique);
        Greenbox5 = new HexagoneBox(0, -1, 1, Color.Vert, Special.Classique);
        triangleVert = Objective.PARCELLE1;
        ligneVert = Objective.PARCELLE4;
        courbeVert = Objective.PARCELLE7;
        board.addBox(Greenbox1);
        board.addBox(Greenbox2);
        board.addBox(Greenbox3);
        board.addBox(Greenbox4);
        board.addBox(Greenbox5);
    }

    @Test
    void initialize() {
    }

    @Test
    void getParcelleObjectifs() {
    }

    @Test
    void getJardinierObjectifs() {
    }

    @Test
    void getPandaObjectifs() {
    }

    @Test
    void rollObjective() {
    }

    @Test
    void rollParcelleObjective() {
    }

    @Test
    void rollJardinierObjective() {
    }

    @Test
    void rollPandaObjective() {
    }

    @Test
    void checkObjectives() {
    }

    @Test
    void checkOneObjective() {
    }

    @Test
    void checkPandaObjectives() {
    }

    @Test
    void checkJardinierObjectives() {
    }

    @Test
    void checkParcelleObjectives() {
        assertEquals(gestionObjectives.checkParcelleObjectives(ligneVert),true);
        assertEquals(gestionObjectives.checkParcelleObjectives(triangleVert),true);
        assertEquals(gestionObjectives.checkParcelleObjectives(courbeVert),true);
    }

    @Test
    void printWinner() {
    }

    @Test
    void checkIfBotCanDrawAnObjective() {
    }
}