package fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.elementoftheboard.BambooNotAvailableException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.elementoftheboard.BambooOfColorNotExistingException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.TakenokoException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.ListOfDifferentSize;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;

import java.util.*;

public class ElementOfTheBoard {

    protected HashMap<Color,Integer> nbOfBambooForEachColorAvailable = new HashMap<>();
    protected HashMap<Special,Integer> nbJetonSpecial;
    protected int nbIrrigationAvailable;
    protected StackOfBox stackOfBox;
    protected LoggerSevere loggerSevere;
    protected final HashMap<Color,Integer> defaultInstructionBamboo = new HashMap<Color,Integer>() {{
        put(Color.VERT, 36);
        put(Color.JAUNE, 30);
        put(Color.ROUGE, 24);
    }};
    protected final HashMap<HexagoneBox,Integer> defaultInstructionBox = new HashMap<HexagoneBox,Integer>() {{
        put(new HexagoneBox(Color.VERT,Special.CLASSIQUE),6);
        put(new HexagoneBox(Color.VERT,Special.ENGRAIS),1);
        put(new HexagoneBox(Color.VERT,Special.PROTEGER),2);
        put(new HexagoneBox(Color.VERT,Special.SOURCE_EAU),2);
        put(new HexagoneBox(Color.JAUNE,Special.CLASSIQUE),6);
        put(new HexagoneBox(Color.JAUNE,Special.ENGRAIS),1);
        put(new HexagoneBox(Color.JAUNE,Special.PROTEGER),1);
        put(new HexagoneBox(Color.JAUNE,Special.SOURCE_EAU),1);
        put(new HexagoneBox(Color.ROUGE,Special.CLASSIQUE),6);
        put(new HexagoneBox(Color.ROUGE,Special.ENGRAIS),1);
        put(new HexagoneBox(Color.ROUGE,Special.PROTEGER),1);
        put(new HexagoneBox(Color.ROUGE,Special.SOURCE_EAU),1);
    }};

    protected final HashMap<Special,Integer> defaultInstructionSpecial = new HashMap<Special,Integer>() {{
        put(Special.ENGRAIS, 3);
        put(Special.SOURCE_EAU, 3);
        put(Special.PROTEGER, 3);
    }};

    protected final int defaultInstructionIrrigation = 20;


    public ElementOfTheBoard(HashMap<Color,Integer> instructionBamboo, HashMap<HexagoneBox,Integer> instructionBox,LoggerSevere loggerSevere){
        this.loggerSevere = loggerSevere;
        this.stackOfBox = new StackOfBox(instructionBox);
        this.nbOfBambooForEachColorAvailable = instructionBamboo;
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(LoggerSevere loggerSevere){
        this.loggerSevere = loggerSevere;
        this.stackOfBox = new StackOfBox(defaultInstructionBox);
        this.nbOfBambooForEachColorAvailable = defaultInstructionBamboo;
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(ArrayList<Integer> listOfBambooAvailable, ArrayList<Color> listOfColor, boolean fromBeginningBamboo,
                             ArrayList<Integer> listOfBoxAvailable, ArrayList<HexagoneBox> listOfBox, boolean fromBeginningBox,
                                LoggerSevere loggerSevere){
        this.loggerSevere = loggerSevere;
        this.stackOfBox = new StackOfBox(generateWithArrayListBasementBox(listOfBoxAvailable,listOfBox,fromBeginningBox));
        this.nbOfBambooForEachColorAvailable = generateWithArrayListBasementBamboo(listOfBambooAvailable,listOfColor,fromBeginningBamboo);
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(ArrayList<Integer> listOfBambooAvailable, ArrayList<Color> listOfColor, boolean fromBeginningBamboo,LoggerSevere loggerSevere){
        this.loggerSevere = loggerSevere;
        this.stackOfBox = new StackOfBox(defaultInstructionBox);
        this.nbOfBambooForEachColorAvailable = generateWithArrayListBasementBamboo(listOfBambooAvailable,listOfColor,fromBeginningBamboo);
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(boolean fromBeginningBox,ArrayList<Integer> listOfBoxAvailable, ArrayList<HexagoneBox> listOfBox,LoggerSevere loggerSevere){
        this.loggerSevere = loggerSevere;
        this.stackOfBox = new StackOfBox(generateWithArrayListBasementBox(listOfBoxAvailable,listOfBox,fromBeginningBox));
        this.nbOfBambooForEachColorAvailable = defaultInstructionBamboo;
        this.nbIrrigationAvailable = this.defaultInstructionIrrigation;
        this.nbJetonSpecial = this.defaultInstructionSpecial;
    }

    public ElementOfTheBoard(ArrayList<Integer> listOfBambooAvailable, ArrayList<Color> listOfColor,
                             ArrayList<Integer> listOfBoxAvailable, ArrayList<HexagoneBox> listOfBox,LoggerSevere loggerSevere){
        this(listOfBambooAvailable,listOfColor,false,listOfBoxAvailable,listOfBox,false,loggerSevere);
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
     * @param fromBeginning : true if the default instruction are not used
     * @return
     */
    private HashMap<HexagoneBox, Integer> generateWithArrayListBasementBox(ArrayList<Integer> listOfNumberToCreate,
                                                                           ArrayList<HexagoneBox> listBox, boolean fromBeginning) {
        HashMap<HexagoneBox,Integer> instruction = defaultInstructionBox;
        instruction = getColorIntegerHashMap(listOfNumberToCreate, listBox, fromBeginning, instruction);
        return instruction;
    }

    /**
     * Method use to launch creation of bamboo instruction with list of Color
     * @param listOfNumberToCreate
     * @param listColor
     * @param fromBeginning : true if the default instruction are not used
     * @return
     */
    private HashMap<Color, Integer> generateWithArrayListBasementBamboo(ArrayList<Integer> listOfNumberToCreate,
                                                                        ArrayList<Color> listColor, boolean fromBeginning) {
        HashMap<Color,Integer> instruction = defaultInstructionBamboo;
        instruction = getColorIntegerHashMap(listOfNumberToCreate, listColor, fromBeginning, instruction);
        return instruction;
    }

    /**
     * Method use to handle exception if the list don't have the same size, and launch the generation of the instruction
     * @param listOfNumberToCreate
     * @param listKey
     * @param fromBeginning : true if the default instruction are not used
     * @param instruction
     * @return
     * @param <T>
     */
    private <T> HashMap<T, Integer> getColorIntegerHashMap(ArrayList<Integer> listOfNumberToCreate, ArrayList<T> listKey, boolean fromBeginning, HashMap<T, Integer> instruction) {
        try {
            checkForThrowingListOfDifferentSizeException(listKey.size(), listOfNumberToCreate.size());
            if (fromBeginning){
                instruction = new HashMap<>();
            }
            instruction = generateWithArrayList(instruction, listOfNumberToCreate, listKey);
        } catch (ListOfDifferentSize e) {
            loggerSevere.addLog("\n  -> An error has occurred : "
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
    protected void checkForThrowingListOfDifferentSizeException(int size1, int size2) throws ListOfDifferentSize {
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
