package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;

import java.util.*;

public class RetrieveBoxIdWithParameters {

    protected HashMap<Color, ArrayList<Integer>> BoxColor = new HashMap<>();
    protected HashMap<Boolean, ArrayList<Integer>> BoxIsIrrigated = new HashMap<>();
    protected HashMap<Integer, ArrayList<Integer>> BoxHeight = new HashMap<>();
    protected HashMap<Special, ArrayList<Integer>> BoxSpeciality = new HashMap<>();

    /**
     * Initiate all the value for the Hashmap in order to avoid checking if a key exist
     */
    public RetrieveBoxIdWithParameters(){
        this(true);
    }

    public RetrieveBoxIdWithParameters(boolean bool){
        if (bool){
            setupGeneral();
        }
    }

    public RetrieveBoxIdWithParameters copy(){
        RetrieveBoxIdWithParameters copy = new RetrieveBoxIdWithParameters();
        copy.BoxColor = new HashMap<>(this.BoxColor);
        copy.BoxIsIrrigated = new HashMap<>(this.BoxIsIrrigated);
        copy.BoxHeight = new HashMap<>(this.BoxHeight);
        copy.BoxSpeciality = new HashMap<>(this.BoxSpeciality);
        return copy;
    }

    private void setupGeneral(){
        this.BoxIsIrrigated.put(true,new ArrayList<>());
        this.BoxIsIrrigated.put(false,new ArrayList<>());
        for (int i=0;i<Color.values().length;i++){
            this.BoxColor.put(Color.values()[i],new ArrayList<>());
        }
        for (int i=0;i<Special.values().length;i++){
            this.BoxSpeciality.put(Special.values()[i],new ArrayList<>());
        }
        for (int i=0;i<5;i++){
            this.BoxHeight.put(i,new ArrayList<>());
        }
    }

    public void setBoxColor(int id, Color color) {
        ArrayList<Integer> listId = BoxColor.get(color);
        listId.remove(Integer.valueOf(id));
        listId.add(id);
        BoxColor.put(color,listId);
    }

    public void setBoxIsIrrigated(int id, boolean isIrrigated) {
        ArrayList<Integer> listId = BoxIsIrrigated.get(isIrrigated);
        listId.remove(Integer.valueOf(id));
        listId.add(id);
        BoxIsIrrigated.put(isIrrigated,listId);
        ArrayList<Integer> listIdToDelete = BoxIsIrrigated.get(!isIrrigated);
        listIdToDelete.remove(Integer.valueOf(id));
        BoxIsIrrigated.put(!isIrrigated,listIdToDelete);
    }

    public void setBoxHeight(int id, int height) {
        ArrayList<Integer> listId = BoxHeight.get(height);
        listId.remove(Integer.valueOf(id));
        listId.add(id);
        BoxHeight.put(height,listId);
    }

    public void setBoxHeightDelete(int id, int height) {
        ArrayList<Integer> listId = BoxHeight.get(height);
        listId.remove(Integer.valueOf(id));
        BoxHeight.put(height,listId);
    }

    public void setBoxSpeciality(int id, Special speciality) {
        ArrayList<Integer> listId = BoxSpeciality.get(speciality);
        listId.remove(Integer.valueOf(id));
        listId.add(id);
        BoxSpeciality.put(speciality,listId);
        if (speciality != Special.CLASSIQUE){
            ArrayList<Integer> listIdToDelete = BoxSpeciality.get(Special.CLASSIQUE);
            listIdToDelete.remove(Integer.valueOf(id));
            BoxSpeciality.put(Special.CLASSIQUE,listIdToDelete);
        }
    }

    public HashMap<Color, ArrayList<Integer>> getBoxColor() {
        return BoxColor;
    }

    public HashMap<Boolean, ArrayList<Integer>> getBoxIsIrrigated() {
        return BoxIsIrrigated;
    }

    public HashMap<Integer, ArrayList<Integer>> getBoxHeight() {
        return BoxHeight;
    }

    public HashMap<Special, ArrayList<Integer>> getBoxSpeciality() {
        return BoxSpeciality;
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
    public ArrayList<Integer> getAllIdThatCompleteCondition(Optional<ArrayList<Color>> color, Optional<ArrayList<Boolean>> isIrrigated, Optional<ArrayList<Integer>> height, Optional<ArrayList<Special>> speciality){
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
        ArrayList<Integer> listToReturn = new ArrayList<>();
        boolean initiated = false;
        for (int i=0;i<allList.size();i++){
            if (listToReturn.isEmpty() && !allList.get(i).isEmpty() && !initiated){
                listToReturn.addAll(allList.get(i));
                initiated = true;
            } else {
                listToReturn.retainAll(allList.get(i));
            }

        }
        if (color.isEmpty() && isIrrigated.isEmpty() && height.isEmpty() && speciality.isEmpty()){
            return mergeAllList(new ArrayList<>(Arrays.asList(true,false)),BoxIsIrrigated);
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
