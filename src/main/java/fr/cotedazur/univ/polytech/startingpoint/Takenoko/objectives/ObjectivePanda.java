package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectivePanda extends Objective{
    public ObjectivePanda(String name, int value, Pattern pattern, ArrayList<Color> colors) {
        super(name, value, TypeObjective.PANDA, pattern, colors);
    }

    Objective MANGER_DEUX_VERTS_1 = new ObjectivePanda("MANGER_DEUX_VERTS_1",3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    Objective MANGER_DEUX_VERTS_2 = new ObjectivePanda("MANGER_DEUX_VERTS_2",3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    Objective MANGER_DEUX_VERTS_3 = new ObjectivePanda("MANGER_DEUX_VERTS_3",3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    Objective MANGER_DEUX_VERTS_4 = new ObjectivePanda("MANGER_DEUX_VERTS_4",3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    Objective MANGER_DEUX_VERTS_5 = new ObjectivePanda("MANGER_DEUX_VERTS_5",3,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    Objective MANGER_DEUX_JAUNES_1 = new ObjectivePanda("MANGER_DEUX_JAUNES_1",4,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    Objective MANGER_DEUX_JAUNES_2 = new ObjectivePanda("MANGER_DEUX_JAUNES_2",4,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    Objective MANGER_DEUX_JAUNES_3 = new ObjectivePanda("MANGER_DEUX_JAUNES_3",4,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    Objective MANGER_DEUX_JAUNES_4 = new ObjectivePanda("MANGER_DEUX_JAUNES_4",4,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    Objective MANGER_DEUX_ROUGES_1 = new ObjectivePanda("MANGER_DEUX_ROUGES_1",5,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge)));
    Objective MANGER_DEUX_ROUGES_2 = new ObjectivePanda("MANGER_DEUX_ROUGES_2",5,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge)));
    Objective MANGER_DEUX_ROUGES_3 = new ObjectivePanda("MANGER_DEUX_ROUGES_3",5,TypeObjective.PANDA,Pattern.MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge)));
    Objective MANGER_TRICOLORE_1 = new ObjectivePanda("MANGER_TRICOLORE_1",6,TypeObjective.PANDA,Pattern.MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge)));
    Objective MANGER_TRICOLORE_2 = new ObjectivePanda("MANGER_TRICOLORE_2",6,TypeObjective.PANDA,Pattern.MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge)));
    Objective MANGER_TRICOLORE_3 = new ObjectivePanda("MANGER_TRICOLORE_3",6,TypeObjective.PANDA,Pattern.MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge)));
}
