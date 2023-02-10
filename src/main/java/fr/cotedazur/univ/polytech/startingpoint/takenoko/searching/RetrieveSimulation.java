package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;

import java.util.ArrayList;
import java.util.HashMap;

public class RetrieveSimulation extends RetrieveBoxIdWithParameters{

    public RetrieveSimulation(RetrieveBoxIdWithParameters retrieveBoxIdWithParameters){
        super(false);
        setupSimulation(retrieveBoxIdWithParameters);
    }

    private void setupSimulation(RetrieveBoxIdWithParameters retrieveBoxIdWithParameters){
        this.BoxColor = (HashMap<Color, ArrayList<Integer>>) retrieveBoxIdWithParameters.getBoxColor().clone();
        this.BoxIsIrrigated = (HashMap<Boolean, ArrayList<Integer>>) retrieveBoxIdWithParameters.getBoxIsIrrigated().clone();
        this.BoxHeight = (HashMap<Integer, ArrayList<Integer>>) retrieveBoxIdWithParameters.getBoxHeight().clone();
        this.BoxSpeciality = (HashMap<Special, ArrayList<Integer>>) retrieveBoxIdWithParameters.getBoxSpeciality().clone();
    }
}
