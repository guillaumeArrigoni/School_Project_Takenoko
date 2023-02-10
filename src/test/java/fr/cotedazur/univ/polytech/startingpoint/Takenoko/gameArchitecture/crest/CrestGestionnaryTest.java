package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.ImpossibleToPlaceIrrigationException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CrestGestionnaryTest {
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static Board board;
    private static HexagoneBoxPlaced boxIn4 ;
    private static HexagoneBoxPlaced boxIn5 ;
    private static HexagoneBoxPlaced boxIn13 ;
    private static Crest crestToPlace;
    private static Crest secondCrestToPlace;
    private static Crest crestThird;
    private static Crest crestFourth;
    private static Crest crestFifth;
    private static Crest crestSixth;
    private static Crest crestOneRangeToIrrigated02;
    private static Crest crestOneRangeToIrrigated03;
    private static Crest crestOneRangeToIrrigated04;
    private static Crest crestOneRangeToIrrigated05;
    private static Crest crestOneRangeToIrrigated06;
    private static BotRandom bot;


    /**
     *                  12     13      14
     *              11    4         5      15
     *          10    3       Lac       6      16
     *              9     2         1      17
     *                  8       7       18
     */
    @BeforeAll
    @Order(1)
    public static void setupGlobal(){
        crestToPlace = new Crest(5,-10,2);
        secondCrestToPlace = new Crest(5,-15,1);
        crestThird = new Crest(5,-20,2);
        crestFourth = new Crest(10,-25,3);
        crestFifth = new Crest(15,-30,2);
        crestSixth = new Crest(-15,25,1);
        crestOneRangeToIrrigated02 = new Crest(10,-5,3);
        crestOneRangeToIrrigated03 = new Crest(5,5,1);
        crestOneRangeToIrrigated04 = new Crest(-5,10,2);
        crestOneRangeToIrrigated05 = new Crest(-10,5,3);
        crestOneRangeToIrrigated06 = new Crest(-5,-5,1);
        bot = new BotRandom("bot",board,new Random(),new GestionObjectives(board,retrieveBoxIdWithParameters,new LoggerError(true)),retrieveBoxIdWithParameters,new HashMap<>(),new LogInfoDemo(true));
    }

    @BeforeEach
    @Order(2)
    public void setUpGeneral() {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters,false, 1, 2,new LoggerSevere(true));
        boxIn5 = new HexagoneBoxPlaced(1,-1,0,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        boxIn4 = new HexagoneBoxPlaced(0,-1,1,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        boxIn13 = new HexagoneBoxPlaced(1,-2,1,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
    }

    private void setup_placeBox_5_4_13(){
        board.addBox(boxIn5, bot);
        board.addBox(boxIn4, bot);
        board.addBox(boxIn13, bot);
    }

    private void setup_placeBox_5_4(){
        board.addBox(boxIn5, bot);
        board.addBox(boxIn4, bot);
    }

    private static Stream<Arguments> provideCrestInOrderToCheckIfInHashmap(){
        return Stream.of(
                Arguments.of(crestToPlace, true, 1),
                Arguments.of(secondCrestToPlace, true, 2),
                Arguments.of(crestThird, true, 3),
                Arguments.of(crestFourth, true, 4),
                Arguments.of(crestFifth, false, 5),
                Arguments.of(crestSixth, true, 4)
        );
    }

    private static Stream<Arguments> provideCrestInOrderToCheckIfChildless(){
        return Stream.of(
                Arguments.of(crestToPlace, false),
                Arguments.of(secondCrestToPlace, false),
                Arguments.of(crestThird, false),
                Arguments.of(crestFourth, true),
                Arguments.of(crestSixth, true)
        );
    }

    private static Stream<Arguments> provideCrestInOrderToCheckIfOneToIrrigated(){
        return Stream.of(
                Arguments.of(crestToPlace, true),
                Arguments.of(secondCrestToPlace, false),
                Arguments.of(crestThird, false),
                Arguments.of(crestFourth, false),
                Arguments.of(crestFifth, false),
                Arguments.of(crestSixth, false),
                Arguments.of(crestOneRangeToIrrigated02, true),
                Arguments.of(crestOneRangeToIrrigated03, true),
                Arguments.of(crestOneRangeToIrrigated04, true),
                Arguments.of(crestOneRangeToIrrigated05, true),
                Arguments.of(crestOneRangeToIrrigated06, true)
                );
    }

    @ParameterizedTest
    @MethodSource("provideCrestInOrderToCheckIfInHashmap")
    void testIf_CrestAreGenerated_And_PlacedInHashMapParentToChildren(Crest crest, boolean bool) {
        setup_placeBox_5_4_13();
        assertEquals(bool, board.getCrestGestionnary().getLinkCrestParentToCrestChildren().containsKey(crest));
        System.out.println();
    }

    @ParameterizedTest
    @MethodSource("provideCrestInOrderToCheckIfInHashmap")
    void testIf_CrestAreGenerated_And_PlacedInHashMapChildToParent(Crest crest, boolean bool) {
        setup_placeBox_5_4_13();
        assertEquals(bool, board.getCrestGestionnary().getLinkCrestChildrenToCrestParent().containsKey(crest));
    }

    @ParameterizedTest
    @MethodSource("provideCrestInOrderToCheckIfInHashmap")
    void testIf_CrestAreGenerated_And_PlacedInHashMapRangeToIrrigated(Crest crest, boolean bool) {
        setup_placeBox_5_4_13();
        assertEquals(bool, board.getCrestGestionnary().getRangeFromIrrigated().containsKey(crest));
    }

    @ParameterizedTest
    @MethodSource("provideCrestInOrderToCheckIfInHashmap")
    void testIf_CrestAreGenerated_And_CoorectValueIn_RangeToIrrigated(Crest crest, boolean bool, int range) {
        setup_placeBox_5_4_13();
        if (bool){
            assertEquals(range, board.getCrestGestionnary().getRangeFromIrrigated().get(crest));
        }
    }

    @ParameterizedTest
    @MethodSource("provideCrestInOrderToCheckIfOneToIrrigated")
    void testIf_CrestAreGenerated_And_PlacedInOneRangeToIrrigated(Crest crest, boolean bool) {
        setup_placeBox_5_4_13();
        assertEquals(bool, board.getCrestGestionnary().getListOfCrestOneRangeToIrrigated().contains(crest));
    }

    @ParameterizedTest
    @MethodSource("provideCrestInOrderToCheckIfChildless")
    void testIf_CrestAreGenerated_And_PlacedInChildless(Crest crest, boolean bool) {
        setup_placeBox_5_4_13();
        assertEquals(bool, board.getCrestGestionnary().getParentChildless().contains(crest));
    }

    @Test
    void testPlaceIrrigation_WithTwoBoxPlaced() throws ImpossibleToPlaceIrrigationException {
        System.out.println("\nRunning test from : testPlaceIrrigation_WithTwoBoxPlaced\n");
        setup_placeBox_5_4_13();
        HexagoneBoxPlaced BoxAimedToBeIrrigated = boxIn13;
        assertFalse(BoxAimedToBeIrrigated.isIrrigate());
        System.out.println("First test passed");
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
        setup_placeBox_5_4();
        HexagoneBoxPlaced BoxAlreadyIrrigated = boxIn4;
        assertTrue(BoxAlreadyIrrigated.isIrrigate());
        System.out.println("First test passed");
        board.getCrestGestionnary().placeIrrigation(crestToPlace, board.getPlacedBox());
        board.getCrestGestionnary().placeIrrigation(secondCrestToPlace, board.getPlacedBox());
        System.out.println("Second test passed"); //Because no Exception is thrown
    }

    @Test
    void testPlaceIrrigation_ExceptionThrownZeroBoxPlaced() {
        Crest nonAlreadyPlaced = crestToPlace;
        ImpossibleToPlaceIrrigationException exception = assertThrows(ImpossibleToPlaceIrrigationException.class, () -> {
            board.getCrestGestionnary().placeIrrigation(nonAlreadyPlaced,board.getPlacedBox());
        });
    }

    @AfterAll
    public static void ending(){
        System.out.println("\nAll test passed successfully !");
    }
}