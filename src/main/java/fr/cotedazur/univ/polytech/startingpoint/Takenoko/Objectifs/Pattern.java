package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;

public enum Pattern {
    PATTERNPARCELLE1("TRIANGLE",0,0,null ),
    PATTERNPARCELLE2("TRIANGLE",0,0,null),
    PATTERNPARCELLE3("TRIANGLE",0,0,null),
    PATTERNPARCELLE4("LIGNE",0,0,null),
    PATTERNPARCELLE5("LIGNE",0,0,null),
    PATTERNPARCELLE6("LIGNE",0,0,null),
    PATTERNPARCELLE7("COURBE",0,0,null),
    PATTERNPARCELLE8("COURBE",0,0,null),
    PATTERNPARCELLE9("COURBE",0,0,null),
    PATTERNPARCELLE10("LOSANGE",0,0,null),
    PATTERNPARCELLE11("LOSANGE",0,0,null),
    PATTERNPARCELLE12("LOSANGE",0,0,null),
    PATTERNPARCELLE13("LOSANGE",0,0,null),
    PATTERNPARCELLE14("LOSANGE",0,0,null),
    PATTERNPARCELLE15("LOSANGE",0,0,null),
    PATTERNJARDINIER1(null,1,4,Special.SourceEau),
    PATTERNJARDINIER2(null,1,4,Special.SourceEau),
    PATTERNJARDINIER3(null,1,4,Special.SourceEau),
    PATTERNJARDINIER4(null,1,4,Special.Engrais),
    PATTERNJARDINIER5(null,1,4,Special.Engrais),
    PATTERNJARDINIER6(null,1,4,Special.Engrais),
    PATTERNJARDINIER7(null,1,4,Special.Protéger),
    PATTERNJARDINIER8(null,1,4,Special.Protéger),
    PATTERNJARDINIER9(null,1,4,Special.Protéger),
    PATTERNJARDINIER10(null,1,4,Special.Classique),
    PATTERNJARDINIER11(null,1,4,Special.Classique),
    PATTERNJARDINIER12(null,1,4,Special.Classique),
    PATTERNJARDINIER13(null,2,3,null),
    PATTERNJARDINIER14(null,3,3,null),
    PATTERNJARDINIER15(null,4,3,null);



    private String forme;
    private int nbBambou;
    private int hauteurBambou;
    private Special special;

    Pattern(String forme, int nbBambou, int hauteurBambou, Special special){
        this.forme = forme;
        this.nbBambou = nbBambou;
        this.hauteurBambou = hauteurBambou;
        this.special = special;
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
