package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination;

import java.util.ArrayList;

public class CombinationSortedOf_P_ElementAmong_N<T> {

    private ArrayList<Combination<T>> listOfCombinationIntermediaire;
    private ArrayList<Combination<T>> listOfCombination;
    private Permutation permutation;
    private CombinationsOf_P_elementsAmong_N combinationsOf_p_elementsAmong_n;

    public CombinationSortedOf_P_ElementAmong_N(ArrayList<T> listOfElement,int p) throws CloneNotSupportedException {
        combinationsOf_p_elementsAmong_n = new CombinationsOf_P_elementsAmong_N<>(listOfElement,p);
        this.listOfCombinationIntermediaire = combinationsOf_p_elementsAmong_n.getListOfCombination();
        permutation = new Permutation<T>(this.listOfCombinationIntermediaire);
        this.listOfCombination = permutation.getListOfCombination();
    }

    public ArrayList<Combination<T>> getListOfCombination() {
        return listOfCombination;
    }
}
