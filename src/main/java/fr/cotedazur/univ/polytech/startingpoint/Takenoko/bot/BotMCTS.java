package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.Node;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;

public class BotMCTS extends Bot{
    Node node;
    List<ActionLog> instructions;
    /**
     * Constructor of the bot
     *
     * @param name                        the name of the bot
     * @param board                       the board of the game     the random generator
     * @param gestionObjectives
     * @param retrieveBoxIdWithParameters
     * @param bambooEated
     */
    public BotMCTS(String name, Board board, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color, Integer> bambooEated,LogInfoDemo logInfoDemo) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated,logInfoDemo);
    }

    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg){
        node = new Node(this.createBotSimulator(), 2, meteo, arg);
        instructions = node.getBestInstruction();
//        System.out.println("instructions : " + instructions.get(0) + " " + instructions.get(1));
        for(int i = 0; i < instructions.size(); i++){
            launchAction(arg);
        }
    }

    @Override
    protected void launchAction(String arg){
        PossibleActions action = instructions.get(0).getAction();
        doAction(arg,action);
        instructions.remove(0);
    }

    @Override
    protected void placeTile(String arg){
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Draw three tiles
        for(int i = 0; i < 3; i++)
            list.add(board.getElementOfTheBoard().getStackOfBox().getFirstBox());
        //Choose a random tile from the tiles drawn
        int placedTileIndex = 0;
        HexagoneBox tileToPlace = list.get(placedTileIndex);
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get(1));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get(2));
        //Choose a random available space
        int[] placedTileCoords = instructions.get(0).getParameters();
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0],placedTileCoords[1],placedTileCoords[2],tileToPlace,retrieveBoxIdWithParameters,board);
        //Add the tile to the board
        board.addBox(placedTile);
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get(1));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get(2));
        super.logInfoDemo.displayPlacementBox(this.name,placedTile);
    }

    @Override
    protected void moveGardener(String arg) {
        board.setGardenerCoords(instructions.get(0).getParameters());
        super.logInfoDemo.displayMovementGardener(this.name,board);
    }

    @Override
    protected void movePanda(String arg) {
        board.setPandaCoords(instructions.get(0).getParameters(),this);
        super.logInfoDemo.displayMovementPanda(this.name,board);
    }

    @Override
    public void drawObjective(String arg) {
        switch(instructions.get(0).getParameters()[0]){
            case 0 -> {
                gestionObjectives.rollParcelleObjective(this, arg);
                super.logInfoDemo.displayPickPatternObj(this.name);
            }
            case 1 -> {
                gestionObjectives.rollPandaObjective(this, arg);
                super.logInfoDemo.displayPickPandaObj(this.name);
            }case 2 -> {
                gestionObjectives.rollJardinierObjective(this, arg);
                super.logInfoDemo.displayPickGardenerObj(this.name);
            }
        }
    }

    @Override
    public TypeObjective choseTypeObjectiveToRoll(String arg) {
        return null;
    }

}
