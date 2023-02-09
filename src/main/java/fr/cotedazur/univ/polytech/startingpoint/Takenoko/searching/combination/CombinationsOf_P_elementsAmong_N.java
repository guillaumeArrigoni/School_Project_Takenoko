package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination;

import java.util.ArrayList;

public class CombinationsOf_P_elementsAmong_N<T> {

    /**
     * Not sorted :
     * Input : a,b,c ; size 2
     * Output : ab  ac  bc
     */

    ArrayList<Combination<T>> listOfCombination;
    int sizeOfCombination;
    ArrayList<T> listToGetCombination;

    /**
     * Will generate all the combination (not sorted) of the list entered with the size entered
     * @param listToGetCombination : the list of the element that will be inside the combination
     * @param sizeOfCombination : the size of the combinations created
     */
    public CombinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination, int sizeOfCombination){
        listOfCombination = new ArrayList<>();
        this.sizeOfCombination = sizeOfCombination;
        this.listToGetCombination = listToGetCombination;
    }

    /**
     * Will generate all the combination (not sorted) of the list entered with the size entered
     * By default the size of the combination is the size of the list entered
     * @param listToGetCombination
     */
    public CombinationsOf_P_elementsAmong_N(ArrayList<T> listToGetCombination){
        this(listToGetCombination,listToGetCombination.size());
    }

    /**
     * Method use to get the combinations
     * The generation of the combinations is when this method is called
     * @return
     */
    public ArrayList<Combination<T>> getListOfCombination() {
        this.listOfCombination = new ArrayList<>();
        interface_P_CombinationIn_N();
        return listOfCombination;
    }

    /**
     * Method use to change the size of the combination
     * @param sizeOfCombination : the newest size of the combination
     */
    public void setSizeOfCombination(int sizeOfCombination) {
        //On réinitialise la liste des combinaisons étant donnée qu'une nouvelle longueur est fourni
        this.listOfCombination = new ArrayList<>();
        this.sizeOfCombination = sizeOfCombination;
    }

    /**
     * Method use to launch the creation of the combination and adjust the parameters
     */
    private void interface_P_CombinationIn_N(){
        P_CombinationIn_N(this.listToGetCombination,this.listToGetCombination.size(),
                this.sizeOfCombination,0,new ArrayList<>(),0);
    }

    /**
     * Method use to generate the combination
     * Is recursive
     * @param listToGetCombination : list of all the element that will be inside the combination created
     * @param sizeOfListIni : the size of the previous parameters
     * @param sizeOfCombination : the size of the combination wanted
     * @param index : the size of the current combination that is in creation
     * @param CurrentCombination : the current combination that is in creation
     * @param i : the index of the element that will be added to the combination that turn
     */
    private void P_CombinationIn_N(ArrayList<T> listToGetCombination,
                                                          int sizeOfListIni,
                                                          int sizeOfCombination,
                                                          int index,
                                                          ArrayList<T> CurrentCombination,
                                                          int i){
        // Current combination is ready, add it
        if (index == sizeOfCombination) {
            Combination<T> newCombination = new Combination<>();
            for (int j = 0; j < sizeOfCombination; j++)
                newCombination.addNewElement(CurrentCombination.get(j));
            this.listOfCombination.add(newCombination);
            return;
        }

        // When no more elements are there to put in CurrentCombination
        if (i >= sizeOfListIni)
            return;

        // current is included, put next at next location
        CurrentCombination.add(index,listToGetCombination.get(i));
        P_CombinationIn_N(listToGetCombination, sizeOfListIni, sizeOfCombination, index + 1, CurrentCombination, i + 1);

        // current is excluded, replace it with next
        P_CombinationIn_N(listToGetCombination, sizeOfListIni, sizeOfCombination, index, CurrentCombination, i + 1);
    }
}
