package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation.GenerateOptimizePathForSeveralBoxWithSimulationRank3;

import java.util.*;

public class getAllBoxFillingThePatternEntered {
    ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction;
    HashMap<HexagoneBoxPlaced,Integer> markForEachBoxAccordingToTheInstruction;
    HashMap<HexagoneBoxPlaced,HashMap<Integer,Boolean>> hashmapToKnowWhichIsIrrigated;
    private Board board;
    private RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    private boolean boxCanBePlaced;

    private HashMap<Integer, Optional<Color>> instruction;


    public getAllBoxFillingThePatternEntered(HashMap<Integer, Optional<Color>> instruction, Board board) throws CrestNotRegistered {
        this.board = board;
        this.retrieveBoxIdWithParameters = board.getRetrieveBoxIdWithParameters();
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
     * @param instruction a Hashmap that have as key the position of the box and as value the color that those box must have.
     *                    The Hashmap MUST have the key 0 because it is the central box.
     *                    The other key are the position of the adjacent box around this central box :
     *                              6       1
     *                          5       0       2
     *                              4       3
     *                    If the Optional<Color> is empty that mean no Box should have been place in this venue
     * @return the ArrayList with all the central box that are filling the condition
     */
    private void getAllBoxFillingTheInstruction_v1_3() throws CrestNotRegistered {
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

    private void comparatorForEachBox_v1_3(ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction, HashMap<HexagoneBoxPlaced, Integer> markForEachBoxAccordingToTheInstruction, HashMap<HexagoneBoxPlaced, HashMap<Integer, Boolean>> hashmapToKnowWhichIsIrrigated, Integer id) throws CrestNotRegistered {
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

    private int getBestConfigurationForABox_v1_3(ArrayList<ArrayList<HexagoneBoxPlaced>> listOfBoxNOtIrrigated) throws CrestNotRegistered {
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

    private int generateMark_v1_3(ArrayList<HexagoneBoxPlaced> listOfBoxNotIrrigated) throws CrestNotRegistered {
        return new GenerateOptimizePathForSeveralBoxWithSimulationRank3(listOfBoxNotIrrigated).getNbTour();
    }




    /**
     * Method v1.2
     * Method that give all the box that are filling the instruction
     * @param instruction a Hashmap that have as key the position of the box and as value the color that those box must have.
     *                    The Hashmap MUST have the key 0 because it is the central box.
     *                    The other key are the position of the adjacent box around this central box :
     *                              6       1
     *                          5       0       2
     *                              4       3
     *                    If the Optional<Color> is empty that mean no Box should have been place in this venue
     * @return the ArrayList with all the central box that are filling the condition
     */
    private void getAllBoxFillingTheInstruction_v1_2(HashMap<Integer, Optional<Color>> instruction) throws CrestNotRegistered {
        ArrayList<Integer> listOfAllBoxIdThatFillInstruction = this.retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(new ArrayList<>(Arrays.asList(instruction.get(0).get()))),Optional.empty(),Optional.empty(),Optional.empty());
        ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction = new ArrayList<>();
        HashMap<HexagoneBoxPlaced,Integer> markForEachBoxAccordingToTheInstruction = new HashMap<>();
        HashMap<HexagoneBoxPlaced,HashMap<Integer,Boolean>> hashmapToKnowWhichIsIrrigated = new HashMap<>();
        for (Integer id : listOfAllBoxIdThatFillInstruction){
            comparatorForEachBox_v1_3(instruction, this.board, listBoxThatAreFillingTheInstruction, markForEachBoxAccordingToTheInstruction, hashmapToKnowWhichIsIrrigated, id);
        }
        this.listBoxThatAreFillingTheInstruction = listBoxThatAreFillingTheInstruction;
        this.markForEachBoxAccordingToTheInstruction = markForEachBoxAccordingToTheInstruction;
        this.hashmapToKnowWhichIsIrrigated = hashmapToKnowWhichIsIrrigated;
    }

    private void comparatorForEachBox_v1_2(HashMap<Integer, Optional<Color>> instruction, Board board, ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction, HashMap<HexagoneBoxPlaced, Integer> markForEachBoxAccordingToTheInstruction, HashMap<HexagoneBoxPlaced, HashMap<Integer, Boolean>> hashmapToKnowWhichIsIrrigated, Integer id) throws CrestNotRegistered {
        HexagoneBoxPlaced box = board.getPlacedBox().get(id);
        boolean test = true;
        HashMap<Integer,Boolean> whichIsIrrigated = new HashMap<>();
        ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigated = new ArrayList<>();
        test = doesPAssAllInstruction_v1_2(instruction, board, box, test, whichIsIrrigated, listOfBoxNOtIrrigated);
        if (test){
            listBoxThatAreFillingTheInstruction.add(box);
            hashmapToKnowWhichIsIrrigated.put(box,whichIsIrrigated);
            markForEachBoxAccordingToTheInstruction.put(box, generateMark_v1_2(listOfBoxNOtIrrigated));
        }
    }


    private boolean doesPAssAllInstruction_v1_2(HashMap<Integer, Optional<Color>> instruction, Board board, HexagoneBoxPlaced box, boolean test, HashMap<Integer, Boolean> whichIsIrrigated, ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigated) {
        for (Integer position : instruction.keySet()){
            int idOfOneAdjacentBox = HexagoneBox.generateID(box.getAdjacentBox().get(position));
            if (board.getPlacedBox().containsKey(idOfOneAdjacentBox)){
                if (instruction.get(position).isEmpty()){
                    test = false;
                    break;
                } else {
                    HexagoneBoxPlaced adjBox = board.getPlacedBox().get(idOfOneAdjacentBox);
                    if (!adjBox.getColor().equals(instruction.get(position).get())){
                        test = false;
                        break;
                    } else if (adjBox.isIrrigate()){
                        whichIsIrrigated.put(position,true);
                    } else {
                        listOfBoxNOtIrrigated.add(adjBox);
                    }
                }
            }
        }
        return test;
    }

    private int generateMark_v1_2(ArrayList<HexagoneBoxPlaced> listOfBoxNotIrrigated) throws CrestNotRegistered {
        return new GenerateOptimizePathForSeveralBoxWithSimulationRank3(listOfBoxNotIrrigated).getNbTour();
    }




    /**
     * Method v1.0
     * Method use to get all the box'id that are filling the instruction.
     * @param colors list of all the color that can take the boxs
     * @param x following those instruction :
     *          1 : just a central box with any of the color
     *          2 : a central box and any of adjacent box to it with any of the colors
     *          3 : describe a courbe (central box with 2 adjacent box) with any of the colors
     *          4 : describe a line (central box with 2 adjacent box) with any of the colors
     *          5 : describe a triangle (central box with 2 adjacent box) with any of the colors
     *          6 : describe a losange (central box with 3 adjacent box) with any of the colors
     * @param retrieveBoxIdWithParameters
     * @param board
     * @return all the box with adjacent box following the pattern entered and that have any of the color entered
     */
    private ArrayList<HexagoneBoxPlaced> getAllBoxFillingTheInstruction_v1_1(ArrayList<Color> colors, int x, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Board board){
        ArrayList<Integer> listOfAllBoxIdThatFillInstruction = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(colors),Optional.empty(),Optional.empty(),Optional.empty());
        ArrayList<HexagoneBoxPlaced> idOfBoxCorrect = new ArrayList<>();
        for (Integer id : listOfAllBoxIdThatFillInstruction){
            HexagoneBoxPlaced box = board.getPlacedBox().get(id);
            switch(x){
                case 1 :
                    idOfBoxCorrect.add(box);
                    break;
                case 2:
                    ArrayList<Integer> t = new ArrayList<>();
                    for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                        if (listOfAllBoxIdThatFillInstruction.contains(HexagoneBox.generateID(box.getAdjacentBox().get(j)))){
                            idOfBoxCorrect.add(box);
                        }
                        break;
                    }
                    break;
                case 3 :
                    checkIfGoodAndAddToTheListIfItIs_v1_1(box, listOfAllBoxIdThatFillInstruction, Arrays.asList(2), idOfBoxCorrect);
                    break;
                case 4 :
                    checkIfGoodAndAddToTheListIfItIs_v1_1(box, listOfAllBoxIdThatFillInstruction, Arrays.asList(3), idOfBoxCorrect);
                    break;
                case 5 :
                    checkIfGoodAndAddToTheListIfItIs_v1_1(box, listOfAllBoxIdThatFillInstruction, Arrays.asList(1), idOfBoxCorrect);
                    break;
                case 6 :
                    checkIfGoodAndAddToTheListIfItIs_v1_1(box, listOfAllBoxIdThatFillInstruction, Arrays.asList(1, 2), idOfBoxCorrect);
                    break;
            }
        }
        return idOfBoxCorrect;
    }

    private void checkIfGoodAndAddToTheListIfItIs_v1_1(HexagoneBoxPlaced box, ArrayList<Integer> y, List<Integer> asList, ArrayList<HexagoneBoxPlaced> idOfBoxCorrect) {
        ArrayList<Integer> g = new ArrayList<>();
        for (int i=1;i<7;i++) {
            int idOfOneAdjacentBox = HexagoneBox.generateID(box.getAdjacentBox().get(i));
            if (y.contains(idOfOneAdjacentBox)) {
                g.add(i);
            }
        }
        if (ParcelleObjectifConditionWithMultipleCheckToDo_v1_1(g,new ArrayList<>(asList))){
            idOfBoxCorrect.add(box);
        }
    }

    private boolean ParcelleObjectifConditionWithMultipleCheckToDo_v1_1(ArrayList<Integer> idOfAdjacentBoxCorrect, ArrayList<Integer> listOfAdvancement) {
        int size = listOfAdvancement.size();
        for (int j = 0; j< idOfAdjacentBoxCorrect.size(); j++){
            boolean test = true;
            for (int k=0;k<size;k++){
                int adjIndice = idOfAdjacentBoxCorrect.get(j)+ listOfAdvancement.get(k);
                if (adjIndice > 6) adjIndice = adjIndice - 6;
                if (!idOfAdjacentBoxCorrect.contains(adjIndice)){
                    test = false;
                    break;
                }
            }
            if (test){
                return true;
            }
        }
        return false;
    }

    /**
     * Method v1.1
     * Method that give all the box that are filling the instruction
     * @param instruction a Hashmap that have as key the position of the box and as value the color that those box must have.
     *                    The Hashmap MUST have the key 0 because it is the central box.
     *                    The other key are the position of the adjacent box around this central box :
     *                              6       1
     *                          5       0       2
     *                              4       3
     * @param retrieveBoxIdWithParameters
     * @param board
     * @return the ArrayList with all the central box that are filling the condition
     */
    private ArrayList<HexagoneBoxPlaced> getAllBoxFillingTheInstruction_v1_0(HashMap<Integer,Color> instruction, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Board board){
        ArrayList<Integer> listOfAllBoxIdThatFillInstruction = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(new ArrayList<>(Arrays.asList(instruction.get(0)))),Optional.empty(),Optional.empty(),Optional.empty());
        ArrayList<HexagoneBoxPlaced> lisOfBoxCorrect = new ArrayList<>();
        for (Integer id : listOfAllBoxIdThatFillInstruction){
            HexagoneBoxPlaced box = board.getPlacedBox().get(id);
            boolean test = true;
            for (Integer position : instruction.keySet()){
                ArrayList<Integer> g = new ArrayList<>();
                int idOfOneAdjacentBox = HexagoneBox.generateID(box.getAdjacentBox().get(position));
                if (board.getPlacedBox().containsKey(idOfOneAdjacentBox)){
                    HexagoneBoxPlaced adjBox = board.getPlacedBox().get(idOfOneAdjacentBox);
                    if (!adjBox.getColor().equals(instruction.get(position))){
                        test = false;
                        break;
                    }
                }
            }
            if (test){
                lisOfBoxCorrect.add(box);
            }
        }
        return lisOfBoxCorrect;
    }
}
