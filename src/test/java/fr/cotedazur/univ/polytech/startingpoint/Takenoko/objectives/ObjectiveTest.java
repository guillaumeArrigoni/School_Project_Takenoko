package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
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
    private static Objective objective6;

    @BeforeAll
    public static void setUp(){
        objective1 = Objective.POSER_TRIANGLE_VERT;
        objective2 = Objective.POSER_LIGNE_JAUNE;
        objective3 = Objective.PLANTER_SUR_PROTEGER_BAMBOU_VERT;
        objective4 = Objective.PLANTER_SUR_ENGRAIS_BAMBOU_VERT;
        objective5 = Objective.POSER_LOSANGE_ROUGE_JAUNE;
        objective6 = Objective.MANGER_DEUX_JAUNES_1;}

    @Test
    void getValue() {
        assertEquals(2, objective1.getValue());
        assertEquals(3, objective2.getValue());
        assertEquals(4,objective3.getValue());
        assertEquals(3,objective4.getValue());
        assertEquals(5, objective5.getValue());
        assertEquals(4,objective6.getValue());
    }

    @Test
    void getPattern() {
        assertEquals(Pattern.POSER_TRIANGLE,objective1.getPattern());
        assertEquals(Pattern.POSER_LIGNE,objective2.getPattern());
        assertEquals(Pattern.PLANTER_SUR_PROTEGER,objective3.getPattern());
        assertEquals(Pattern.PLANTER_SUR_ENGRAIS,objective4.getPattern());
        assertEquals(Pattern.POSER_LOSANGE, objective5.getPattern());
        assertEquals(Pattern.MANGER_DEUX_BAMBOUS, objective6.getPattern());
    }

    @Test
    void getColors() {
        assertEquals(new ArrayList<>(Arrays.asList( Color.Vert)), objective1.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Jaune)), objective2.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Vert)), objective3.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Vert)), objective4.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Rouge, Color.Jaune)), objective5.getColors());
        assertEquals(new ArrayList<>(Arrays.asList( Color.Jaune)), objective6.getColors());
    }

    @Test
    void getType() {
        assertEquals(TypeObjective.PARCELLE, objective1.getType());
        assertEquals(TypeObjective.PARCELLE, objective2.getType());
        assertEquals(TypeObjective.JARDINIER, objective3.getType());
        assertEquals(TypeObjective.JARDINIER, objective4.getType());
        assertEquals(TypeObjective.PARCELLE, objective5.getType());
        assertEquals(TypeObjective.PANDA, objective6.getType());
    }

    @Test
    void testToString() {
        assertEquals("Type : Parcelle, Valeur : 2", objective1.toString());
        assertEquals("Type : Parcelle, Valeur : 3", objective2.toString());
        assertEquals("Type : Jardinier, Valeur : 4", objective3.toString());
        assertEquals("Type : Jardinier, Valeur : 3", objective4.toString());
        assertEquals("Type : Parcelle, Valeur : 5", objective5.toString());
        assertEquals("Type : Panda, Valeur : 4", objective6.toString());
    }
}