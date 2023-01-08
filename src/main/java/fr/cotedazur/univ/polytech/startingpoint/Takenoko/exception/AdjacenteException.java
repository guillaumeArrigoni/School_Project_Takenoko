package fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception;

public class AdjacenteException extends TakenokoException{

    private final String tab1;
    private final String tab2;

    public AdjacenteException(int[] tab1,int[]tab2){
        super("There is no coordinate in common between the 2 tab entered :\n" + tab1.toString() + "\n" + tab2.toString());
        this.tab1 = tab1.toString();
        this.tab2 = tab2.toString();
    }

    @Override
    public String getErrorTitle() {
        return "There is no coordinate in common between the 2 tab entered :\n" + tab1 + "\n" + tab2;
    }
}
