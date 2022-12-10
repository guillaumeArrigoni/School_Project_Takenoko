package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.TypeOfStackBox;

import java.util.HashMap;

public class ElementOfTheGame {


    /**
     * StackOfAvailableBox is a Hashmap that contain in key all the box that can be picked and placed.
     * Associate to each key the number of same box available
     * Type :
     *      - TypeOfStackBox : the type of the box category
     *      - Integer : the number of box from this type in the stack that can be picked
     */
    private HashMap<TypeOfStackBox,Integer> StackOfAvailableBox;

    public ElementOfTheGame(){
        createStackOfAvailableBox();
    }

    /**
     * Method use to generate the StackOfAvailableBox that can be picked and placed
     */
    private void createStackOfAvailableBox(){
        StackOfAvailableBox.put(TypeOfStackBox.JauneClassic,5);
        StackOfAvailableBox.put(TypeOfStackBox.RougeClassic,5);
        StackOfAvailableBox.put(TypeOfStackBox.VertClassic,5);
    }

    /**
     * Method use to update the Hashmap StackOfAvailableBox when someone place a box on a board
     * @param typeOfBoxPlaced : the type of the box that is picked and placed.
     *                        Type : TypeOfStackBox
     */
    public void newBoxPlaced(TypeOfStackBox typeOfBoxPlaced){
        StackOfAvailableBox.put(typeOfBoxPlaced,StackOfAvailableBox.get(typeOfBoxPlaced)-1);
        if (StackOfAvailableBox.get(typeOfBoxPlaced)<=0){
            StackOfAvailableBox.remove(typeOfBoxPlaced);
        }
    }
}
