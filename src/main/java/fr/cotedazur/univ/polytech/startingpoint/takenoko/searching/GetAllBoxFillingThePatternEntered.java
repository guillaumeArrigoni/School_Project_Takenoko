package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.pathIrrigation.GenerateOptimizePathForSeveralBoxWithSimulation;

import java.util.*;

public class GetAllBoxFillingThePatternEntered {
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


    public GetAllBoxFillingThePatternEntered(HashMap<Integer, Optional<Color>> instruction, Board board, boolean boxCanBePlaced) throws CrestNotRegistered, CloneNotSupportedException {
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

    /**
     * Method use to launch the algorithm for the box with a right id
     * @param listBoxThatAreFillingTheInstruction : ArrayList that will be updated, list of all the box that are correct for the instruction
     * @param markForEachBoxAccordingToTheInstruction : Hashmap that will be updated, give for each box, the best mark associated
     * @param hashmapToKnowWhichIsIrrigated : Hashmap that will be updated, give for each box, the hashmap of irrigation associated (cf whichIsIrrigatedUnder)
     * @param id : the id of one of the box correct given the instruction
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
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

    /**
     * Method use to get only the best mark for each box
     *      <=> Only the configuration with the shortest number of turn to irrigate the box is retained
     * @param listOfBoxNOtIrrigated : the list that contain all the listOfBoxNOtIrrigatedUnder available
     *                                Use to generate the mark
     * @return : the index in the list of the best configuration
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
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

    /**
     * Try all the rotation possible in order to know if this box and thus this combination pass the instruction
     * Update the variable whichIsIrrigated and listOfBoxNOtIrrigated with the correct rotation
     * @param box : the box use to generate the adjacent box and thus the combination
     * @param whichIsIrrigated : the list of all the whichIsIrrigatedUnder available
     * @param listOfBoxNOtIrrigated : the list of all the listOfBoxNOtIrrigatedUnder available
     * @return true if at least one rotation is available
     *      <=> This box is available
     */
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

    /**
     * Method use to know if this index of rotation is correct for the instruction
     * Check if the box must not be place and verify all the condition if it is
     * If not launch isTestSpecifiedCondition_v1_3
     * @param box the box use to generate the adjacent box
     * @param i : the index of rotation
     * @param test : the boolean to modify and return, allow to know if the instruction are passed for this rotation and this index
     * @param whichIsIrrigatedUnder : update the hashMap for this combination with if the box is irrigated or not
     * @param listOfBoxNOtIrrigatedUnder : the list of box not irrigated (they will be used to generate the mark)
     * @return true if the index of rotation is correct for the instruction
     */
    private boolean runInstruction_v1_3(HexagoneBoxPlaced box, int i, boolean test, HashMap<Integer, Boolean> whichIsIrrigatedUnder, ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigatedUnder) {
        for (int pos : this.instruction.keySet()) {
            int idOfOneAdjacentBox = HexagoneBox.generateID(box.getAdjacentBox().get(pos + i));
            if (this.instruction.get(pos).isEmpty()) {
                if (this.board.getPlacedBox().containsKey(idOfOneAdjacentBox) ||
                        (this.boxCanBePlaced && !board.getAvailableBox().contains(idOfOneAdjacentBox))) test = false;
            } else {
                test = isTestSpecifiedCondition_v1_3(i, test, whichIsIrrigatedUnder, listOfBoxNOtIrrigatedUnder, pos, idOfOneAdjacentBox);
            }
            if (!test){
                break;
            }
        }
        return test;
    }

    /**
     * Method use to know if this index is correct for the instruction
     * And if it is, check if the box is not irrigated and add it to the list of the box not irrigated in order to generate the mark
     * @param i : the index of rotation use to take the box in the instruction
     * @param test : true if the test is passed <=> this index is correct for the instruction
     * @param whichIsIrrigatedUnder : update the hashMap for this combination with if the box is irrigated or not
     * @param listOfBoxNOtIrrigatedUnder : the list of box not irrigated (they will be used to generate the mark)
     * @param pos : the position of the instruction we are looking for
     * @param idOfOneAdjacentBox : the id of the box at the position pos rotated by i
     * @return true if the test is passed, false if not
     *      <=> This box is correct for the instruction
     */
    private boolean isTestSpecifiedCondition_v1_3(int i, boolean test, HashMap<Integer, Boolean> whichIsIrrigatedUnder, ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigatedUnder, int pos, int idOfOneAdjacentBox) {
        if (this.board.getPlacedBox().containsKey(idOfOneAdjacentBox) &&
                this.board.getPlacedBox().get(idOfOneAdjacentBox).getColor() == this.instruction.get(pos).get()) {
            HexagoneBoxPlaced adjBox = this.board.getPlacedBox().get(idOfOneAdjacentBox);
            if (adjBox.isIrrigate()) {
                whichIsIrrigatedUnder.put(pos + i, true);
            } else {
                whichIsIrrigatedUnder.put(pos + i, false);
                listOfBoxNOtIrrigatedUnder.add(adjBox);
            }
        } else {
            test = false;
        }
        return test;
    }

    /**
     * Method use to generate the mark of a Combination
     *      The mark is the number of turn need to irrigate all the box in the combination
     * @param listOfBoxNotIrrigated : the list of box not irrigated in the combination
     * @return : a number that is the number of turn need to irrigate all the box
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    private int generateMark_v1_3(ArrayList<HexagoneBoxPlaced> listOfBoxNotIrrigated) throws CrestNotRegistered, CloneNotSupportedException {
        return new GenerateOptimizePathForSeveralBoxWithSimulation(listOfBoxNotIrrigated).getNbTour();
    }
}
