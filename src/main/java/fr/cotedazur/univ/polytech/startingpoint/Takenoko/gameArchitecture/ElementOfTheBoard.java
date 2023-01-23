package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.ListOfDifferentSize;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;

import static java.util.Map.entry;

public class ElementOfTheBoard {

    private HashMap<Color,Integer> nbOfBambooForEachColorAvailable;
    private HashMap<Color,Integer> defaultInstruction;
    private final StackOfBox stackOfBox;
    private final Board board;
    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    public ElementOfTheBoard(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, HashMap<Color,Integer> instruction){
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.board = board;
        this.stackOfBox = new StackOfBox(board,retrieveBoxIdWithParameters);
        setup_defaultInstruction();
        setup_NbOfBambooForEachColorAvailable(instruction);
    }

    public ElementOfTheBoard(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters){
        this(board,retrieveBoxIdWithParameters,new HashMap<>());
        setup_NbOfBambooForEachColorAvailable(defaultInstruction);
    }

    public ElementOfTheBoard(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters,
                             ArrayList<Integer> listOfBambooAvailable, ArrayList<Color> listOfColor,
                             boolean fromBegining){
        this(board,retrieveBoxIdWithParameters,new HashMap<>());
        setup_NbOfBambooForEachColorAvailable(generateWithArrayListBasement(listOfBambooAvailable,listOfColor,fromBegining));
    }

    /**
     * Method use to generate execute the different option when the constructor with boolean different option is create
     * @param listOfNumberToCreate
     * @param listColor
     * @param fromBegining
     * @return
     */
    private HashMap<Color, Integer> generateWithArrayListBasement(ArrayList<Integer> listOfNumberToCreate,
                                                                        ArrayList<Color> listColor, boolean fromBegining) {
        HashMap<Color,Integer> instruction = defaultInstruction;
        try {
            checkForThrowing_ListOfDifferentSize_Exception(listColor.size(), listOfNumberToCreate.size());
            if (fromBegining){
                instruction = new HashMap<>();
            }
            instruction = generateWithArrayList(instruction, listOfNumberToCreate, listColor);
        } catch (ListOfDifferentSize e) {
            System.err.println("\n  -> An error has occurred : "
                    + e.getErrorTitle()
                    + "\n"
                    + "In line :\n"
                    + e.getStackTrace()
                    + "\n\n"
                    + "In order to bypass error, the default generation is set.");
        }
        return instruction;
    }

    /**
     * Method use to check if the 2 list are from the same size, take 2 Integer as parameters
     * @param size1
     * @param size2
     * @throws ListOfDifferentSize
     */
    private void checkForThrowing_ListOfDifferentSize_Exception(int size1,int size2) throws ListOfDifferentSize {
        if (size1 != size2) {
            throw new ListOfDifferentSize(size1,size2);
        }
    }

    /**
     * Method use to add the new instruction to those already implement in defaultInstruction
     * @param instructionToComplete the hashmap of instruction that have to be completed
     * @param listOfNumberToCreate
     * @param listColor
     * @return
     */
    private HashMap<Color,Integer> generateWithArrayList(HashMap<Color,Integer> instructionToComplete,
                                                               ArrayList<Integer> listOfNumberToCreate,
                                                               ArrayList<Color> listColor){
        HashMap<Color,Integer> instructionForGeneration = instructionToComplete;
        for (int i =0;i<listColor.size();i++){
            Color key = listColor.get(i);
            int numberToCreate = listOfNumberToCreate.get(i);
            if (instructionForGeneration.containsKey(key)){
                numberToCreate = numberToCreate + instructionForGeneration.get(key);
            }
            instructionForGeneration.put(key,numberToCreate);
        }
        return instructionToComplete;
    }






    public StackOfBox getStackOfBox(){
        return stackOfBox;
    }

    public HashMap<Color,Integer> getNbOfBambooForEachColorAvailable(){
        return nbOfBambooForEachColorAvailable;
    }

    private void setup_defaultInstruction(){
        this.defaultInstruction = (HashMap<Color, Integer>) Map.ofEntries(
                entry(Color.Vert,36),
                entry(Color.Jaune,30),
                entry(Color.Rouge,24)
        );
    }

    private void setup_NbOfBambooForEachColorAvailable(HashMap<Color,Integer> instruction){

    }
}
