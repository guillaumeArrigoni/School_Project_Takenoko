package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import java.util.ArrayList;

public class Combination<T> {

    private final ArrayList<T> listOfElementInTheCombination;
    private final int size;

    public Combination(ArrayList<T> listOfElementInTheCombination){
        this.listOfElementInTheCombination = listOfElementInTheCombination;
        this.size = this.listOfElementInTheCombination.size();
    }

    public ArrayList<T> getListOfElementInTheCombination() {
        return listOfElementInTheCombination;
    }

    public int getSize() {
        return size;
    }
}
