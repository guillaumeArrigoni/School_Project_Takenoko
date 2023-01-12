package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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

class HexagoneBoxTest {

    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static Board board;
    private static BotRandom botRandom;
    private static Random random;
    private static MeteoDice meteoDice;
    private static GestionObjectives gestionObjectives;
    private static HexagoneBox vert01;
    private static HexagoneBox vert02;
    private static HexagoneBox vert07;
    private static HexagoneBox jaune03;
    private static HexagoneBox jaune08;
    private static HexagoneBox rouge09Protected;
    private static HexagoneBox vert18Engrais;
    private static HexagoneBox vert19;
    private static HexagoneBox vert20;

    /**
     *                  12     13      14
     *              11    4         5      15
     *          10    3       Lac       6      16
     *              9     2         1      17
     *                  8       7       18
     *                      20      19
     */


    @BeforeAll
    @Order(1)
    public static void setUpGeneral() {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters);
        gestionObjectives = new GestionObjectives(board,retrieveBoxIdWithParameters);
        random = mock(Random.class);
        meteoDice = mock(MeteoDice.class);
        botRandom = new BotRandom("testBot", board, random, meteoDice, gestionObjectives, retrieveBoxIdWithParameters, new HashMap<Color,Integer>());
        vert01 = new HexagoneBox(0,1,-1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        vert02 = new HexagoneBox(-1,1,0, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        vert07 = new HexagoneBox(-1,2,-1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        jaune03 = new HexagoneBox(-1,0,1, Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        jaune08 = new HexagoneBox(-2,2,0, Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        rouge09Protected = new HexagoneBox(-2,1,1, Color.Rouge, Special.Protéger, retrieveBoxIdWithParameters,board);
        vert18Engrais = new HexagoneBox(0,2,-2,Color.Vert,Special.Engrais,retrieveBoxIdWithParameters,board);
        vert19 = new HexagoneBox(-1,3,-2,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        vert20 = new HexagoneBox(-2,3,-1,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(vert01);
        board.addBox(vert02);
        board.addBox(vert07);
        board.addBox(jaune03);
        board.addBox(jaune08);
        board.addBox(rouge09Protected);
        board.addBox(vert18Engrais);
        board.addBox(vert19);
        board.addBox(vert20);
    }


    private static Stream<Arguments> provide_Grow_Eat_Bamboo(){
        vert01.setHeightBamboo(3);
        vert02.setHeightBamboo(4);
        jaune03.setHeightBamboo(0);
        return Stream.of(
                Arguments.of(vert01, 4, 2),
                Arguments.of(vert02, 4, 3),
                Arguments.of(jaune03, 1, 0)
        );
    }

    private static Stream<Arguments> provideAdjacentBox(){
        HashMap<Integer,int[]> adjBox = vert07.getAdjacentBox();
        return Stream.of(
                Arguments.of(adjBox.get(1), vert01.getCoordinates()),
                Arguments.of(adjBox.get(2), vert18Engrais.getCoordinates()),
                Arguments.of(adjBox.get(3), vert19.getCoordinates()),
                Arguments.of(adjBox.get(4), vert20.getCoordinates()),
                Arguments.of(adjBox.get(5), jaune08.getCoordinates()),
                Arguments.of(adjBox.get(6), vert02.getCoordinates()),
                Arguments.of(vert07.getAdjacentBoxOfIndex(6), vert02.getCoordinates()),
                Arguments.of(vert01.getAdjacentBoxOfIndex(3), vert18Engrais.getCoordinates())
        );
    }

    private static Stream<Arguments> provideGenerateAndSeparateId(){
        return Stream.of(
                Arguments.of(new int[]{0,1,-1},1990100),
                Arguments.of(new int[]{-1,0,1},1010099),
                Arguments.of(new int[]{1,-1,0},1009901)
                );
    }

    private static Stream<Arguments> provideCrest(){
        return Stream.of(
                Arguments.of(vert01,
                        new Crest(5,5,1),
                        new Crest(5,10,2),
                        new Crest(0,15,3),
                        new Crest(-5,15,1),
                        new Crest(-5,10,2),
                        new Crest(0,5,3))
                );
    }

    private boolean equals(int[] coord1, int[] coord2){
        return (coord1[0]==coord2[0] &&
                coord1[1]==coord2[1] &&
                coord1[2]==coord2[2]);
    }


    @ParameterizedTest
    @MethodSource("provide_Grow_Eat_Bamboo")
    void testGrowBamboo(HexagoneBox box, int x) {
        box.growBamboo();
        assertEquals(x,box.getHeightBamboo());
    }

    @ParameterizedTest
    @MethodSource("provide_Grow_Eat_Bamboo")
    void testEatBamboo(HexagoneBox box, int useless, int x) {
        box.eatBamboo();
        assertEquals(x,box.getHeightBamboo());
    }

    @ParameterizedTest
    @MethodSource("provideAdjacentBox")
    void testGetAdjacentBox_AndOfIndex(int[] boxCoordinate, int[] checkCoordinate) {
        assertTrue(equals(checkCoordinate,boxCoordinate));
    }

    @ParameterizedTest
    @MethodSource("provideGenerateAndSeparateId")
    void testGenerateID(int[] coords, int id) {
        assertEquals(id,HexagoneBox.generateID(coords));
    }

    @ParameterizedTest
    @MethodSource("provideGenerateAndSeparateId")
    void testSeparateID(int[] coords, int id) {
        assertTrue(equals(coords,HexagoneBox.separateID(id)));
    }

    @ParameterizedTest
    @MethodSource("provideCrest")
    void testCrestAroundBox(HexagoneBox box, Crest c1, Crest c2, Crest c3, Crest c4, Crest c5, Crest c6){
        assertEquals(box.getListOfCrestAroundBox().get(0).getId(),c1.getId());
        assertEquals(box.getListOfCrestAroundBox().get(1).getId(),c2.getId());
        assertEquals(box.getListOfCrestAroundBox().get(2).getId(),c3.getId());
        assertEquals(box.getListOfCrestAroundBox().get(3).getId(),c4.getId());
        assertEquals(box.getListOfCrestAroundBox().get(4).getId(),c5.getId());
        assertEquals(box.getListOfCrestAroundBox().get(5).getId(),c6.getId());
    }

    /**
     * Test should work when box are not irrigated by default.
     */
    @Test
    void testIrrigationAutomatic(){
        RetrieveBoxIdWithParameters retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        Board board = new Board(retrieveBoxIdWithParameters);
        HexagoneBox vertClassique = new HexagoneBox(0,1,-1,Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(vertClassique);
        assertEquals(true,vertClassique.isIrrigate());
    }
}