package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import java.util.ArrayList;

public class combinationsOf_P_elementsAmong_N<T> {
    ArrayList<ArrayList<T>> listOfCombination;
    int sizeOfCombination;
    ArrayList<T> listToGetCombination;

    public combinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination, int sizeOfCombination){
        listOfCombination = new ArrayList<>();
        this.sizeOfCombination = sizeOfCombination;
        this.listToGetCombination = listToGetCombination;
    }

    public combinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination){
        this(listToGetCombination,listToGetCombination.size());
    }

    public ArrayList<ArrayList<T>> getListOfCombination() {
        interface_P_CombinationIn_N();
        return listOfCombination;
    }

    public void setSizeOfCombination(int sizeOfCombination) {
        //On réinitialise la liste des combinaisons étant donnée qu'une nouvelle longueur est fourni
        this.listOfCombination = new ArrayList<>();
        this.sizeOfCombination = sizeOfCombination;
    }

    private void interface_P_CombinationIn_N(){
        P_CombinationIn_N(this.listToGetCombination,this.listToGetCombination.size(),
                this.sizeOfCombination,0,new ArrayList<>(),0);
    }

    private void P_CombinationIn_N(ArrayList<T> listToGetCombination,
                                                          int sizeOfListIni,
                                                          int sizeOfCombination,
                                                          int index,
                                                          ArrayList<T> CurrentCombination,
                                                          int i){
        // Current combination is ready, add it
        if (index == sizeOfCombination) {
            ArrayList<T> newCombination = new ArrayList<>();
            for (int j = 0; j < sizeOfCombination; j++)
                newCombination.add(CurrentCombination.get(j));
            this.listOfCombination.add(newCombination);
            return;
        }

        // When no more elements are there to put in CurrentCombination
        if (i >= sizeOfListIni)
            return;

        // current is included, put next at next location
        CurrentCombination.add(index,listToGetCombination.get(i));
        P_CombinationIn_N(listToGetCombination, sizeOfListIni, sizeOfCombination, index + 1, CurrentCombination, i + 1);

        // current is excluded, replace it with next
        P_CombinationIn_N(listToGetCombination, sizeOfListIni, sizeOfCombination, index, CurrentCombination, i + 1);
    }
}
