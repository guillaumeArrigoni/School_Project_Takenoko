package fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface;

public enum Color {
    /**
     * The different Color of the box
     */
    Lac(0, "Lac"),
    Jaune(1, "Jaune"),
    Vert(2, "Vert"),
    Rouge(3, "Rouge");
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
