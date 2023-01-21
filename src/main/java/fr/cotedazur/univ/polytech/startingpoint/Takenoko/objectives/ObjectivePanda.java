package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectivePanda extends Objective{
    public ObjectivePanda(String name, int value, Pattern pattern, ArrayList<Color> colors) {
        super(name, value, TypeObjective.PANDA, pattern, colors);
    }
}
