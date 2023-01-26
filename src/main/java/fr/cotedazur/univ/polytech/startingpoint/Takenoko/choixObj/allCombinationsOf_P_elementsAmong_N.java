package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.IntegerNotPossible;

import java.util.ArrayList;
import java.util.HashMap;

public class allCombinationsOf_P_elementsAmong_N<T> extends combinationsOf_P_elementsAmong_N<T> {

    private HashMap<Integer,ArrayList<ArrayList<T>>> allCombination;
    private int minCombinationSize;
    private int maxCombinationSize;

    public allCombinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination) {
        this(listToGetCombination,listToGetCombination.size());
    }

    public allCombinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination, int sizeOfCombination) {
        this(listToGetCombination,sizeOfCombination,1,listToGetCombination.size());
    }

    public allCombinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination, int sizeOfCombination, int minCombinationSize, int maxCombinationSize) {
        super(listToGetCombination, sizeOfCombination);
        this.allCombination = new HashMap<>();
        this.minCombinationSize = minCombinationSize;
        this.maxCombinationSize = maxCombinationSize;
    }

    private void generateAllCombinationWanted(){
        for(int i=minCombinationSize;i<maxCombinationSize;i++){
            this.setSizeOfCombination(i);
            this.allCombination.put(i,this.getListOfCombination());
        }
    }

    public HashMap<Integer, ArrayList<ArrayList<T>>> getAllCombination() {
        return allCombination;
    }

    public void setMinCombinationSize(int minCombinationSize) {
        checkIfPossibleAndChangeIf(minCombinationSize,1,maxCombinationSize,false);
    }

    public void setMaxCombinationSize(int maxCombinationSize) {
        checkIfPossibleAndChangeIf(maxCombinationSize,minCombinationSize,this.listToGetCombination.size(),true);
    }

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
                    + "Value not changed to bypass error");
        }
    }
}
