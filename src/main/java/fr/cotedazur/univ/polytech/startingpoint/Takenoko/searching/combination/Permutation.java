package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination.Combination;

import java.util.ArrayList;

public class Permutation<T> {

    ArrayList<Combination<T>> listOfCombination;

    public Permutation(ArrayList<Combination<T>> elements) throws CloneNotSupportedException {
        listOfCombination = new ArrayList<>();
        permute(elements);
    }

    public void permute(ArrayList<Combination<T>> elements) throws CloneNotSupportedException {
        for (Combination combination : elements){
            backtrack(new Combination<>(), combination);
        }
    }

    public ArrayList<Combination<T>> getListOfCombination() {
        return listOfCombination;
    }

    private void backtrack(Combination<T> temp, Combination<T> elements) throws CloneNotSupportedException {
        if (temp.getSize() == elements.getSize()) {
            Combination<T> clone = temp.clone();
            this.listOfCombination.add(clone);
        } else {
            for (T t : elements.getListOfElementInTheCombination()) {
                if (temp.getListOfElementInTheCombination().contains(t)) {
                    continue;
                }
                temp.addNewElement(t);
                backtrack(temp, elements);
                temp.removeElement(temp.getSize() - 1);
            }
        }
    }
}