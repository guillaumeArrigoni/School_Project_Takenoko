package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;

public class Pattern {
    private String forme;
    private int nbBambou;
    private int hauteurBambou;
    private Special special;

    Pattern( int nbBambou, int hauteurBambou, Special special){
        this.nbBambou = nbBambou;
        this.hauteurBambou = hauteurBambou;
        this.special = special;
    }
    Pattern(String forme){
        this.forme = forme;
    }

    public String getForme() {
        return forme;
    }

    public int getNbBambou() {
        return nbBambou;
    }

    public int getHauteurBambou() {
        return hauteurBambou;
    }

    public Special getSpecial() {
        return special;
    }
}
