package fr.cotedazur.univ.polytech.startingpoint.takenoko;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MeteoDiceTest {

    @Test
    void roll() {
        MeteoDice dice = new MeteoDice();
        ArrayList<MeteoDice.Meteo> listeMeteo = new ArrayList<>(Arrays.asList(MeteoDice.Meteo.values()));
        for(int i=0; i<100; i++){
            assertTrue(listeMeteo.contains(dice.roll()));
        }
    }
}