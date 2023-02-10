package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.pathirrigation;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxSimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveSimulation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
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
    private static BotRandom bot;

    @BeforeAll
    @Order(1)
    public static void setup() throws CrestNotRegistered, CloneNotSupportedException {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters,false,1,1,new LoggerSevere(true));
        boardSimulation = new BoardSimulation(board);
        retrieveSimulation = boardSimulation.getRetrieveBoxIdWithParameters();
        hexagoneBoxPlaced1 = new HexagoneBoxSimulation(-1,0,1, Color.VERT, Special.CLASSIQUE,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced2 = new HexagoneBoxSimulation(0,1,-1, Color.VERT, Special.CLASSIQUE,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced3 = new HexagoneBoxSimulation(-1,1,0, Color.VERT, Special.CLASSIQUE,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced4 = new HexagoneBoxSimulation(-2,1,1, Color.VERT, Special.CLASSIQUE,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced5 = new HexagoneBoxSimulation(-2,2,0, Color.VERT, Special.CLASSIQUE,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced6 = new HexagoneBoxSimulation(-1,2,-1, Color.VERT, Special.CLASSIQUE,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced7 = new HexagoneBoxSimulation(-3,2,1, Color.VERT, Special.CLASSIQUE,retrieveSimulation,boardSimulation);
        hexagoneBoxPlaced8 = new HexagoneBoxSimulation(0,2,-2, Color.VERT, Special.CLASSIQUE,retrieveSimulation,boardSimulation);
        bot = new BotRandom("bot",board,new Random(),new GestionObjectives(board,retrieveBoxIdWithParameters,new LoggerError(true)),retrieveBoxIdWithParameters,new HashMap<>(),new LogInfoDemo(true));
        boardSimulation.addBox(hexagoneBoxPlaced1,bot);
        boardSimulation.addBox(hexagoneBoxPlaced2,bot);
        boardSimulation.addBox(hexagoneBoxPlaced3,bot);
        boardSimulation.addBox(hexagoneBoxPlaced4,bot);
        boardSimulation.addBox(hexagoneBoxPlaced5,bot);
        boardSimulation.addBox(hexagoneBoxPlaced6,bot);
        boardSimulation.addBox(hexagoneBoxPlaced7,bot);
        boardSimulation.addBox(hexagoneBoxPlaced8,bot);
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
                    boardSimulation.placeIrrigation(generateOptimizePathForSeveralBox.getPathForEachBox().get(box).get(i));
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