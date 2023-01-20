package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

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
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated);
        this.instructions = instructions;
        legal = true;

    }


    @Override
    public void playTurn(MeteoDice.Meteo meteo) {
        if ((instructions.getAction() == PossibleActions.MOVE_GARDENER &&  Action.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords()).isEmpty()) ||
                (instructions.getAction() == PossibleActions.DRAW_OBJECTIVE && objectives.size() == 5)){
            legal = false;
            return;
        }
        doAction();
    }

    @Override
    protected void doAction() {
        switch (instructions.getAction()){
            case DRAW_AND_PUT_TILE:
                placeTile();
                break;
            case MOVE_GARDENER:
                moveGardener();
                break;
            case DRAW_OBJECTIVE:
                drawObjective();
                break;
        }

    }

    @Override
    protected void placeTile() {

    }

    @Override
    protected void moveGardener() {

    }

    @Override
    public void drawObjective() {


    }

    public boolean isLegal(){
        return legal;
    }

    public ActionLog getInstructions(){
        return instructions;
    }

}
