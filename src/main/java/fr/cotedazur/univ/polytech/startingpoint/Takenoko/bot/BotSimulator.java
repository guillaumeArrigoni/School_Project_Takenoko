package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLogIrrigation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
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
        doAction(arg);
    }

    @Override
    protected void doAction(String arg) {
        switch (instructions.getAction()) {
            case DRAW_AND_PUT_TILE -> placeTile(arg);
            case MOVE_GARDENER -> moveGardener(arg);
            case DRAW_OBJECTIVE -> drawObjective(arg);
            case TAKE_IRRIGATION -> nbIrrigation++;
            case PLACE_IRRIGATION -> placeIrrigation(arg);
            case GROW_BAMBOO -> growBambooRain(arg);
            case ADD_AUGMENT -> placeAugment(arg);
            default ->//MOVE PANDA
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
            case 0 -> gestionObjectives.rollParcelleObjective(this, arg);
            case 1 -> gestionObjectives.rollPandaObjective(this, arg);
            default -> gestionObjectives.rollJardinierObjective(this, arg);
        }
    }

    public boolean isLegal(){
        return legal;
    }

    public ActionLog getInstructions(){
        return instructions;
    }

}
