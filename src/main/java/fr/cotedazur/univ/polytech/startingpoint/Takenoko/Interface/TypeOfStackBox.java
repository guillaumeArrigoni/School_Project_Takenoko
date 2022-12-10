package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface;

public enum TypeOfStackBox {
    /**
     * The different type of StackBox that can be picked up
     */
    JauneClassic(0,Color.Jaune,Special.Classique),
    JauneSourceEau(1, Color.Jaune,Special.SourceEau),
    JauneEngrais(2, Color.Jaune,Special.Engrais),
    JauneProtected(3, Color.Jaune,Special.Protéger),
    VertClassic(4,Color.Vert,Special.Classique),
    VertSourceEau(5, Color.Vert,Special.SourceEau),
    VertEngrais(6, Color.Vert,Special.Engrais),
    VertProtected(7, Color.Vert,Special.Protéger),
    RougeClassic(8,Color.Rouge,Special.Classique),
    RougeSourceEau(9, Color.Rouge,Special.SourceEau),
    RougeEngrais(10, Color.Rouge,Special.Engrais),
    RougeProtected(11, Color.Rouge,Special.Protéger);
    private final int indice;

    private final Special special;
    private final Color color;

    TypeOfStackBox(int indice, Color color,Special special) {
        this.indice = indice;
        this.color = color;
        this.special = special;
    }

    public int getIndice() {
        return indice;
    }
    public Color getColor(){return color;}
    public Special getSpecial(){return special;}

    @Override
    public String toString() {
        return color.toString() + special.toString();
    }
}
