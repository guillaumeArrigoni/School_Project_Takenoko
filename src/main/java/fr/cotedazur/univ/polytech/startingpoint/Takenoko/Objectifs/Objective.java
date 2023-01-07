package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import java.util.ArrayList;
import java.util.Arrays;

public enum Objective {
    PARCELLE1(2, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE1, new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE2(3, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE2, new ArrayList<>(Arrays.asList(Color.Jaune))),
    PARCELLE3(4, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE3, new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE4(2, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE4, new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE5(3, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE5, new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE6(4, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE6, new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE7(2, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE7, new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE8(3, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE8, new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE9(4, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE9, new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE10(3, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE10, new ArrayList<>(Arrays.asList( Color.Vert))),
    PARCELLE11(4, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE11, new ArrayList<>(Arrays.asList( Color.Jaune))),
    PARCELLE12(5, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE12, new ArrayList<>(Arrays.asList( Color.Rouge))),
    PARCELLE13(3, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE13, new ArrayList<>(Arrays.asList( Color.Vert,Color.Jaune))),
    PARCELLE14(4, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE14, new ArrayList<>(Arrays.asList( Color.Vert,Color.Rouge))),
    PARCELLE15(5, TypeObjective.PARCELLE, Pattern.PATTERNPARCELLE15, new ArrayList<>(Arrays.asList( Color.Rouge,Color.Jaune))),
    JARDINIER1(4,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER1,new ArrayList<>(Arrays.asList(Color.Vert))),
    JARDINIER2(4,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER2,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER3(4,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER3,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER4(3,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER4,new ArrayList<>(Arrays.asList(Color.Vert))),
    JARDINIER5(4,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER5,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER6(5,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER6,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER7(4,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER7,new ArrayList<>(Arrays.asList(Color.Vert))),
    JARDINIER8(5,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER8,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER9(6,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER9,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER10(5,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER10,new ArrayList<>(Arrays.asList(Color.Vert))),
    JARDINIER11(6,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER11,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER12(7,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER12,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER13(6,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER13,new ArrayList<>(Arrays.asList(Color.Rouge))),
    JARDINIER14(7,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER14,new ArrayList<>(Arrays.asList(Color.Jaune))),
    JARDINIER15(8,TypeObjective.JARDINIER,Pattern.PATTERNJARDINIER15,new ArrayList<>(Arrays.asList(Color.Vert)));


    private int value;
    private TypeObjective type;
    private Pattern pattern;
    private ArrayList<Color> colors;

    Objective(int value, TypeObjective type, Pattern pattern, ArrayList<Color> colors) {
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

    public TypeObjective getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Type : " + this.type.toString() + ", Valeur : " + this.value ;
    }
}
