package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PermutationTest {

    private static CombinationsOf_P_elementsAmong_N combinationsOf_p_elementsAmong_n;
    private static ArrayList<String> lettre;
    private static Permutation<String> permutation;

    @BeforeAll
    @Order(1)
    public static void setup() throws CloneNotSupportedException {
        lettre = new ArrayList<>();
        setupLettre(3);
        Combination<String> listOfLettre = new Combination<>(lettre);
        ArrayList<Combination<String>> listOfCombinationOfLettre = new ArrayList<>();
        listOfCombinationOfLettre.add(listOfLettre);
        permutation = new Permutation<>(listOfCombinationOfLettre);

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
        assertEquals(listCheckTest, permutation.getListOfCombination());
    }

    private static void setupFirstCheckOf(ArrayList<Combination<String>> listCheckTest) {
        Combination<String> combination1 = new Combination<>(new ArrayList<>(Arrays.asList("a","b","c")));
        Combination<String> combination2 = new Combination<>(new ArrayList<>(Arrays.asList("a","c","b")));
        Combination<String> combination3 = new Combination<>(new ArrayList<>(Arrays.asList("b","a","c")));
        Combination<String> combination4 = new Combination<>(new ArrayList<>(Arrays.asList("b","c","a")));
        Combination<String> combination5 = new Combination<>(new ArrayList<>(Arrays.asList("c","a","b")));
        Combination<String> combination6 = new Combination<>(new ArrayList<>(Arrays.asList("c","b","a")));
        listCheckTest.add(combination1);
        listCheckTest.add(combination2);
        listCheckTest.add(combination3);
        listCheckTest.add(combination4);
        listCheckTest.add(combination5);
        listCheckTest.add(combination6);
    }
}