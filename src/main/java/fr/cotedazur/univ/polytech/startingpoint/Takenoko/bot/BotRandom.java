package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.TypeObjective;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation.GenerateAWayToIrrigateTheBox;

import java.util.*;


/**
 * This class is the bot that will play the game
 */
public class BotRandom extends Bot {

    /**
     * The random generator
     */
    private final Random random;

    public BotRandom(String name, Board board, Random random, GestionObjectives gestionObjectives, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Map<Color, Integer> bambooEated, LogInfoDemo logInfoDemo) {
        super(name, board, gestionObjectives, retrieveBoxIdWithParameters, bambooEated, logInfoDemo);
        this.random = random;
    }


    //METHODS

    @Override
    public void playTurn(MeteoDice.Meteo meteo, String arg) {
        possibleActions = PossibleActions.getAllActions();
        placeIrrigation(arg);
        switch (meteo) {
            case VENT -> {
                //Deux fois la même action autorisé
                if (arg.equals("demo")) logInfoDemo.addLog("Le dé a choisi : VENT");
                doAction(arg);
                possibleActions = PossibleActions.getAllActions();
                doAction(arg);
            }
            case PLUIE -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le dé a choisi : PLUIE");
                growBambooRain(arg);
                doAction(arg);
                doAction(arg);
            }
            case NUAGES -> {
                logInfoDemo.addLog("Le dé a choisi : NUAGES");
                placeAugment(arg);
                doAction(arg);
                doAction(arg);
            }
            case ORAGE -> {
                logInfoDemo.addLog("Le dé a choisi : ORAGE");
                movePandaStorm();
                doAction(arg);
                doAction(arg);
            }
            default/*SOLEIL*/ -> {
                logInfoDemo.addLog("Le dé a choisi : SOLEIL");
                doAction(arg);
                doAction(arg);
                doAction(arg);
            }


        }
    }

    @Override
    protected void doAction(String arg) {
        PossibleActions action = chooseAction();
        switch (action) {
            case DRAW_AND_PUT_TILE -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a choisi : PiocherPoserTuile");
                placeTile(arg);
            }
            case MOVE_GARDENER -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a choisi : BougerJardinier");
                moveGardener(arg);
            }
            case DRAW_OBJECTIVE -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a choisi : PiocherObjectif");
                drawObjective(arg);
            }
            case MOVE_PANDA -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a choisi : BougerPanda");
                movePanda(arg);
            }
            default /*TAKE_IRRIGATION*/ -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a choisi : PrendreIrrigation");
                this.nbIrrigation++;
                placeIrrigation(arg);
            }
        }
    }

    @Override
    protected void placeTile(String arg) {
        //Init
        List<HexagoneBox> list = new ArrayList<>();
        //Get all the available coords
        List<int[]> availableTilesList = board.getAvailableBox().stream().toList();
        //Draw three tiles
        for (int i = 0; i < 3; i++)
            list.add(board.getElementOfTheBoard().getStackOfBox().getFirstBox());
        //Choose a random tile from the tiles drawn
        int placedTileIndex = random.nextInt(0, 3);
        HexagoneBox tileToPlace = list.get(placedTileIndex);
        //Choose a random available space
        int[] placedTileCoords = availableTilesList.get(random.nextInt(0, availableTilesList.size()));
        //Set the coords of the tile
        HexagoneBoxPlaced placedTile = new HexagoneBoxPlaced(placedTileCoords[0], placedTileCoords[1], placedTileCoords[2], tileToPlace, retrieveBoxIdWithParameters, board);
        //Add the tile to the board
        board.addBox(placedTile);
        if (arg.equals("demo"))
            logInfoDemo.addLog(this.name + " a placé une tuile " + tileToPlace.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 2) % 3));
        board.getElementOfTheBoard().getStackOfBox().addNewBox(list.get((placedTileIndex + 1) % 3));
        if (arg.equals("demo"))
            logInfoDemo.addLog(this.name + " a placé une tuile " + placedTile.getColor() + " en " + Arrays.toString(placedTile.getCoordinates()));
    }

    @Override
    protected void moveGardener(String arg) {
        List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(board, board.getGardenerCoords());
        board.setGardenerCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())));
        if (arg.equals("demo"))
            logInfoDemo.addLog(this.name + " a déplacé le jardinier en " + Arrays.toString(board.getGardenerCoords()));
    }

    @Override
    public void movePanda(String arg) {
        List<int[]> possibleMoves = Bot.possibleMoveForGardenerOrPanda(board, board.getPandaCoords());
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())), this);
        if (arg.equals("demo"))
            logInfoDemo.addLog(this.name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()));
    }

    @Override
    public void growBambooRain(String arg) {
        List<HexagoneBoxPlaced> tmp = new ArrayList<>();
        for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
            if (box.isIrrigate() && box.getHeightBamboo() < 4) {
                tmp.add(box);
            }
        }
        if (!tmp.isEmpty()) {
            HexagoneBoxPlaced box = tmp.get(random.nextInt(0, tmp.size()));
            board.growAfterRain(box);
            logInfoDemo.addLog(this.name + " a fait pousser du bambou grâce à la pluie en " + Arrays.toString(box.getCoordinates()));
        }
    }

    @Override
    public void drawObjective(String arg) {
        gestionObjectives.rollObjective(this, arg);
    }

    @Override
    public void placeIrrigation(String arg) {
        if (random.nextInt(0, 4) == 0) {
            List<GenerateAWayToIrrigateTheBox> tmp = new ArrayList<>();
            GenerateAWayToIrrigateTheBox temp;
            for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
                if (!box.isIrrigate()) {
                    try {
                        temp = new GenerateAWayToIrrigateTheBox(box);
                        if (temp.getPathToIrrigation().size() <= this.nbIrrigation)
                            tmp.add(temp);
                    } catch (Exception e) {
                        logInfoDemo.addLog("erreur irrigation");
                    }
                }
            }
            if (!tmp.isEmpty()) {
                temp = tmp.get(random.nextInt(0, tmp.size()));
                for (ArrayList<Crest> path : temp.getPathToIrrigation()) {
                    logInfoDemo.addLog("Le bot a placé une irrigation en " + Arrays.toString(path.get(0).getCoordinates()));
                    board.placeIrrigation(path.get(0));
                    nbIrrigation--;
                }
            }
        }
    }

    protected void placeAugment(String arg) {
        int rdm = random.nextInt(1, 4);
        Special special = null;
        boolean x = board.getElementOfTheBoard().getNbJetonSpecial().get(Special.SourceEau) > 0 ||
                board.getElementOfTheBoard().getNbJetonSpecial().get(Special.Engrais) > 0 ||
                board.getElementOfTheBoard().getNbJetonSpecial().get(Special.Protéger) > 0;
        while (x) {
            switch (rdm) {
                case 1 -> special = Special.SourceEau;
                case 2 -> special = Special.Engrais;
                default -> special = Special.Protéger;
            }
            x = !board.getElementOfTheBoard().pickSpecial(special);
            rdm = ((rdm + 1) % 3) + 1;
        }

        if (special != null) {
            List<HexagoneBoxPlaced> tmp = new ArrayList<>();
            for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
                if (box.getSpecial() == Special.Classique) {
                    tmp.add(box);
                }
            }
            if (!tmp.isEmpty()) {
                HexagoneBoxPlaced box = tmp.get(random.nextInt(0, tmp.size()));
                box.setSpecial(special);
                if (arg.equals("demo"))
                    logInfoDemo.addLog(this.name + " a placé une " + special + " en " + Arrays.toString(box.getCoordinates()));
            }
        }
    }

    protected PossibleActions chooseAction() {
        PossibleActions acp = possibleActions.get(random.nextInt(possibleActions.size()));
        //Check if the action is possible
        if (isObjectiveIllegal(acp) ||
                (acp != PossibleActions.DRAW_OBJECTIVE && acp != PossibleActions.DRAW_AND_PUT_TILE && acp != PossibleActions.MOVE_GARDENER && acp != PossibleActions.MOVE_PANDA && acp != PossibleActions.TAKE_IRRIGATION))
            return chooseAction();
        possibleActions.remove(acp);
        return acp;
    }

    public void movePandaStorm() {
        List<int[]> possibleMoves = new ArrayList<>();
        for (HexagoneBoxPlaced box : board.getPlacedBox().values()) {
            possibleMoves.add(box.getCoordinates());
        }
        board.setPandaCoords(possibleMoves.get(random.nextInt(0, possibleMoves.size())), this);
        logInfoDemo.addLog(this.name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()) + " grâce à l'orage");
    }

    public TypeObjective choseTypeObjectiveToRoll(String arg) {
        int i = random.nextInt(0, 3);
        switch (i) {
            case 1 -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a choisi : Piocher un objectif de jardinier");
                return TypeObjective.JARDINIER;
            }
            case 2 -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a choisi : Piocher un objectif de panda");
                return TypeObjective.PANDA;
            }
            default -> {
                if (arg.equals("demo")) logInfoDemo.addLog("Le bot a choisi : Piocher un objectif de parcelle");
                return TypeObjective.PARCELLE;
            }
        }
    }
}

