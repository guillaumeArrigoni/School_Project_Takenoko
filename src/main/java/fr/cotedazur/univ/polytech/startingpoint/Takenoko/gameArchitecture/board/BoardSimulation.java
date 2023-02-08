package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.ElementOfTheBoard;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.ElementOfTheBoardCheated;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.ElementOfTheBoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;

public class BoardSimulation extends Board {

    protected RetrieveSimulation retrieveSimulation;
    protected CrestGestionnarySimulation crestGestionnarySimulation;
    public BoardSimulation(Board board) {
        this(board, new ElementOfTheBoardCheated());
    }

    public BoardSimulation(Board board, ElementOfTheBoard elementOfTheBoard){
        super(board.getRetrieveBoxIdWithParameters(), board.isAllIrrigated(), board.getIdOfTheBoard(), elementOfTheBoard);
        super.elementOfTheBoard = new ElementOfTheBoardSimulation(elementOfTheBoard);
        setupSimulation(board);
    }

    public BoardSimulation(Board board, ElementOfTheBoardCheated elementOfTheBoardCheated) {
        super(board.getRetrieveBoxIdWithParameters(), board.isAllIrrigated(), board.getIdOfTheBoard(), elementOfTheBoardCheated);
        super.elementOfTheBoard = elementOfTheBoardCheated;
        setupSimulation(board);
    }


    /**
     * Method use to copy the board and place in the new board generated
     * the HexagoneBoxSimulation corresponding to the box already place in the true board
     * @param board : the true box use to to generate this simulation
     */
    private void setupSimulation(Board board) {
        this.crestGestionnarySimulation = new CrestGestionnarySimulation(board.getCrestGestionnary());
        this.retrieveSimulation = new RetrieveSimulation(board.getRetrieveBoxIdWithParameters());
        super.retrieveBoxIdWithParameters = this.retrieveSimulation;
        super.crestGestionnary = this.crestGestionnarySimulation;
        super.gardenerCoords = board.getGardenerCoords().clone();
        super.pandaCoords = board.getPandaCoords().clone();
        for (HexagoneBoxPlaced box : board.placedBox.values()) {
            if (box.getColor()== Color.Lac){
                continue;
            }
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
}