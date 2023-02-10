package fr.cotedazur.univ.polytech.startingpoint.takenoko.choixobj;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.ObjectivePanda;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.ObjectiveParcelle;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.GetAllBoxFillingThePatternEntered;

import java.util.*;

public class choixApprocheBot {

    public choixApprocheBot(){

    }


    private int nbActionForPanda(ArrayList<ObjectivePanda> objs, AbstractMap<Color,Integer> bamboo){
        int nbAction = 0;
        for (ObjectivePanda obj : objs){
            int nbBamboo = (obj.getColors().size()==3) ? 1 : 2;
            for (Color color : obj.getColors()){
                if(bamboo.containsKey(color)){
                    int differenceBambooNeedAndBambooGet = nbBamboo-bamboo.get(color);
                    if (differenceBambooNeedAndBambooGet>0){
                        nbAction = nbAction + differenceBambooNeedAndBambooGet;
                    }
                }
            }
        }
        return nbAction;
    }


    private void smart_v1(ObjectiveParcelle objectiveParcelle, Board board) throws CrestNotRegistered, CloneNotSupportedException {
        ArrayList<HashMap<Integer, Optional<Color>>> g = generateInstruction(objectiveParcelle);
        GetAllBoxFillingThePatternEntered i = new GetAllBoxFillingThePatternEntered(g.get(0),board,true);
    }

    private ArrayList<HashMap<Integer, Optional<Color>>> generateInstruction(ObjectiveParcelle objectiveParcelle){
        ArrayList<HashMap<Integer,Optional<Color>>>  listOfInstruction = new ArrayList<>();
        listOfInstruction.add(new HashMap<>());
        ArrayList<Color> colors = objectiveParcelle.getColors();
        listOfInstruction.get(0).put(0,Optional.of(colors.get(0)));
        listOfInstruction.get(0).put(1,Optional.of(colors.get(0)));
        switch (objectiveParcelle.getPattern().getForme()){
            case "TRIANGLE" :
                listOfInstruction.get(0).put(2,Optional.empty());
                break;
            case "COURBE" :
                listOfInstruction.get(0).put(3,Optional.empty());
                break;
            case "LIGNE" :
                listOfInstruction.get(0).put(4,Optional.empty());
                break;
            case "LOSANGE" :
                listOfInstruction.add(0,new HashMap<>());
                HashMap<Integer,Optional<Color>> instruction = new HashMap<>();
                for (int i =0;i<2;i++){
                    instruction = new HashMap<>();
                    instruction.put(0,Optional.of(colors.get(i)));
                    instruction.put(1,Optional.of(colors.get(i)));
                    instruction.put(2,Optional.of(colors.get(1-i)));
                    instruction.put(3,Optional.empty());
                    listOfInstruction.add(i,instruction);

                    instruction = new HashMap<>();
                    instruction.put(0,Optional.of(colors.get(i)));
                    instruction.put(1,Optional.of(colors.get(i)));
                    instruction.put(2,Optional.empty());
                    instruction.put(3,Optional.of(colors.get(1-i)));
                    listOfInstruction.add(1+i,instruction);

                    instruction = new HashMap<>();
                    instruction.put(0,Optional.of(colors.get(i)));
                    instruction.put(1,Optional.empty());
                    instruction.put(2,Optional.of(colors.get(1-i)));
                    instruction.put(3,Optional.of(colors.get(1-i)));
                    listOfInstruction.add(2+i,instruction);
                }
        }
        return listOfInstruction;
    }
}
