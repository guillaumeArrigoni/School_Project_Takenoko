package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
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
    public GestionObjectives(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters){
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.board = board;
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
        ArrayList<Objective> listOfObjectifDone = new ArrayList<>();
        for(Objective objective : bot.getObjectives()){
            if(checkOneObjective(objective)){
                bot.addScore(objective);
                System.out.println(objective.toString() + " a été réalisé");
                listOfObjectifDone.add(objective);
            }
        }
        ArrayList<Objective> listOfAllObjectivesFromABot = bot.getObjectives();
        listOfAllObjectivesFromABot.removeAll(listOfObjectifDone);
        bot.setObjectives(listOfAllObjectivesFromABot);
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
            case "TRIANGLE" -> checkParcelleTriangleOrLigneOrCourbeObjectives(objective,1);
            case "LIGNE" -> checkParcelleTriangleOrLigneOrCourbeObjectives(objective,3);
            case "COURBE" -> checkParcelleTriangleOrLigneOrCourbeObjectives(objective,2);
            case "LOSANGE" -> checkParcelleLosangeObjectives(objective);
            default -> false;
        };
    }

    /**
     * Method use to know if a rhombus parcel Objective is complete
     * @param objective that we want to check
     * @return true if the objective is completed or false if it does not.
     */
    private boolean checkParcelleLosangeObjectives(Objective objective) {
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            HexagoneBox box = board.getPlacedBox().get(listOfIdAvailable.get(i));
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(HexagoneBox.generateID(box.getAdjacentBox().get(j)))){
                    idOfAdjacentBoxCorrect.add(j);
                }
            }
            if (ParcelleLosangeObjectifCondition(box, idOfAdjacentBoxCorrect)) return true;
        }
        return false;
    }

    /**
     * Method with the rhombus parcel condition
     * @param box which we want to know if, with the adjacent box, the rhombus parcel is completed
     * @param idOfAdjacentBoxCorrect contains all the adjacent box of the previous box that complete the objective condition (color, irrigated ...)
     * @return true if the rhombus parcel is completed, false if it does not
     */
    private boolean ParcelleLosangeObjectifCondition(HexagoneBox box, ArrayList<Integer> idOfAdjacentBoxCorrect) {
        for (int j = 0; j< idOfAdjacentBoxCorrect.size(); j++){
            int adjIndice1 = (idOfAdjacentBoxCorrect.get(j)+1)%7;
            int adjIndice2 = (idOfAdjacentBoxCorrect.get(j)+2)%7;
            if (adjIndice1 == 0) adjIndice1 = 1;
            if (adjIndice2 == 0) adjIndice2 = 1;
            if (board.isCoordinateInBoard(box.getAdjacentBox().get(adjIndice1)) &&
                    board.isCoordinateInBoard(box.getAdjacentBox().get(adjIndice2))){
                if (idOfAdjacentBoxCorrect.contains(adjIndice1)
                        && board.getBoxWithCoordinates(box.getAdjacentBox().get(adjIndice1)).getColor() == box.getColor()
                        && idOfAdjacentBoxCorrect.contains(adjIndice2)
                        && board.getBoxWithCoordinates(box.getAdjacentBox().get(adjIndice2)).getColor() == box.getColor()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method use to know if a triangle, line or curve parcel Objective is complete
     * @param objective that we want to check
     * @param x that have the value :
     *          1 if it is a triangle objective
     *          2 if it is a curve objective
     *          3 if it is a line objective
     * @return true if the objective is completed or false if it does not.
     */
    private boolean checkParcelleTriangleOrLigneOrCourbeObjectives(Objective objective, int x){
        ArrayList<Integer> listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(),Optional.empty(),Optional.empty());
        for (int i=0;i<listOfIdAvailable.size();i++){
            ArrayList<Integer> idOfAdjacentBoxCorrect = getAllAdjacentBoxThatCompleteTheCondition(listOfIdAvailable, i);
            if (ParcelleObjectifCondition(idOfAdjacentBoxCorrect, x)) return true;
        }
        return false;
    }


    /**
     * Method with the triangle, line and curve condition
     * @param listOfIdAvailable contains all the box placed in the board that complete the requirement of the objective (color, irrigated,...)
     * @param i that have the value as before
     * @return true if the parcel is compelted, false if it does not
     */
    private ArrayList<Integer> getAllAdjacentBoxThatCompleteTheCondition(ArrayList<Integer> listOfIdAvailable, int i) {
        HexagoneBox box = board.getPlacedBox().get(listOfIdAvailable.get(i));
        ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
        for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
            if (listOfIdAvailable.contains(HexagoneBox.generateID(box.getAdjacentBox().get(j)))){
                idOfAdjacentBoxCorrect.add(j);
            }
        }
        return idOfAdjacentBoxCorrect;
    }

    /**
     * Method with the triangle, line and curve condition
     * @param idOfAdjacentBoxCorrect wontains all the box that filled the objective's requirement and are adjacent of another box that also complete the objective's requirement
     * @param x that have the same value as before
     * @return true if the objective is completed or false if it does not.
     */
    private boolean ParcelleObjectifCondition(ArrayList<Integer> idOfAdjacentBoxCorrect, int x) {
        for (int j = 0; j< idOfAdjacentBoxCorrect.size(); j++){
            int adjIndice = (idOfAdjacentBoxCorrect.get(j)+ x)%7;
            if (adjIndice == 0) adjIndice = 1;
            if (idOfAdjacentBoxCorrect.contains(adjIndice)){
                return true;
            }
        }
        return false;
    }

    public void printWinner(Bot bot1, Bot bot2){
         int i = bot1.getScore() - bot2.getScore();
         if(i > 0){
             System.out.println("Bot1 a gagné avec " + bot1.getScore() + " points !");
        } else if (i < 0) {
             System.out.println("Bot2 a gagné avec " + bot2.getScore() + " points !");
         } else {
             System.out.println("Egalité !");
         }
    }

    public boolean checkIfBotCanDrawAnObjective(Bot bot){
        return bot.getObjectives().size() < 5;
    }
}
