package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLogIrrigation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.lang.reflect.Array;
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
    public BotSimulator(String name, Board board, GestionObjectives gestionObjectives, ArrayList<Objective> objectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color, Integer> bambooEated, ActionLog instructions, int nbIrrigation, LogInfoDemo logInfoDemo, int numberObjectiveDone) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated, logInfoDemo);
        this.objectives = objectives;
        this.instructions = instructions;
        this.nbIrrigation = nbIrrigation;
        this.numberObjectiveDone = numberObjectiveDone;
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
            case TAKE_IRRIGATION:
                takeIrrigation(arg);
                break;
            case PLACE_IRRIGATION:
                placeIrrigation(arg);
                break;
            case GROW_BAMBOO:
                growBambooRain(arg);
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
        HexagoneBox tileToPlace = list.get(instructions.getParameters()[3]);
        //Choose a random available space
        int[] placedTileCoords = new int[]{instructions.getParameters()[0],instructions.getParameters()[1],instructions.getParameters()[2]};
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0],placedTileCoords[1],placedTileCoords[2],tileToPlace,retrieveBoxIdWithParameters,board);
        //Add the tile to the board
        board.addBox(placedTile);
    }

    protected void takeIrrigation(String arg){
        nbIrrigation++;
    }

    protected void placeIrrigation(String arg){
        ActionLogIrrigation actionLogIrrigation = (ActionLogIrrigation) instructions;
        for (ArrayList<Crest> path : actionLogIrrigation.getParamirrig()) {
            board.placeIrrigation(path.get(0));
            nbIrrigation--;
        }
    }

    protected void growBambooRain(String arg){
        HexagoneBoxPlaced box = getBoard().getPlacedBox().get(instructions.getParameters()[0]);
        box.growBamboo();
    }

    @Override
    protected void moveGardener(String arg) {
        board.setGardenerCoords(instructions.getParameters());
    }
    /*
            if(Action.possibleMoveForGardenerOrPanda(board,board.getGardenerCoords()).contains(instructions.getParameters()))
            board.setGardenerCoords(instructions.getParameters());
        else{
            legal = false;
        }
     */

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
