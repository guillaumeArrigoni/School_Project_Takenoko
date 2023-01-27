package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnary;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class GenerateOptimizePathForSeveralBox {

    private ArrayList<HexagoneBoxPlaced> boxToIrrigate;
    private final CrestGestionnary crestGestionnary;
    private HashMap<HexagoneBoxPlaced,ArrayList<Crest>> pathForEachBox;


    public GenerateOptimizePathForSeveralBox(ArrayList<HexagoneBoxPlaced> boxs) throws CrestNotRegistered {
        this.boxToIrrigate = boxs;
        this.crestGestionnary = boxs.get(0).getBoard().getCrestGestionnary();
        setupPathForEachBox();
        generatePath(boxToIrrigate,boxToIrrigate.size());
    }

    public HashMap<HexagoneBoxPlaced, ArrayList<Crest>> getPathForEachBox() {
        return pathForEachBox;
    }

    private void setupPathForEachBox(){
        for(HexagoneBoxPlaced box : boxToIrrigate){
            this.pathForEachBox.put(box,new ArrayList<>());
        }
    }

    private void generatePath(ArrayList<HexagoneBoxPlaced> previousBoxConcerned, int sizeCombination) throws CrestNotRegistered {
        if(sizeCombination ==0){
            return;
        }
        int rank = pathForEachBox.get(previousBoxConcerned.get(0)).size();
        Crest lastCommonCrest = pathForEachBox.get(previousBoxConcerned.get(0)).get(rank-1);
        HashMap<ArrayList<HexagoneBoxPlaced>,HashMap<Integer,Crest>> linkCombinationToThePath = getSelectedPathForASpecifiedSizeOfCombination(sizeCombination,rank,lastCommonCrest,previousBoxConcerned);
        if (linkCombinationToThePath.isEmpty()){
            generatePath(previousBoxConcerned,sizeCombination-1);
        } else {
            ArrayList<HexagoneBoxPlaced> uniqueCombination = completedPathForTheBoxInTheCombination(rank, linkCombinationToThePath);
            ArrayList<HexagoneBoxPlaced> listOfOtherBoxNotIncludeInTheCombinationSelected = previousBoxConcerned;
            listOfOtherBoxNotIncludeInTheCombinationSelected.removeAll(uniqueCombination);
            generatePath(uniqueCombination,sizeCombination-1);
            generatePath(listOfOtherBoxNotIncludeInTheCombinationSelected,sizeCombination-1);
        }

    }

    private ArrayList<HexagoneBoxPlaced> completedPathForTheBoxInTheCombination(int rank, HashMap<ArrayList<HexagoneBoxPlaced>, HashMap<Integer, Crest>> linkCombinationToThePath) {
        int max = -1;
        ArrayList<ArrayList<HexagoneBoxPlaced>> listOfCombinationWithTheBestPath = getCombinationWithTheBestPath(linkCombinationToThePath,max);
        //We always take the first element even if when more element are possible in this rank, a best option can exist in the futur rank.
        ArrayList<HexagoneBoxPlaced> uniqueCombination = listOfCombinationWithTheBestPath.get(0);
        for (HexagoneBoxPlaced box : uniqueCombination){
            ArrayList<Crest> pathForTheSpecifiedBox = pathForEachBox.get(box);
            for (int i=0;i<max;i++){
                pathForTheSpecifiedBox.add(linkCombinationToThePath.get(uniqueCombination).get(rank +i));
            }
            pathForEachBox.put(box,pathForTheSpecifiedBox);
        }
        return uniqueCombination;
    }

    private ArrayList<ArrayList<HexagoneBoxPlaced>> getCombinationWithTheBestPath(HashMap<ArrayList<HexagoneBoxPlaced>,HashMap<Integer,Crest>> linkRankToCrestSelected, int maxToModify){
        maxToModify = -1;
        ArrayList<ArrayList<HexagoneBoxPlaced>> listOfMaxAdvancement = new ArrayList<>();
        for (ArrayList<HexagoneBoxPlaced> combination : linkRankToCrestSelected.keySet()){
            int sizeAssociated = linkRankToCrestSelected.get(combination).keySet().size();
            if(sizeAssociated==maxToModify){
                listOfMaxAdvancement.add(combination);
            } else if (sizeAssociated>maxToModify){
                listOfMaxAdvancement = new ArrayList<>(Arrays.asList(combination));
                maxToModify = sizeAssociated;
            }
        }
        return listOfMaxAdvancement;
    }

    private HashMap<ArrayList<HexagoneBoxPlaced>,HashMap<Integer,Crest>> getSelectedPathForASpecifiedSizeOfCombination(int sizeOfCombination, int rank, Crest crestParent, ArrayList<HexagoneBoxPlaced> lastCombination) throws CrestNotRegistered {
        CombinationsOf_P_elementsAmong_N combinationsOf_p_elementsAmong_n = new CombinationsOf_P_elementsAmong_N<>(lastCombination,sizeOfCombination);
        ArrayList<ArrayList<HexagoneBoxPlaced>> allCombinationOfSpecifiedSize = combinationsOf_p_elementsAmong_n.getListOfCombination();
        HashMap<ArrayList<HexagoneBoxPlaced>,HashMap<Integer,Crest>> selectedPath = new HashMap<>();
        for (ArrayList<HexagoneBoxPlaced> combination : allCombinationOfSpecifiedSize){
            Optional<HashMap<Integer,Crest>> linkRankToCrestSelected = getBestPathForACombinationAndARank(combination,rank,crestParent);
            if (linkRankToCrestSelected.isPresent()){
                int sizeOfAdvancement = linkRankToCrestSelected.get().keySet().size();
                selectedPath.put(combination,linkRankToCrestSelected.get());
            }
        }
        return selectedPath;
    }

    private Optional<HashMap<Integer,Crest>> getBestPathForACombinationAndARank(ArrayList<HexagoneBoxPlaced> combination, int rankGiven, Crest crestParent) throws CrestNotRegistered {
        ArrayList<HashMap<Crest,Crest>> pathForACombination = generateAllPathGivenACombinationARankAndAParentCrest(combination,rankGiven,crestParent);
        int size = pathForACombination.size();
        HashMap<Integer,Crest> crestSelected = new HashMap<>();
        if (size>0){
            //On prends n'importe quel Crest au rang maximum car ils aboutissent tous à la même chose
            Crest crestChild = pathForACombination.get(size-1).keySet().stream().toList().get(0);
            crestSelected.put(rankGiven + size -1,crestChild);
            for (int i = size-2;i>-1;i--){
                HashMap<Crest,Crest> allCrestOfARank = pathForACombination.get(i);
                Crest crestChildBis = allCrestOfARank.get(crestChild);
                crestSelected.put(rankGiven + i,crestChildBis);
            }
            return Optional.of(crestSelected);
        }
        return Optional.empty();
    }


    private ArrayList<HashMap<Crest, Crest>> generateAllPathGivenACombinationARankAndAParentCrest(ArrayList<HexagoneBoxPlaced> combination, int rankGiven, Crest crestParent) throws CrestNotRegistered {
        int rank = rankGiven;
        HashMap<Crest,Crest> linkChild_Parent = new HashMap<>();
        Crest fakeCrest = new Crest(99,99,1);
        linkChild_Parent.put(crestParent,fakeCrest);
        ArrayList<HashMap<Crest,Crest>> successiveHashMapOfCrestAvailable = new ArrayList<>();
        successiveHashMapOfCrestAvailable.add(linkChild_Parent);
        int index = 0;
        boolean noMore = false;
        while(!noMore){
            noMore = true;
            linkChild_Parent = new HashMap<>();
            for (Crest crest : successiveHashMapOfCrestAvailable.get(index).keySet()){
                ArrayList<Crest> p = getListOfCommonCrest(crest,rank,combination);
                if (p.size()!=0){
                    for (Crest childCrest : p){
                        linkChild_Parent.put(childCrest,crest);
                    }
                }
            }
            if (!linkChild_Parent.isEmpty()){
                noMore = false;
                successiveHashMapOfCrestAvailable.add(linkChild_Parent);
            }
            rank = rank + 1;
            index = index+1;
        }
        return successiveHashMapOfCrestAvailable;
    }

    private ArrayList<Crest> getListOfCommonCrest(Crest crestPArent, int rank, ArrayList<HexagoneBoxPlaced> currentCombination) throws CrestNotRegistered {
        ArrayList<Crest> crestAvailableForThisCombinationInThisRankForThisCrest = new ArrayList<>();
        for(HexagoneBoxPlaced box : currentCombination){
            GenerateAWayToIrrigateTheBox generateAWayToIrrigateTheBox = new GenerateAWayToIrrigateTheBox(box);
            ArrayList<Crest> crestOfRankToIrrigateTheBox = generateAWayToIrrigateTheBox.getPathToIrrigation().get(rank);
            crestAvailableForThisCombinationInThisRankForThisCrest.retainAll(crestOfRankToIrrigateTheBox);
        }
        crestAvailableForThisCombinationInThisRankForThisCrest.retainAll(
                crestGestionnary.getLinkCrestParentToCrestChildren().get(crestPArent));
        return crestAvailableForThisCombinationInThisRankForThisCrest;
    }

}
