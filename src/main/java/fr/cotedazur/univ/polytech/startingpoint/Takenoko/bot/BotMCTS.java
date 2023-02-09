package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLog;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.ActionLogIrrigation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS.Node;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
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
     * @param gestionObjectives             d
     * @param retrieveBoxIdWithParameters   d
     * @param bambooEated               d
     */
    public BotMCTS(String name, Board board, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color, Integer> bambooEated, LogInfoDemo logInfoDemo) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated, logInfoDemo);
    }

    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg) {
        if (MeteoDice.Meteo.VENT == meteo || meteo == MeteoDice.Meteo.NO_METEO) { //NOMETEO VENT
            node = new Node(this.createBotSimulator(), 3, meteo, arg);
        } else {
            node = new Node(this.createBotSimulator(), 4, meteo, arg); //ORAGE NUAGE SOLEIL PLUIE
        }
        instructions = node.getBestInstruction();
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

    /*
    @Override
    protected void doAction(String arg) {
        switch (instructions.get(0).getAction()) {
            case DRAW_AND_PUT_TILE -> placeTile(arg);
            case MOVE_GARDENER -> moveGardener(arg);
            case DRAW_OBJECTIVE -> drawObjective(arg);
            case TAKE_IRRIGATION -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a pris une irrigation");
                nbIrrigation++;}
            case PLACE_IRRIGATION -> placeIrrigation(arg);
            case GROW_BAMBOO -> growBambooRain(arg);
            case ADD_AUGMENT -> placeAugment(arg);
            default -> movePanda(arg);
        }
        instructions.remove(0);
     */


    @Override
    protected void placeTile(String arg){
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Draw three tiles
        for(int i = 0; i < 3; i++)
            list.add(board.getElementOfTheBoard().getStackOfBox().getFirstBox());
        //Choose a random tile from the tiles drawn
        int placedTileIndex = instructions.get(0).getParameters()[3];
        HexagoneBox tileToPlace = list.get(placedTileIndex);
        //Choose a random available space
        int[] placedTileCoords = new int[]{instructions.get(0).getParameters()[0],instructions.get(0).getParameters()[1],instructions.get(0).getParameters()[2]};
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0],placedTileCoords[1],placedTileCoords[2],tileToPlace,retrieveBoxIdWithParameters,board);
        //Add the tile to the board
        board.addBox(placedTile);
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 1) % 3));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 2) % 3));
        super.logInfoDemo.displayPlacementBox(this.name,placedTile);
    }

    @Override
    protected void moveGardener(String arg) {
        board.setGardenerCoords(instructions.get(0).getParameters());
        super.logInfoDemo.displayMovementGardener(this.name,board);
    }

    protected void growBambooRain(String arg){
        HexagoneBoxPlaced box = getBoard().getPlacedBox().get(instructions.get(0).getParameters()[0]);
        board.growAfterRain(box);
        logInfoDemo.addLog(this.name + " a fait pousser du bambou grâce à la pluie en " + Arrays.toString(box.getCoordinates()));
    }

    @Override
    protected void movePanda(String arg) {
        board.setPandaCoords(instructions.get(0).getParameters(),this);
        super.logInfoDemo.displayMovementPanda(this.name,board);
    }


    protected void placeIrrigation(String arg){
        ActionLogIrrigation actionLogIrrigation = (ActionLogIrrigation) instructions;
        for (ArrayList<Crest> path : actionLogIrrigation.getParamirrig()) {
            logInfoDemo.addLog("Le bot a placé une irrigation en " + Arrays.toString(path.get(0).getCoordinates()));
            board.placeIrrigation(path.get(0));
            nbIrrigation--;
        }
    }

    protected void placeAugment(String arg){
        HexagoneBoxPlaced box = board.getPlacedBox().get(instructions.get(0).getParameters()[0]);
        switch (instructions.get(0).getParameters()[1]) {
            case 1 -> {
                board.getElementOfTheBoard().pickSpecial(Special.SourceEau);
                logInfoDemo.addLog(this.name + " a placé une source d'eau en " + Arrays.toString(box.getCoordinates()));
                box.setSpecial(Special.SourceEau);
            }
            case 2 -> {
                board.getElementOfTheBoard().pickSpecial(Special.Engrais);
                box.setSpecial(Special.Engrais);
                logInfoDemo.addLog(this.name + " a placé un engrais en " + Arrays.toString(box.getCoordinates()));
            }
            default -> {
                board.getElementOfTheBoard().pickSpecial(Special.Protéger);
                box.setSpecial(Special.Protéger);
                logInfoDemo.addLog(this.name + " a placé une protection en " + Arrays.toString(box.getCoordinates()));
            }
        }
    }



    @Override
    public void drawObjective(String arg) {
        switch(instructions.get(0).getParameters()[0]){
            case 0 -> {
                gestionObjectives.rollParcelleObjective(this);
                super.logInfoDemo.displayPickPatternObj(this.name);
            }
            case 1 -> {
                gestionObjectives.rollPandaObjective(this);
                super.logInfoDemo.displayPickPandaObj(this.name);
            }case 2 -> {
                gestionObjectives.rollJardinierObjective(this);
                super.logInfoDemo.displayPickGardenerObj(this.name);
            }
        }
    }

    @Override
    public TypeObjective choseTypeObjectiveToRoll(String arg) {
        return null;
    }
}
