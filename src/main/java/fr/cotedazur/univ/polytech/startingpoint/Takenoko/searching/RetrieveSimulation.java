package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching;

public class RetrieveSimulation extends RetrieveBoxIdWithParameters{

    public RetrieveSimulation(RetrieveBoxIdWithParameters retrieveBoxIdWithParameters){
        super(false);
        setupSimulation(retrieveBoxIdWithParameters);
    }

    private void setupSimulation(RetrieveBoxIdWithParameters retrieveBoxIdWithParameters){
        this.BoxColor = retrieveBoxIdWithParameters.getBoxColor();
        this.BoxIsIrrigated = retrieveBoxIdWithParameters.getBoxIsIrrigated();
        this.BoxHeight = retrieveBoxIdWithParameters.getBoxHeight();
        this.BoxSpeciality = retrieveBoxIdWithParameters.getBoxSpeciality();
    }
}
