package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnary;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class irrigations {

    private ArrayList<HexagoneBoxPlaced> boxToIrrigate;
    private final CrestGestionnary crestGestionnary;
    private allCombinationsOf_P_elementsAmong_N allCombinationsOf_P_elementsAmong_N;


    public irrigations(ArrayList<HexagoneBoxPlaced> boxs){
        this.boxToIrrigate = boxs;
        this.crestGestionnary = boxs.get(0).getBoard().getCrestGestionnary();
        this.allCombinationsOf_P_elementsAmong_N = new allCombinationsOf_P_elementsAmong_N<HexagoneBoxPlaced>(boxs,boxs.size());
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


    private void getBestPathForACombinationAndARank(ArrayList<HexagoneBoxPlaced> combination,int rankGiven, Crest crestParent) throws CrestNotRegistered {
        int rank = rankGiven;
        //ArrayList<Crest> listOfPossibleParent = new ArrayList<>(Arrays.asList(crestParent));
        //HashMap<Crest,ArrayList<Crest>> linkParent_Children = new HashMap<>();
        HashMap<Crest,Crest> linkChild_Parent = new HashMap<>();
        Crest fakeCrest = new Crest(99,99,1);
        linkChild_Parent.put(crestParent,fakeCrest);
        ArrayList<HashMap<Crest,Crest>> successiveHashMapOfCrestAvailable = new ArrayList<>();
        successiveHashMapOfCrestAvailable.add(linkChild_Parent);
        int index = 0;
        while(true){
            linkChild_Parent = new HashMap<>();
            for (Crest crest : successiveHashMapOfCrestAvailable.get(index).keySet()){
                ArrayList<Crest> p = getListOfCommonCrest(crest,rank,combination);
                if (p.size()!=0){
                    //linkParent_Children.put(crest,p);
                    //listOfPossibleParent.add(crest);
                    for (Crest childCrest : p){
                        linkChild_Parent.put(childCrest,crest);
                    }
                }
            }
            if (!linkChild_Parent.isEmpty()){
                successiveHashMapOfCrestAvailable.add(linkChild_Parent);
            }
            rank = rank + 1;
            index = index+1;

        }

    }

    private ArrayList<Crest> getListOfCommonCrest(Crest crestPArent, int rank, ArrayList<HexagoneBoxPlaced> currentCombination) throws CrestNotRegistered {
        ArrayList<Crest> crestAvailableForThisCombinationInThisRankForThisCrest = new ArrayList<>();
        for(HexagoneBoxPlaced box : currentCombination){
            generateAWayToIrrigateTheBox y = new generateAWayToIrrigateTheBox(box);
            ArrayList<Crest> q = y.getPathToIrrigation().get(rank);
            crestAvailableForThisCombinationInThisRankForThisCrest.retainAll(q);
        }
        crestAvailableForThisCombinationInThisRankForThisCrest.retainAll(
                crestGestionnary.getLinkCrestParentToCrestChildren().get(crestPArent));
        return crestAvailableForThisCombinationInThisRankForThisCrest;
    }

}
