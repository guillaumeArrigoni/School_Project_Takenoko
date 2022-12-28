package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import java.util.ArrayList;
import java.util.Arrays;

public enum Objectives {
    PARCELLE1(2, "PARCELLE", Pattern.PATTERNPARCELLE1, new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE2(3, "PARCELLE", Pattern.PATTERNPARCELLE2, new ArrayList<>(Arrays.asList(Color.Jaune))),
    PARCELLE3(4, "PARCELLE", Pattern.PATTERNPARCELLE3, new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE4(2, "PARCELLE", Pattern.PATTERNPARCELLE4, new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE5(3, "PARCELLE", Pattern.PATTERNPARCELLE5, new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE6(4, "PARCELLE", Pattern.PATTERNPARCELLE6, new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE7(2, "PARCELLE", Pattern.PATTERNPARCELLE7, new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE8(3, "PARCELLE", Pattern.PATTERNPARCELLE8, new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE9(4, "PARCELLE", Pattern.PATTERNPARCELLE9, new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE10(3, "PARCELLE", Pattern.PATTERNPARCELLE10, new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE11(4, "PARCELLE", Pattern.PATTERNPARCELLE11, new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE12(5, "PARCELLE", Pattern.PATTERNPARCELLE12, new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE13(3, "PARCELLE", Pattern.PATTERNPARCELLE13, new ArrayList<>(Arrays.asList( Color.Vert,Color.Jaune))),
    PARCELLE14(4, "PARCELLE", Pattern.PATTERNPARCELLE14, new ArrayList<>(Arrays.asList( Color.Vert,Color.Rouge))),
    PARCELLE15(5, "PARCELLE", Pattern.PATTERNPARCELLE15, new ArrayList<>(Arrays.asList( Color.Rouge,Color.Jaune))),
    JARDINIER1(4,"JARDINIER",Pattern.PATTERNJARDINIER1,new ArrayList<>(Arrays.asList(Color.Vert))),
    JARDINIER2(4,"JARDINIER",Pattern.PATTERNJARDINIER2,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER3(4,"JARDINIER",Pattern.PATTERNJARDINIER3,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER4(3,"JARDINIER",Pattern.PATTERNJARDINIER4,new ArrayList<>(Arrays.asList(Color.Vert))),
    JARDINIER5(4,"JARDINIER",Pattern.PATTERNJARDINIER5,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER6(5,"JARDINIER",Pattern.PATTERNJARDINIER6,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER7(4,"JARDINIER",Pattern.PATTERNJARDINIER7,new ArrayList<>(Arrays.asList(Color.Vert))),
    JARDINIER8(5,"JARDINIER",Pattern.PATTERNJARDINIER8,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER9(6,"JARDINIER",Pattern.PATTERNJARDINIER9,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER10(5,"JARDINIER",Pattern.PATTERNJARDINIER10,new ArrayList<>(Arrays.asList(Color.Vert))),
    JARDINIER11(6,"JARDINIER",Pattern.PATTERNJARDINIER11,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER12(7,"JARDINIER",Pattern.PATTERNJARDINIER12,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER13(6,"JARDINIER",Pattern.PATTERNJARDINIER13,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER14(7,"JARDINIER",Pattern.PATTERNJARDINIER14,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER15(8,"JARDINIER",Pattern.PATTERNJARDINIER15,new ArrayList<>(Arrays.asList(Color.Vert)));


    private int value;
    private String type;
    private Pattern pattern;
    private ArrayList<Color> colors;

    Objectives(int value,String type, Pattern pattern, ArrayList<Color> colors) {
        this.value = value;
        this.type = type;
        this.pattern = pattern;
        this.colors = colors;
    }




    public int getValue() {
        return value;
    }

    public Pattern getPattern() {
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
