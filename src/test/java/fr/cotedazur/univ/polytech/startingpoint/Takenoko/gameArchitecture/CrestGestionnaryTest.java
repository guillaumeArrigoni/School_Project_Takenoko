package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.ImpossibleToPlaceIrrigationException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.ElementOfTheGame;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CrestGestionnaryTest {
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static Board board;
    private static BotRandom botRandom;
    private static Random random;
    private static MeteoDice meteoDice;
    private static GestionObjectives gestionObjectives;
    private static CrestGestionnary crestGestionnary;

    /**
     *                  12     13      14
     *              11    4         5      15
     *          10    3       Lac       6      16
     *              9     2         1      17
     *                  8       7       18
     */


    @BeforeEach
    @Order(1)
    public void setUpGeneral() {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters,false);
        gestionObjectives = new GestionObjectives(board,retrieveBoxIdWithParameters);
        random = mock(Random.class);
        meteoDice = mock(MeteoDice.class);

    }

    @Test
    void launchUpdatingCrestWithAddingNewBox() {

    }

    //Should work when irrigation add to the game (box not always irrigated)
    @Test
    void testPlaceIrrigation_WithTwoBoxPlaced() throws ImpossibleToPlaceIrrigationException {
        System.out.println("\nRunning test from : testPlaceIrrigation_WithTwoBoxPlaced\n");
        HexagoneBox BoxAimedToBeIrrigated = setup_testPlaceIrrigation_WithTwoBoxPlaced();
        assertFalse(BoxAimedToBeIrrigated.isIrrigate());
        System.out.println("First test passed");
        Crest crestToPlace = new Crest(5,-10,2);
        Crest secondCrestToPlace = new Crest(5,-15,1);
        board.getCrestGestionnary().placeIrrigation(crestToPlace, board.getPlacedBox());
        assertFalse(BoxAimedToBeIrrigated.isIrrigate());
        System.out.println("Second test passed");
        board.getCrestGestionnary().placeIrrigation(secondCrestToPlace, board.getPlacedBox());
        assertTrue(BoxAimedToBeIrrigated.isIrrigate());
        System.out.println("Third test passed");
    }

    @Test
    void testPlaceIrrigation_WithOneBoxPlaced() throws ImpossibleToPlaceIrrigationException {
        System.out.println("\nRunning test from : testPlaceIrrigation_WithOneBoxPlaced\n");
        HexagoneBox BoxAlreadyIrrigated = setup_testPlaceIrrigation_WithOneBoxPlaced();
        assertTrue(BoxAlreadyIrrigated.isIrrigate());
        System.out.println("First test passed");
        Crest crestToPlace = new Crest(5,-10,2);
        Crest secondCrestToPlace = new Crest(5,-15,1);
        board.getCrestGestionnary().placeIrrigation(crestToPlace, board.getPlacedBox());
        board.getCrestGestionnary().placeIrrigation(secondCrestToPlace, board.getPlacedBox());
        System.out.println("Second test passed"); //Because no Exception is thrown
    }

    @Test
    void testPlaceIrrigation_ExceptionThrownZeroBoxPlaced() {
        Crest nonAlreadyPlaced = new Crest(5,-10,2);
        ImpossibleToPlaceIrrigationException exception = assertThrows(ImpossibleToPlaceIrrigationException.class, () -> {
            board.getCrestGestionnary().placeIrrigation(nonAlreadyPlaced,board.getPlacedBox());
        });
    }

    private HexagoneBox setup_testPlaceIrrigation_WithTwoBoxPlaced(){
        HexagoneBox boxIn5 = new HexagoneBox(1,-1,0,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        HexagoneBox boxIn4 = new HexagoneBox(0,-1,1,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        HexagoneBox boxIn13 = new HexagoneBox(1,-2,1,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(boxIn5);
        board.addBox(boxIn4);
        board.addBox(boxIn13);
        return boxIn13;
    }

    private HexagoneBox setup_testPlaceIrrigation_WithOneBoxPlaced(){
        HexagoneBox boxIn5 = new HexagoneBox(1,-1,0,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        HexagoneBox boxIn4 = new HexagoneBox(0,-1,1,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(boxIn5);
        board.addBox(boxIn4);
        return boxIn4;
    }

    @AfterAll
    public static void ending(){
        System.out.println("\nAll test passed successfully !");
    }
}