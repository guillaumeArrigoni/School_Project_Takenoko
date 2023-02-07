package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BotSimulator extends Bot{

    ActionLog instructions;

    boolean legal;
    /**
     * Constructor of the bot
     *
     * @param name                        the name of the bot
     * @param board                       the board of the game
     * @param gestionObjectives
     * @param retrieveBoxIdWithParameters
     * @param bambooEated
     */
    public BotSimulator(String name, Board board, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color, Integer> bambooEated, ActionLog instructions) {
        super(name + 's', board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated);
        this.instructions = instructions;
        legal = true;

    }

    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg) {
        if (isObjectiveIllegal(instructions.getAction())){
            legal = false;
            return;
        }
        doAction(arg);
    }

    @Override
    protected void doAction(String arg) {
        switch (instructions.getAction()){
            case DRAW_AND_PUT_TILE:
                placeTile(arg);
                break;
            case MOVE_GARDENER:
                moveGardener(arg);
                break;
            case DRAW_OBJECTIVE:
                drawObjective(arg);
                break;
            default://MOVE PANDA
                movePanda(arg);
        }

    }

    @Override
    protected void placeTile(String arg){
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Draw three tiles
        for(int i = 0; i < 3; i++)
            list.add(board.getElementOfTheBoard().getStackOfBox().getFirstBox());
        //Choose a random tile from the tiles drawn
        HexagoneBox tileToPlace = list.get(0);
        //Choose a random available space
        int[] placedTileCoords = instructions.getParameters();
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0],placedTileCoords[1],placedTileCoords[2],tileToPlace,retrieveBoxIdWithParameters,board);
        //Add the tile to the board
        board.addBox(placedTile);
    }

    @Override
    protected void moveGardener(String arg) {
        if(Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).contains(instructions.getParameters()))
            board.setGardenerCoords(instructions.getParameters());
        else{
            legal = false;
        }
    }

    @Override
    protected void movePanda(String arg) {
        board.setPandaCoords(instructions.getParameters(),this);
    }

    @Override
    public void drawObjective(String arg) {
        switch(instructions.getParameters()[0]){
            case 0 -> gestionObjectives.rollParcelleObjective(this, arg);
            case 1 -> gestionObjectives.rollPandaObjective(this, arg);
            case 2 -> gestionObjectives.rollJardinierObjective(this, arg);
        }
    }

    @Override
    public void addScore(Objective objective, String arg){
        this.score += objective.getValue();
    }

    public boolean isLegal(){
        return legal;
    }

    public ActionLog getInstructions(){
        return instructions;
    }

}
