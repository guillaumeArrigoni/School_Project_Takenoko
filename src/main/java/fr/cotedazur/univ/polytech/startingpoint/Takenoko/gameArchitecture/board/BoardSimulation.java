package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.ElementOfTheBoard;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.ElementOfTheBoardCheated;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnary;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;

import java.util.ArrayList;
import java.util.HashMap;

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
        setupSimulation2(board);
    }

    private void setupSimulation2(Board board) {
        this.crestGestionnarySimulation = new CrestGestionnarySimulation(board.getCrestGestionnary());
        this.retrieveSimulation = new RetrieveSimulation(board.getRetrieveBoxIdWithParameters());
        super.retrieveBoxIdWithParameters = this.retrieveSimulation;
        super.crestGestionnary = this.crestGestionnarySimulation;
        super.gardenerCoords = board.getGardenerCoords().clone();
        super.pandaCoords = board.getPandaCoords().clone();
        for (HexagoneBoxPlaced box : board.placedBox.values()) {
            super.addBox(new HexagoneBoxSimulation(
                    box.getCoordinates()[0],
                    box.getCoordinates()[1],
                    box.getCoordinates()[2],
                    box.getColor(),
                    box.getSpecial(),
                    retrieveSimulation,
                    this));
        }
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