package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation.GenerateOptimizePathForSeveralBoxWithSimulation;

import java.util.*;

public class getAllBoxFillingThePatternEntered {
    private ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction;
    private HashMap<HexagoneBoxPlaced,Integer> markForEachBoxAccordingToTheInstruction;
    private HashMap<HexagoneBoxPlaced,HashMap<Integer,Boolean>> hashmapToKnowWhichIsIrrigated;
    private Board board;
    private RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    /**
     * True if we want only the pattern that allow to place the box missing.
     */
    private boolean boxCanBePlaced;

    /**
     * A Hashmap that have as key the position of the box and as value the color that those box must have.
     *                    The Hashmap MUST have the key 0 because it is the central box.
     *                    The other key are the position of the adjacent box around this central box :
     *                              6       1
     *                          5       0       2
     *                              4       3
     *                    If the Optional<Color> is empty that mean no Box should have been place in this venue
     *                    The class will check if :
     *                          in this position OR
     *                          in any other position rotated
     *                    The instruction is verified
     */
    private HashMap<Integer, Optional<Color>> instruction;


    public getAllBoxFillingThePatternEntered(HashMap<Integer, Optional<Color>> instruction, Board board, boolean boxCanBePlaced) throws CrestNotRegistered, CloneNotSupportedException {
        this.board = board;
        this.retrieveBoxIdWithParameters = board.getRetrieveBoxIdWithParameters();
        this.boxCanBePlaced = boxCanBePlaced;
        getAllBoxFillingTheInstruction_v1_3();
    }

    public ArrayList<HexagoneBoxPlaced> getListBoxThatAreFillingTheInstruction() {
        return listBoxThatAreFillingTheInstruction;
    }

    public HashMap<HexagoneBoxPlaced, Integer> getMarkForEachBoxAccordingToTheInstruction() {
        return markForEachBoxAccordingToTheInstruction;
    }

    public HashMap<HexagoneBoxPlaced, HashMap<Integer, Boolean>> getHashmapToKnowWhichIsIrrigated() {
        return hashmapToKnowWhichIsIrrigated;
    }



