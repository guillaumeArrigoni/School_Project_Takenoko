package fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;

import java.util.ArrayList;

public class Objective {

    private final String name;
    private final int value;
    private final TypeObjective type;
    private final Pattern pattern;
    private final ArrayList<Color> colors;

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
