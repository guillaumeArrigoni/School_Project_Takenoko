package fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties;

public enum Special {
    /**
     * The different special particularity of the box
     */
    CLASSIQUE(0, "classic"),
    SOURCE_EAU(1,"water's source"),
    ENGRAIS(2,"fertilizer"),
    PROTEGER(3,"protected");

    private final int indice;

    private final String description;

    Special(int indice, String description) {
        this.indice = indice;
        this.description = description;
    }

    public int getRank() {
        return indice;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
