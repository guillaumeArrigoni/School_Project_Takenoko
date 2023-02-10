package fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.elementOfTheBoard;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.TakenokoException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;

public class BambooNotAvailableException extends TakenokoException {

    private Color color;
    private String text;

    public BambooNotAvailableException(Color color){
        super("Impossible to place bamboo because there is no more bamboo " + color.toString() + " in stock.");
        this.color = color;
        generateErrorMessage();
    }

    private void generateErrorMessage(){
        text = "Impossible to place bamboo because there is no more bamboo " + color.toString() + " in stock.";
    }

    @Override
    public String getErrorTitle() {
        return text;
    }
}
