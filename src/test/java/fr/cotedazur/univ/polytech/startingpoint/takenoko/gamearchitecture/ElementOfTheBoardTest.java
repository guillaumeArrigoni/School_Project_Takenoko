package fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.ListOfDifferentSize;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementOfTheBoardTest {

    static ElementOfTheBoard elementOfTheBoardClassic ;
    static ElementOfTheBoard elementOfTheBoardNewBox ;
    static ElementOfTheBoard elementOfTheBoardNewColor ;
    static ElementOfTheBoard elementOfTheBoardNewBoxAndColor ;
    static ElementOfTheBoard elementOfTheBoardAddBox ;
    static ElementOfTheBoard elementOfTheBoardAddColor ;
    static ElementOfTheBoard elementOfTheBoardAddBoxAndColor ;
    static ArrayList<Integer> newBoxNumber;
    static ArrayList<Integer> newBambooNumber;
    static ArrayList<HexagoneBox> newBoxValue;
    static ArrayList<Color> newBambooValue;
    static ArrayList<HexagoneBox> newBoxValueGenerated = new ArrayList<>();
    static ArrayList<HexagoneBox> addBoxValueGenerated = new ArrayList<>();
    static ArrayList<HexagoneBox> defaultBoxValueGenerated = new ArrayList<>();
    static HashMap<Color,Integer> newBambooValueHashMap = new HashMap<>();
    static HashMap<Color,Integer> addBambooValueHashMap = new HashMap<>();
    static LoggerSevere loggerSevere;

    @BeforeAll
    @Order(1)
    public static void setUpGeneral() {
        loggerSevere = new LoggerSevere(true);
        newBoxNumber = new ArrayList<>(Arrays.asList(1,2));
        newBambooNumber = new ArrayList<>(Arrays.asList(2,3));
        newBoxValue = new ArrayList<>(Arrays.asList(
                new HexagoneBox(Color.VERT, Special.CLASSIQUE),
                new HexagoneBox(Color.VERT,Special.PROTEGER)));
        newBambooValue = new ArrayList<>(Arrays.asList(Color.JAUNE,Color.ROUGE));
        elementOfTheBoardClassic = new ElementOfTheBoard(loggerSevere);
        elementOfTheBoardNewBox = new ElementOfTheBoard(true,newBoxNumber,newBoxValue,loggerSevere);
        elementOfTheBoardNewColor = new ElementOfTheBoard(newBambooNumber,newBambooValue,true,loggerSevere);
        elementOfTheBoardNewBoxAndColor = new ElementOfTheBoard(
                newBambooNumber,newBambooValue,true,newBoxNumber,newBoxValue,true,loggerSevere);
        elementOfTheBoardAddBox = new ElementOfTheBoard(false,newBoxNumber,newBoxValue,loggerSevere);
        elementOfTheBoardAddColor = new ElementOfTheBoard(newBambooNumber,newBambooValue,false,loggerSevere);
        elementOfTheBoardAddBoxAndColor = new ElementOfTheBoard(newBambooNumber,newBambooValue,newBoxNumber,newBoxValue,loggerSevere);
        setupBoxGenerationDefault();
        setupBoxGenerationAdd();
        addBoxValueGenerated();
        newBambooValueHashMap = updateHashmap(new HashMap<>(),newBambooValue,newBambooNumber);
        addBambooValueHashMap = updateHashmap(elementOfTheBoardClassic.defaultInstructionBamboo,newBambooValue,newBambooNumber);
    }

    private static void setupBoxGenerationDefault(){
        for (HexagoneBox box : elementOfTheBoardClassic.defaultInstructionBox.keySet()){
            for (int j=0;j<elementOfTheBoardClassic.defaultInstructionBox.get(box);j++){
                defaultBoxValueGenerated.add(new HexagoneBox(box));
            }
        }
    }

    private static void setupBoxGenerationAdd(){
        for (int i=0;i<newBoxNumber.size();i++){
            for (int j=0;j<newBoxNumber.get(i);j++){
                newBoxValueGenerated.add(new HexagoneBox(newBoxValue.get(i)));
            }
        }
    }

    private static void addBoxValueGenerated(){
        addBoxValueGenerated.addAll(newBoxValueGenerated);
        addBoxValueGenerated.addAll(defaultBoxValueGenerated);
    }

    private static HashMap<Color, Integer> updateHashmap(HashMap<Color,Integer> hashToUpdate, ArrayList<Color> listColor, ArrayList<Integer> listInteger){
        HashMap<Color,Integer> hashMapToReturn = (HashMap<Color, Integer>) hashToUpdate.clone();
        for (int i=0;i<listColor.size();i++){
            int value = listInteger.get(i);
            if (hashToUpdate.containsKey(listColor.get(i))){
                value = value + hashToUpdate.get(listColor.get(i));
            }
            hashMapToReturn.put(listColor.get(i),value);
        }
        return hashMapToReturn;
    }

    private static Stream<Arguments> provideStackOfBoxCheck(){
        return Stream.of(
                Arguments.of(defaultBoxValueGenerated, elementOfTheBoardClassic),
                Arguments.of(newBoxValueGenerated, elementOfTheBoardNewBox),
                Arguments.of(defaultBoxValueGenerated, elementOfTheBoardNewColor),
                Arguments.of(newBoxValueGenerated, elementOfTheBoardNewBoxAndColor),
                Arguments.of(addBoxValueGenerated, elementOfTheBoardAddBox),
                Arguments.of(defaultBoxValueGenerated, elementOfTheBoardAddColor),
                Arguments.of(addBoxValueGenerated, elementOfTheBoardAddBoxAndColor)
                );
    }

    private static Stream<Arguments> provideStackOfBambooCheck(){
        System.out.println(elementOfTheBoardClassic.defaultInstructionBamboo);
        return Stream.of(
                Arguments.of(elementOfTheBoardClassic.defaultInstructionBamboo, elementOfTheBoardClassic),
                Arguments.of(elementOfTheBoardClassic.defaultInstructionBamboo, elementOfTheBoardNewBox),
                Arguments.of(newBambooValueHashMap, elementOfTheBoardNewColor),
                Arguments.of(newBambooValueHashMap, elementOfTheBoardNewBoxAndColor),
                Arguments.of(elementOfTheBoardClassic.defaultInstructionBamboo, elementOfTheBoardAddBox),
                Arguments.of(addBambooValueHashMap, elementOfTheBoardAddColor),
                Arguments.of(addBambooValueHashMap, elementOfTheBoardAddBoxAndColor)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStackOfBoxCheck")
    void testGetStackOfBox(ArrayList<HexagoneBox> listBox, ElementOfTheBoard elementOfTheBoard) {
        assertTrue(elementOfTheBoard.getStackOfBox().stackOfBox.containsAll(listBox));
    }

    @ParameterizedTest
    @MethodSource("provideStackOfBambooCheck")
    void testGetNbOfBambooForEachColorAvailable(
            HashMap<Color,Integer> reference,
            ElementOfTheBoard elementOfTheBoard) {
        System.out.println(reference);
        assertEquals(reference,elementOfTheBoard.getNbOfBambooForEachColorAvailable());
    }

    @Test
    void testExceptionTwoListsOfDifferentSize(){
        ListOfDifferentSize exception = assertThrows(ListOfDifferentSize.class, () -> {
            elementOfTheBoardClassic.checkForThrowingListOfDifferentSizeException(newBoxValue.size(),new ArrayList<>(Arrays.asList(1)).size());
        });
    }
}