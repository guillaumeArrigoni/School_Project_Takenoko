package fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception;

public class ListOfDifferentSize extends TakenokoException{

    private int firstSize;
    private int secondSize;
    private String text;

    public ListOfDifferentSize(int firstSize, int secondSize){
        super("Impossible generate the box of the game because the list entered have two different size : " + Integer.toString(firstSize) + " and " + Integer.toString(secondSize) + ".");
        this.firstSize = firstSize;
        this.secondSize = secondSize;
        generateErrorMessage();
    }

    private void generateErrorMessage(){
        text = "Impossible generate the box of the game because the list entered have two different size : "
                + Integer.toString(firstSize)
                + " and "
                + Integer.toString(secondSize)
                + ".";
    }

    @Override
    public String getErrorTitle() {
        return text;
    }
}