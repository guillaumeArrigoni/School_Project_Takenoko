package fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.crest;

import java.util.ArrayList;
import java.util.HashMap;

public class CrestGestionnarySimulation extends  CrestGestionnary {

    public CrestGestionnarySimulation(CrestGestionnary crestGestionnary){
        super(false);
        setupGeneral(crestGestionnary);
    }

    private void setupGeneral(CrestGestionnary crestGestionnary){
        this.linkCrestParentToCrestChildren = (HashMap<Crest, ArrayList<Crest>>) crestGestionnary.getLinkCrestParentToCrestChildren().clone();
        this.linkCrestChildrenToCrestParent = (HashMap<Crest, ArrayList<Crest>>) crestGestionnary.getLinkCrestChildrenToCrestParent().clone();
        this.rangeFromIrrigated = (HashMap<Crest, Integer>) crestGestionnary.getRangeFromIrrigated().clone();
        this.parentChildless = (ArrayList<Crest>) crestGestionnary.getParentChildless().clone();
        this.alreadyIrrigated = (ArrayList<Integer>) crestGestionnary.getAlreadyIrrigated().clone();
        this.listOfCrestOneRangeToIrrigated = (ArrayList<Crest>) crestGestionnary.getListOfCrestOneRangeToIrrigated().clone();
        this.listOfCrestIrrigated = (ArrayList<Crest>) crestGestionnary.getListOfCrestIrrigated().clone();
    }

}
