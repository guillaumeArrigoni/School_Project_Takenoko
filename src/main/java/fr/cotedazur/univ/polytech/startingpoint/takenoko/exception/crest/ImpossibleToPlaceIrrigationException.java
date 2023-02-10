package fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.crest;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.TakenokoException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.crest.Crest;

public class ImpossibleToPlaceIrrigationException extends TakenokoException {

    private Crest crest;
    private String text;

    public ImpossibleToPlaceIrrigationException(Crest crest){
        super("Impossible to place an irrigation because there is no box placed in this venue and therefore no crest available.");
        this.crest = crest;
        generateErrorMessage();
    }

    private void generateErrorMessage(){
        text = "Impossible to place an irrigation because there is no box placed in this venue and therefore no crest available.\n" +
                "Trying to place the irrigation in venue : "
                + Integer.toString(crest.getCoordinates()[0]) + " x, "
                + Integer.toString(crest.getCoordinates()[1]) + " y.";
    }

    @Override
    public String getErrorTitle() {
        return text;
    }
}