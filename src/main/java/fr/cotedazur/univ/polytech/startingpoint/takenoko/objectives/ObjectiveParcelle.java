package fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Color;

import java.util.ArrayList;

public class ObjectiveParcelle extends Objective{

    public ObjectiveParcelle(String name, int value, Pattern pattern, ArrayList<Color> colors) {
        super(name, value, TypeObjective.PARCELLE, pattern, colors);
    }
}
