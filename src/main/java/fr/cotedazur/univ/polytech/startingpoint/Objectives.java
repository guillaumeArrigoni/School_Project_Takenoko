package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;

import java.util.ArrayList;
import java.util.Arrays;

public enum Objectives {
    OBJECTIF1(1, "parcelle", null, new ArrayList<>(Arrays.asList( Color.Vert, Color.Rouge))),
    OBJECTIF2(1, "parcelle", null, new ArrayList<>(Arrays.asList(Color.Vert, Color.Jaune)));
    private int value;

    private String type;
    private String pattern;
    private ArrayList<Color> colors;

    Objectives(int value,String type, String pattern, ArrayList<Color> colors) {
        this.value = value;
        this.type = type;
        this.pattern = pattern;
        this.colors = colors;
    }

    public int getValue() {
        return value;
    }

    public String getPattern() {
        return pattern;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return "valeur : " + value +", type : " +type + ", pattern : "+ pattern + ", colors : " + colors;
    }
}
