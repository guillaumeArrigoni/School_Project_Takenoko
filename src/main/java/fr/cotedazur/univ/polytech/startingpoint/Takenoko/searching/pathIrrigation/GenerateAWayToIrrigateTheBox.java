package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnary;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

public class GenerateAWayToIrrigateTheBox {

    private final HexagoneBoxPlaced box;
    private final CrestGestionnary crestGestionnary;
    private ArrayList<Crest> closestCrestToIrrigatedOfTheBox;

    private BoardSimulation boardSimulation;
    private CrestGestionnarySimulation crestGestionnarySimulation;
    /**
     * To have a path just take the first element for the different ArrayList :
     * pathToIrrigation.get(0).get(0)
     * pathToIrrigation.get(1).get(0)
     * pathToIrrigation.get(2).get(0)
     * pathToIrrigation.get(3).get(0)
     * etc...
     */
    private ArrayList<ArrayList<Crest>> pathToIrrigation;

    public GenerateAWayToIrrigateTheBox(HexagoneBoxPlaced box) throws CrestNotRegistered, CloneNotSupportedException {
        this.box = box;
        this.crestGestionnary = box.getBoard().getCrestGestionnary();
        setup();
    }

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

    private void setup() throws CrestNotRegistered, CloneNotSupportedException {
        setupClosestCrest();
        this.boardSimulation = new BoardSimulation((Board) box.getBoard().clone());
        this.crestGestionnarySimulation = boardSimulation.getCrestGestionnary();
        boardSimulation.addBox(box);
        setupPath(closestCrestToIrrigatedOfTheBox);
    }

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

    private void setupPathWithOneCrest(Crest crest) throws CrestNotRegistered {
        setupPath(new ArrayList<>(Arrays.asList(crest)));
    }

    private void setupPath(ArrayList<Crest> crests) throws CrestNotRegistered {
        ArrayList<ArrayList<Crest>> intructions = new ArrayList<>();
        intructions.add(new ArrayList<>(crests));
        int rangeFirstCrestInlist = tryGetRange(intructions.get(0).get(0));
        while (rangeFirstCrestInlist != 1) {
            ArrayList<Crest> listCrestToAdd = crestGestionnarySimulation.getLinkCrestChildrenToCrestParent().get(intructions.get(0).get(0));
            for (Crest crestInList : intructions.get(0)){
                listCrestToAdd.addAll((Collection<? extends Crest>) crestGestionnarySimulation.getLinkCrestChildrenToCrestParent().get(crestInList).clone());
                LinkedHashSet<Crest> set = new LinkedHashSet<>(listCrestToAdd);
                listCrestToAdd.clear();
                listCrestToAdd.addAll(set);
            }
            intructions.add(0,listCrestToAdd);
            rangeFirstCrestInlist = tryGetRange(intructions.get(0).get(0));
        }
        this.pathToIrrigation = intructions;
    }

    private int tryGetRange(Crest crest){
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
    }
}
