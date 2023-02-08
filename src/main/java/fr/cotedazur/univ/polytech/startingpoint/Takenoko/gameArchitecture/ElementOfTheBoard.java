package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.elementOfTheBoard.BambooNotAvailableException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.elementOfTheBoard.BambooOfColorNotExistingException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.TakenokoException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.ListOfDifferentSize;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;

import java.util.*;

public class ElementOfTheBoard {

    protected HashMap<Color,Integer> nbOfBambooForEachColorAvailable = new HashMap<>();
    protected HashMap<Special,Integer> nbJetonSpecial;
    protected int nbIrrigationAvailable;
    protected StackOfBox stackOfBox;
    protected final HashMap<Color,Integer> defaultInstructionBamboo = new HashMap<Color,Integer>() {{
        put(Color.Vert, 36);
        put(Color.Jaune, 30);
        put(Color.Rouge, 24);
    }};
    protected final HashMap<HexagoneBox,Integer> defaultInstructionBox = new HashMap<HexagoneBox,Integer>() {{
        put(new HexagoneBox(Color.Vert,Special.Classique),6);
        put(new HexagoneBox(Color.Vert,Special.Engrais),1);
        put(new HexagoneBox(Color.Vert,Special.Protéger),2);
        put(new HexagoneBox(Color.Vert,Special.SourceEau),2);
        put(new HexagoneBox(Color.Jaune,Special.Classique),6);
        put(new HexagoneBox(Color.Jaune,Special.Engrais),1);
        put(new HexagoneBox(Color.Jaune,Special.Protéger),1);
        put(new HexagoneBox(Color.Jaune,Special.SourceEau),1);
        put(new HexagoneBox(Color.Rouge,Special.Classique),6);
        put(new HexagoneBox(Color.Rouge,Special.Engrais),1);
        put(new HexagoneBox(Color.Rouge,Special.Protéger),1);
        put(new HexagoneBox(Color.Rouge,Special.SourceEau),1);
    }};

    protected final HashMap<Special,Integer> defaultInstructionSpecial = new HashMap<Special,Integer>() {{
        put(Special.Engrais, 3);
        put(Special.SourceEau, 3);
        put(Special.Protéger, 3);
    }};

    protected final int defaultInstructionIrrigation = 20;


