package fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception;


        import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;

public class IntegerNotPossible extends TakenokoException{

    private final int nbNotPossible;
    private final int minValuePossible;
    private final int maxValuePossible;
    private String text;

    public IntegerNotPossible(int nbNotPossible, int minValuePossible, int maxValuePossible){
        super("Impossible to set the integer to " + nbNotPossible + " the minimal value is " + minValuePossible + " and the maximal is " + maxValuePossible);
        this.nbNotPossible = nbNotPossible;
        this.maxValuePossible = maxValuePossible;
        this.minValuePossible = minValuePossible;
        generateErrorMessage();
    }

    private void generateErrorMessage(){
        text = "Impossible to set the integer to " + nbNotPossible + " the minimal value is " + minValuePossible + " and the maximal is " + maxValuePossible;
    }

    @Override
    public String getErrorTitle() {
        return text;
    }
}