package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.ListOfDifferentSize;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;

import static java.util.Map.entry;

public class StackOfBox {

    private ArrayList<HexagoneBox> stackOfBox;

    /**
     * @param generationInstruction : Hashmpa with as key the example of HexagoneBox and as value the number to create
     */
    public StackOfBox(HashMap<HexagoneBox,Integer> generationInstruction) {
        setupStackOfBox(generationInstruction);
    }

    /**
     * Method use to add a new element in the stack of HexagoneBox
     * @param box
     */
    public void addNewBox(HexagoneBoxPlaced box){
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
     * Method use to generate the stack of HexagoneBox with a Hashmap
     * @param generationInstruction
     */
    private void setupStackOfBox(HashMap<HexagoneBox, Integer> generationInstruction) {
        for (HexagoneBox box : generationInstruction.keySet()){
            for (int j = 0; j< generationInstruction.get(box); j++){
                Color color = box.getColor();
                Special special = box.getSpecial();
                stackOfBox.add(new HexagoneBox(color,special));
            }
        }
        Collections.shuffle(this.stackOfBox);
    }
}
