package fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Color;

import java.util.HashMap;

public class ElementOfTheBoardSimulation extends ElementOfTheBoard {

    public ElementOfTheBoardSimulation(ElementOfTheBoard elementOfTheBoard){
        super(elementOfTheBoard.loggerSevere);
        super.nbOfBambooForEachColorAvailable = (HashMap<Color, Integer>) elementOfTheBoard.nbOfBambooForEachColorAvailable.clone();
        super.stackOfBox = new StackOfBoxSimulation(elementOfTheBoard.stackOfBox);
    }
}
