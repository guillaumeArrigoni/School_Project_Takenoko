package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.pathIrrigation;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.combination.Combination;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.combination.CombinationsOf_P_elementsAmong_N;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.crest.CrestGestionnary;


import java.util.*;

public class GenerateOptimizePathForSeveralBox {

    protected ArrayList<HexagoneBoxPlaced> boxToIrrigate;
    protected CrestGestionnary crestGestionnary;
    private HashMap<HexagoneBoxPlaced,ArrayList<Crest>> pathForEachBox;

    private HashMap<HexagoneBoxPlaced,GenerateAWayToIrrigateTheBox> generalHashMapOfPathForEachBox;


    /**
     * Will give the path to irrigate each box entered
     * Give the best path at the initialization
     * The path is the best one in common
     * @param boxs : the list of box to irrigate
     * @param bool : true to generate the variable, false to bypass that process
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    public GenerateOptimizePathForSeveralBox(ArrayList<HexagoneBoxPlaced> boxs, boolean bool) throws CrestNotRegistered, CloneNotSupportedException {
        if (bool){
            this.boxToIrrigate = boxs;
            this.crestGestionnary = boxs.get(0).getBoard().getCrestGestionnary();
            setupPathForEachBox();
            setupGeneralWayForEachBox();
            generatePath(boxToIrrigate,boxToIrrigate.size());
        }
    }

    public GenerateOptimizePathForSeveralBox(ArrayList<HexagoneBoxPlaced> boxs) throws CrestNotRegistered, CloneNotSupportedException {
        this(boxs,true);
    }

    public HashMap<HexagoneBoxPlaced, ArrayList<Crest>> getPathForEachBox() {
        return pathForEachBox;
    }

    /**
     * Method use to set up the final variable
     */
    protected void setupPathForEachBox(){
        pathForEachBox = new HashMap<>();
        for(HexagoneBoxPlaced box : boxToIrrigate){
            this.pathForEachBox.put(box,new ArrayList<>());
        }
    }

    /**
     * Method use to generate the variable which contain the path from GenerateAWayToIrrigateTheBox for each box
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    protected void setupGeneralWayForEachBox() throws CrestNotRegistered, CloneNotSupportedException {
        generalHashMapOfPathForEachBox = new HashMap<>();
        for (HexagoneBoxPlaced box : boxToIrrigate){
            generalHashMapOfPathForEachBox.put(box,new GenerateAWayToIrrigateTheBox(box));
        }
    }

    /**
     * Method recursive use to generate the best path for each box
     * @param previousBoxConcerned : all the box in the given branch of generation
     * @param sizeCombination : the size of the new combination to generate
     * @throws CrestNotRegistered
     */
    protected void generatePath(ArrayList<HexagoneBoxPlaced> previousBoxConcerned, int sizeCombination) throws CrestNotRegistered {
        if(sizeCombination ==0 || previousBoxConcerned.isEmpty()){
            return;
        }
        ArrayList<Crest> listCrestParent = new ArrayList<>();
        Crest lastCommonCrest;
        int rank = pathForEachBox.get(previousBoxConcerned.get(0)).size();
        if (rank == 0){
            lastCommonCrest = new Crest(99,99,1);
        } else {
            lastCommonCrest = pathForEachBox.get(previousBoxConcerned.get(0)).get(rank-1);
        }

        HashMap<Combination<HexagoneBoxPlaced>,HashMap<Integer,Crest>> linkCombinationToThePath = getSelectedPathForASpecifiedSizeOfCombination(sizeCombination,rank,lastCommonCrest,previousBoxConcerned);
        if (linkCombinationToThePath.isEmpty()){ //si pas de chemin valable pour cette taille de combinaison, on recommence à partir des mêmes box mais à la taille des combinaison -1
            generatePath(previousBoxConcerned,sizeCombination-1);
        } else {
            Combination<HexagoneBoxPlaced> uniqueCombination = completedPathForTheBoxInTheCombination(rank, linkCombinationToThePath);
            ArrayList<HexagoneBoxPlaced> listOfOtherBoxNotIncludeInTheCombinationSelected = previousBoxConcerned;
            listOfOtherBoxNotIncludeInTheCombinationSelected.removeAll(uniqueCombination.getListOfElementInTheCombination());

            generatePath(uniqueCombination.getListOfElementInTheCombination(),sizeCombination-1);
            generatePath(listOfOtherBoxNotIncludeInTheCombinationSelected,listOfOtherBoxNotIncludeInTheCombinationSelected.size());
        }
    }

