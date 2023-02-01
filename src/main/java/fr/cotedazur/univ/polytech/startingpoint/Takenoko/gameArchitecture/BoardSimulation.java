package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;

public class BoardSimulation extends Board {
    public BoardSimulation(RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, boolean allIrrigated, int id, Board board) {
        super(retrieveBoxIdWithParameters, allIrrigated, id);
        setupSimulation(board);
    }


    private void setupSimulation(Board board){
        this.retrieveBoxIdWithParameters = new RetrieveSimulation(board.getRetrieveBoxIdWithParameters());
        this.crestGestionnary = new CrestGestionnarySimulation(board.getCrestGestionnary());
        this.numberBoxPlaced = board.getNumberBoxPlaced();
        this.AvailableBox = board.getAvailableBox();
        this.gardenerCoords = board.getGardenerCoords();
        this.pandaCoords = board.getPandaCoords();
        this.placedBox = board.getPlacedBox();
    }


}
