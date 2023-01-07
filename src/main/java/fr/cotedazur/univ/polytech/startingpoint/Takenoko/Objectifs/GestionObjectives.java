package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Objectifs;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.UniqueObjectCreated;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;

import java.util.*;

public class GestionObjectives {

    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private final Board board;
    private ArrayList<Objective> ParcelleObjectifs;
    private ArrayList<Objective> JardinierObjectifs;
    private ArrayList<Objective> PandaObjectifs;

    /**
     * Contient 3 hashmap qui stockent les differents types d'objectifs disponibles (les pioches).
     */
    public GestionObjectives(){
        this.retrieveBoxIdWithParameters = UniqueObjectCreated.getRetrieveBoxIdWithParameters();
        this.board = UniqueObjectCreated.getBoard();
        this.ParcelleObjectifs = new ArrayList<>();
        this.JardinierObjectifs = new ArrayList<>();
        this.PandaObjectifs = new ArrayList<>();
    }

    /**
     * Remplit les hashmap avec tous les objectifs du jeu.
     */
    public void initialize() {
       for(Objective objective : Objective.values()) {
           if(objective.getType().equals(TypeObjective.PARCELLE)) {
               ParcelleObjectifs.add(objective);
           } if(objective.getType().equals(TypeObjective.JARDINIER)){
               JardinierObjectifs.add(objective);
           } if(objective.getType().equals(TypeObjective.PANDA)){
               PandaObjectifs.add(objective);
           }
       }
    }

    public ArrayList<Objective> getParcelleObjectifs() {
        return ParcelleObjectifs;
    }

    public ArrayList<Objective> getJardinierObjectifs() {
        return JardinierObjectifs;
    }

    public ArrayList<Objective> getPandaObjectifs() {
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
        int i = new Random().nextInt(0, getParcelleObjectifs().size());
        Objective objective = getParcelleObjectifs().get(i);
        getParcelleObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif.");
        System.out.println(objective);
    }
    public void rollJardinierObjective(Bot bot){
        int i = new Random().nextInt(0, getJardinierObjectifs().size());
        Objective objective = getJardinierObjectifs().get(i);
        getJardinierObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif. ");
        System.out.println(objective);
    }
    public void rollPandaObjective(Bot bot){
        int i = new Random().nextInt(0, getPandaObjectifs().size());
        Objective objective = getPandaObjectifs().get(i);
        getPandaObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif. ");
        System.out.println(objective);
    }
    public void checkObjectives(Bot bot){
        ArrayList<Objective> a = new ArrayList<>();
        for(Objective objective : bot.getObjectives()){
            if(checkOneObjective(objective)){
                bot.addScore(objective);
                System.out.println(objective.toString() + " a été réalisé");
                a.add(objective);
                //bot.getObjectives().remove(objective);
            }
        }
        ArrayList<Objective> b = bot.getObjectives();
        b.removeAll(a);
        bot.setObjectives(b);
    }


    public boolean checkOneObjective(Objective objective){
        return switch(objective.getType()) {
            case PARCELLE -> checkParcelleObjectives(objective);
            case JARDINIER -> checkJardinierObjectives(objective);
            case PANDA -> checkPandaObjectives(objective);
        };

    }

    public boolean checkPandaObjectives(Objective objective) {
        return false;
    }

    public boolean checkJardinierObjectives(Objective objective) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(new ArrayList<Color>(Arrays.asList(Color.Lac))), Optional.empty(),Optional.empty(),Optional.empty());
        return false;
    }

    public boolean checkParcelleObjectives(Objective objective) {
        return switch (objective.getPattern().getForme()){
            case "TRIANGLE" -> checkParcelleTriangleObjectives(objective);
            case "LIGNE" -> checkParcelleLigneObjectives(objective);
            case "COURBE" -> checkParcelleCourbeObjectives(objective);
            case "LOSANGE" -> checkParcelleLosangeObjectives(objective);
            default -> false;
        };
    }

    private boolean checkParcelleLosangeObjectives(Objective objective) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getGetBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(HexagoneBox.generateID(box.getAdjacentBox().get(j)))){
                    idOfAdjacentBoxCorrect.add(j);
                }
            }
            for (int j=0;j<idOfAdjacentBoxCorrect.size();j++){
                if (board.getGetBox().containsKey(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j)+1)%6)) &&
                        board.getGetBox().containsKey(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j)+2)%6)) &&
                        board.getGetBox().containsKey(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j)+3)%6))) {
                    if (idOfAdjacentBoxCorrect.contains((idOfAdjacentBoxCorrect.get(j) + 1) % 6)
                            && board.getGetBox().get(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j) + 1) % 6)).getColor() == box.getColor()
                            && idOfAdjacentBoxCorrect.contains((idOfAdjacentBoxCorrect.get(j) + 2) % 6)
                            && board.getGetBox().get(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j) + 2) % 6)).getColor() != box.getColor()
                            && idOfAdjacentBoxCorrect.contains((idOfAdjacentBoxCorrect.get(j) + 3) % 6)
                            && board.getGetBox().get(box.getAdjacentBox().get((idOfAdjacentBoxCorrect.get(j) + 3) % 6)).getColor() != box.getColor()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkParcelleCourbeObjectives(Objective objective) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getGetBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(HexagoneBox.generateID(box.getAdjacentBox().get(j)))){
                    idOfAdjacentBoxCorrect.add(j);
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

    private boolean checkParcelleLigneObjectives(Objective objective) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getGetBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(HexagoneBox.generateID(box.getAdjacentBox().get(j)))){
                    idOfAdjacentBoxCorrect.add(j);
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

    private boolean checkParcelleTriangleObjectives(Objective objective) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getGetBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(HexagoneBox.generateID(box.getAdjacentBox().get(j)))){
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
