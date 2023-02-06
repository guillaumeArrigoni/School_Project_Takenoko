package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest;

import java.util.ArrayList;
import java.util.HashMap;

public class CrestGestionnarySimulation extends  CrestGestionnary {

    public CrestGestionnarySimulation(CrestGestionnary crestGestionnary){
        super(false);
        setupGeneral(crestGestionnary);
    }

    private void setupGeneral(CrestGestionnary crestGestionnary){
        this.linkCrestParentToCrestChildren = crestGestionnary.getLinkCrestParentToCrestChildren();
        this.linkCrestChildrenToCrestParent = crestGestionnary.getLinkCrestChildrenToCrestParent();
        this.rangeFromIrrigated = crestGestionnary.getRangeFromIrrigated();
        this.parentChildless = crestGestionnary.getParentChildless();
        this.alreadyIrrigated = crestGestionnary.getAlreadyIrrigated();
        this.listOfCrestOneRangeToIrrigated = crestGestionnary.getListOfCrestOneRangeToIrrigated();
        this.listOfCrestIrrigated = crestGestionnary.getListOfCrestIrrigated();
    }

}
