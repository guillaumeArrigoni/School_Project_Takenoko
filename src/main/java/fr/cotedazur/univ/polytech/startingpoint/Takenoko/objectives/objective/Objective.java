package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.objective;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.pattern.Pattern;

import java.util.ArrayList;

public class Objective {

    private String name;
    private int value;
    private TypeObjective type;
    private Pattern pattern;
    private ArrayList<Color> colors;

    public Objective(String name, int value, TypeObjective type, Pattern pattern, ArrayList<Color> colors) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.pattern = pattern;
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public TypeObjective getType() {
        return type;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    @Override
    public String toString(){
        return "Objectif : " +  this.getName() + ", Valeur : " + this.getValue();
    }
}
