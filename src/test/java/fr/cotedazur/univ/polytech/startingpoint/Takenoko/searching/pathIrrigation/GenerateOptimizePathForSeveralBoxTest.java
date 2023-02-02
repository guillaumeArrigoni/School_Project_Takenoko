package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation.GenerateOptimizePathForSeveralBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GenerateOptimizePathForSeveralBoxTest {

    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static Board board;
    private static GenerateOptimizePathForSeveralBoxSimulation generateOptimizePathForSeveralBox;
    private static HexagoneBoxSimulation hexagoneBoxPlaced1;
    private static HexagoneBoxSimulation hexagoneBoxPlaced2;
    private static HexagoneBoxSimulation hexagoneBoxPlaced3;
    private static HexagoneBoxSimulation hexagoneBoxPlaced4;
    private static HexagoneBoxSimulation hexagoneBoxPlaced5;
    private static HexagoneBoxSimulation hexagoneBoxPlaced6;
    private static HexagoneBoxSimulation hexagoneBoxPlaced7;
    private static HexagoneBoxSimulation hexagoneBoxPlaced8;
    private static ArrayList<HexagoneBoxSimulation> boxToIrrigate;
    private static BoardSimulation boardSimulation;
    private static RetrieveSimulation retrieveSimulation;

    @BeforeAll
    @Order(1)
    public static void setup() throws CrestNotRegistered {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters,false,1);
        boardSimulation = new BoardSimulation(board);
        retrieveSimulation = boardSimulation.getRetrieveBoxIdWithParameters();
        hexagoneBoxPlaced1 = new HexagoneBoxSimulation(-1,0,1, Color.Vert, Special.Classique,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced2 = new HexagoneBoxSimulation(0,1,-1, Color.Vert, Special.Classique,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced3 = new HexagoneBoxSimulation(-1,1,0, Color.Vert, Special.Classique,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced4 = new HexagoneBoxSimulation(-2,1,1, Color.Vert, Special.Classique,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced5 = new HexagoneBoxSimulation(-2,2,0, Color.Vert, Special.Classique,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced6 = new HexagoneBoxSimulation(-1,2,-1, Color.Vert, Special.Classique,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced7 = new HexagoneBoxSimulation(-3,2,1, Color.Vert, Special.Classique,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced8 = new HexagoneBoxSimulation(0,2,-2, Color.Vert, Special.Classique,retrieveSimulation,boardSimulation);
        boardSimulation.addBox(hexagoneBoxPlaced1);
        boardSimulation.addBox(hexagoneBoxPlaced2);
        boardSimulation.addBox(hexagoneBoxPlaced3);
        boardSimulation.addBox(hexagoneBoxPlaced4);
        boardSimulation.addBox(hexagoneBoxPlaced5);
        boardSimulation.addBox(hexagoneBoxPlaced6);
        boardSimulation.addBox(hexagoneBoxPlaced7);
        boardSimulation.addBox(hexagoneBoxPlaced8);
        boxToIrrigate = new ArrayList<>(Arrays.asList(hexagoneBoxPlaced8,hexagoneBoxPlaced5));

        generateOptimizePathForSeveralBox = new GenerateOptimizePathForSeveralBoxSimulation(boxToIrrigate);
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
                Arguments.of(false,hexagoneBoxPlaced8)
                );
    }

    private static Stream<Arguments> provideCheckBoxIrrigatedAfter() throws CrestNotRegistered {
        placeIrrigation();
        return Stream.of(
                Arguments.of(true,hexagoneBoxPlaced1),
                Arguments.of(true,hexagoneBoxPlaced2),
                Arguments.of(true,hexagoneBoxPlaced3),
                Arguments.of(false,hexagoneBoxPlaced4),
                Arguments.of(true,hexagoneBoxPlaced5),
                Arguments.of(true,hexagoneBoxPlaced6),
                Arguments.of(false,hexagoneBoxPlaced7),
                Arguments.of(true,hexagoneBoxPlaced8)
        );
    }

    private static void placeIrrigation() {
        boxToIrrigate = new ArrayList<>(Arrays.asList(hexagoneBoxPlaced8,hexagoneBoxPlaced5));
        for (HexagoneBoxPlaced box : boxToIrrigate){
            for (int i=0;i<generateOptimizePathForSeveralBox.getPathForEachBox().get(box).size();i++){
                if (!generateOptimizePathForSeveralBox.getPathForEachBox().get(box).get(i).isIrrigated()){
                    board.placeIrrigation(generateOptimizePathForSeveralBox.getPathForEachBox().get(box).get(i));
                }
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