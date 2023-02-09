package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination;

import java.util.ArrayList;

public class CombinationSortedOf_P_ElementAmong_N<T> {

    /**
     * Combination sorted :
     * Input a,b,c ; size 2,3
     * Output : ab  ba  ac  ca  bc  cb      abc bca cab acb bac cba
     */

    private ArrayList<Combination<T>> listOfCombinationIntermediaire;
    private ArrayList<Combination<T>> listOfCombination;
    private Permutation permutation;
    private CombinationsOf_P_elementsAmong_N combinationsOf_p_elementsAmong_n;

    /**
     * The combination sorted of a list is get thanks to :
     *      the list of combination not sorted
     *      all those element are then permuted by all the way possible
     * @param listOfElement : list of all the element that will be used to create the combination
     * @param size : the size of the combiantion that will be created
     * @throws CloneNotSupportedException : : because the class Permutation will be used
     *                          This class need to create clone of already existing combination
     *                          The exception will be thrown if the clone can't be created
     */
    public CombinationSortedOf_P_ElementAmong_N(ArrayList<T> listOfElement,int size) throws CloneNotSupportedException {
        combinationsOf_p_elementsAmong_n = new CombinationsOf_P_elementsAmong_N<>(listOfElement,size);
        this.listOfCombinationIntermediaire = combinationsOf_p_elementsAmong_n.getListOfCombination();
        permutation = new Permutation<T>(this.listOfCombinationIntermediaire);
        this.listOfCombination = permutation.getListOfCombination();
    }

    public ArrayList<Combination<T>> getListOfCombination() {
        return listOfCombination;
    }
}
