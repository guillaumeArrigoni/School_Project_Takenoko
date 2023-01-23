package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CrestTest {

    static Crest crest1;
    static Crest crest2;
    static Crest crest3;

    @BeforeAll
    @Order(1)
    public static void setUpGeneral() {
        crest1 = new Crest(5, -5, 1);
        crest2 = new Crest(5, 0, 2);
        crest3 = new Crest(0, +5, 3);
    }

    private static Stream<Arguments> provideCrestChildren(){
        return Stream.of(
                Arguments.of(crest1.getListOfCrestChildren(),
                        new ArrayList<Integer>(Arrays.asList(0,-5,3)),
                        new ArrayList<Integer>(Arrays.asList(5,-10,2)),
                        new ArrayList<Integer>(Arrays.asList(10,-5,3)),
                        new ArrayList<Integer>(Arrays.asList(5,0,2))),
                Arguments.of(crest2.getListOfCrestChildren(),
                        new ArrayList<Integer>(Arrays.asList(5,-5,1)),
                        new ArrayList<Integer>(Arrays.asList(10,-5,3)),
                        new ArrayList<Integer>(Arrays.asList(5,5,1)),
                        new ArrayList<Integer>(Arrays.asList(0,5,3))),
                Arguments.of(crest3.getListOfCrestChildren(),
                        new ArrayList<Integer>(Arrays.asList(5,0,2)),
                        new ArrayList<Integer>(Arrays.asList(5,5,1)),
                        new ArrayList<Integer>(Arrays.asList(-5,10,2)),
                        new ArrayList<Integer>(Arrays.asList(-5,5,1)))
        );
    }


    private static Stream<Arguments> provideAdjacentBox(){
        return Stream.of(
                Arguments.of(crest1.getIdOfAdjacentBox(),
                        HexagoneBox.generateID(new int[]{1,-1,0}),
                        HexagoneBox.generateID(new int[]{0,0,0})),
                Arguments.of(crest2.getIdOfAdjacentBox(),
                        HexagoneBox.generateID(new int[]{1,0,-1}),
                        HexagoneBox.generateID(new int[]{0,0,0})),
                Arguments.of(crest3.getIdOfAdjacentBox(),
                        HexagoneBox.generateID(new int[]{0,1,-1}),
                        HexagoneBox.generateID(new int[]{0,0,0}))
        );
    }

    private static Stream<Arguments> provideIdCoords(){
        return Stream.of(
                Arguments.of(new int[]{5,-5},1995005),
                Arguments.of(new int[]{+5,0},1000005),
                Arguments.of(new int[]{0,5},1005000),
                Arguments.of(new int[]{-5,5},1005995),
                Arguments.of(new int[]{-5,0},1000995),
                Arguments.of(new int[]{0,-5},1995000)
        );
    }


    @ParameterizedTest
    @MethodSource("provideAdjacentBox")
    void testCoordinatesOfAdjacentBox(int[] tabAdjacentBox, int coords1, int coords2) {
        assertTrue((tabAdjacentBox[0] == coords1 && tabAdjacentBox[1] == coords2) ||
                (tabAdjacentBox[0] == coords2 && tabAdjacentBox[1] == coords1));
    }


    @ParameterizedTest
    @MethodSource("provideCrestChildren")
    void testListOfCrestChildren(ArrayList<ArrayList<Integer>> listOfCoordsOfCrestChildren,
                                ArrayList<Integer> coords1,
                                ArrayList<Integer> coords2,
                                ArrayList<Integer> coords3,
                                ArrayList<Integer> coords4) {
        assertTrue(listOfCoordsOfCrestChildren.get(0).containsAll(coords1));
        assertTrue(listOfCoordsOfCrestChildren.get(1).containsAll(coords2));
        assertTrue(listOfCoordsOfCrestChildren.get(2).containsAll(coords3));
        assertTrue(listOfCoordsOfCrestChildren.get(3).containsAll(coords4));
    }

    @ParameterizedTest
    @MethodSource("provideIdCoords")
    void testGenerateId(int[] coords, int id) {
        int newId = this.crest1.generateID(coords);
        assertEquals(newId,id);
    }

    @ParameterizedTest
    @MethodSource("provideIdCoords")
    void testSeparateId(int[] coords, int id) {
        int[] newCoords = this.crest1.separateID(id);
        assertTrue((newCoords[0] == coords[0] && newCoords[1] == coords[1]) ||
                (newCoords[1] == coords[0] && newCoords[0] == coords[1]));
    }

}