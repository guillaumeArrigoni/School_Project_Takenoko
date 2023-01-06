package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

public class UniqueObjectCreated {


    private static Board board;

    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    private static ElementOfTheGame elementOfTheGame;

    public static void setBoard(Board board) {
        UniqueObjectCreated.board = board;
    }

    public static void setRetrieveBoxIdWithParameters(RetrieveBoxIdWithParameters retrieveBoxIdWithParameters) {
        UniqueObjectCreated.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
    }

    public static void setElementOfTheGame(ElementOfTheGame elementOfTheGame) {
        UniqueObjectCreated.elementOfTheGame = elementOfTheGame;
    }

    public static Board getBoard() {
        return board;
    }

    public static RetrieveBoxIdWithParameters getRetrieveBoxIdWithParameters() {
        return retrieveBoxIdWithParameters;
    }

    public static ElementOfTheGame getElementOfTheGame() {
        return elementOfTheGame;
    }
}
