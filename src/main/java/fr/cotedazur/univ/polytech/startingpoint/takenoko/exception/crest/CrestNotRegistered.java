package fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.crest;


import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.TakenokoException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.crest.Crest;

public class CrestNotRegistered extends TakenokoException {

    private final Crest crest;
    private String text;

    public CrestNotRegistered(Crest crest){
        super("Impossible to access to this crest (: " + crest.toString() + " ) information in the class CrestGestionary");
        this.crest = crest;
        generateErrorMessage();
    }

    private void generateErrorMessage(){
        text = "Impossible to access to this crest (: " + crest.toString() + " ) information in the class CrestGestionary";
    }

    @Override
    public String getErrorTitle() {
        return text;
    }
}