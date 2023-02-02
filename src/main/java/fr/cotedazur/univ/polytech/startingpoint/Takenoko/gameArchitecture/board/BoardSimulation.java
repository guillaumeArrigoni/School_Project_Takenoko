package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.ElementOfTheBoard;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.ElementOfTheBoardCheated;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnary;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;

public class BoardSimulation extends Board {

    protected RetrieveSimulation retrieveSimulation;
    protected CrestGestionnarySimulation crestGestionnarySimulation;
    protected ElementOfTheBoardCheated elementOfTheBoardCheated;
    public BoardSimulation(Board board) {
        this(board, new ElementOfTheBoardCheated());
    }

    public BoardSimulation(Board board, ElementOfTheBoardCheated elementOfTheBoard) {
        super(board.getRetrieveBoxIdWithParameters(), board.isAllIrrigated(), board.getIdOfTheBoard(), elementOfTheBoard);
        this.elementOfTheBoardCheated = elementOfTheBoard;
        setupSimulation(board);
    }



    private void setupSimulation(Board board){
        this.crestGestionnarySimulation = new CrestGestionnarySimulation(board.getCrestGestionnary());
        this.retrieveSimulation = new RetrieveSimulation(board.getRetrieveBoxIdWithParameters());
        super.retrieveBoxIdWithParameters = this.retrieveSimulation;
        super.crestGestionnary = this.crestGestionnarySimulation;
        super.numberBoxPlaced = board.getNumberBoxPlaced();
        super.AvailableBox = board.getAvailableBox();
        super.gardenerCoords = board.getGardenerCoords();
        super.pandaCoords = board.getPandaCoords();
        super.placedBox = board.getPlacedBox();
    }

    @Override
    public RetrieveSimulation getRetrieveBoxIdWithParameters() {
        return this.retrieveSimulation;
    }
    @Override
    public CrestGestionnarySimulation getCrestGestionnary() {
        return this.crestGestionnarySimulation;
    }
    @Override
    public ElementOfTheBoardCheated getElementOfTheBoard() {
        return this.elementOfTheBoardCheated;
    }

}
