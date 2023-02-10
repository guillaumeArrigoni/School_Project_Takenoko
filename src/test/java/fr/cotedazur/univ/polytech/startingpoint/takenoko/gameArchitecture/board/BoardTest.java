package fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.board;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.Logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.Logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.ElementOfTheBoardCheated;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
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
    private static ElementOfTheBoardCheated elementOfTheBoardCheated;
    private static LogInfoDemo logInfoDemo;
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
    public static void setUpGeneral() {
        logInfoDemo = new LogInfoDemo(true);
        elementOfTheBoardCheated = new ElementOfTheBoardCheated();
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters,true, 1,elementOfTheBoardCheated, 2,new LoggerSevere(true));
        gestionObjectives = new GestionObjectives(board,retrieveBoxIdWithParameters,new LoggerError(true));
        random = mock(Random.class);
        meteoDice = mock(MeteoDice.class);
        vert01 = new HexagoneBoxPlaced(0,1,-1, Color.VERT, Special.CLASSIQUE, retrieveBoxIdWithParameters,board);
        vert02 = new HexagoneBoxPlaced(-1,1,0, Color.VERT, Special.CLASSIQUE, retrieveBoxIdWithParameters,board);
        vert07 = new HexagoneBoxPlaced(-1,2,-1, Color.VERT, Special.CLASSIQUE, retrieveBoxIdWithParameters,board);
        jaune03 = new HexagoneBoxPlaced(-1,0,1, Color.JAUNE, Special.CLASSIQUE, retrieveBoxIdWithParameters,board);
        jaune08 = new HexagoneBoxPlaced(-2,2,0, Color.JAUNE, Special.CLASSIQUE, retrieveBoxIdWithParameters,board);
        rouge09Protected = new HexagoneBoxPlaced(-2,1,1, Color.ROUGE, Special.PROTEGER, retrieveBoxIdWithParameters,board);
        vert18Engrais = new HexagoneBoxPlaced(0,2,-2,Color.VERT,Special.ENGRAIS,retrieveBoxIdWithParameters,board);
        notPlacedInBoard = new HexagoneBoxPlaced(1,1,-2,Color.VERT,Special.CLASSIQUE,retrieveBoxIdWithParameters,board);
        bot = new BotRandom("bot",board,new Random(),gestionObjectives,retrieveBoxIdWithParameters,new HashMap<>(),logInfoDemo);
        board.addBox(vert01,bot);
        board.addBox(vert02,bot);
        board.addBox(vert07,bot);
        board.addBox(jaune03,bot);
        board.addBox(jaune08,bot);
        board.addBox(rouge09Protected,bot);
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
        botRandom = new BotRandom("testBot", board, random, gestionObjectives, retrieveBoxIdWithParameters, new HashMap<Color,Integer>(),logInfoDemo);
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
                Arguments.of(vert01, listHeight.get(0)-1, Color.VERT,1),
                Arguments.of(vert02, listHeight.get(1)-1, Color.VERT,2),
                Arguments.of(jaune03, listHeight.get(2)-1, Color.JAUNE,1),
                Arguments.of(vert07, listHeight.get(3), Color.VERT,2),
                Arguments.of(rouge09Protected, listHeight.get(5), Color.ROUGE,0)
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
        board.addBox(vert18Engrais,bot);
        assertEquals(8,board.getNumberBoxPlaced());
    }

    @ParameterizedTest
    @MethodSource("provideGardenerMoveAndChecking")
    void testsetGardenerCoordsBambooHeight(HexagoneBoxPlaced box,
                                           ArrayList<Integer> differentBambooHeightInTheBox1_2_3_7_8_9,
                                           ArrayList<HexagoneBoxPlaced> listOfBox) {
        int[] coords = box.getCoordinates();
        board.setGardenerCoords(coords,bot);
        System.out.println(board.getElementOfTheBoard().getNbOfBambooForEachColorAvailable());
        for (int i =0;i<differentBambooHeightInTheBox1_2_3_7_8_9.size();i++){
            assertEquals(differentBambooHeightInTheBox1_2_3_7_8_9.get(i),listOfBox.get(i).getHeightBamboo());
        }
    }

    @ParameterizedTest
    @MethodSource("provideGardenerMoveAndChecking")
    void testSet_AND_GetGardenerCoords(HexagoneBoxPlaced box) {
        int[] coords = box.getCoordinates();
        board.setGardenerCoords(coords,bot);
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