    /**
     * Method use to update the global variable, for each box in the best combination, the path associated
     * @param rank : the rank use to start, corresponding to the range to the irrigation
     * @param linkCombinationToThePath : the Hashmap generated in getSelectedPathForASpecifiedSizeOfCombination
     * @return : the best combination (if there is some combination with the same path size,
     *      only the first one is taken to simplify the algorithm
     */
    //ajoute à la var global, pour les box dans la combinaison retenu, le chemin généré
    private Combination<HexagoneBoxPlaced> completedPathForTheBoxInTheCombination(int rank, HashMap<Combination<HexagoneBoxPlaced>, HashMap<Integer, Crest>> linkCombinationToThePath) {
        HashMap<Integer,ArrayList<Combination<HexagoneBoxPlaced>>> listOfCombinationWithTheBestPath = getCombinationWithTheBestPath(linkCombinationToThePath);
        int max = listOfCombinationWithTheBestPath.keySet().stream().toList().get(0); //car toujours un seul élément, le dictionnaire sert juste à renvoyé 2 valeurs, la liste en value et la longueur du chemin généré en clef
        //We always take the first element even if when more element are possible in this rank, a best option can exist in the futur rank.
        Combination<HexagoneBoxPlaced> uniqueCombination = listOfCombinationWithTheBestPath.get(max).get(0);
        for (HexagoneBoxPlaced box : uniqueCombination.getListOfElementInTheCombination()){
            ArrayList<Crest> pathForTheSpecifiedBox = pathForEachBox.get(box);
            for (int i=0;i<max;i++){
                pathForTheSpecifiedBox.add(linkCombinationToThePath.get(uniqueCombination).get(rank +i));
            }
            pathForEachBox.put(box,pathForTheSpecifiedBox);
        }
        return uniqueCombination;
    }

    /**
     * Method use to get the best combination which have the longest path given a starting rank
     * @param linkRankToCrestSelected : the Hashmap generated in getSelectedPathForASpecifiedSizeOfCombination
     * @return : a Hashmap that have as key the range of the best path
     *          and as value the ArrayList of the best combination
     */
    //donne le/les combinaisons avec le plus grand chemin généré à partir d'un rang donné
    private HashMap<Integer,ArrayList<Combination<HexagoneBoxPlaced>>> getCombinationWithTheBestPath(HashMap<Combination<HexagoneBoxPlaced>,HashMap<Integer,Crest>> linkRankToCrestSelected){
        int maxToModify = -1;
        ArrayList<Combination<HexagoneBoxPlaced>> listOfMaxAdvancement = new ArrayList<>();
        HashMap<Integer,ArrayList<Combination<HexagoneBoxPlaced>>> maxAssociatedToTheList = new HashMap<>();
        for (Combination<HexagoneBoxPlaced> combination : linkRankToCrestSelected.keySet()){
            int sizeAssociated = linkRankToCrestSelected.get(combination).keySet().size();
            if(sizeAssociated==maxToModify){
                listOfMaxAdvancement.add(combination);
            } else if (sizeAssociated>maxToModify){
                listOfMaxAdvancement = new ArrayList<>(Arrays.asList(combination));
                maxToModify = sizeAssociated;
            }
        }
        maxAssociatedToTheList.put(maxToModify,listOfMaxAdvancement);
        return maxAssociatedToTheList;
    }

