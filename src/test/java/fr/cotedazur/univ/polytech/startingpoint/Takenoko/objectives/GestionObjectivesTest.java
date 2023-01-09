package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.ElementOfTheGame;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestionObjectivesTest {


    private static HexagoneBox Greenbox1;
    private static HexagoneBox Greenbox2;
    private static HexagoneBox Greenbox3;
    private static HexagoneBox Greenbox4;
    private static HexagoneBox Greenbox5;
    private static HexagoneBox Yellowbox;
    private static HexagoneBox Engraisbox;
    private static HexagoneBox Protegerbox;
    private static HexagoneBox Redbox;
    private static Objective triangleVert;
    private static Objective ligneVert;
    private static Objective courbeVert;
    private static Objective losangeVert;
    private static Objective planterBambouJauneClassique;
    private static Objective planterBambouJauneEngrais;
    private static Objective planterBambouRougeProteger;
    private static Objective planterDeuxBambousRouges;
    private static Board board;
    private static GestionObjectives gestionObjectives;
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static ElementOfTheGame elementOfTheGame;

    @BeforeAll
    public static void setupBox() {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        elementOfTheGame = new ElementOfTheGame();
        board = new Board(retrieveBoxIdWithParameters);
        gestionObjectives = new GestionObjectives(board,retrieveBoxIdWithParameters);
        Greenbox1 = new HexagoneBox(-1, 1, 0, Color.Vert, Special.Classique, retrieveBoxIdWithParameters);
        Greenbox2 = new HexagoneBox(0, 1, -1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters);
        Greenbox3 = new HexagoneBox(-1, 2, -1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters);
        Greenbox4 = new HexagoneBox(-1, 0, 1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters);
        Greenbox5 = new HexagoneBox(0, -1, 1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters);
        Yellowbox = new HexagoneBox(0, 2, -2, Color.Jaune, Special.Classique, retrieveBoxIdWithParameters);
        Redbox = new HexagoneBox(1,-1,0,Color.Rouge,Special.Classique,retrieveBoxIdWithParameters);
        Engraisbox = new HexagoneBox(1,0,-1,Color.Jaune,Special.Engrais,retrieveBoxIdWithParameters);
        Protegerbox = new HexagoneBox(-2,1,1,Color.Rouge,Special.Protéger,retrieveBoxIdWithParameters);
        triangleVert = Objective.POSER_TRIANGLE_VERT;
        ligneVert = Objective.POSER_LIGNE_VERTE;
        courbeVert = Objective.POSER_COURBE_VERTE;
        losangeVert = Objective.POSER_LOSANGE_VERT;
        planterBambouJauneClassique = Objective.PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE;
        planterBambouJauneEngrais = Objective.PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE;
        planterBambouRougeProteger = Objective.PLANTER_SUR_PROTEGER_BAMBOU_ROUGE;
        planterDeuxBambousRouges = Objective.PLANTER_DEUX_BAMBOUS_ROUGES;
        board.addBox(Greenbox1);
        board.addBox(Greenbox2);
        board.addBox(Greenbox3);
        board.addBox(Greenbox4);
        board.addBox(Greenbox5);
        board.addBox(Yellowbox);
        board.addBox(Redbox);
        board.addBox(Engraisbox);
        board.addBox(Protegerbox);
        Yellowbox.growBamboo();
        Yellowbox.growBamboo();
        Yellowbox.growBamboo();
        Yellowbox.growBamboo();
        Engraisbox.growBamboo();
        Engraisbox.growBamboo();
        Engraisbox.growBamboo();
        Engraisbox.growBamboo();
        Protegerbox.growBamboo();
        Protegerbox.growBamboo();
        Protegerbox.growBamboo();
        Protegerbox.growBamboo();
        Redbox.growBamboo();
        Redbox.growBamboo();
        Redbox.growBamboo();
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
        assertTrue(gestionObjectives.checkJardinierObjectives(planterBambouJauneClassique));
        assertTrue(gestionObjectives.checkJardinierObjectives(planterBambouJauneEngrais));
        assertTrue(gestionObjectives.checkJardinierObjectives(planterBambouRougeProteger));
        assertFalse(gestionObjectives.checkJardinierObjectives(planterDeuxBambousRouges));

    }

    @Test
    void checkParcelleObjectives() {
        assertEquals(gestionObjectives.checkParcelleObjectives(ligneVert),true);
        assertEquals(gestionObjectives.checkParcelleObjectives(triangleVert),true);
        assertEquals(gestionObjectives.checkParcelleObjectives(courbeVert),true);
        assertEquals(gestionObjectives.checkParcelleObjectives(losangeVert),true);
    }

    @Test
    void printWinner() {
    }

    @Test
    void checkIfBotCanDrawAnObjective() {
    }
}