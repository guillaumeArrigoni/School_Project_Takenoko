package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import java.util.ArrayList;
import java.util.Arrays;

public enum Objective {
    POSER_TRIANGLE_VERT(2, TypeObjective.PARCELLE, Pattern.POSER_TRIANGLE, new ArrayList<>(Arrays.asList( Color.Vert))),
    POSER_TRIANGLE_JAUNE(3, TypeObjective.PARCELLE, Pattern.POSER_TRIANGLE, new ArrayList<>(Arrays.asList(Color.Jaune))),
    POSER_TRIANGLE_ROUGE(4, TypeObjective.PARCELLE, Pattern.POSER_TRIANGLE, new ArrayList<>(Arrays.asList( Color.Rouge))),
    POSER_LIGNE_VERTE(2, TypeObjective.PARCELLE, Pattern.POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Vert))),
    POSER_LIGNE_JAUNE(3, TypeObjective.PARCELLE, Pattern.POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Jaune))),
    POSER_LIGNE_ROUGE(4, TypeObjective.PARCELLE, Pattern.POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Rouge))),
    POSER_COURBE_VERTE(2, TypeObjective.PARCELLE, Pattern.POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Vert))),
    POSER_COURBE_JAUNE(3, TypeObjective.PARCELLE, Pattern.POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Jaune))),
    POSER_COURBE_ROUGE(4, TypeObjective.PARCELLE, Pattern.POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Rouge))),
    POSER_LOSANGE_VERT(3, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert))),
    POSER_LOSANGE_JAUNE(4, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Jaune))),
    POSER_LOSANGE_ROUGE(5, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Rouge))),
    POSER_LOSANGE_VERT_JAUNE(3, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert,Color.Jaune))),
    POSER_LOSANGE_VERT_ROUGE(4, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert,Color.Rouge))),
    POSER_LOSANGE_ROUGE_JAUNE(5, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Rouge,Color.Jaune))),
    PLANTER_SUR_SOURCE_EAU_BAMBOU_VERT(4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Vert))),
    PLANTER_SUR_SOURCE_EAU_BAMBOU_JAUNE(4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Jaune))),
    PLANTER_SUR_SOURCE_EAU_BAMBOU_ROUGE(4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Rouge))),
    PLANTER_SUR_ENGRAIS_BAMBOU_VERT(3,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Vert))),
    PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE(4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Jaune))),
    PLANTER_SUR_ENGRAIS_BAMBOU_ROUGE(5,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Rouge))),
    PLANTER_SUR_PROTEGER_BAMBOU_VERT(4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Vert))),
    PLANTER_SUR_PROTEGER_BAMBOU_JAUNE(5,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Jaune))),
    PLANTER_SUR_PROTEGER_BAMBOU_ROUGE(6,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Rouge))),
    PLANTER_SUR_CLASSIQUE_BAMBOU_VERT(5,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Vert))),
    PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE(6,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Jaune))),
    PLANTER_SUR_CLASSIQUE_BAMBOU_ROUGE(7,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Rouge))),
    PLANTER_DEUX_BAMBOUS_ROUGES(6,TypeObjective.JARDINIER,Pattern.PLANTER_DEUX_ROUGES,new ArrayList<>(Arrays.asList(Color.Rouge))),
    PLANTER_TROIS_BAMBOUS_JAUNES(7,TypeObjective.JARDINIER,Pattern.PLANTER_TROIS_JAUNES,new ArrayList<>(Arrays.asList(Color.Jaune))),
    PLANTER_QUATRE_BAMBOUS_VERTS(8,TypeObjective.JARDINIER,Pattern.PLANTER_QUATRE_VERTS,new ArrayList<>(Arrays.asList(Color.Vert))),
    MANGER_DEUX_VERTS_1(3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert))),
    MANGER_DEUX_VERTS_2(3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert))),
    MANGER_DEUX_VERTS_3(3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert))),
    MANGER_DEUX_VERTS_4(3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert))),
    MANGER_DEUX_VERTS_5(3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert))),
    MANGER_DEUX_JAUNES_1(4,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune))),
    MANGER_DEUX_JAUNES_2(4,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune))),
    MANGER_DEUX_JAUNES_3(4,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune))),
    MANGER_DEUX_JAUNES_4(4,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune))),
    MANGER_DEUX_ROUGES_1(5,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge))),
    MANGER_DEUX_ROUGES_2(5,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge))),
    MANGER_DEUX_ROUGES_3(5,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge))),
    MANGER_TRICOLORE_1(6,TypeObjective.PANDA,Pattern.MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge))),
    MANGER_TRICOLORE_2(6,TypeObjective.PANDA,Pattern.MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge))),
    MANGER_TRICOLORE_3(6,TypeObjective.PANDA,Pattern.MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge)));

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
        return this.getType().toString() + ", Valeur : " + this.value ;
    }
}
