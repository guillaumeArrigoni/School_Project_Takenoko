package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BoardTest {

    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static Board board;
    private static BotRandom botRandom;
    private static Random random;
    private static MeteoDice meteoDice;
    private static GestionObjectives gestionObjectives;
    private static HexagoneBoxPlaced vert01;
    private static HexagoneBoxPlaced vert02;
    private static HexagoneBoxPlaced vert07;
    private static HexagoneBoxPlaced jaune03;
    private static HexagoneBoxPlaced jaune08;
    private static HexagoneBoxPlaced rouge09Protected;
    private static HexagoneBoxPlaced vert18Engrais;
    private static HexagoneBoxPlaced notPlacedInBoard;
    private static Crest crest1;
    private static Crest crest2;
    /**
     *                  12     13      14
     *              11    4         5      15
     *          10    3       Lac       6      16
     *              9     2         1      17
     *                  8       7       18
     */


    @BeforeAll
    @Order(1)
    public static void setUpGeneral() {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters,true, 1);
        gestionObjectives = new GestionObjectives(board,retrieveBoxIdWithParameters);
        random = mock(Random.class);
        meteoDice = mock(MeteoDice.class);
        vert01 = new HexagoneBoxPlaced(0,1,-1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        vert02 = new HexagoneBoxPlaced(-1,1,0, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        vert07 = new HexagoneBoxPlaced(-1,2,-1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        jaune03 = new HexagoneBoxPlaced(-1,0,1, Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        jaune08 = new HexagoneBoxPlaced(-2,2,0, Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        rouge09Protected = new HexagoneBoxPlaced(-2,1,1, Color.Rouge, Special.Protéger, retrieveBoxIdWithParameters,board);
        vert18Engrais = new HexagoneBoxPlaced(0,2,-2,Color.Vert,Special.Engrais,retrieveBoxIdWithParameters,board);
        notPlacedInBoard = new HexagoneBoxPlaced(1,1,-2,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(vert01);
        board.addBox(vert02);
        board.addBox(vert07);
        board.addBox(jaune03);
        board.addBox(jaune08);
        board.addBox(rouge09Protected);
    }

    private static void setupHeight(ArrayList<Integer> listHeight){
        vert01.setHeightBamboo(listHeight.get(0));
        vert02.setHeightBamboo(listHeight.get(1));
        jaune03.setHeightBamboo(listHeight.get(2));
        vert07.setHeightBamboo(listHeight.get(3));
        jaune08.setHeightBamboo(listHeight.get(4));
        rouge09Protected.setHeightBamboo(listHeight.get(5));
    }

    private static void setupBambooAte(){
        botRandom = new BotRandom("testBot", board, random, meteoDice, gestionObjectives, retrieveBoxIdWithParameters, new HashMap<Color,Integer>());
    }

    @BeforeEach
    @Order(2)
    public void setUpGeneral2() {
        crest1 = new Crest(-5,10,2);
        crest2 = new Crest(-5,15,3);
    }

    /**
     * Parameters of the Arguments :
     *      - HexagoneBox : the box
     *      - int : Number of bamboo hold by the box
     *      - Color : The color of the box
     *      - int : Number of bamboo from the box's Color the bot should have
     */
    private static Stream<Arguments> providePandaMoveAndChecking(){
        setupBambooAte();
        ArrayList<Integer> listHeight = new ArrayList<>(Arrays.asList(2,3,1,0,0,3));
        setupHeight(listHeight);
        return Stream.of(
                Arguments.of(vert01, listHeight.get(0)-1, Color.Vert,1),
                Arguments.of(vert02, listHeight.get(1)-1, Color.Vert,2),
                Arguments.of(jaune03, listHeight.get(2)-1, Color.Jaune,1),
                Arguments.of(vert07, listHeight.get(3), Color.Vert,2),
                Arguments.of(rouge09Protected, listHeight.get(5), Color.Rouge,0)
        );
    }

    private static Stream<Arguments> provideGardenerMoveAndChecking(){
        ArrayList<HexagoneBoxPlaced> listOfBox = new ArrayList<>(Arrays.asList(vert01,vert02,jaune03,vert07,jaune08,rouge09Protected));
        setupHeight(new ArrayList<>(Arrays.asList(3,4,2,0,0,3)));
        return Stream.of(
                Arguments.of(jaune08, new ArrayList<>(Arrays.asList(3,4,2,0,1,3)),
                        new ArrayList<>(Arrays.asList(vert01,vert02,jaune03,vert07,jaune08,rouge09Protected))),
                Arguments.of(vert01, new ArrayList<>(Arrays.asList(4,4,2,1,1,3)),
                        new ArrayList<>(Arrays.asList(vert01,vert02,jaune03,vert07,jaune08,rouge09Protected))),
                Arguments.of(rouge09Protected, new ArrayList<>(Arrays.asList(4,4,2,1,1,4)),
                        new ArrayList<>(Arrays.asList(vert01,vert02,jaune03,vert07,jaune08,rouge09Protected)))
        );
    }

    private static Stream<Arguments> provideBoxAndChecking(){
        return Stream.of(
                Arguments.of(vert07),
                Arguments.of(jaune03),
                Arguments.of(vert01),
                Arguments.of(vert02),
                Arguments.of(rouge09Protected)
        );
    }

    private static void cleanAllBambooInBox(ArrayList<HexagoneBoxPlaced> boxTocleanBamboo){
        for (HexagoneBoxPlaced hexagoneBoxPlaced : boxTocleanBamboo) {
            hexagoneBoxPlaced.setHeightBamboo(0);
        }
    }

    @Test
    void testGetNumberBoxPlaced() {
        assertEquals(7,board.getNumberBoxPlaced());
        board.addBox(vert18Engrais);
        assertEquals(8,board.getNumberBoxPlaced());
    }

    @ParameterizedTest
    @MethodSource("provideGardenerMoveAndChecking")
    void testsetGardenerCoordsBambooHeight(HexagoneBoxPlaced box,
                                           ArrayList<Integer> differentBambooHeightInTheBox1_2_3_7_8_9,
                                           ArrayList<HexagoneBoxPlaced> listOfBox) {
        int[] coords = box.getCoordinates();
        board.setGardenerCoords(coords);
        System.out.println(board.getElementOfTheBoard().getNbOfBambooForEachColorAvailable());
        for (int i =0;i<differentBambooHeightInTheBox1_2_3_7_8_9.size();i++){
            assertEquals(differentBambooHeightInTheBox1_2_3_7_8_9.get(i),listOfBox.get(i).getHeightBamboo());
        }
    }

    @ParameterizedTest
    @MethodSource("provideGardenerMoveAndChecking")
    void testSet_AND_GetGardenerCoords(HexagoneBoxPlaced box) {
        int[] coords = box.getCoordinates();
        board.setGardenerCoords(coords);
        assertEquals(coords,board.getGardenerCoords());
    }

    @ParameterizedTest
    @MethodSource("providePandaMoveAndChecking")
    void testSetPandaCoordsBambooHeightDown(HexagoneBoxPlaced box, int x) {
        int[] coords = box.getCoordinates();
        board.setPandaCoords(coords,botRandom);
        assertTrue(board.getPlacedBox().get(HexagoneBox.generateID(board.getPandaCoords())).getHeightBamboo()==x);
    }

    @ParameterizedTest
    @MethodSource("providePandaMoveAndChecking")
    void testSetPandaCoordsBambooAteEarnByBot(HexagoneBoxPlaced box, int Useless, Color color, int nbBambooAte) {
        int[] coords = box.getCoordinates();
        board.setPandaCoords(coords,botRandom);
        assertTrue(nbBambooAte==botRandom.getBambooEaten().get(color));
    }

    @ParameterizedTest
    @MethodSource("providePandaMoveAndChecking")
    void testSet_AND_GetPandaCoords(HexagoneBoxPlaced box) {
        int[] coords = box.getCoordinates();
        board.setPandaCoords(coords,botRandom);
        assertTrue(board.getPandaCoords()==coords);
    }

    @ParameterizedTest
    @MethodSource("providePandaMoveAndChecking")
    void testGetBoxWithCoordinates(HexagoneBoxPlaced box) {
        assertEquals(box,board.getBoxWithCoordinates(box.getCoordinates()));
    }

    @Test
    void testIsCoordinateInBoard() {
        assertFalse(board.isCoordinateInBoard(notPlacedInBoard.getCoordinates()));
        assertTrue(board.isCoordinateInBoard(vert01.getCoordinates()));
    }
    @AfterAll
    public static void ending(){
        System.out.println("\nAll test passed successfully !");
    }
}