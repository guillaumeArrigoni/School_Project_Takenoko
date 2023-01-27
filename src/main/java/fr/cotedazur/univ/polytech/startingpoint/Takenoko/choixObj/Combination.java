package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;

import java.util.ArrayList;
import java.util.Objects;

public class Combination<T> implements Comparable<Combination>{

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

    @Override
    public int compareTo(Combination combination) {
        if (this.listOfElementInTheCombination == combination.listOfElementInTheCombination){
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object object){
        if (object == this) {
            return true;
        }
        if (!(object instanceof Combination<?>)) {
            return false;
        }
        Combination secondCombination = (Combination) object;
        for (T t : this.listOfElementInTheCombination){
            if(!secondCombination.listOfElementInTheCombination.contains(t)){
                return false;
            }
        }
        return true;
        //return (secondCombination.listOfElementInTheCombination == this.listOfElementInTheCombination);
    }

    @Override
    public String toString(){
        return String.valueOf(this.listOfElementInTheCombination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.listOfElementInTheCombination);
    }
}
