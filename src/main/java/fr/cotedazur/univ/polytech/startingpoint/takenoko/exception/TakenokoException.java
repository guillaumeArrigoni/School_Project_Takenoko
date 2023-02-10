package fr.cotedazur.univ.polytech.startingpoint.takenoko.exception;

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

/*
System.err.println("\n  -> An error has occurred : "
                    + e.getErrorTitle()
                    + "\n"
                    + "In line :\n"
                    + e.getStackTrace()
                    + "\n\n"
                    + "Fatal error.");
            throw new RuntimeException();
 */