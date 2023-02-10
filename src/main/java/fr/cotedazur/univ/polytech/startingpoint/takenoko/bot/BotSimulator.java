package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.tree.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.tree.ActionLogIrrigation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.Objective;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The `BotSimulator` class provides a simulation environment for bots to interact in.
 *
 */
public class BotSimulator extends Bot{

    /**
     * the instructions of the bot
     */
    ActionLog instructions;

    /**
     * if the instructions are legal
     */
    boolean legal;

    /**
     * Constructs a new `Bot` instance with the given parameters.
     *
     * @param bot The bot to simulate
     * @param board The game board
     * @param gestionObjectives A class that manages the objectives of the game
     * @param retrieveBoxIdWithParameters A class that retrieves the box ID
     * @param bambooEaten A map of the bamboo eaten by color
     * @param instructions The instructions of the bot
     * @param objectives The objectives of the game
     */
    public BotSimulator(Bot bot, Board board, GestionObjectives gestionObjectives, List<Objective> objectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color, Integer> bambooEaten, ActionLog instructions) {
        super(bot.name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEaten, bot.logInfoDemo);
        this.objectives = objectives;
        this.instructions = instructions;
        this.nbIrrigation = bot.nbIrrigation;
        this.numberObjectiveDone = bot.numberObjectiveDone;
        legal = true;

    }

    /**
     * Allows the bot to play a turn
     * @param meteo The weather of the turn
     * @param arg  A string argument for the logger
     */
    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg) {
        if (isObjectiveIllegal(instructions.getAction())){
            legal = false;
            return;
        }
        launchAction(arg);
    }

    /**
     * Allows the bot to initiate an action
     * @param arg A string argument for the logger
     */
    @Override
    protected void launchAction(String arg){
        PossibleActions action = instructions.getAction();
        doAction(arg,action);
    }
    /**
     * Allows the bot to place a tile
     * @param arg A string argument for the logger
     */
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
        board.addBox(placedTile,this);
    }

    /**
     * Allows the bot to place an irrigation
     * @param arg A string argument for the logger
     */
    protected void placeIrrigationBase(String arg){
        ActionLogIrrigation actionLogIrrigation = (ActionLogIrrigation) instructions;
        for (ArrayList<Crest> path : actionLogIrrigation.getParamirrig()) {
            board.placeIrrigation(path.get(0));
            nbIrrigation--;
        }
    }
    /**
     * Allows the bot to grow bamboo if the weather is the rain
     * @param arg A string argument for the logger
     */
    protected void growBambooRain(String arg){
        HexagoneBoxPlaced box = getBoard().getPlacedBox().get(instructions.getParameters()[0]);
        board.growAfterRain(box);
    }
    /**
     * Allows the bot to move the gardener
     * @param arg A string argument for the logger
     */
    @Override
    protected void moveGardener(String arg) {
        board.setGardenerCoords(instructions.getParameters(),this);
    }
    /**
     * Allows the bot to place an augment
     * @param arg A string argument for the logger
     */
    @Override
    protected void placeAugment(String arg){
        HexagoneBoxPlaced box = board.getPlacedBox().get(instructions.getParameters()[0]);
        switch (instructions.getParameters()[1]) {
            case 1 -> {
                board.getElementOfTheBoard().pickSpecial(Special.SOURCE_EAU);
                box.setSpecial(Special.SOURCE_EAU);
            }
            case 2 -> {
                board.getElementOfTheBoard().pickSpecial(Special.ENGRAIS);
                box.setSpecial(Special.ENGRAIS);
            }
            default -> {
                board.getElementOfTheBoard().pickSpecial(Special.PROTEGER);
                box.setSpecial(Special.PROTEGER);
            }
        }
    }
    /**
     * Allows the bot to move the panda
     * @param arg A string argument for the logger
     */
    @Override
    protected void movePanda(String arg) {
        board.setPandaCoords(instructions.getParameters(),this);
    }
    /**
     * Allows the bot to draw an objective
     * @param arg A string argument for the logger
     */
    @Override
    public void drawObjective(String arg) {
        switch(instructions.getParameters()[0]){
            case 0 -> gestionObjectives.rollParcelleObjective(this);
            case 1 -> gestionObjectives.rollPandaObjective(this);
            default -> gestionObjectives.rollJardinierObjective(this);
        }
    }

    //Getters
    public boolean isLegal(){
        return legal;
    }

    public ActionLog getInstructions(){
        return instructions;
    }

    public void setInstructions(ActionLog instructions){
        this.instructions = instructions;
    }

}
