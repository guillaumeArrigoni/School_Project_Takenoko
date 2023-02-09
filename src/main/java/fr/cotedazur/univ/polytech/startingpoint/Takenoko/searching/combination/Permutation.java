package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination;

import java.util.ArrayList;

public class Permutation<T> {

    ArrayList<Combination<T>> listOfCombination;

    /**
     * Will created all the combination sorted for a list of combination entered
     * @param listOfCombination : the list of all the combination which the sorted combination will be created
     * @throws CloneNotSupportedException : if Enable to create the clone of a Combination
     */
    public Permutation(ArrayList<Combination<T>> listOfCombination) throws CloneNotSupportedException {
        this.listOfCombination = new ArrayList<>();
        launchPermutation(listOfCombination);
    }

    public ArrayList<Combination<T>> getListOfCombination() {
        return listOfCombination;
    }

    /**
     * Launch the permutation algorithm
     * @param listOfCombination : the list of all the Combination use to create the sorted combination
     * @throws CloneNotSupportedException
     */
    public void launchPermutation(ArrayList<Combination<T>> listOfCombination) throws CloneNotSupportedException {
        for (Combination combination : listOfCombination){
            permutationAlgorithm(new Combination<>(), combination);
        }
    }

    /**
     * Method recursive that will create the new sorted combination wanted
     * @param newCombinationInCreation : the new Combination that is in creation
     * @param combinationUseToCreateNewOne : the Combination that is used to create new one
     * @throws CloneNotSupportedException : is thrown if enable to create a clone of a Combination
     */
    private void permutationAlgorithm(Combination<T> newCombinationInCreation, Combination<T> combinationUseToCreateNewOne) throws CloneNotSupportedException {
        if (newCombinationInCreation.getSize() == combinationUseToCreateNewOne.getSize()) {
            Combination<T> clone = newCombinationInCreation.clone();
            this.listOfCombination.add(clone);
        } else {
            for (T elementInCombination : combinationUseToCreateNewOne.getListOfElementInTheCombination()) {
                if (newCombinationInCreation.getListOfElementInTheCombination().contains(elementInCombination)) {
                    continue;
                }
                newCombinationInCreation.addNewElement(elementInCombination);
                permutationAlgorithm(newCombinationInCreation, combinationUseToCreateNewOne);
                newCombinationInCreation.removeElement(newCombinationInCreation.getSize() - 1);
            }
        }
    }
}