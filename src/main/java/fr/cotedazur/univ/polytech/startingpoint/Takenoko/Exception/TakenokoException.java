package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Exception;

public class TakenokoException extends Exception {
    private final String errorTitle;

    public TakenokoException(String message) {
        super(message);
        this.errorTitle = "An error has occurred";
    }

    public String getErrorTitle() {
        return this.errorTitle;
    }
}
