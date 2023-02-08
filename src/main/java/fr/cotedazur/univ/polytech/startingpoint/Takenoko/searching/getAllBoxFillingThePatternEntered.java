package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation.GenerateOptimizePathForSeveralBoxWithSimulation;

import java.util.*;

public class getAllBoxFillingThePatternEntered {
    ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction;
    HashMap<HexagoneBoxPlaced,Integer> markForEachBoxAccordingToTheInstruction;
    HashMap<HexagoneBoxPlaced,HashMap<Integer,Boolean>> hashmapToKnowWhichIsIrrigated;
    private Board board;
    private RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;


    public getAllBoxFillingThePatternEntered(HashMap<Integer, Optional<Color>> instruction, Board board) throws CrestNotRegistered {
        this.board = board;
        this.retrieveBoxIdWithParameters = board.getRetrieveBoxIdWithParameters();
        getAllBoxFillingTheInstruction(instruction);
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
    private void getAllBoxFillingTheInstruction(HashMap<Integer, Optional<Color>> instruction) throws CrestNotRegistered {
        ArrayList<Integer> listOfAllBoxIdThatFillInstruction = this.retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(new ArrayList<>(Arrays.asList(instruction.get(0).get()))),Optional.empty(),Optional.empty(),Optional.empty());
        ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction = new ArrayList<>();
        HashMap<HexagoneBoxPlaced,Integer> markForEachBoxAccordingToTheInstruction = new HashMap<>();
        HashMap<HexagoneBoxPlaced,HashMap<Integer,Boolean>> hashmapToKnowWhichIsIrrigated = new HashMap<>();
        for (Integer id : listOfAllBoxIdThatFillInstruction){
            comparatorForEachBox(instruction, this.board, listBoxThatAreFillingTheInstruction, markForEachBoxAccordingToTheInstruction, hashmapToKnowWhichIsIrrigated, id);
        }
        this.listBoxThatAreFillingTheInstruction = listBoxThatAreFillingTheInstruction;
        this.markForEachBoxAccordingToTheInstruction = markForEachBoxAccordingToTheInstruction;
        this.hashmapToKnowWhichIsIrrigated = hashmapToKnowWhichIsIrrigated;
    }

    private void comparatorForEachBox(HashMap<Integer, Optional<Color>> instruction, Board board, ArrayList<HexagoneBoxPlaced> listBoxThatAreFillingTheInstruction, HashMap<HexagoneBoxPlaced, Integer> markForEachBoxAccordingToTheInstruction, HashMap<HexagoneBoxPlaced, HashMap<Integer, Boolean>> hashmapToKnowWhichIsIrrigated, Integer id) throws CrestNotRegistered {
        HexagoneBoxPlaced box = board.getPlacedBox().get(id);
        boolean test = true;
        HashMap<Integer,Boolean> whichIsIrrigated = new HashMap<>();
        ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigated = new ArrayList<>();
        test = doesPAssAllInstruction(instruction, board, box, test, whichIsIrrigated, listOfBoxNOtIrrigated);
        if (test){
            listBoxThatAreFillingTheInstruction.add(box);
            hashmapToKnowWhichIsIrrigated.put(box,whichIsIrrigated);
            markForEachBoxAccordingToTheInstruction.put(box,generateMark(listOfBoxNOtIrrigated));
        }
    }

    private boolean doesPAssAllInstruction(HashMap<Integer, Optional<Color>> instruction, Board board, HexagoneBoxPlaced box, boolean test, HashMap<Integer, Boolean> whichIsIrrigated, ArrayList<HexagoneBoxPlaced> listOfBoxNOtIrrigated) {
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

    private int generateMark(ArrayList<HexagoneBoxPlaced> listOfBoxNotIrrigated) throws CrestNotRegistered, CloneNotSupportedException {
        return new GenerateOptimizePathForSeveralBoxWithSimulation(listOfBoxNotIrrigated).getNbTour();
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
    private ArrayList<HexagoneBoxPlaced> getAllBoxFillingTheInstruction_1(ArrayList<Color> colors, int x, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Board board){
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
                    checkIfGoodAndAddToTheListIfItIs(box, listOfAllBoxIdThatFillInstruction, Arrays.asList(2), idOfBoxCorrect);
                    break;
                case 4 :
                    checkIfGoodAndAddToTheListIfItIs(box, listOfAllBoxIdThatFillInstruction, Arrays.asList(3), idOfBoxCorrect);
                    break;
                case 5 :
                    checkIfGoodAndAddToTheListIfItIs(box, listOfAllBoxIdThatFillInstruction, Arrays.asList(1), idOfBoxCorrect);
                    break;
                case 6 :
                    checkIfGoodAndAddToTheListIfItIs(box, listOfAllBoxIdThatFillInstruction, Arrays.asList(1, 2), idOfBoxCorrect);
                    break;
            }
        }
        return idOfBoxCorrect;
    }

    private void checkIfGoodAndAddToTheListIfItIs(HexagoneBoxPlaced box, ArrayList<Integer> y, List<Integer> asList, ArrayList<HexagoneBoxPlaced> idOfBoxCorrect) {
        ArrayList<Integer> g = new ArrayList<>();
        for (int i=1;i<7;i++) {
            int idOfOneAdjacentBox = HexagoneBox.generateID(box.getAdjacentBox().get(i));
            if (y.contains(idOfOneAdjacentBox)) {
                g.add(i);
            }
        }
        if (ParcelleObjectifConditionWithMultipleCheckToDo(g,new ArrayList<>(asList))){
            idOfBoxCorrect.add(box);
        }
    }

    private boolean ParcelleObjectifConditionWithMultipleCheckToDo(ArrayList<Integer> idOfAdjacentBoxCorrect, ArrayList<Integer> listOfAdvancement) {
        int size = listOfAdvancement.size();
        for (int j = 0; j< idOfAdjacentBoxCorrect.size(); j++){
            boolean test = true;
            for (int k=0;k<size;k++){
                int adjIndice = (idOfAdjacentBoxCorrect.get(j)+ listOfAdvancement.get(k))%7;
                if (adjIndice == 0) adjIndice = 1;
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
    private ArrayList<HexagoneBoxPlaced> getAllBoxFillingTheInstruction_2(HashMap<Integer,Color> instruction, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Board board){
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
