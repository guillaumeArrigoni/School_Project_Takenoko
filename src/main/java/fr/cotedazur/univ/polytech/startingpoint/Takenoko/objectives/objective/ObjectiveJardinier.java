package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.objective;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.pattern.Pattern;

import java.util.ArrayList;

public class ObjectiveJardinier extends Objective{

    public ObjectiveJardinier(String name, int value, Pattern pattern, ArrayList<Color> colors) {
        super(name, value, TypeObjective.JARDINIER, pattern, colors);
    }
}
