package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.ElementOfTheGame;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BoardTest {

    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static Board board;
    private static HexagoneBox vert01;
    private static HexagoneBox vert02;
    private static HexagoneBox vert07;
    private static HexagoneBox jaune03;
    private static HexagoneBox jaune08;
    private static HexagoneBox rouge09;
    private static ElementOfTheGame elementOfTheGame;
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
        System.out.println("b");
        retrieveBoxIdWithParameters = mock(RetrieveBoxIdWithParameters.class);
        UniqueObjectCreated.setRetrieveBoxIdWithParameters(retrieveBoxIdWithParameters);
        elementOfTheGame = new ElementOfTheGame();
        UniqueObjectCreated.setElementOfTheGame(elementOfTheGame);
        board = new Board();
        UniqueObjectCreated.setBoard(board);
        System.out.println(UniqueObjectCreated.getBoard());
        System.out.println(board);


    }

    @BeforeAll
    @Order(2)
    public static void setUpBox() {
        vert01 = new HexagoneBox(0,1,-1, Color.Vert, Special.Classique);
        vert02 = new HexagoneBox(-1,1,0, Color.Vert, Special.Classique);
        vert07 = new HexagoneBox(-1,2,-1, Color.Vert, Special.Classique);
        jaune03 = new HexagoneBox(-1,0,1, Color.Jaune, Special.Classique);
        jaune08 = new HexagoneBox(-2,2,0, Color.Jaune, Special.Classique);
        rouge09 = new HexagoneBox(-2,1,1, Color.Rouge, Special.Protéger);
        vert01.setHeightBamboo(3);
        vert02.setHeightBamboo(4);
        jaune03.setHeightBamboo(2);
        rouge09.setHeightBamboo(3);
    }

    @BeforeAll
    @Order(3)
    public static void setUpBoard() {
        System.out.println("a");
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board();
        board.addBox(vert01);
        board.addBox(vert02);
        board.addBox(vert07);
        board.addBox(jaune03);
        board.addBox(jaune08);
        board.addBox(rouge09);
    }

    private static Stream<Arguments> provideComparisonEquals(){
        return Stream.of(
                Arguments.of(vert07, 0),
                Arguments.of(jaune03, 1),
                Arguments.of(vert01, 2),
                Arguments.of(vert02, 3),
                Arguments.of(rouge09, 3)
        );
    }

    @Test
    void getGardenerCoords() {
    }

    @ParameterizedTest
    @MethodSource("provideComparisonEquals")
    void testgetPandaCoords(HexagoneBox box, int x) {
        int[] coords = box.getCoordinates();
        board.setPandaCoords(coords);
        assertTrue(board.getPlacedBox().get(HexagoneBox.generateID(board.getPandaCoords())).getHeightBamboo()==x);
    }

    @Test
    void getBoxWithCoordinates() {
    }

    @Test
    void getNumberBoxPlaced() {
    }

    @Test
    void getPlacedBox() {
    }

    @Test
    void getAllBoxPlaced() {
    }

    @Test
    void getAvailableBox() {
    }

    @Test
    void setPandaCoords() {
    }

    @Test
    void setGardenerCoords() {
    }

    @Test
    void isCoordinateInBoard() {
    }

    @Test
    void addBox() {
    }
}