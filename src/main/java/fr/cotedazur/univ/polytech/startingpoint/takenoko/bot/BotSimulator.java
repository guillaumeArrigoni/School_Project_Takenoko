package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.MCTS.ActionLogIrrigation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BotSimulator extends Bot{

    ActionLog instructions;

    boolean legal;
    /**
     * Constructor of the bot
     *the name of the bot
     * @param board                       the board of the game
     * @param gestionObjectives             d
     * @param retrieveBoxIdWithParameters       d
     * @param bambooEated                   d
     */
    public BotSimulator(Bot bot, Board board, GestionObjectives gestionObjectives, List<Objective> objectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color, Integer> bambooEated, ActionLog instructions) {
        super(bot.name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated, bot.logInfoDemo);
        this.objectives = objectives;
        this.instructions = instructions;
        this.nbIrrigation = bot.nbIrrigation;
        this.numberObjectiveDone = bot.numberObjectiveDone;
        legal = true;

    }

    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg) {
        if (isObjectiveIllegal(instructions.getAction())){
            legal = false;
            return;
        }
        launchAction(arg);
    }

    @Override
    public void movePandaStorm() {
    }

    @Override
    protected void launchAction(String arg){
        PossibleActions action = instructions.getAction();
        doAction(arg,action);
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


    protected void placeIrrigation(String arg){
        ActionLogIrrigation actionLogIrrigation = (ActionLogIrrigation) instructions;
        for (ArrayList<Crest> path : actionLogIrrigation.getParamirrig()) {
            board.placeIrrigation(path.get(0));
            nbIrrigation--;
        }
    }

    protected void growBambooRain(String arg){
        HexagoneBoxPlaced box = getBoard().getPlacedBox().get(instructions.getParameters()[0]);
        board.growAfterRain(box);
    }

    @Override
    protected void moveGardener(String arg) {
        board.setGardenerCoords(instructions.getParameters());
    }

    @Override
    protected void placeAugment(String arg){
        HexagoneBoxPlaced box = board.getPlacedBox().get(instructions.getParameters()[0]);
        switch (instructions.getParameters()[1]) {
            case 1 -> {
                board.getElementOfTheBoard().pickSpecial(Special.SourceEau);
                box.setSpecial(Special.SourceEau);
            }
            case 2 -> {
                board.getElementOfTheBoard().pickSpecial(Special.Engrais);
                box.setSpecial(Special.Engrais);
            }
            default -> {
                board.getElementOfTheBoard().pickSpecial(Special.Protéger);
                box.setSpecial(Special.Protéger);
            }
        }
    }

    @Override
    protected void movePanda(String arg) {
        board.setPandaCoords(instructions.getParameters(),this);
    }

    @Override
    public void drawObjective(String arg) {
        switch(instructions.getParameters()[0]){
            case 0 -> gestionObjectives.rollParcelleObjective(this);
            case 1 -> gestionObjectives.rollPandaObjective(this);
            case 2 -> gestionObjectives.rollJardinierObjective(this);
        }
    }

    public boolean isLegal(){
        return legal;
    }

    public ActionLog getInstructions(){
        return instructions;
    }

}
