package fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties;

public enum Color {
    /**
     * The different Color of the box
     */
    LAC(0, "Lac"),
    JAUNE(1, "Jaune"),
    VERT(2, "Vert"),
    ROUGE(3, "Rouge");
    private final int indice;

    private final String description;

    Color(int indice, String description) {
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