    /**
     * Method use to :
     *      generate all the combination given a size and a list of box
     *      give the best path for each combination
     * @param sizeOfCombination : the size of the combination that will be created
     * @param rank : the rank use to start, corresponding to the range to the irrigation
     * @param crestParent : the crest parent declared as path in the previous step
     *                          <=> The path must start by the children of them
     * @param boxFromLastCombination : the list of box use to generate the now combination
     * @return : A Hashmap that associate for each Combination the Hashmap of the path
     * @throws CrestNotRegistered
     */
    //associe à chaque combinaison le dictionnaire (contenant le chemin à suivre)
    private HashMap<Combination<HexagoneBoxPlaced>,HashMap<Integer,Crest>> getSelectedPathForASpecifiedSizeOfCombination(int sizeOfCombination, int rank, Crest crestParent, ArrayList<HexagoneBoxPlaced> boxFromLastCombination) throws CrestNotRegistered {
        CombinationsOf_P_elementsAmong_N combinationsOf_p_elementsAmong_n = new CombinationsOf_P_elementsAmong_N<>(boxFromLastCombination,sizeOfCombination);
        ArrayList<Combination<HexagoneBoxPlaced>> allCombinationOfSpecifiedSize = combinationsOf_p_elementsAmong_n.getListOfCombination();
        HashMap<Combination<HexagoneBoxPlaced>,HashMap<Integer,Crest>> selectedPath = new HashMap<>();
        for (Combination<HexagoneBoxPlaced> combination : allCombinationOfSpecifiedSize){
            Optional<HashMap<Integer,Crest>> linkRankToCrestSelected = getBestPathForACombinationAndARank(combination,rank,crestParent);
            if (linkRankToCrestSelected.isPresent()){
                selectedPath.put(combination,linkRankToCrestSelected.get());
            }
        }
        return selectedPath;
    }

    /**
     * Method use to retain only the longest path for a combination given a starting rank
     * @param combination : the combination use to generate the path
     * @param rankGiven : the rank use to start, corresponding to the range to the irrigation
     * @param crestParent : the crest parent declared as path in the previous step
     *                          <=> The path must start by the children of them
     * @return : Optional<HashMap> that is empty is no path are available for this combination
     *              And that contain for each rank the crest to irrigate if at least one path is available
     * @throws CrestNotRegistered
     */
    //on génère le chemin le plus long pour une combinaison à partir d'un rang
    private Optional<HashMap<Integer,Crest>> getBestPathForACombinationAndARank(Combination<HexagoneBoxPlaced> combination, int rankGiven, Crest crestParent) throws CrestNotRegistered {
        ArrayList<HashMap<Crest,Crest>> pathForACombination = generateAllPathGivenACombinationARankAndAParentCrest(combination,rankGiven,crestParent);
        int size = pathForACombination.size();
        HashMap<Integer,Crest> crestSelected = new HashMap<>();
        if (size>0){
            //On prends n'importe quel Crest au rang maximum car ils aboutissent tous à la même chose
            Crest crestChild = pathForACombination.get(size-1).keySet().stream().toList().get(0);
            crestSelected.put(rankGiven + size -1,crestChild);
            for (int i = size-1;i>0;i--){
                HashMap<Crest,Crest> allCrestOfARank = pathForACombination.get(i);
                Crest crestChildBis = allCrestOfARank.get(crestChild);
                crestSelected.put(rankGiven + i-1,crestChildBis);
                crestChild = crestChildBis;
            }
            return Optional.of(crestSelected);
        }
        return Optional.empty();
    }

