package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectiveParcelle extends Objective{

    public ObjectiveParcelle(String name, int value,Pattern pattern, ArrayList<Color> colors) {
        super(name, value, TypeObjective.PARCELLE, pattern, colors);
    }

    ObjectiveParcelle POSER_TRIANGLE_VERT = new ObjectiveParcelle("POSER_TRIANGLE_VERT",2, TypeObjective.PARCELLE, Pattern.POSER_TRIANGLE, new ArrayList<>(Arrays.asList( Color.Vert)));
    ObjectiveParcelle POSER_TRIANGLE_JAUNE= new ObjectiveParcelle("POSER_TRIANGLE_JAUNE",3, TypeObjective.PARCELLE, Pattern.POSER_TRIANGLE, new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveParcelle POSER_TRIANGLE_ROUGE= new ObjectiveParcelle("POSER_TRIANGLE_ROUGE",4, TypeObjective.PARCELLE, Pattern.POSER_TRIANGLE, new ArrayList<>(Arrays.asList( Color.Rouge)));
    ObjectiveParcelle POSER_LIGNE_VERTE= new ObjectiveParcelle("POSER_LIGNE_VERTE",2, TypeObjective.PARCELLE, Pattern.POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Vert)));
    ObjectiveParcelle POSER_LIGNE_JAUNE= new ObjectiveParcelle("POSER_LIGNE_JAUNE",3, TypeObjective.PARCELLE, Pattern.POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Jaune)));
    ObjectiveParcelle POSER_LIGNE_ROUGE= new ObjectiveParcelle("POSER_LIGNE_ROUGE",4, TypeObjective.PARCELLE, Pattern.POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Rouge)));
    ObjectiveParcelle POSER_COURBE_VERTE= new ObjectiveParcelle("POSER_COURBE_VERTE",2, TypeObjective.PARCELLE, Pattern.POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Vert)));
    ObjectiveParcelle POSER_COURBE_JAUNE= new ObjectiveParcelle("POSER_COURBE_JAUNE",3, TypeObjective.PARCELLE, Pattern.POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Jaune)));
    ObjectiveParcelle POSER_COURBE_ROUGE= new ObjectiveParcelle("POSER_COURBE_ROUGE",4, TypeObjective.PARCELLE, Pattern.POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Rouge)));
    ObjectiveParcelle POSER_LOSANGE_VERT= new ObjectiveParcelle("POSER_LOSANGE_VERT",3, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert)));
    ObjectiveParcelle POSER_LOSANGE_JAUNE= new ObjectiveParcelle("POSER_LOSANGE_JAUNE",4, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Jaune)));
    ObjectiveParcelle POSER_LOSANGE_ROUGE= new ObjectiveParcelle("POSER_LOSANGE_ROUGE",5, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Rouge)));
    ObjectiveParcelle POSER_LOSANGE_VERT_JAUNE= new ObjectiveParcelle("POSER_LOSANGE_VERT_JAUNE",3, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert,Color.Jaune)));
    ObjectiveParcelle POSER_LOSANGE_VERT_ROUGE= new ObjectiveParcelle("POSER_LOSANGE_VERT_ROUGE",4, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert,Color.Rouge)));
    ObjectiveParcelle POSER_LOSANGE_ROUGE_JAUNE= new ObjectiveParcelle("POSER_LOSANGE_ROUGE_JAUNE",5, TypeObjective.PARCELLE, Pattern.POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Rouge,Color.Jaune)));
}
