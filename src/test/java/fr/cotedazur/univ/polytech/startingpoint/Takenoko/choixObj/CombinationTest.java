package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CombinationTest {

    private static Combination<String> combination;

    @BeforeAll
    @Order(1)
    public static void setup(){
        combination = new Combination();
        combination.addNewElement("r");
        combination.addNewElement("t");
    }

    @Test
    void checkComparableMethod() {
        ArrayList<String> listToCheck = new ArrayList<>(Arrays.asList("r","t"));
        ArrayList<String> secondListToCheck = new ArrayList<>(Arrays.asList("t","r"));
        Combination combinationToCheck1 = new Combination<>(listToCheck);
        Combination combinationToCheck2 = new Combination<>(secondListToCheck);
        assertEquals(combinationToCheck1,combination);
        assertEquals(combinationToCheck2,combination);
    }

    @Test
    void getSize() {
        assertEquals(2,combination.getSize());
    }
}