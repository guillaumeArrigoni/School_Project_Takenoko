package fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.ElementOfTheBoard;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.ElementOfTheBoardCheated;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.ElementOfTheBoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxSimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveSimulation;

import java.util.HashMap;
import java.util.Random;

public class BoardSimulation extends Board {

    protected RetrieveSimulation retrieveSimulation;
    protected CrestGestionnarySimulation crestGestionnarySimulation;
    public BoardSimulation(Board board) {
        this(board, new ElementOfTheBoardCheated(), board.getNumberOfPlayers());
    }

    public BoardSimulation(Board board, ElementOfTheBoard elementOfTheBoard, int numberOfPlayers){
        super(board.getRetrieveBoxIdWithParameters(), board.isAllIrrigated(), board.getIdOfTheBoard(), elementOfTheBoard, numberOfPlayers,board.getLoggerSevere());
        super.elementOfTheBoard = new ElementOfTheBoardSimulation(elementOfTheBoard);
        setupSimulation(board);
    }

    public BoardSimulation(Board board, ElementOfTheBoardCheated elementOfTheBoardCheated, int numberOfPlayers) {
        super(board.getRetrieveBoxIdWithParameters(), board.isAllIrrigated(), board.getIdOfTheBoard(), elementOfTheBoardCheated, numberOfPlayers,board.getLoggerSevere());
        super.elementOfTheBoard = elementOfTheBoardCheated;
        setupSimulation(board);
    }


    /**
     * Method use to copy the board and place in the new board generated
     * the HexagoneBoxSimulation corresponding to the box already place in the true board
     * @param board : the true box use to generate this simulation
     */
    private void setupSimulation(Board board) {
        this.crestGestionnarySimulation = new CrestGestionnarySimulation(board.getCrestGestionnary());
        this.retrieveSimulation = new RetrieveSimulation(board.getRetrieveBoxIdWithParameters());
        super.retrieveBoxIdWithParameters = this.retrieveSimulation;
        super.crestGestionnary = this.crestGestionnarySimulation;
        super.gardenerCoords = board.getGardenerCoords().clone();
        super.pandaCoords = board.getPandaCoords().clone();
        BotRandom bot = new BotRandom("bot",board,new Random(),new GestionObjectives(board,retrieveBoxIdWithParameters,new LoggerError(true)),retrieveBoxIdWithParameters,new HashMap<>(),new LogInfoDemo(true));
        for (HexagoneBoxPlaced box : board.placedBox.values()) {
            if (box.getColor()== Color.LAC){
                continue;
            }
            super.addBox(new HexagoneBoxSimulation(
                    box.getCoordinates()[0],
                    box.getCoordinates()[1],
                    box.getCoordinates()[2],
                    box.getColor(),
                    box.getSpecial(),
                    retrieveSimulation,
                    this),bot);
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