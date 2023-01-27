package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnary;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class irrigations {

    private ArrayList<HexagoneBoxPlaced> boxToIrrigate;
    private final CrestGestionnary crestGestionnary;
    private allCombinationsOf_P_elementsAmong_N allCombinationsOf_P_elementsAmong_N;
    private HashMap<Integer,ArrayList<ArrayList<HexagoneBoxPlaced>>> allCombination;
    private HashMap<HexagoneBoxPlaced,ArrayList<Crest>> pathForEachBox;


    public irrigations(ArrayList<HexagoneBoxPlaced> boxs){
        this.boxToIrrigate = boxs;
        this.crestGestionnary = boxs.get(0).getBoard().getCrestGestionnary();
        this.allCombinationsOf_P_elementsAmong_N = new allCombinationsOf_P_elementsAmong_N<HexagoneBoxPlaced>(boxs,boxs.size());
        this.allCombination = allCombinationsOf_P_elementsAmong_N.getAllCombination();
        setupPathForEachBox();
    }

    private void setupPathForEachBox(){
        for(HexagoneBoxPlaced box : boxToIrrigate){
            this.pathForEachBox.put(box,new ArrayList<>());
        }
    }

    private void a(ArrayList<ArrayList<HexagoneBoxPlaced>> s) throws CrestNotRegistered {
        Crest uniqueCrestParent = new Crest(0,0,0); //TODO
        HashMap<ArrayList<Crest>,ArrayList<ArrayList<Crest>>> potentialPath = new HashMap<>();

        HashMap<ArrayList<HexagoneBoxPlaced>,ArrayList<Crest>> d = new HashMap<>();
        int rangDerniereCombinaison = 1;
        for(ArrayList<HexagoneBoxPlaced> currentCombination : s){
            int rangDeCetteCombinaison = 0;
            ArrayList<Crest> crestAvailableForThisCombinationInThisRank = new ArrayList<>();
            do{
                for(HexagoneBoxPlaced box : currentCombination){
                    generateAWayToIrrigateTheBox y = new generateAWayToIrrigateTheBox(box);
                    ArrayList<Crest> q = y.getPathToIrrigation().get(rangDerniereCombinaison + rangDeCetteCombinaison);
                    crestAvailableForThisCombinationInThisRank.retainAll(q);
                }
                //par sécurité
                crestAvailableForThisCombinationInThisRank.retainAll(crestGestionnary.getLinkCrestParentToCrestChildren().get(uniqueCrestParent));
                if (crestAvailableForThisCombinationInThisRank.size()!=0){
                    ArrayList<ArrayList<Crest>> dd= new ArrayList<>();
                    if (potentialPath.containsKey(currentCombination)){
                        dd = potentialPath.get(currentCombination);
                    }
                    dd.add(crestAvailableForThisCombinationInThisRank);
                }
            } while(crestAvailableForThisCombinationInThisRank.size()!=0);


        }



    }

    private void r(ArrayList<HexagoneBoxPlaced> previousBoxConcerned, int sizeCombination) throws CrestNotRegistered {
        int rank = pathForEachBox.get(previousBoxConcerned.get(0)).size();
        Crest lastCommonCrest = pathForEachBox.get(previousBoxConcerned.get(0)).get(rank-1);
        HashMap<ArrayList<HexagoneBoxPlaced>,HashMap<Integer,Crest>> t = getSelectedPathForASpecifiedSizeOfCombination(sizeCombination,rank,lastCommonCrest,previousBoxConcerned);
        if (t.isEmpty()){
            r(previousBoxConcerned,sizeCombination-1);
        } else {
            int max = -1;
            ArrayList<ArrayList<HexagoneBoxPlaced>> w = v(t,max);
            if (w.size()>1){
                //Should not happen
                //TODO try update it see commit
            } else {
                ArrayList<HexagoneBoxPlaced> uniqueCombination = w.get(0);
                for (HexagoneBoxPlaced box : uniqueCombination){
                    ArrayList<Crest> gg = pathForEachBox.get(box);
                    for (int i=0;i<max;i++){
                        gg.add(t.get(uniqueCombination).get(rank+i));
                    }
                    pathForEachBox.put(box,gg);
                }
                ArrayList<HexagoneBoxPlaced> y = previousBoxConcerned;
                y.removeAll(uniqueCombination);

                r(uniqueCombination,sizeCombination-1);
                r(y,sizeCombination-1);
            }
        }

    }

    private ArrayList<ArrayList<HexagoneBoxPlaced>> v(HashMap<ArrayList<HexagoneBoxPlaced>,HashMap<Integer,Crest>> g, int maxToModify){
        maxToModify = -1;
        ArrayList<ArrayList<HexagoneBoxPlaced>> listOfMaxAdvancement = new ArrayList<>();
        for (ArrayList<HexagoneBoxPlaced> combination : g.keySet()){
            int sizeAssociated = g.get(combination).keySet().size();
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
        combinationsOf_P_elementsAmong_N combinationsOf_p_elementsAmong_n = new combinationsOf_P_elementsAmong_N<>(lastCombination,sizeOfCombination);


        ArrayList<ArrayList<HexagoneBoxPlaced>> allCombinationOfSpecifiedSize = combinationsOf_p_elementsAmong_n.getListOfCombination();
        HashMap<ArrayList<HexagoneBoxPlaced>,HashMap<Integer,Crest>> selectedPath = new HashMap<>();
        for (ArrayList<HexagoneBoxPlaced> combination : allCombinationOfSpecifiedSize){
            Optional<HashMap<Integer,Crest>> u = getBestPathForACombinationAndARank(combination,rank,crestParent);
            if (u.isPresent()){
                int sizeOfAdvancement = u.get().keySet().size();
                selectedPath.put(combination,u.get());
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
            generateAWayToIrrigateTheBox generateAWayToIrrigateTheBox = new generateAWayToIrrigateTheBox(box);
            ArrayList<Crest> crestOfRankToIrrigateTheBox = generateAWayToIrrigateTheBox.getPathToIrrigation().get(rank);
            crestAvailableForThisCombinationInThisRankForThisCrest.retainAll(crestOfRankToIrrigateTheBox);
        }
        crestAvailableForThisCombinationInThisRankForThisCrest.retainAll(
                crestGestionnary.getLinkCrestParentToCrestChildren().get(crestPArent));
        return crestAvailableForThisCombinationInThisRankForThisCrest;
    }

}
