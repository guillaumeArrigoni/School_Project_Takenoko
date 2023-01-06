package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;

import java.util.*;

public class CheckObjectives {


    HashMap<Color, ArrayList<Integer>> BoxColor = new HashMap<>();
    HashMap<Boolean, ArrayList<Integer>> BoxIsIrrigated = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> BoxHeight = new HashMap<>();
    HashMap<Special, ArrayList<Integer>> BoxSpeciality = new HashMap<>();
    /**
     * Hashmap to know the height, the color, the special characteristic of the box :
     *      Integer : Height of the bamboo
     *      Color : color of the bamboo
     *      Special : if the bamboo is from a special characteristic
     *      ArrayList : id of all the box that complete this characteristic
     */
    HashMap<Integer,HashMap<Color,HashMap<Special,ArrayList<Integer>>>> BoxBambooSize;

    /**
     * Initiate all the value for the Hashmap in order to avoid checking if a key exist
     */
    public CheckObjectives(){
        ArrayList<Integer> empty = new ArrayList<>();
        this.BoxIsIrrigated.put(true,empty);
        this.BoxIsIrrigated.put(false,empty);
        for (int i=0;i<Color.values().length;i++){
            this.BoxColor.put(Color.values()[i],empty);
        }
        for (int i=0;i<Special.values().length;i++){
            this.BoxSpeciality.put(Special.values()[i],empty);
        }
        for (int i=0;i<5;i++){
            this.BoxHeight.put(i,empty);
        }
    }

    public void setBoxColor(int id, Color color) {
        ArrayList<Integer> listId = BoxColor.get(color);
        listId.add(id);
        BoxColor.put(color,listId);
    }

    public void setBoxIsIrrigated(int id, boolean isIrrigated) {
        ArrayList<Integer> listId = BoxIsIrrigated.get(isIrrigated);
        listId.add(id);
        BoxIsIrrigated.put(isIrrigated,listId);
    }

    public void setBoxHeight(int id, int height) {
        ArrayList<Integer> listId = BoxHeight.get(height);
        listId.add(id);
        BoxHeight.put(height,listId);
    }

    public void setBoxSpeciality(int id, Special speciality) {
        ArrayList<Integer> listId = BoxSpeciality.get(speciality);
        listId.add(id);
        BoxSpeciality.put(speciality,listId);
    }

    private ArrayList<Integer> getAllIdThatCompleteCondition(Optional<Color> color, Optional<Boolean> isIrrigated, Optional<Integer> height, Optional<Special> Speciality){
        ArrayList<ArrayList<Integer>> allList = new ArrayList<>();
        if (color.isPresent()){
            allList.add(BoxColor.get(color));
        }
        if (isIrrigated.isPresent()){
            allList.add(BoxIsIrrigated.get(isIrrigated));
        }
        if (height.isPresent()){
            allList.add(BoxHeight.get(height));
        }
        if (Speciality.isPresent()){
            allList.add(BoxSpeciality.get(Speciality));
        }
        ArrayList<Integer> listToReturn;
        if (allList.isEmpty()){
            listToReturn = new ArrayList<>();
        } else {
            listToReturn = allList.get(0);
        }
        for (int i=1;i<allList.size();i++){
            listToReturn.retainAll(allList.get(i));
        }
        return listToReturn;
    }

    private ArrayList<Integer> getAllIdThatCompleteCondition2(Optional<ArrayList<Color>> color, Optional<ArrayList<Boolean>> isIrrigated, Optional<ArrayList<Integer>> height, Optional<ArrayList<Special>> speciality){
        ArrayList<ArrayList<Integer>> allList = new ArrayList<>();
        HashMap<Integer,ArrayList<Integer>> allListInDico = new HashMap<>();
        if (color.isPresent()){
            /*for (int i=0;i<color.get().size();i++){
                ArrayList<Integer> tempoList;
                if (allListInDico.containsKey(1)){
                    tempoList = BoxColor.get(color.get().get(i));
                    tempoList.addAll(BoxColor.get(color.get().get(i)));
                } else {
                    tempoList = allListInDico.get(1);
                }
                allListInDico.put(1,tempoList);
            }*/
            allList.add(mergeAllList(color.get(),BoxColor));
        }
        if (isIrrigated.isPresent()){
            allList.add(mergeAllList(isIrrigated.get(),BoxIsIrrigated));
        }
        if (height.isPresent()){
            allList.add(mergeAllList(height.get(),BoxHeight));
        }
        if (speciality.isPresent()){
            allList.add(mergeAllList(speciality.get(),BoxSpeciality));
        }
        ArrayList<Integer> listToReturn;
        /*if (allListInDico.isEmpty()){
            listToReturn = new ArrayList<>();
        } else {
            listToReturn = allListInDico.get(0);
        }
        for (int i=1;i<allListInDico.size();i++){
            listToReturn.retainAll(allListInDico.get(i));
        }
        return listToReturn;*/
        if (allList.isEmpty()){
            listToReturn = new ArrayList<>();
        } else {
            listToReturn = allList.get(0);
        }
        for (int i=1;i<allList.size();i++){
            listToReturn.retainAll(allList.get(i));
        }
        return listToReturn;
    }

    private ArrayList<Integer> mergeAllList(ArrayList listToMerge, HashMap dicoRelated){
        ArrayList<Integer> tempoList = new ArrayList<>();
        for (int i=0;i<listToMerge.size();i++){
            tempoList.removeAll((ArrayList) dicoRelated.get(listToMerge.get(i)));
            tempoList.addAll((ArrayList) dicoRelated.get(listToMerge.get(i)));
        }
        return tempoList;
    }

}
