package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination.AllCombinationsOf_P_elementsAmong_N;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination.Combination;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AllCombinationsOf_P_elementsAmong_NTest {

    private static AllCombinationsOf_P_elementsAmong_N allCombinationsOf_p_elementsAmong_n;
    private static ArrayList<String> lettre;

    @BeforeAll
    @Order(1)
    public static void setup(){
        lettre = new ArrayList<>();
        setupLettre(3);
        allCombinationsOf_p_elementsAmong_n = new AllCombinationsOf_P_elementsAmong_N<>(lettre,2);

    }

    private static void setupLettre(int size){
        for (int i=97;i<97+size;i++){
            lettre.add(Character.toString((char) i));
        }
    }

    private static Stream<Arguments> provideCheckCombination(){
        Combination<String> combinationOne1 = new Combination<>(new ArrayList<>(Arrays.asList("a")));
        Combination<String> combinationOne2 = new Combination<>(new ArrayList<>(Arrays.asList("b")));
        Combination<String> combinationOne3 = new Combination<>(new ArrayList<>(Arrays.asList("c")));
        Combination<String> combinationTwo1 = new Combination<>(new ArrayList<>(Arrays.asList("a","b")));
        Combination<String> combinationTwo2 = new Combination<>(new ArrayList<>(Arrays.asList("a","c")));
        Combination<String> combinationTwo3 = new Combination<>(new ArrayList<>(Arrays.asList("b","c")));
        Combination<String> combinationThree1 = new Combination<>(new ArrayList<>(Arrays.asList("a","b","c")));
        ArrayList<Combination<String>> checkOne = new ArrayList<>(Arrays.asList(combinationOne1,combinationOne2,combinationOne3));
        ArrayList<Combination<String>> checkTwo = new ArrayList<>(Arrays.asList(combinationTwo1,combinationTwo2,combinationTwo3));
        ArrayList<Combination<String>> checkThree = new ArrayList<>(Arrays.asList(combinationThree1));
        return Stream.of(
                Arguments.of(checkOne,allCombinationsOf_p_elementsAmong_n.getAllCombination().get(1)),
                Arguments.of(checkTwo,allCombinationsOf_p_elementsAmong_n.getAllCombination().get(2)),
                Arguments.of(checkThree,allCombinationsOf_p_elementsAmong_n.getAllCombination().get(3))
        );
    }

    private static Stream<Arguments> provideCheckCombinationAfterSet(){
        allCombinationsOf_p_elementsAmong_n.setMaxCombinationSize(2);
        allCombinationsOf_p_elementsAmong_n.setMinCombinationSize(2);
        Combination<String> combinationTwo1 = new Combination<>(new ArrayList<>(Arrays.asList("a","b")));
        Combination<String> combinationTwo2 = new Combination<>(new ArrayList<>(Arrays.asList("a","c")));
        Combination<String> combinationTwo3 = new Combination<>(new ArrayList<>(Arrays.asList("b","c")));
        ArrayList<Combination<String>> checkTwo = new ArrayList<>(Arrays.asList(combinationTwo1,combinationTwo2,combinationTwo3));
        return Stream.of(
                Arguments.of(checkTwo,allCombinationsOf_p_elementsAmong_n.getAllCombination().get(2))
        );
    }


    @ParameterizedTest
    @MethodSource("provideCheckCombination")
    void checkCombination(ArrayList<Combination<String>> checkingList,ArrayList<Combination<String>> listToCheck) {
        assertEquals(checkingList,listToCheck);
    }

    @ParameterizedTest
    @MethodSource("provideCheckCombinationAfterSet")
    void checkCombinationAfterSet(ArrayList<Combination<String>> checkingList,ArrayList<Combination<String>> listToCheck) {
        assertEquals(checkingList,listToCheck);
    }
}