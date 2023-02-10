package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.combination;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.IntegerNotPossible;

import java.util.ArrayList;
import java.util.HashMap;

public class AllCombinationsOf_P_elementsAmong_N<T> extends CombinationsOf_P_elementsAmong_N<T> {

    /**
     * Not sorted :
     * Input : a,b,c
     * Output : a   b   c   ab  ac  bc  abc
     */

    private HashMap<Integer,ArrayList<Combination<T>>> allCombination;
    private int minCombinationSize;
    private int maxCombinationSize;

    /**
     * Will generate all the combination (not sorted) which the size is between the 2 Integer entered
     * By default, the size of the upper combination generated is the size of the list entered and the lowest size is 1
     * @param listToGetCombination : the list with all the element that will be inside the combination created
     */
    public AllCombinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination) {
        this(listToGetCombination,listToGetCombination.size());
    }

    /**
     * Will generate all the combination which the size is between the 2 Integer entered
     * By default the lowest size of the combination created is 1
     * @param listToGetCombination :  the list with all the element that will be inside the combination created
     * @param sizeOfCombination : determinate the upper size of the combination created
     */
    public AllCombinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination, int sizeOfCombination) {
        this(listToGetCombination,sizeOfCombination,1,listToGetCombination.size());
    }

    /**
     * Will generate all the combination which the size is between the 2 Integer entered
     * @param listToGetCombination :  the list with all the element that will be inside the combination created
     * @param sizeOfCombination : useless but needed because of the parent class
     * @param minCombinationSize : the lowest size of the combination
     * @param maxCombinationSize : the upper size of the combination
     */
    public AllCombinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination, int sizeOfCombination, int minCombinationSize, int maxCombinationSize) {
        super(listToGetCombination, sizeOfCombination);
        this.allCombination = new HashMap<>();
        this.minCombinationSize = minCombinationSize;
        this.maxCombinationSize = maxCombinationSize;
    }

    /**
     * Method use to generate all the combination wanted
     */
    private void generateAllCombinationWanted(){
        for(int i=minCombinationSize;i<=maxCombinationSize;i++){
            this.setSizeOfCombination(i);
            this.allCombination.put(i,this.getListOfCombination());
        }
    }

    /**
     * Method use to get all the combination generated
     * @return a hashMap with as :
     *      key : the size of the combination
     *      values : the ArrayList of the combination which the size is the key
     */
    public HashMap<Integer, ArrayList<Combination<T>>> getAllCombination() {
        allCombination = new HashMap<>();
        generateAllCombinationWanted();
        return allCombination;
    }

    /**
     * Method use to change the lowest size of the combination created
     * @param minCombinationSize
     */
    public void setMinCombinationSize(int minCombinationSize) {
        checkIfPossibleAndChangeIf(minCombinationSize,1,maxCombinationSize,false);
    }

    /**
     * Method use to change the upper size of the combination created
     * @param maxCombinationSize
     */
    public void setMaxCombinationSize(int maxCombinationSize) {
        checkIfPossibleAndChangeIf(maxCombinationSize,minCombinationSize,this.listToGetCombination.size(),true);
    }

    /**
     * Method use to check if the change of size is available
     * If it is not catch the error and don't change the size in order to bypass the error and run the code
     * @param newValue : the new value that will be changed
     * @param minValue : the previous minValue
     * @param maxValue : the previous maxValue
     * @param isMax : true if the new value is the max, false if not
     */
    private void checkIfPossibleAndChangeIf(int newValue, int minValue, int maxValue, boolean isMax){
        try {
            if (maxValue<newValue || minValue>newValue){
                throw new IntegerNotPossible(newValue, minValue,maxValue);
            }
            if (isMax){
                this.maxCombinationSize = newValue;
            } else {
                this.minCombinationSize = newValue;
            }
        } catch (IntegerNotPossible e){
            System.err.println("\n  -> An error has occurred : "
                    + e.getErrorTitle()
                    + "\n"
                    + "In line :\n"
                    + e.getStackTrace()
                    + "\n\n"
                    + "Value not changed in order to bypass the error");
        }
    }
}
