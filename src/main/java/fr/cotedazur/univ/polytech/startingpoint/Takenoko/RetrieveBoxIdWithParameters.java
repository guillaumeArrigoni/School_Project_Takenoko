package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;

import java.util.*;

public class RetrieveBoxIdWithParameters {


    private HashMap<Color, ArrayList<Integer>> BoxColor = new HashMap<>();
    private HashMap<Boolean, ArrayList<Integer>> BoxIsIrrigated = new HashMap<>();
    private HashMap<Integer, ArrayList<Integer>> BoxHeight = new HashMap<>();
    private HashMap<Special, ArrayList<Integer>> BoxSpeciality = new HashMap<>();

    /**
     * Initiate all the value for the Hashmap in order to avoid checking if a key exist
     */
    public RetrieveBoxIdWithParameters(){
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


    /**
     * Method use to get all the id of the box that complete the requirement below
     * All the parameters below are Optionals, they can be passed with an Optional.empty()
     * @param color : is an ArrayList of Color that are requested
     * @param isIrrigated : is an ArrayList of Boolean that are requested
     * @param height : is an ArrayList of Integer that are requested
     * @param speciality : is an ArrayList of Special that are requested
     * @return the ArrayList of all the id that complete the requirement
     */
    public ArrayList<Integer> getAllIdThatCompleteCondition2(Optional<ArrayList<Color>> color, Optional<ArrayList<Boolean>> isIrrigated, Optional<ArrayList<Integer>> height, Optional<ArrayList<Special>> speciality){
        ArrayList<ArrayList<Integer>> allList = new ArrayList<>();
        HashMap<Integer,ArrayList<Integer>> allListInDico = new HashMap<>();
        if (color.isPresent()){
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

    /**
     * Method use to merge all the list that are described.
     * @param listToMerge : an ArrayList of Objects that are the key of the HashMap below
     * @param dicoRelated : a Hashmap that use the previous element are give an ArrayList of id
     * @return an ArrayList with all the id that are merge from all the list
     */
    private ArrayList<Integer> mergeAllList(ArrayList listToMerge, HashMap dicoRelated){
        ArrayList<Integer> tempoList = new ArrayList<>();
        for (int i=0;i<listToMerge.size();i++){
            tempoList.removeAll((ArrayList) dicoRelated.get(listToMerge.get(i)));
            tempoList.addAll((ArrayList) dicoRelated.get(listToMerge.get(i)));
        }
        return tempoList;
    }

}
