package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import java.util.ArrayList;

public class Combination<T> {

    private ArrayList<T> listOfElementInTheCombination;
    private int size;

    public Combination(ArrayList<T> listOfElementInTheCombination){
        this.listOfElementInTheCombination = listOfElementInTheCombination;
        this.size = this.listOfElementInTheCombination.size();
    }

    public Combination(){
        this(new ArrayList<>());
    }

    public ArrayList<T> getListOfElementInTheCombination() {
        return listOfElementInTheCombination;
    }

    public int getSize() {
        return size;
    }

    public void addNewElement(T t){
        this.listOfElementInTheCombination.add(t);
        this.size = this.size +1;
    }
}
