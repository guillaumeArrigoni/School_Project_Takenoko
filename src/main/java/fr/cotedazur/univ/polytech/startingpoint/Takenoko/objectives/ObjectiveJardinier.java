package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectiveJardinier extends Objective{

    public ObjectiveJardinier(String name, int value, Pattern pattern, ArrayList<Color> colors) {
        super(name, value, TypeObjective.JARDINIER, pattern, colors);
    }

    ObjectiveJardinier PLANTER_SUR_SOURCE_EAU_BAMBOU_VERT = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_VERT",4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectiveJardinier PLANTER_SUR_SOURCE_EAU_BAMBOU_JAUNE = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_JAUNE",4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_SOURCE_EAU_BAMBOU_ROUGE = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_ROUGE",4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_SUR_ENGRAIS_BAMBOU_VERT = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_VERT",3,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectiveJardinier PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE",4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_ENGRAIS_BAMBOU_ROUGE = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_ROUGE",5,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_SUR_PROTEGER_BAMBOU_VERT = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_VERT",4,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectiveJardinier PLANTER_SUR_PROTEGER_BAMBOU_JAUNE = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_JAUNE",5,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_PROTEGER_BAMBOU_ROUGE = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_ROUGE",6,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_SUR_CLASSIQUE_BAMBOU_VERT = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_VERT",5,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectiveJardinier PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE",6,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_CLASSIQUE_BAMBOU_ROUGE = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_ROUGE",7,TypeObjective.JARDINIER,Pattern.PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_DEUX_BAMBOUS_ROUGES = new ObjectiveJardinier("PLANTER_DEUX_BAMBOUS_ROUGES",6,TypeObjective.JARDINIER,Pattern.PLANTER_DEUX_ROUGES,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_TROIS_BAMBOUS_JAUNES = new ObjectiveJardinier("PLANTER_TROIS_BAMBOUS_JAUNES",7,TypeObjective.JARDINIER,Pattern.PLANTER_TROIS_JAUNES,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_QUATRE_BAMBOUS_VERTS = new ObjectiveJardinier("PLANTER_QUATRE_BAMBOUS_VERTS",8,TypeObjective.JARDINIER,Pattern.PLANTER_QUATRE_VERTS,new ArrayList<>(Arrays.asList(Color.Vert)));
}
