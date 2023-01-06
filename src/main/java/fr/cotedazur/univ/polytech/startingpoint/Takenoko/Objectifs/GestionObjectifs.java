package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.UniqueObjectCreated;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;

import java.util.*;

public class GestionObjectifs {

    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private final Board board;
    private HashMap<Integer, Objectives> ParcelleObjectifs;
    private HashMap<Integer, Objectives> JardinierObjectifs;
    private HashMap<Integer, Objectives> PandaObjectifs;

    /**
     * Contient 3 hashmap qui stockent les differents types d'objectifs disponibles (les pioches).
     */
    public GestionObjectifs(){
        this.retrieveBoxIdWithParameters = UniqueObjectCreated.getRetrieveBoxIdWithParameters();
        this.board = UniqueObjectCreated.getBoard();
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
           if(objectives.getType().equals(TypeObjective.PARCELLE)) {
               ParcelleObjectifs.put(idParcelle++, objectives);
           } if(objectives.getType().equals(TypeObjective.JARDINIER)){
               JardinierObjectifs.put(idJardinier++, objectives);
           } if(objectives.getType().equals(TypeObjective.PANDA)){
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
    public void rollObjective(Bot bot){
        TypeObjective typeObjective = bot.chooseTypeObjectiveToRoll();
        switch (typeObjective){
        case PARCELLE -> rollParcelleObjective(bot);
        case JARDINIER -> rollJardinierObjective(bot);
        /** Il n'y a pas encore d'Objectifs Panda.**/
        case PANDA -> rollParcelleObjective(bot);
        };
    }
    public void rollParcelleObjective(Bot bot){
        int i = new Random().nextInt(1, getParcelleObjectifs().size() +1);
        Objectives objective = getParcelleObjectifs().get(i);
        getParcelleObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif.");
    }
    public void rollJardinierObjective(Bot bot){
        int i = new Random().nextInt(1, getJardinierObjectifs().size() +1);
        Objectives objective = getJardinierObjectifs().get(i);
        getJardinierObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif. ");
    }
    public void rollPandaObjective(Bot bot){
        int i = new Random().nextInt(1, getPandaObjectifs().size() +1);
        Objectives objective = getPandaObjectifs().get(i);
        getPandaObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif. " + objective.toString());
    }
    public void checkObjectives(Bot bot){
        for(Objectives objective : bot.getObjectives()){
            if(checkOneObjective(objective)){
                bot.addScore(objective);
                System.out.println(objective.toString() + "a été réalisé");
                bot.getObjectives().remove(objective);
            }
        }
    }


    public boolean checkOneObjective(Objectives objectives){
        return switch(objectives.getType()) {
            case PARCELLE -> checkParcelleObjectives(objectives);
            case JARDINIER -> checkJardinierObjectives(objectives);
            case PANDA -> checkPandaObjectives(objectives);
        };

    }

    public boolean checkPandaObjectives(Objectives objectives) {
        return false;
    }

    public boolean checkJardinierObjectives(Objectives objectives) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(new ArrayList<Color>(Arrays.asList(Color.Lac))), Optional.empty(),Optional.empty(),Optional.empty());
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
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objectives.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getPlacedBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(box.getAdjacentBox().get(j))){
                    idOfAdjacentBoxCorrect.add(j);
                }
                int size = idOfAdjacentBoxCorrect.size();
            }
            for (int j=0;j<idOfAdjacentBoxCorrect.size();j++){
                if (idOfAdjacentBoxCorrect.contains((idOfAdjacentBoxCorrect.get(j)+1)%6)
                        && board.getGetBox().get(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j)+1)%6)).getColor()==box.getColor()
                        && idOfAdjacentBoxCorrect.contains((idOfAdjacentBoxCorrect.get(j)+2)%6)
                        && board.getGetBox().get(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j)+2)%6)).getColor()!=box.getColor()
                        && idOfAdjacentBoxCorrect.contains((idOfAdjacentBoxCorrect.get(j)+3)%6)
                        && board.getGetBox().get(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j)+3)%6)).getColor()!=box.getColor()){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkParcelleCourbeObjectives(Objectives objectives) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objectives.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getPlacedBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(box.getAdjacentBox().get(j))){
                    idOfAdjacentBoxCorrect.add(j);
                }
                int size = idOfAdjacentBoxCorrect.size();
                if (size > 1 && ((idOfAdjacentBoxCorrect.get(size-1)-idOfAdjacentBoxCorrect.get(size-2) == 1) || (idOfAdjacentBoxCorrect.get(0)==1 && idOfAdjacentBoxCorrect.get(size-1)==6))){
                    return true;
                }
            }
            for (int j=0;j<idOfAdjacentBoxCorrect.size();j++){
                if (idOfAdjacentBoxCorrect.contains((idOfAdjacentBoxCorrect.get(j)+2)%6)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkParcelleLigneObjectives(Objectives objectives) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objectives.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getPlacedBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(box.getAdjacentBox().get(j))){
                    idOfAdjacentBoxCorrect.add(j);
                }
                int size = idOfAdjacentBoxCorrect.size();
                if (size > 1 && ((idOfAdjacentBoxCorrect.get(size-1)-idOfAdjacentBoxCorrect.get(size-2) == 1) || (idOfAdjacentBoxCorrect.get(0)==1 && idOfAdjacentBoxCorrect.get(size-1)==6))){
                    return true;
                }
            }
            for (int j=0;j<idOfAdjacentBoxCorrect.size();j++){
                if (idOfAdjacentBoxCorrect.contains((idOfAdjacentBoxCorrect.get(j)+3)%6)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkParcelleTriangleObjectives(Objectives objectives) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objectives.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getPlacedBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(box.getAdjacentBox().get(j))){
                    idOfAdjacentBoxCorrect.add(j);
                }
                int size = idOfAdjacentBoxCorrect.size();
                if (size > 1 && ((idOfAdjacentBoxCorrect.get(size-1)-idOfAdjacentBoxCorrect.get(size-2) == 1) || (idOfAdjacentBoxCorrect.get(0)==1 && idOfAdjacentBoxCorrect.get(size-1)==6))){
                    return true;
                }
            }
        }
        return false;
    }
    public void printWinner(Bot bot1, Bot bot2){
         int i = bot1.getScore() - bot2.getScore();
         if(i > 0){
             System.out.println("Bot1 wins with " + bot1.getScore() + " points !");
        } else if (i < 0) {
             System.out.println("Bot2 wins with " + bot1.getScore() + " points !");
         } else {
             System.out.println("Draw ! Nobody wins.");
         }
    }

    public boolean checkIfBotCanDrawAnObjective(Bot bot){
        return bot.getObjectives().size() < 5;
    }
    /*public void printWinner(Bot ... bots){
        int max=0;
        int i=1;
        int idWinner=0;
        for(Bot bot : bots){
            System.out.println("Score Bot" + i + " : " + bot.getScore());
            if(bot.getScore() <max){
                max = bot.getScore();
                idWinner = i;
            }
            i++;
        }
        System.out.println("Winner :  Bot" + idWinner + " wins with " + max + " points" );
    }
*/}
