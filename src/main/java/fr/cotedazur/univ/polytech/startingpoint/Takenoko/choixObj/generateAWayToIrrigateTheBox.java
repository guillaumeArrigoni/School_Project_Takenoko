package fr.cotedazur.univ.polytech.startingpoint.Takenoko.choixObj;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnary;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.util.ArrayList;
import java.util.Arrays;

public class generateAWayToIrrigateTheBox {

    private final HexagoneBoxPlaced box;
    private final CrestGestionnary crestGestionnary;
    private ArrayList<Crest> closestCrestToIrrigatedOfTheBox;
    private ArrayList<ArrayList<Crest>> pathToIrrigation;

    public generateAWayToIrrigateTheBox(HexagoneBoxPlaced box) throws CrestNotRegistered {
        this.box = box;
        this.crestGestionnary = box.getBoard().getCrestGestionnary();
        setup();
    }

    public ArrayList<Crest> getClosestCrestToIrrigatedOfTheBox() {
        return closestCrestToIrrigatedOfTheBox;
    }

    public ArrayList<ArrayList<Crest>> getPathToIrrigation() {
        return pathToIrrigation;
    }

    private void setup() throws CrestNotRegistered {
        setupClosestCrest();
        setupPath(closestCrestToIrrigatedOfTheBox);
    }

    private void setupClosestCrest(){
        ArrayList<Crest> listOfClosestToIrrigation = new ArrayList<>(Arrays.asList(box.getListOfCrestAroundBox().get(0)));
        ArrayList<Crest> listAdjCrest = box.getListOfCrestAroundBox();
        for(Crest crest : listAdjCrest){
            int rangeCrest = crest.getRange_to_irrigation();
            int rangeFirstCrestInList = listOfClosestToIrrigation.get(0).getRange_to_irrigation();
            if (rangeCrest==rangeFirstCrestInList){
                listOfClosestToIrrigation.add(crest);
            } else if (rangeCrest<rangeFirstCrestInList){
                listOfClosestToIrrigation = new ArrayList<>(Arrays.asList(crest));
            }
        }
        this.closestCrestToIrrigatedOfTheBox = listOfClosestToIrrigation;
    }

    private void setupPathWithOneCrest(Crest crest) throws CrestNotRegistered {
        ArrayList<ArrayList<Crest>> intructions = new ArrayList<>();
        intructions.add(new ArrayList<>(Arrays.asList(crest)));
        int rangeFirstCrestInlist = tryGetRange(intructions.get(0).get(0));
        while (rangeFirstCrestInlist != 1) {
            ArrayList<Crest> listCrestToAdd = crestGestionnary.getLinkCrestChildrenToCrestParent().get(intructions.get(0).get(0));
            for (Crest crestInList : intructions.get(0)){
                listCrestToAdd.addAll(crestGestionnary.getLinkCrestChildrenToCrestParent().get(crestInList));
            }
            intructions.add(0,listCrestToAdd);
            rangeFirstCrestInlist = tryGetRange(intructions.get(0).get(0));
        }
        this.pathToIrrigation = intructions;
    }

    private void setupPath(ArrayList<Crest> crests) throws CrestNotRegistered {
        ArrayList<ArrayList<Crest>> intructions = new ArrayList<>();
        intructions.add(new ArrayList<>(crests));
        int rangeFirstCrestInlist = tryGetRange(intructions.get(0).get(0));
        while (rangeFirstCrestInlist != 1) {
            ArrayList<Crest> listCrestToAdd = crestGestionnary.getLinkCrestChildrenToCrestParent().get(intructions.get(0).get(0));
            for (Crest crestInList : intructions.get(0)){
                listCrestToAdd.addAll(crestGestionnary.getLinkCrestChildrenToCrestParent().get(crestInList));
            }
            intructions.add(0,listCrestToAdd);
            rangeFirstCrestInlist = tryGetRange(intructions.get(0).get(0));
        }
        this.pathToIrrigation = intructions;
    }

    private int tryGetRange(Crest crest){
        try {
            return crestGestionnary.getRangeFromIrrigatedOfCrest(crest);
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
