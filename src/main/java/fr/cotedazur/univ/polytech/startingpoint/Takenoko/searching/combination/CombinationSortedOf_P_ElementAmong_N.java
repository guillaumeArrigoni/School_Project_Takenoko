package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination;

import java.util.ArrayList;

public class CombinationSortedOf_P_ElementAmong_N<T> {

    private ArrayList<Combination<T>> listOfCombination;

    public CombinationSortedOf_P_ElementAmong_N(ArrayList<T> listOfElement,int p){
        this.listOfCombination = new ArrayList<>();
        GenerateCombinations(listOfElement,p);
    }

    public ArrayList<Combination<T>> getListOfCombination() {
        return listOfCombination;
    }

    private void GenerateCombinations(ArrayList<T> listOfElement, int p) {
        ArrayList<T> combination = new ArrayList<>();
        backtrack(combination, listOfElement.size(), p, 0, 0, listOfElement);
    }

    private void backtrack(ArrayList<T> combination, int n, int p, int index, int start, ArrayList<T> listOfElement) {
        if (index == p) {
            ArrayList<T> comb = new ArrayList<>();
            for (int i = 0; i < p; i++) {
                comb.add(combination.get(i));
            }
            Combination<T> combinationToPlace = new Combination<T>(comb);
            this.listOfCombination.add(combinationToPlace);
            return;
        }

        for (int i = start; i < n; i++) {
            combination.add(index,listOfElement.get(i));
            backtrack(combination, n, p, index + 1, i + 1, listOfElement);
        }
    }
}
