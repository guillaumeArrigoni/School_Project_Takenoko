package fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.elementoftheboard;

        import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.TakenokoException;
        import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;

public class BambooOfColorNotExistingException extends TakenokoException {

    private Color color;
    private String text;

    public BambooOfColorNotExistingException(Color color){
        super("Impossible to place bamboo because there is no bamboo of color " + color.toString() + " in this rules.");
        this.color = color;
        generateErrorMessage();
    }

    private void generateErrorMessage(){
        text = "Impossible to place bamboo because there is no bamboo of color " + color.toString() + " in this rules.";
    }

    @Override
    public String getErrorTitle() {
        return text;
    }
}
