package fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeObjectiveTest {

    private static TypeObjective typeObjective1;
    private static TypeObjective typeObjective2;
    private static TypeObjective typeObjective3;

    @BeforeAll
    public static void setUp(){
        typeObjective1 = TypeObjective.PARCELLE;
        typeObjective2 = TypeObjective.JARDINIER;
        typeObjective3 = TypeObjective.PANDA;
    }

    @Test
    void getDescription() {
        assertEquals("Parcelle", typeObjective1.getDescription());
        assertEquals("Jardinier", typeObjective2.getDescription());
        assertEquals("Panda", typeObjective3.getDescription());
    }

    @Test
    void testToString() {
        assertEquals("Type : Parcelle", typeObjective1.toString());
        assertEquals("Type : Jardinier", typeObjective2.toString());
        assertEquals("Type : Panda", typeObjective3.toString());
    }
}