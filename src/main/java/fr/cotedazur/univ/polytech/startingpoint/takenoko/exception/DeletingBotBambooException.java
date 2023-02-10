package fr.cotedazur.univ.polytech.startingpoint.takenoko.exception;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;

import java.util.ArrayList;

public class DeletingBotBambooException extends TakenokoException{

    private final ArrayList<Color> colorImpossibleToDelete;
    private String text;

    public DeletingBotBambooException(ArrayList<Color> colorImpossibleToDelete){
        super("Impossible to delete some color from the bot because he already have 0 as value for them");
        this.colorImpossibleToDelete = colorImpossibleToDelete;
        generateErrorMessage();
    }

    private void generateErrorMessage(){
        text = "Impossible to delete these color from the bot because he already have 0 as value for them :";
        for (int i =0; i<colorImpossibleToDelete.size();i++){
            text = text + "\n- " + colorImpossibleToDelete.get(i).toString();
        }
    }

    @Override
    public String getErrorTitle() {
        return text;
    }
}