package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;

public enum Pattern {
    POSER_TRIANGLE("TRIANGLE",0,0,null ),
    POSER_LIGNE("LIGNE",0,0,null),
    POSER_COURBE("COURBE",0,0,null),
    POSER_LOSANGE("LOSANGE",0,0,null),
    PLANTER_SUR_SOURCE_EAU(null,1,4,Special.SourceEau),
    PLANTER_SUR_ENGRAIS(null,1,4,Special.Engrais),
    PLANTER_SUR_PROTEGER(null,1,4,Special.Protéger),
    PLANTER_SUR_CLASSIQUE(null,1,4,Special.Classique),
    PLANTER_DEUX_ROUGES(null,2,3,null),
    PLANTER_TROIS_JAUNES(null,3,3,null),
    PLANTER_QUATRE_VERTS(null,4,3,null),
    MANGER_DEUX_BAMBOUS(null,2,1,null),
    MANGER_TROIS_BAMBOUS(null,3,1,null);



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