    /**
     * Method v1.3
     * Method that give all the box that are filling the instruction.
     * @return the ArrayList with all the central box that are filling the condition
     */
    private void getAllBoxFillingTheInstruction_v1_3() throws CrestNotRegistered, CloneNotSupportedException {
        ArrayList<Integer> listOfAllBoxIdThatFillInstruction = this.retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(new ArrayList<>(Arrays.asList(this.instruction.get(0).get()))),Optional.empty(),Optional.empty(),Optional.empty());
        ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction = new ArrayList<>();
        HashMap<HexagoneBoxPlaced,Integer> markForEachBoxAccordingToTheInstruction = new HashMap<>();
        HashMap<HexagoneBoxPlaced,HashMap<Integer,Boolean>> hashmapToKnowWhichIsIrrigated = new HashMap<>();
        for (Integer id : listOfAllBoxIdThatFillInstruction){
            comparatorForEachBox_v1_3(listBoxThatAreFillingTheInstruction, markForEachBoxAccordingToTheInstruction, hashmapToKnowWhichIsIrrigated, id);
        }
        this.listBoxThatAreFillingTheInstruction = listBoxThatAreFillingTheInstruction;
        this.markForEachBoxAccordingToTheInstruction = markForEachBoxAccordingToTheInstruction;
        this.hashmapToKnowWhichIsIrrigated = hashmapToKnowWhichIsIrrigated;
    }

    private void comparatorForEachBox_v1_3(ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction, HashMap<HexagoneBoxPlaced, Integer> markForEachBoxAccordingToTheInstruction, HashMap<HexagoneBoxPlaced, HashMap<Integer, Boolean>> hashmapToKnowWhichIsIrrigated, Integer id) throws CrestNotRegistered, CloneNotSupportedException {
        HexagoneBoxPlaced box = board.getPlacedBox().get(id);
        ArrayList<HashMap<Integer,Boolean>> whichIsIrrigated = new ArrayList<>();
        ArrayList<ArrayList<HexagoneBoxPlaced>> listOfBoxNOtIrrigated = new ArrayList<>();
        boolean test = doesPassAllInstruction_v1_3(box, whichIsIrrigated, listOfBoxNOtIrrigated);
        if (test){
            int index = getBestConfigurationForABox_v1_3(listOfBoxNOtIrrigated);
            listBoxThatAreFillingTheInstruction.add(box);
            hashmapToKnowWhichIsIrrigated.put(box,whichIsIrrigated.get(index));
            markForEachBoxAccordingToTheInstruction.put(box, generateMark_v1_3(listOfBoxNOtIrrigated.get(index)));
        }
    }

    private int getBestConfigurationForABox_v1_3(ArrayList<ArrayList<HexagoneBoxPlaced>> listOfBoxNOtIrrigated) throws CrestNotRegistered, CloneNotSupportedException {
        int nbTour = generateMark_v1_3(listOfBoxNOtIrrigated.get(0));
        int index = 0;
        for (int i = 1; i< listOfBoxNOtIrrigated.size(); i++){
            int potentialNewValue = generateMark_v1_3(listOfBoxNOtIrrigated.get(i));
            if (potentialNewValue<nbTour){
                nbTour = potentialNewValue;
                index = i;
            }
        }
        return index;
    }

    private boolean doesPassAllInstruction_v1_3(HexagoneBoxPlaced box, ArrayList<HashMap<Integer, Boolean>> whichIsIrrigated, ArrayList<ArrayList<HexagoneBoxPlaced>> listOfBoxNOtIrrigated) {
        for (int i = 0;i<6;i++){
            boolean test = true;
            HashMap<Integer, Boolean> whichIsIrrigatedUnder = new HashMap<>();
            ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigatedUnder = new ArrayList<>();
            test = runInstruction_v1_3(box, i, test, whichIsIrrigatedUnder, listOfBoxNOtIrrigatedUnder);
            if (test){
                whichIsIrrigated.add(whichIsIrrigatedUnder);
                listOfBoxNOtIrrigated.add(listOfBoxNOtIrrigatedUnder);
            }
        }
        if (whichIsIrrigated.size()!=0){
            return true;
        }
        return false;
    }

    private boolean runInstruction_v1_3(HexagoneBoxPlaced box, int i, boolean test, HashMap<Integer, Boolean> whichIsIrrigatedUnder, ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigatedUnder) {
        for (int pos : this.instruction.keySet()) {
            int idOfOneAdjacentBox = HexagoneBox.generateID(box.getAdjacentBox().get(pos + i));
            if (this.instruction.get(pos).isEmpty()) {
                if (this.board.getPlacedBox().containsKey(idOfOneAdjacentBox) ||
                        (this.boxCanBePlaced && board.getAvailableBox().contains(idOfOneAdjacentBox))) test = false;
            } else {
                test = isTestSpecifiedCondition_v1_3(i, test, whichIsIrrigatedUnder, listOfBoxNOtIrrigatedUnder, pos, idOfOneAdjacentBox);
            }
            if (!test){
                break;
            }
        }
        return test;
    }

    private boolean isTestSpecifiedCondition_v1_3(int i, boolean test, HashMap<Integer, Boolean> whichIsIrrigatedUnder, ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigatedUnder, int pos, int idOfOneAdjacentBox) {
        if (this.board.getPlacedBox().containsKey(idOfOneAdjacentBox) &&
                this.board.getPlacedBox().get(idOfOneAdjacentBox).getColor() == this.instruction.get(pos).get()) {
            HexagoneBoxPlaced adjBox = this.board.getPlacedBox().get(idOfOneAdjacentBox);
            if (adjBox.isIrrigate()) {
                whichIsIrrigatedUnder.put(pos + i, true);
            } else {
                listOfBoxNOtIrrigatedUnder.add(adjBox);
            }
        } else {
            test = false;
        }
        return test;
    }

    private int generateMark_v1_3(ArrayList<HexagoneBoxPlaced> listOfBoxNotIrrigated) throws CrestNotRegistered, CloneNotSupportedException {
        return new GenerateOptimizePathForSeveralBoxWithSimulation(listOfBoxNotIrrigated).getNbTour();
    }
}
