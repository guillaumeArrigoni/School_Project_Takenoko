package fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;

import java.util.HashMap;

public class ElementOfTheBoardSimulation extends ElementOfTheBoard {

    public ElementOfTheBoardSimulation(ElementOfTheBoard elementOfTheBoard){
        super(elementOfTheBoard.loggerSevere);
        super.nbOfBambooForEachColorAvailable = (HashMap<Color, Integer>) elementOfTheBoard.nbOfBambooForEachColorAvailable.clone();
        super.stackOfBox = new StackOfBoxSimulation(elementOfTheBoard.stackOfBox);
    }
}
