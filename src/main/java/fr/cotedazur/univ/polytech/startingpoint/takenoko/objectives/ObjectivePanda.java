package fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Color;

import java.util.ArrayList;

public class ObjectivePanda extends Objective{
    public ObjectivePanda(String name, int value, Pattern pattern, ArrayList<Color> colors) {
        super(name, value, TypeObjective.PANDA, pattern, colors);
    }
}
