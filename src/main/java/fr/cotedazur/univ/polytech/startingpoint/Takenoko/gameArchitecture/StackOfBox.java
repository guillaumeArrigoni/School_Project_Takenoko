package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.ListOfDifferentSize;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;

import static java.util.Map.entry;

public class StackOfBox {

    private final Board board;

    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    private ArrayList<HexagoneBox> stackOfBox;

    private HashMap<HexagoneBox,Integer> defaultInstruction;

    /**
     * @param board
     * @param retrieveBoxIdWithParameters
     * @param generationInstruction : Hashmpa with as key the example of HexagoneBox and as value the number to create
     */
    public StackOfBox(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters,
                      HashMap<HexagoneBox,Integer> generationInstruction) {
        setupDefaultInstruction();
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.board = board;
        setupStackOfBox(board, retrieveBoxIdWithParameters, generationInstruction);
    }

    /**
     * @param board
     * @param retrieveBoxIdWithParameters
     * @param listOfNumberToCreate the number for each box to generate
     * @param listBox the list of box to generate
     * @param fromBegining :
     *                     true if the hashmap generated don't have as basement the defaultInstruction
     *                     false if not.
     * @throws ListOfDifferentSize if the 2 list entered have 2 differents size
     */
    public StackOfBox(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters,
                      ArrayList<Integer> listOfNumberToCreate, ArrayList<HexagoneBox> listBox,
                      boolean fromBegining) throws ListOfDifferentSize {
        this(board,retrieveBoxIdWithParameters,new HashMap<>());
        HashMap<HexagoneBox,Integer> instructionForGeneration =
                generateWithArrayListBasement(listOfNumberToCreate, listBox, fromBegining);
        setupStackOfBox(board,retrieveBoxIdWithParameters,instructionForGeneration);
    }

    public StackOfBox(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters) {
        this(board,retrieveBoxIdWithParameters,new HashMap<>());
        setupStackOfBox(board, retrieveBoxIdWithParameters, defaultInstruction);
    }

    /**
     * Method use to add a new element in the stack of HexagoneBox
     * @param box
     */
    public void addNewBox(HexagoneBox box){
        this.stackOfBox.add(box);
    }

    /**
     * Method use to get the first element in the stack of HexagoneBox
     * @return
     */
    public HexagoneBox getFirstBox(){
        HexagoneBox box = stackOfBox.get(0);
        stackOfBox.remove(0);
        return box;
    }

    /**
     * Method use to generate the defaultInstruction
     */
    private void setupDefaultInstruction(){
        defaultInstruction = (HashMap<HexagoneBox, Integer>) Map.ofEntries(
                entry(new HexagoneBox(Color.Lac,Special.Classique,retrieveBoxIdWithParameters,board),1),
                entry(new HexagoneBox(Color.Vert,Special.Classique,retrieveBoxIdWithParameters,board),6),
                entry(new HexagoneBox(Color.Vert,Special.Engrais,retrieveBoxIdWithParameters,board),1),
                entry(new HexagoneBox(Color.Vert,Special.Protéger,retrieveBoxIdWithParameters,board),2),
                entry(new HexagoneBox(Color.Vert,Special.SourceEau,retrieveBoxIdWithParameters,board),2),
                entry(new HexagoneBox(Color.Jaune,Special.Classique,retrieveBoxIdWithParameters,board),6),
                entry(new HexagoneBox(Color.Jaune,Special.Engrais,retrieveBoxIdWithParameters,board),1),
                entry(new HexagoneBox(Color.Jaune,Special.Protéger,retrieveBoxIdWithParameters,board),1),
                entry(new HexagoneBox(Color.Jaune,Special.SourceEau,retrieveBoxIdWithParameters,board),1),
                entry(new HexagoneBox(Color.Rouge,Special.Classique,retrieveBoxIdWithParameters,board),6),
                entry(new HexagoneBox(Color.Rouge,Special.Engrais,retrieveBoxIdWithParameters,board),1),
                entry(new HexagoneBox(Color.Rouge,Special.Protéger,retrieveBoxIdWithParameters,board),1),
                entry(new HexagoneBox(Color.Rouge,Special.SourceEau,retrieveBoxIdWithParameters,board),1)
        );
    }

    /**
     * Method use to generate the stack of HexagoneBox with a Hashmap
     * @param board
     * @param retrieveBoxIdWithParameters
     * @param generationInstruction
     */
    private void setupStackOfBox(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters,
                                 HashMap<HexagoneBox, Integer> generationInstruction) {
        for (HexagoneBox box : generationInstruction.keySet()){
            for (int j = 0; j< generationInstruction.get(box); j++){
                Color color = box.getColor();
                Special special = box.getSpecial();
                stackOfBox.add(new HexagoneBox(color,special, retrieveBoxIdWithParameters, board));
            }
        }
        Collections.shuffle(this.stackOfBox);
    }

    /**
     * Method use to generate execute the different option when the constructor with boolean different option is create
     * @param listOfNumberToCreate
     * @param listBox
     * @param fromBegining
     * @return
     */
    private HashMap<HexagoneBox, Integer> generateWithArrayListBasement(ArrayList<Integer> listOfNumberToCreate,
                                                                        ArrayList<HexagoneBox> listBox, boolean fromBegining) {
        HashMap<HexagoneBox,Integer> instruction = defaultInstruction;
        try {
            checkForThrowing_ListOfDifferentSize_Exception(listBox.size(), listOfNumberToCreate.size());
            if (fromBegining){
                instruction = new HashMap<>();
            }
            instruction = generateWithArrayList(instruction, listOfNumberToCreate, listBox);
        } catch (ListOfDifferentSize e) {
            System.err.println("\n  -> An error has occurred : "
                    + e.getErrorTitle()
                    + "\n"
                    + "In line :\n"
                    + e.getStackTrace()
                    + "\n\n"
                    + "In order to bypass error, the default generation is setting.");
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
     * @param listBox
     * @return
     */
    private HashMap<HexagoneBox,Integer> generateWithArrayList(HashMap<HexagoneBox,Integer> instructionToComplete,
                                                               ArrayList<Integer> listOfNumberToCreate,
                                                               ArrayList<HexagoneBox> listBox){
        HashMap<HexagoneBox,Integer> instructionForGeneration = instructionToComplete;
        for (int i =0;i<listBox.size();i++){
            HexagoneBox key = listBox.get(i);
            int numberToCreate = listOfNumberToCreate.get(i);
            if (instructionForGeneration.containsKey(key)){
                numberToCreate = numberToCreate + instructionForGeneration.get(key);
            }
            instructionForGeneration.put(key,numberToCreate);
        }
        return instructionToComplete;
    }
}
