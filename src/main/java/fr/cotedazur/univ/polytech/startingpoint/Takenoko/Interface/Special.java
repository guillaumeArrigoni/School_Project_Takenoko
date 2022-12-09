package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface;

public enum Special {
    Classic(0, "classic");
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
