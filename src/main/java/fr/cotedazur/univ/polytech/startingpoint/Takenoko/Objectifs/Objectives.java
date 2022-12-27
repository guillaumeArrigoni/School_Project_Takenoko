package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;

import java.util.ArrayList;
import java.util.Arrays;

public enum Objectives {
    PARCELLE1(2, "PARCELLE", "TRIANGLE", new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE2(3, "PARCELLE", "TRIANGLE", new ArrayList<>(Arrays.asList(Color.Jaune))),
    PARCELLE3(4, "PARCELLE", "TRIANGLE", new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE4(2, "PARCELLE", "LIGNE", new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE5(3, "PARCELLE", "LIGNE", new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE6(4, "PARCELLE", "LIGNE", new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE7(2, "PARCELLE", "COURBE", new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE8(3, "PARCELLE", "COURBE", new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE9(4, "PARCELLE", "COURBE", new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE10(3, "PARCELLE", "LOSANGE", new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE11(4, "PARCELLE", "LOSANGE", new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE12(5, "PARCELLE", "LOSANGE", new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE13(3, "PARCELLE", "LOSANGE", new ArrayList<>(Arrays.asList( Color.Vert,Color.Jaune))),
    PARCELLE14(4, "PARCELLE", "LOSANGE", new ArrayList<>(Arrays.asList( Color.Vert,Color.Rouge))),
    PARCELLE15(5, "PARCELLE", "LOSANGE", new ArrayList<>(Arrays.asList( Color.Rouge,Color.Jaune)));
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
