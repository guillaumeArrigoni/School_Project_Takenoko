package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;

import java.util.HashMap;

public class ElementOfTheBoardSimulation extends ElementOfTheBoard {

    public ElementOfTheBoardSimulation(ElementOfTheBoard elementOfTheBoard){
        super();
        super.nbOfBambooForEachColorAvailable = (HashMap<Color, Integer>) elementOfTheBoard.nbOfBambooForEachColorAvailable.clone();
        super.stackOfBox = new StackOfBoxSimulation(elementOfTheBoard.stackOfBox);
    }
}
