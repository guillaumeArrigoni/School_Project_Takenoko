package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveTest {

    private static Objective objective1;
    private static Objective objective2;
    private static Objective objective3;
    private static Objective objective4;
    private static Objective objective5;

    @BeforeAll
    public static void setUp(){
        objective1 = Objective.PARCELLE1;
        objective2 = Objective.PARCELLE5;
        objective3 = Objective.JARDINIER7;
        objective4 = Objective.JARDINIER4;
        objective5 = Objective.PARCELLE15;}

    @Test
    void getValue() {
        assertEquals(2, objective1.getValue());
        assertEquals(3, objective2.getValue());
        assertEquals(4,objective3.getValue());
        assertEquals(3,objective4.getValue());
        assertEquals(5, objective5.getValue());
    }

    @Test
    void getPattern() {
        assertEquals(Pattern.PATTERNPARCELLE1,objective1.getPattern());
        assertEquals(Pattern.PATTERNPARCELLE5,objective2.getPattern());
        assertEquals(Pattern.PATTERNJARDINIER7,objective3.getPattern());
        assertEquals(Pattern.PATTERNJARDINIER4,objective4.getPattern());
        assertEquals(Pattern.PATTERNPARCELLE15, objective5.getPattern());
    }

    @Test
    void getColors() {
        assertEquals(new ArrayList<>(Arrays.asList( Color.Vert)), objective1.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Jaune)), objective2.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Vert)), objective3.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Vert)), objective4.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Rouge, Color.Jaune)), objective5.getColors());
    }

    @Test
    void getType() {
        assertEquals(TypeObjective.PARCELLE, objective1.getType());
        assertEquals(TypeObjective.PARCELLE, objective2.getType());
        assertEquals(TypeObjective.JARDINIER, objective3.getType());
        assertEquals(TypeObjective.JARDINIER, objective4.getType());
        assertEquals(TypeObjective.PARCELLE, objective5.getType());
    }

    @Test
    void testToString() {
        assertEquals("Type : Parcelle, Valeur : 2", objective1.toString());
        assertEquals("Type : Parcelle, Valeur : 3", objective2.toString());
        assertEquals("Type : Jardinier, Valeur : 4", objective3.toString());
        assertEquals("Type : Jardinier, Valeur : 3", objective4.toString());
        assertEquals("Type : Parcelle, Valeur : 5", objective5.toString());
    }
}