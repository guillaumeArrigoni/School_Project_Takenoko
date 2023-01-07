package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

public enum TypeObjective {
    PARCELLE("Parcelle"),
    JARDINIER("Jardinier"),
    PANDA("Panda");

    private final String description;
    TypeObjective(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    @Override
    public String toString(){
        return "Type : " + this.getDescription();
    }
}
