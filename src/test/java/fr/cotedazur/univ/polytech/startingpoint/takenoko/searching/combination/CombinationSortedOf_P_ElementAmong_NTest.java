package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.combination;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CombinationSortedOf_P_ElementAmong_NTest {

    private static CombinationSortedOf_P_ElementAmong_N combinationSortedOf_p_elementAmong_n;
    private static ArrayList<String> lettre;

    @BeforeAll
    @Order(1)
    public static void setup() throws CloneNotSupportedException {
        lettre = new ArrayList<>();
        setupLettre(3);
        combinationSortedOf_p_elementAmong_n = new CombinationSortedOf_P_ElementAmong_N(lettre,2);
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
        assertEquals(listCheckTest, combinationSortedOf_p_elementAmong_n.getListOfCombination());
    }

    private static void setupFirstCheckOf(ArrayList<Combination<String>> listCheckTest) {
        Combination<String> combination1 = new Combination<>(new ArrayList<>(Arrays.asList("a","b")));
        Combination<String> combination2 = new Combination<>(new ArrayList<>(Arrays.asList("b","a")));
        Combination<String> combination3 = new Combination<>(new ArrayList<>(Arrays.asList("a","c")));
        Combination<String> combination4 = new Combination<>(new ArrayList<>(Arrays.asList("c","a")));
        Combination<String> combination5 = new Combination<>(new ArrayList<>(Arrays.asList("b","c")));
        Combination<String> combination6 = new Combination<>(new ArrayList<>(Arrays.asList("c","b")));
        listCheckTest.add(combination1);
        listCheckTest.add(combination2);
        listCheckTest.add(combination3);
        listCheckTest.add(combination4);
        listCheckTest.add(combination5);
        listCheckTest.add(combination6);
    }
}