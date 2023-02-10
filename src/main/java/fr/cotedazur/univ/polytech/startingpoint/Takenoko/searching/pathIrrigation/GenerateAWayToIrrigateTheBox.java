package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnary;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;

import java.util.*;

public class GenerateAWayToIrrigateTheBox {

    private final HexagoneBoxPlaced box;
    private final CrestGestionnary crestGestionnary;
    private ArrayList<Crest> closestCrestToIrrigatedOfTheBox;
    private BoardSimulation boardSimulation;
    private CrestGestionnarySimulation crestGestionnarySimulation;
    private Crest fakeCrest;

    /**
     * TODO : in order to be able to use all the different way to irrigate this box,
     *  replace the Crest with a Hashmap with as key a constant
     *  that will determinate the way correponding and as value the crest corresponding
     *  to know the sub path, each new range from the box, ad a digit to the key :
     *              Range 1 : 1   2   3   ...
     *              Range 2 : 10    21,22    31,32,33
     *              Range 3 : 101,102     210;221,222;223     ...
     * To have a path just take the first element for the different ArrayList :
     * pathToIrrigation.get(0).get(0)
     * pathToIrrigation.get(1).get(0)
     * pathToIrrigation.get(2).get(0)
     * pathToIrrigation.get(3).get(0)
     * etc...
     * That way fix a possible bug
     */
    private ArrayList<ArrayList<Crest>> pathToIrrigation;



    /**
     * Will create the different way to irrigate a box
     * @param box : the box that will be used to generate the path
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    public GenerateAWayToIrrigateTheBox(HexagoneBoxPlaced box) throws CrestNotRegistered, CloneNotSupportedException {
        this.box = box;
        this.crestGestionnary = box.getBoard().getCrestGestionnary();
        setup();
    }

    /**
     * Will create the different way to irrigate a box
     * @param box : the box that will be used to generate the path
     * @param board : the board that will be cloned in order to generate the path in his clone
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    public GenerateAWayToIrrigateTheBox(HexagoneBoxPlaced box, BoardSimulation board) throws CrestNotRegistered, CloneNotSupportedException {
        this.box = box;
        this.crestGestionnary = board.getCrestGestionnary();
        setup();
    }



    public ArrayList<Crest> getClosestCrestToIrrigatedOfTheBox() {
        return closestCrestToIrrigatedOfTheBox;
    }

    public ArrayList<ArrayList<Crest>> getPathToIrrigation() {
        return pathToIrrigation;
    }


    /**
     * Method use to set up the generation
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    private void setup() throws CrestNotRegistered, CloneNotSupportedException {
        fakeCrest = new Crest(99,99,1);
        setupClosestCrest();
        this.boardSimulation = new BoardSimulation((Board) box.getBoard().clone());
        this.crestGestionnarySimulation = new CrestGestionnarySimulation(boardSimulation.getCrestGestionnary());
        BotRandom bot = new BotRandom("bot",boardSimulation,new Random(),new GestionObjectives(boardSimulation,new RetrieveBoxIdWithParameters(),new LoggerError(true)),new RetrieveBoxIdWithParameters(),new HashMap<>(),new LogInfoDemo(true));
        if (!this.boardSimulation.getPlacedBox().containsKey(box.getId())){
            boardSimulation.addBox(box,bot);
        }
        setupPath(closestCrestToIrrigatedOfTheBox);
    }

    /**
     * Method use to get the closestCrest to be irrigated for the box as global parameters
     */
    private void setupClosestCrest(){
        ArrayList<Crest> listOfClosestToIrrigation = new ArrayList<>();
        ArrayList<Crest> listAdjCrest = box.getListOfCrestAroundBox();
        int rangeFirstCrestInList = 999;
        for(Crest crest : listAdjCrest){
            int rangeCrest = crest.getRange_to_irrigation();
            if (rangeCrest==rangeFirstCrestInList){
                listOfClosestToIrrigation.add(crest);
            } else if (rangeCrest<rangeFirstCrestInList){
                listOfClosestToIrrigation = new ArrayList<>(Arrays.asList(crest));
                rangeFirstCrestInList = rangeCrest;
            }
        }
        this.closestCrestToIrrigatedOfTheBox = listOfClosestToIrrigation;
    }

    /**
     * Method use to get the path to irrigate a box with as reference the Crest's parameter
     * @param crest : the crest use as origin to generate the path
     *                  <=> the path to irrigate this Crest
     * @throws CrestNotRegistered
     */
    private void setupPathWithOneCrest(Crest crest) throws CrestNotRegistered {
        setupPath(new ArrayList<>(Arrays.asList(crest)));
    }

    /**
     * Algorithm use to generate the path
     * @param crests : the list of origin crest
     *         <=> The crest that will be irrigated thanks to the path
     *         (Unless the bug exposed before is fix,
     *         only the path to irrigate the first crest of this list is sure to don't throw any error)
     * @throws CrestNotRegistered
     */
    private void setupPath(ArrayList<Crest> crests) throws CrestNotRegistered {
        ArrayList<ArrayList<Crest>> intructions = new ArrayList<>();
        intructions.add(new ArrayList<>(crests));
        int rangeFirstCrestInlist = tryGetRange(intructions.get(0).get(0));
        boolean pass = true;
        while (rangeFirstCrestInlist > 1 && pass) {
            ArrayList<Crest> listCrestToAdd = new ArrayList<>();
            for (Crest crestInList : intructions.get(0)){
                if (crestInList.getId() == (fakeCrest.getId())){
                    pass = false;
                } else if (!crestGestionnarySimulation.getListOfCrestIrrigated().contains(crestInList) && !crestInList.isIrrigated()){
                    listCrestToAdd.addAll((Collection<? extends Crest>) crestGestionnarySimulation.getLinkCrestChildrenToCrestParent().get(crestInList).clone());
                    LinkedHashSet<Crest> set = new LinkedHashSet<>(listCrestToAdd);
                    listCrestToAdd.clear();
                    listCrestToAdd.addAll(set);
                    listCrestToAdd.removeAll(crestGestionnarySimulation.getListOfCrestIrrigated());
                }
            }
            if (!listCrestToAdd.isEmpty()){
                intructions.add(0,listCrestToAdd);
            }
            rangeFirstCrestInlist = tryGetRange(intructions.get(0).get(0));
        }
        this.pathToIrrigation = intructions;
    }

    /**
     * Method use to handle the error when the algorithm look for the range of a Crest to the irrigation
     * Happen chen the crestGestionnary is not update and thus all the crest to the range of the box are not generated
     *      <=> the target box may not be placed in the board
     * @param crest : the crest which one the range will be given
     * @return
     */
    private int tryGetRange(Crest crest){
        if (!crest.equals(fakeCrest)){
            try {
                return crestGestionnarySimulation.getRangeFromIrrigatedOfCrest(crest);
            } catch (CrestNotRegistered e) {
                System.err.println("\n  -> An error has occurred : "
                        + e.getErrorTitle()
                        + "\n"
                        + "In line :\n"
                        + e.getStackTrace()
                        + "\n\n"
                        + "Fatal error.");
                throw new RuntimeException();
            }
        } else {
            return 0;
        }
    }
}
