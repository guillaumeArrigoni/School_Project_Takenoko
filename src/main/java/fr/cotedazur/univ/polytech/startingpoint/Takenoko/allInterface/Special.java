package fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface;

public enum Special {
    /**
     * The different special particularity of the box
     */
    Classique(0, "classic"),
    SourceEau(1,"water's source"),
    Engrais(2,"fertilizer"),
    Protéger(3,"protected");

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