    public ElementOfTheBoard(HashMap<Color,Integer> instructionBamboo, HashMap<HexagoneBox,Integer> instructionBox){
        this.stackOfBox = new StackOfBox(instructionBox);
        this.nbOfBambooForEachColorAvailable = instructionBamboo;
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(){
        this.stackOfBox = new StackOfBox(defaultInstructionBox);
        this.nbOfBambooForEachColorAvailable = defaultInstructionBamboo;
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(ArrayList<Integer> listOfBambooAvailable, ArrayList<Color> listOfColor, boolean fromBeginingBamboo,
                             ArrayList<Integer> listOfBoxAvailable, ArrayList<HexagoneBox> listOfBox, boolean fromBeginingBox){
        this.stackOfBox = new StackOfBox(generateWithArrayListBasementBox(listOfBoxAvailable,listOfBox,fromBeginingBox));
        this.nbOfBambooForEachColorAvailable = generateWithArrayListBasementBamboo(listOfBambooAvailable,listOfColor,fromBeginingBamboo);
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(ArrayList<Integer> listOfBambooAvailable, ArrayList<Color> listOfColor, boolean fromBeginingBamboo){
        this.stackOfBox = new StackOfBox(defaultInstructionBox);
        this.nbOfBambooForEachColorAvailable = generateWithArrayListBasementBamboo(listOfBambooAvailable,listOfColor,fromBeginingBamboo);
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(boolean fromBeginingBox,ArrayList<Integer> listOfBoxAvailable, ArrayList<HexagoneBox> listOfBox){
        this.stackOfBox = new StackOfBox(generateWithArrayListBasementBox(listOfBoxAvailable,listOfBox,fromBeginingBox));
        this.nbOfBambooForEachColorAvailable = defaultInstructionBamboo;
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(ArrayList<Integer> listOfBambooAvailable, ArrayList<Color> listOfColor,
                             ArrayList<Integer> listOfBoxAvailable, ArrayList<HexagoneBox> listOfBox){
        this(listOfBambooAvailable,listOfColor,false,listOfBoxAvailable,listOfBox,false);
    }

    public boolean pickSpecial(Special special){
        if (this.nbJetonSpecial.containsKey(special)){
            int valueSpecial = this.nbJetonSpecial.get(special);
            if (valueSpecial==0){
                return false;
            } else {
                valueSpecial = valueSpecial-1;
                this.nbJetonSpecial.put(special,valueSpecial);
                return true;
            }
        }
        return false;
    }

    public boolean pickIrrigation(){
        if (this.nbIrrigationAvailable==0){
            return false;
        }
        this.nbIrrigationAvailable = this.nbIrrigationAvailable-1;
        return true;
    }

    public HashMap<Special, Integer> getNbJetonSpecial() {
        return nbJetonSpecial;
    }

    public int getNbIrrigationAvailable() {
        return nbIrrigationAvailable;
    }

    public StackOfBox getStackOfBox(){
        return stackOfBox;
    }

    public HashMap<Color,Integer> getNbOfBambooForEachColorAvailable(){
        return nbOfBambooForEachColorAvailable;
    }

    public void placeBamboo(Color color) throws TakenokoException {
        if (!this.nbOfBambooForEachColorAvailable.containsKey(color)){
            throw new BambooOfColorNotExistingException(color);
        } else if (this.nbOfBambooForEachColorAvailable.get(color)==0){
            throw new BambooNotAvailableException(color);
        } else {
            this.nbOfBambooForEachColorAvailable.put(color,this.nbOfBambooForEachColorAvailable.get(color)-1);
        }
    }

    public void giveBackBamboo(Color color) throws TakenokoException {
        if (!this.nbOfBambooForEachColorAvailable.containsKey(color)) {
            throw new BambooOfColorNotExistingException(color);
        } else {
            this.nbOfBambooForEachColorAvailable.put(color,this.nbOfBambooForEachColorAvailable.get(color)+1);
        }
    }


    /**
     * Method use to launch creation of box instruction with list of Box
     * @param listOfNumberToCreate
     * @param listBox
     * @param fromBegining : true if the default instruction are not used
     * @return
     */
    private HashMap<HexagoneBox, Integer> generateWithArrayListBasementBox(ArrayList<Integer> listOfNumberToCreate,
                                                                           ArrayList<HexagoneBox> listBox, boolean fromBegining) {
        HashMap<HexagoneBox,Integer> instruction = defaultInstructionBox;
        instruction = getColorIntegerHashMap(listOfNumberToCreate, listBox, fromBegining, instruction);
        return instruction;
    }

    /**
     * Method use to launch creation of bamboo instruction with list of Color
     * @param listOfNumberToCreate
     * @param listColor
     * @param fromBegining : true if the default instruction are not used
     * @return
     */
    private HashMap<Color, Integer> generateWithArrayListBasementBamboo(ArrayList<Integer> listOfNumberToCreate,
                                                                        ArrayList<Color> listColor, boolean fromBegining) {
        HashMap<Color,Integer> instruction = defaultInstructionBamboo;
        instruction = getColorIntegerHashMap(listOfNumberToCreate, listColor, fromBegining, instruction);
        return instruction;
    }

    /**
     * Method use to handle exception if the list don't have the same size, and launch the generation of the instruction
     * @param listOfNumberToCreate
     * @param listKey
     * @param fromBegining : true if the default instruction are not used
     * @param instruction
     * @return
     * @param <T>
     */
    private <T> HashMap<T, Integer> getColorIntegerHashMap(ArrayList<Integer> listOfNumberToCreate, ArrayList<T> listKey, boolean fromBegining, HashMap<T, Integer> instruction) {
        try {
            checkForThrowing_ListOfDifferentSize_Exception(listKey.size(), listOfNumberToCreate.size());
            if (fromBegining){
                instruction = new HashMap<>();
            }
            instruction = generateWithArrayList(instruction, listOfNumberToCreate, listKey);
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
    protected void checkForThrowing_ListOfDifferentSize_Exception(int size1,int size2) throws ListOfDifferentSize {
        if (size1 != size2) {
            throw new ListOfDifferentSize(size1,size2);
        }
    }

    /**
     * Method use to add the new instruction to those already implement in defaultInstruction
     * @param instructionToComplete the hashmap of instruction that have to be completed
     * @param listOfNumberToCreate
     * @param listKey
     * @return
     */
    private <T> HashMap<T,Integer> generateWithArrayList(HashMap<T,Integer> instructionToComplete,
                                                         ArrayList<Integer> listOfNumberToCreate,
                                                         ArrayList<T> listKey){
        HashMap<T,Integer> instructionForGeneration = instructionToComplete;
        for (int i =0;i<listKey.size();i++){
            T key = listKey.get(i);
            int numberToCreate = listOfNumberToCreate.get(i);
            if (instructionForGeneration.containsKey(key)){
                numberToCreate = numberToCreate + instructionForGeneration.get(key);
            }
            instructionForGeneration.put(key,numberToCreate);
        }
        return instructionToComplete;
    }
}
