package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CombinationSortedOf_P_ElementAmong_NTest {

    private static CombinationsOf_P_elementsAmong_N combinationsOf_p_elementsAmong_n;
    private static ArrayList<String> lettre;

    @BeforeAll
    @Order(1)
    public static void setup(){
        lettre = new ArrayList<>();
        setupLettre(3);
        combinationsOf_p_elementsAmong_n = new CombinationsOf_P_elementsAmong_N<>(lettre,2);

    }

    private static void setupLettre(int size){
        for (int i=97;i<97+size;i++){
            lettre.add(Character.toString((char) i));
        }
    }


    @Test
    void checkCombination() {
        ArrayList<Combination<String>> listCheckTest = new ArrayList<>();
        setupFirstCheckOf(listCheckTest);
        assertEquals(listCheckTest, combinationsOf_p_elementsAmong_n.getListOfCombination());
        listCheckTest = new ArrayList<>();
        Combination<String> combination7 = new Combination<>(new ArrayList<>(Arrays.asList("a","b","c","d")));
        listCheckTest.add(combination7);
        combinationsOf_p_elementsAmong_n.setSizeOfCombination(4);
        assertEquals(listCheckTest,combinationsOf_p_elementsAmong_n.getListOfCombination());
    }

    private static void setupFirstCheckOf(ArrayList<Combination<String>> listCheckTest) {
        Combination<String> combination1 = new Combination<>(new ArrayList<>(Arrays.asList("a","b")));
        Combination<String> combination2 = new Combination<>(new ArrayList<>(Arrays.asList("a","c")));
        Combination<String> combination3 = new Combination<>(new ArrayList<>(Arrays.asList("b","c")));
        Combination<String> combination4 = new Combination<>(new ArrayList<>(Arrays.asList("b","a")));
        Combination<String> combination5 = new Combination<>(new ArrayList<>(Arrays.asList("c","a")));
        Combination<String> combination6 = new Combination<>(new ArrayList<>(Arrays.asList("c","b")));
        listCheckTest.add(combination1);
        listCheckTest.add(combination2);
        listCheckTest.add(combination3);
        listCheckTest.add(combination4);
        listCheckTest.add(combination5);
        listCheckTest.add(combination6);
    }
}