    /**
     * Method use to generate all the common path for a combination starting from the rank until there is no path available
     * @param combination : the combination of box use to generate the path
     * @param rankGiven : the rank use to start, corresponding to the range to the irrigation
     * @param crestParent : the crest parent declared as path in the previous step
     *                          <=> The path must start by the children of them
     * @return : a Hashmap that associate to each child the parent associated
     * @throws CrestNotRegistered
     */
    //pour une combinaison donnée et un rang, on génère tous les chemins en commun de toutes les box dans la combinaison à partir du rang jusqu'à ce qu'il n'y ai plus de crest en commun
    private ArrayList<HashMap<Crest, Crest>> generateAllPathGivenACombinationARankAndAParentCrest(Combination<HexagoneBoxPlaced> combination, int rankGiven, Crest crestParent) throws CrestNotRegistered {
        int rank = rankGiven; //rang == 0 : signifi les coté à 1de l'irrigation (pouvant être irrigué en 1 tour)
        int index = 0;
        HashMap<Crest,Crest> linkChild_Parent = new HashMap<>();
        Crest fakeCrest = new Crest(99,99,1); //TODO pour signifié les crests déjà irrigué
        boolean noMore = false;
        ArrayList<HashMap<Crest,Crest>> successiveHashMapOfCrestAvailable = new ArrayList<>();

        linkChild_Parent.put(crestParent,fakeCrest);
        successiveHashMapOfCrestAvailable.add(linkChild_Parent);
        do{
            noMore = true;
            linkChild_Parent = new HashMap<>();
            ArrayList<Crest> listOfCrestAvailable;
            if (rank ==0){
                listOfCrestAvailable = (ArrayList<Crest>) crestGestionnary.getListOfCrestOneRangeToIrrigated().clone();
                ArrayList<Crest> p = get_ListOfCommonCrest_GivenACombinationAndARank(listOfCrestAvailable,rank,combination);
                if (p.size()!=0){
                    for (Crest childCrest : p){
                        linkChild_Parent.put(childCrest,fakeCrest);
                    }
                }
            } else {
                for(Crest crest : successiveHashMapOfCrestAvailable.get(index).keySet()){
                    listOfCrestAvailable = (ArrayList<Crest>) crestGestionnary.getLinkCrestParentToCrestChildren().get(crest).clone();
                    ArrayList<Crest> p = get_ListOfCommonCrest_GivenACombinationAndARank(listOfCrestAvailable,rank,combination);
                    if (p.size()!=0){
                        for (Crest childCrest : p){
                            linkChild_Parent.put(childCrest,crest);
                        }
                    }
                }
            }
            if (!linkChild_Parent.isEmpty()){
                noMore = false;
                successiveHashMapOfCrestAvailable.add(linkChild_Parent);
            }
            rank = rank + 1;
            index = index +1;
        } while(!noMore);
        successiveHashMapOfCrestAvailable.remove(0); //car non utile
        return successiveHashMapOfCrestAvailable;
    }


    /**
     * Method use to retain all the Crest in common for each Box from a combination
     * @param listCrestParent : the list of the parent from this step
     *                         <=> The Crest Available are the child in common from the parent
     * @param rank : the rank of the generation of the path
     *             <=> The range to the irrigation
     * @param currentCombination : the current combination of box use to generate a path
     * @return : the list of the Crest in common
     * @throws CrestNotRegistered
     */
    private ArrayList<Crest> get_ListOfCommonCrest_GivenACombinationAndARank(ArrayList<Crest> listCrestParent, int rank, Combination<HexagoneBoxPlaced> currentCombination) throws CrestNotRegistered {
        ArrayList<Crest> crestAvailableForThisCombinationInThisRankForThisCrest = listCrestParent;
        for(HexagoneBoxPlaced box : currentCombination.getListOfElementInTheCombination()){
            GenerateAWayToIrrigateTheBox generateAWayToIrrigateTheBox = this.generalHashMapOfPathForEachBox.get(box);
            ArrayList<Crest> crestOfRankToIrrigateTheBox;
            if (rank<generateAWayToIrrigateTheBox.getPathToIrrigation().size()){
                crestOfRankToIrrigateTheBox = generateAWayToIrrigateTheBox.getPathToIrrigation().get(rank);
            } else {
                crestOfRankToIrrigateTheBox = new ArrayList<>();
            }
            crestAvailableForThisCombinationInThisRankForThisCrest.retainAll(crestOfRankToIrrigateTheBox);
        }
        crestAvailableForThisCombinationInThisRankForThisCrest.retainAll(listCrestParent);
        return crestAvailableForThisCombinationInThisRankForThisCrest;
    }

}
