package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;

import java.util.ArrayList;

public class ObjectiveParcelle extends Objective{

    public ObjectiveParcelle(String name, int value, Pattern pattern, ArrayList<Color> colors) {
        super(name, value, TypeObjective.PARCELLE, pattern, colors);
    }
}
