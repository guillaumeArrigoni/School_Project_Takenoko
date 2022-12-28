package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionObjectifs {
    private HashMap<Integer, Objectives> ParcelleObjectifs;
    private HashMap<Integer, Objectives> JardinierObjectifs;
    private HashMap<Integer, Objectives> PandaObjectifs;

    /**
     * Contient 3 hashmap qui stockent les differents types d'objectifs disponibles (les pioches).
     */
    public GestionObjectifs(){
        this.ParcelleObjectifs = new HashMap<>();
        this.JardinierObjectifs = new HashMap<>();
        this.PandaObjectifs = new HashMap<>();
    }

    /**
     * Remplit les hashmap avec tous les objectifs du jeu.
     */
    public void initialize() {
       int idParcelle = 1;
       int idJardinier = 1;
       int idPanda = 1;
       for(Objectives objectives : Objectives.values()) {
           if(objectives.getType().equals("PARCELLE")) {
               ParcelleObjectifs.put(idParcelle++, objectives);
           } if(objectives.getType().equals("JARDINIER")){
               JardinierObjectifs.put(idJardinier++, objectives);
           } if(objectives.getType().equals("PANDA")){
               PandaObjectifs.put(idPanda++, objectives);
           }
       }
    }

    public HashMap<Integer, Objectives> getParcelleObjectifs() {
        return ParcelleObjectifs;
    }

    public HashMap<Integer, Objectives> getJardinierObjectifs() {
        return JardinierObjectifs;
    }

    public HashMap<Integer, Objectives> getPandaObjectifs() {
        return PandaObjectifs;
    }

    /**
     * Choisit aléatoirement un objectif de la catégorie correspondant au choix du bot.
     * Supprime cet objectif de la hashmap associée (objectif plus disponible).
     */
    public Objectives rollParcelleObjectifs(){
        int i = (int) (Math.random()*getParcelleObjectifs().size()) +1;
        Objectives objective = getParcelleObjectifs().get(i);
        getParcelleObjectifs().remove(i);
        return  objective;
    }
    public Objectives rollJardinierObjectifs(){
        int i = (int) (Math.random()*getJardinierObjectifs().size()) +1;
        Objectives objective = getJardinierObjectifs().get(i);
        getJardinierObjectifs().remove(i);
        return  objective;
    }
    public Objectives rollPandaObjectifs(){
        int i = (int) (Math.random()*getPandaObjectifs().size()) +1;
        Objectives objective = getPandaObjectifs().get(i);
        getPandaObjectifs().remove(i);
        return  objective;
    }

    public boolean checkObjectives(Objectives objectives){
        return switch(objectives.getType()) {
            case "PARCELLE" -> checkParcelleObjectives(objectives);
            case "JARDINIER" -> checkJardinierObjectives(objectives);
            case "PANDA" -> checkPandaObjectives(objectives);
            default -> false;
        };

    }

    public boolean checkPandaObjectives(Objectives objectives) {
        return false;
    }

    public boolean checkJardinierObjectives(Objectives objectives) {
        return false;
    }

    public boolean checkParcelleObjectives(Objectives objectives) {
        return switch (objectives.getPattern().getForme()){
            case "TRIANGLE" -> checkParcelleTriangleObjectives(objectives);
            case "LIGNE" -> checkParcelleLigneObjectives(objectives);
            case "COURBE" -> checkParcelleCourbeObjectives(objectives);
            case "LOSANGE" -> checkParcelleLosangeObjectives(objectives);
            default -> false;
        };
    }

    private boolean checkParcelleLosangeObjectives(Objectives objectives) {
        return false;
    }

    private boolean checkParcelleCourbeObjectives(Objectives objectives) {
        return false;
    }

    private boolean checkParcelleLigneObjectives(Objectives objectives) {
        return false;
    }

    private boolean checkParcelleTriangleObjectives(Objectives objectives) {
        return false;
    }
}
