package fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.elementOfTheBoard;

        import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.TakenokoException;
        import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;

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
