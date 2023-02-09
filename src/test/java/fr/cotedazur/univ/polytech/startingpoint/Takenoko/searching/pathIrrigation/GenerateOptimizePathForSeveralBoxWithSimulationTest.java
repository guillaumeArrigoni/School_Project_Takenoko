package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GenerateOptimizePathForSeveralBoxWithSimulationTest {

    private static GenerateOptimizePathForSeveralBoxWithSimulation generateOptimizePathForSeveralBoxWithSimulation;
    private static HexagoneBoxPlaced hexagoneBoxPlaced1;
    private static HexagoneBoxPlaced hexagoneBoxPlaced2;
    private static HexagoneBoxPlaced hexagoneBoxPlaced3;
    private static HexagoneBoxPlaced hexagoneBoxPlaced4;
    private static HexagoneBoxPlaced hexagoneBoxPlaced5;
    private static HexagoneBoxPlaced hexagoneBoxPlaced6;
    private static HexagoneBoxPlaced hexagoneBoxPlaced7;
    private static HexagoneBoxPlaced hexagoneBoxPlaced8;
    private static HexagoneBoxPlaced hexagoneBoxPlaced9;
    private static ArrayList<HexagoneBoxPlaced> boxToIrrigate;
    private static Board board;
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    @BeforeAll
    @Order(1)
    public static void setup() throws CrestNotRegistered, CloneNotSupportedException {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters,false,1 ,1);
        retrieveBoxIdWithParameters = board.getRetrieveBoxIdWithParameters();
        hexagoneBoxPlaced1 = new HexagoneBoxPlaced(-1,0,1, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        hexagoneBoxPlaced3 = new HexagoneBoxPlaced(-1,1,0, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        hexagoneBoxPlaced2 = new HexagoneBoxPlaced(0,1,-1, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        hexagoneBoxPlaced4 = new HexagoneBoxPlaced(-2,1,1, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        hexagoneBoxPlaced5 = new HexagoneBoxPlaced(-2,2,0, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        hexagoneBoxPlaced6 = new HexagoneBoxPlaced(-1,2,-1, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        hexagoneBoxPlaced7 = new HexagoneBoxPlaced(-3,2,1, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        hexagoneBoxPlaced8 = new HexagoneBoxPlaced(0,2,-2, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        hexagoneBoxPlaced9 = new HexagoneBoxPlaced(-2,3,-1, Color.Vert, Special.Classique,retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced1);
        board.addBox(hexagoneBoxPlaced2);
        board.addBox(hexagoneBoxPlaced3);
        board.addBox(hexagoneBoxPlaced4);
        board.addBox(hexagoneBoxPlaced5);
        board.addBox(hexagoneBoxPlaced6);
        board.addBox(hexagoneBoxPlaced7);
        board.addBox(hexagoneBoxPlaced8);
        board.addBox(hexagoneBoxPlaced9);
        boxToIrrigate = new ArrayList<>(Arrays.asList(hexagoneBoxPlaced9,hexagoneBoxPlaced4));
        generateOptimizePathForSeveralBoxWithSimulation = new GenerateOptimizePathForSeveralBoxWithSimulation(boxToIrrigate);
    }

    private static Stream<Arguments> provideCheckBoxIrrigatedBefore(){
        return Stream.of(
                Arguments.of(true,hexagoneBoxPlaced1),
                Arguments.of(true,hexagoneBoxPlaced2),
                Arguments.of(true,hexagoneBoxPlaced3),
                Arguments.of(false,hexagoneBoxPlaced4),
                Arguments.of(false,hexagoneBoxPlaced5),
                Arguments.of(false,hexagoneBoxPlaced6),
                Arguments.of(false,hexagoneBoxPlaced7),
                Arguments.of(false,hexagoneBoxPlaced8),
                Arguments.of(false,hexagoneBoxPlaced9)
                );
    }

    private static Stream<Arguments> provideCheckBoxIrrigatedAfter() throws CrestNotRegistered {
        placeIrrigation();
        return Stream.of(
                Arguments.of(true,hexagoneBoxPlaced1),
                Arguments.of(true,hexagoneBoxPlaced2),
                Arguments.of(true,hexagoneBoxPlaced3),
                Arguments.of(true,hexagoneBoxPlaced4),
                Arguments.of(true,hexagoneBoxPlaced5),
                Arguments.of(true,hexagoneBoxPlaced6),
                Arguments.of(false,hexagoneBoxPlaced7),
                Arguments.of(false,hexagoneBoxPlaced8),
                Arguments.of(true,hexagoneBoxPlaced9)
                );
    }

    private static void placeIrrigation() {
        boxToIrrigate = new ArrayList<>(Arrays.asList(hexagoneBoxPlaced8,hexagoneBoxPlaced5));
        ArrayList<Crest> listCrest = generateOptimizePathForSeveralBoxWithSimulation.getChosenPath();
        for(int i=0;i<listCrest.size();i++){
            if (!board.getCrestGestionnary().getListOfCrestIrrigated().contains(listCrest.get(i))){
                board.placeIrrigation(listCrest.get(i));
                System.out.println(listCrest.get(i));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideCheckBoxIrrigatedBefore")
    void checkBoxIrrigatedBefore(boolean bool, HexagoneBoxPlaced box) {
        assertEquals(bool,box.isIrrigate());
    }

    @ParameterizedTest
    @MethodSource("provideCheckBoxIrrigatedAfter")
    void checkBoxIrrigatedAfter(boolean bool, HexagoneBoxPlaced box) {
        assertEquals(bool,box.isIrrigate());
    }
}