package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.DeletingBotBambooException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;

import java.util.*;

public class GestionObjectives {

    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private final Board board;
    private ArrayList<ObjectiveParcelle> ParcelleObjectifs;
    private ArrayList<ObjectiveJardinier> JardinierObjectifs;
    private ArrayList<ObjectivePanda> PandaObjectifs;

    private final int NB_LISTES_OBJECTIVES = 3;

    PatternParcelle POSER_TRIANGLE = new PatternParcelle("TRIANGLE");
    PatternParcelle POSER_LIGNE = new PatternParcelle("LIGNE");
    PatternParcelle POSER_COURBE = new PatternParcelle("COURBE");
    PatternParcelle POSER_LOSANGE = new PatternParcelle("LOSANGE");
    PatternJardinier PLANTER_SUR_SOURCE_EAU = new PatternJardinier(1,4, Special.SourceEau);
    PatternJardinier PLANTER_SUR_ENGRAIS = new PatternJardinier(1,4,Special.Engrais);
    PatternJardinier PLANTER_SUR_PROTEGER = new PatternJardinier(1,4,Special.Protéger);
    PatternJardinier PLANTER_SUR_CLASSIQUE = new PatternJardinier(1,4,Special.Classique);
    PatternJardinier PLANTER_DEUX_ROUGES = new PatternJardinier(2,3,null);
    PatternJardinier PLANTER_TROIS_JAUNES = new PatternJardinier(3,3,null);
    PatternJardinier PLANTER_QUATRE_VERTS = new PatternJardinier(4,3,null);
    PatternPanda MANGER_DEUX_BAMBOUS = new PatternPanda(2,1,null);
    PatternPanda MANGER_TROIS_BAMBOUS = new PatternPanda(3,1,null);
    ObjectiveParcelle POSER_TRIANGLE_VERT = new ObjectiveParcelle("POSER_TRIANGLE_VERT",2, POSER_TRIANGLE, new ArrayList<>(Arrays.asList( Color.Vert)));
    ObjectiveParcelle POSER_TRIANGLE_JAUNE= new ObjectiveParcelle("POSER_TRIANGLE_JAUNE",3, POSER_TRIANGLE, new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveParcelle POSER_TRIANGLE_ROUGE= new ObjectiveParcelle("POSER_TRIANGLE_ROUGE",4, POSER_TRIANGLE, new ArrayList<>(Arrays.asList( Color.Rouge)));
    ObjectiveParcelle POSER_LIGNE_VERTE= new ObjectiveParcelle("POSER_LIGNE_VERTE",2, POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Vert)));
    ObjectiveParcelle POSER_LIGNE_JAUNE= new ObjectiveParcelle("POSER_LIGNE_JAUNE",3, POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Jaune)));
    ObjectiveParcelle POSER_LIGNE_ROUGE= new ObjectiveParcelle("POSER_LIGNE_ROUGE",4, POSER_LIGNE, new ArrayList<>(Arrays.asList( Color.Rouge)));
    ObjectiveParcelle POSER_COURBE_VERTE= new ObjectiveParcelle("POSER_COURBE_VERTE",2, POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Vert)));
    ObjectiveParcelle POSER_COURBE_JAUNE= new ObjectiveParcelle("POSER_COURBE_JAUNE",3, POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Jaune)));
    ObjectiveParcelle POSER_COURBE_ROUGE= new ObjectiveParcelle("POSER_COURBE_ROUGE",4, POSER_COURBE, new ArrayList<>(Arrays.asList( Color.Rouge)));
    ObjectiveParcelle POSER_LOSANGE_VERT= new ObjectiveParcelle("POSER_LOSANGE_VERT",3, POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert)));
    ObjectiveParcelle POSER_LOSANGE_JAUNE= new ObjectiveParcelle("POSER_LOSANGE_JAUNE",4, POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Jaune)));
    ObjectiveParcelle POSER_LOSANGE_ROUGE= new ObjectiveParcelle("POSER_LOSANGE_ROUGE",5, POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Rouge)));
    ObjectiveParcelle POSER_LOSANGE_VERT_JAUNE= new ObjectiveParcelle("POSER_LOSANGE_VERT_JAUNE",3, POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert,Color.Jaune)));
    ObjectiveParcelle POSER_LOSANGE_VERT_ROUGE= new ObjectiveParcelle("POSER_LOSANGE_VERT_ROUGE",4, POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Vert,Color.Rouge)));
    ObjectiveParcelle POSER_LOSANGE_ROUGE_JAUNE= new ObjectiveParcelle("POSER_LOSANGE_ROUGE_JAUNE",5, POSER_LOSANGE, new ArrayList<>(Arrays.asList( Color.Rouge,Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_SOURCE_EAU_BAMBOU_VERT = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_VERT",4,PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectiveJardinier PLANTER_SUR_SOURCE_EAU_BAMBOU_JAUNE = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_JAUNE",4,PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_SOURCE_EAU_BAMBOU_ROUGE = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_ROUGE",4,PLANTER_SUR_SOURCE_EAU,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_SUR_ENGRAIS_BAMBOU_VERT = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_VERT",3,PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectiveJardinier PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE",4,PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_ENGRAIS_BAMBOU_ROUGE = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_ROUGE",5,PLANTER_SUR_ENGRAIS,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_SUR_PROTEGER_BAMBOU_VERT = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_VERT",4,PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectiveJardinier PLANTER_SUR_PROTEGER_BAMBOU_JAUNE = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_JAUNE",5,PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_PROTEGER_BAMBOU_ROUGE = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_ROUGE",6,PLANTER_SUR_PROTEGER,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_SUR_CLASSIQUE_BAMBOU_VERT = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_VERT",5,PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectiveJardinier PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE",6,PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_SUR_CLASSIQUE_BAMBOU_ROUGE = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_ROUGE",7,PLANTER_SUR_CLASSIQUE,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_DEUX_BAMBOUS_ROUGES = new ObjectiveJardinier("PLANTER_DEUX_BAMBOUS_ROUGES",6,PLANTER_DEUX_ROUGES,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectiveJardinier PLANTER_TROIS_BAMBOUS_JAUNES = new ObjectiveJardinier("PLANTER_TROIS_BAMBOUS_JAUNES",7,PLANTER_TROIS_JAUNES,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectiveJardinier PLANTER_QUATRE_BAMBOUS_VERTS = new ObjectiveJardinier("PLANTER_QUATRE_BAMBOUS_VERTS",8,PLANTER_QUATRE_VERTS,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectivePanda MANGER_DEUX_VERTS_1 = new ObjectivePanda("MANGER_DEUX_VERTS_1",3,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectivePanda MANGER_DEUX_VERTS_2 = new ObjectivePanda("MANGER_DEUX_VERTS_2",3,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectivePanda MANGER_DEUX_VERTS_3 = new ObjectivePanda("MANGER_DEUX_VERTS_3",3,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectivePanda MANGER_DEUX_VERTS_4 = new ObjectivePanda("MANGER_DEUX_VERTS_4",3,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectivePanda MANGER_DEUX_VERTS_5 = new ObjectivePanda("MANGER_DEUX_VERTS_5",3,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert)));
    ObjectivePanda MANGER_DEUX_JAUNES_1 = new ObjectivePanda("MANGER_DEUX_JAUNES_1",4,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectivePanda MANGER_DEUX_JAUNES_2 = new ObjectivePanda("MANGER_DEUX_JAUNES_2",4,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectivePanda MANGER_DEUX_JAUNES_3 = new ObjectivePanda("MANGER_DEUX_JAUNES_3",4,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectivePanda MANGER_DEUX_JAUNES_4 = new ObjectivePanda("MANGER_DEUX_JAUNES_4",4,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Jaune)));
    ObjectivePanda MANGER_DEUX_ROUGES_1 = new ObjectivePanda("MANGER_DEUX_ROUGES_1",5,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectivePanda MANGER_DEUX_ROUGES_2 = new ObjectivePanda("MANGER_DEUX_ROUGES_2",5,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectivePanda MANGER_DEUX_ROUGES_3 = new ObjectivePanda("MANGER_DEUX_ROUGES_3",5,MANGER_DEUX_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Rouge)));
    ObjectivePanda MANGER_TRICOLORE_1 = new ObjectivePanda("MANGER_TRICOLORE_1",6,MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge)));
    ObjectivePanda MANGER_TRICOLORE_2 = new ObjectivePanda("MANGER_TRICOLORE_2",6,MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge)));
    ObjectivePanda MANGER_TRICOLORE_3 = new ObjectivePanda("MANGER_TRICOLORE_3",6,MANGER_TROIS_BAMBOUS,new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge)));

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
    public ArrayList<ObjectiveParcelle> ListOfObjectiveParcelleByDefault(){
        return new ArrayList<>(Arrays.asList(
                POSER_TRIANGLE_VERT,
                POSER_TRIANGLE_JAUNE,
                POSER_TRIANGLE_ROUGE,
                POSER_LIGNE_VERTE,
                POSER_LIGNE_JAUNE,
                POSER_LIGNE_ROUGE,
                POSER_COURBE_VERTE,
                POSER_COURBE_JAUNE,
                POSER_COURBE_ROUGE,
                POSER_LOSANGE_VERT,
                POSER_LOSANGE_JAUNE,
                POSER_LOSANGE_ROUGE,
                POSER_LOSANGE_VERT_ROUGE,
                POSER_LOSANGE_VERT_JAUNE,
                POSER_LOSANGE_ROUGE_JAUNE));
    }
    public ArrayList<ObjectiveJardinier> ListOfObjectiveJardinierByDefault(){
        return new ArrayList<>(Arrays.asList(
                PLANTER_SUR_SOURCE_EAU_BAMBOU_VERT,
                PLANTER_SUR_SOURCE_EAU_BAMBOU_JAUNE,
                PLANTER_SUR_SOURCE_EAU_BAMBOU_ROUGE,
                PLANTER_SUR_ENGRAIS_BAMBOU_VERT,
                PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE,
                PLANTER_SUR_ENGRAIS_BAMBOU_ROUGE,
                PLANTER_SUR_PROTEGER_BAMBOU_VERT,
                PLANTER_SUR_PROTEGER_BAMBOU_JAUNE,
                PLANTER_SUR_PROTEGER_BAMBOU_ROUGE,
                PLANTER_SUR_CLASSIQUE_BAMBOU_VERT,
                PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE,
                PLANTER_SUR_CLASSIQUE_BAMBOU_ROUGE,
                PLANTER_DEUX_BAMBOUS_ROUGES,
                PLANTER_TROIS_BAMBOUS_JAUNES,
                PLANTER_QUATRE_BAMBOUS_VERTS));
    }
    public ArrayList<ObjectivePanda> ListOfObjectivePandaByDefault(){
        return new ArrayList<>(Arrays.asList(
                MANGER_DEUX_VERTS_1,
                MANGER_DEUX_VERTS_2,
                MANGER_DEUX_VERTS_3,
                MANGER_DEUX_VERTS_4,
                MANGER_DEUX_VERTS_5,
                MANGER_DEUX_JAUNES_1,
                MANGER_DEUX_JAUNES_2,
                MANGER_DEUX_JAUNES_3,
                MANGER_DEUX_JAUNES_4,
                MANGER_DEUX_ROUGES_1,
                MANGER_DEUX_ROUGES_2,
                MANGER_DEUX_ROUGES_3,
                MANGER_TRICOLORE_1,
                MANGER_TRICOLORE_2,
                MANGER_TRICOLORE_3
        ));
    }

    /**
     * Remplit les hashmap avec tous les objectifs du jeu.
     */
    public void initialize(ArrayList<ObjectiveParcelle> objectiveParcelleArrayList,ArrayList<ObjectiveJardinier> objectiveJardinierArrayList, ArrayList<ObjectivePanda> objectivePandaArrayList) {
        this.ParcelleObjectifs = objectiveParcelleArrayList;
        this.JardinierObjectifs = objectiveJardinierArrayList;
        this.PandaObjectifs = objectivePandaArrayList;
    }

    public ArrayList<ObjectiveParcelle> getParcelleObjectifs() {
        return ParcelleObjectifs;
    }

    public ArrayList<ObjectiveJardinier> getJardinierObjectifs() {
        return JardinierObjectifs;
    }

    public ArrayList<ObjectivePanda> getPandaObjectifs() {
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
        case PANDA -> rollPandaObjective(bot);
        }
    }
    public void rollParcelleObjective(Bot bot){
        int i = new Random().nextInt(0, getParcelleObjectifs().size());
        Objective objective = this.getParcelleObjectifs().get(i);
        this.getParcelleObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif.");
        System.out.println(objective);
    }
    public void rollJardinierObjective(Bot bot){
        int i = new Random().nextInt(0, getJardinierObjectifs().size());
        Objective objective = this.getJardinierObjectifs().get(i);
        this.getJardinierObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif. ");
        System.out.println(objective);
    }
    public void rollPandaObjective(Bot bot){
        int i = new Random().nextInt(0, getPandaObjectifs().size());
        Objective objective = this.getPandaObjectifs().get(i);
        this.getPandaObjectifs().remove(i);
        bot.getObjectives().add(objective);
        System.out.println(bot.getName() + " a pioché un nouvel objectif. ");
        System.out.println(objective);
    }
    public void checkObjectives(Bot bot){
        ArrayList<Objective> listOfObjectifDone = new ArrayList<>();
        for(Objective objective : bot.getObjectives()){
            if(checkOneObjective(objective, bot)){
                bot.addScore(objective);
                System.out.println(objective.toString() + ", a été réalisé");
                listOfObjectifDone.add(objective);
            }
        }
        ArrayList<Objective> listOfAllObjectivesFromABot = bot.getObjectives();
        listOfAllObjectivesFromABot.removeAll(listOfObjectifDone);
        bot.setObjectives(listOfAllObjectivesFromABot);
    }


    public boolean checkOneObjective(Objective objective, Bot bot){
        return switch(objective.getType()) {
            case PARCELLE -> checkParcelleObjectives(objective);
            case JARDINIER -> checkJardinierObjectives(objective);
            case PANDA -> checkPandaObjectives(objective, bot);
        };

    }

    public boolean checkPandaObjectives(Objective objective, Bot bot) {
        boolean isDone = false;
        try{
            if (objective.getPattern().getNbBambou() == 3){
                if (bot.getBambooEaten().get(Color.Jaune)>=1 &&
                        bot.getBambooEaten().get(Color.Vert)>=1 &&
                        bot.getBambooEaten().get(Color.Rouge)>=1){
                    bot.deleteBambooEaten(new ArrayList<>(Arrays.asList(Color.Vert,Color.Jaune,Color.Rouge)));
                    isDone = true;
                }
            } else {
                if (bot.getBambooEaten().get(objective.getColors().get(0))>=2){
                    bot.deleteBambooEaten(new ArrayList<>(Arrays.asList(objective.getColors().get(0),objective.getColors().get(0))));
                    isDone = true;
                }
            }
        } catch (DeletingBotBambooException e) {
            System.err.println("\n  -> An error has occurred : " + e.getErrorTitle() + "\n");
        }
        return isDone;
    }

    public boolean checkJardinierObjectives(Objective objective) {
        ArrayList<Integer> listOfIdAvailable = new ArrayList<>();
        if(objective.getPattern().getSpecial() == null){
            listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(),Optional.of(new ArrayList<>(Arrays.asList(objective.getPattern().getHauteurBambou()))),Optional.empty());
        }
        else {
            listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(), Optional.of(new ArrayList<>(Arrays.asList(objective.getPattern().getHauteurBambou()))), Optional.of(new ArrayList<>(Arrays.asList(objective.getPattern().getSpecial()))));
        }
        return listOfIdAvailable.size() == objective.getPattern().getNbBambou();
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
             System.out.println( bot1.getName() + " a gagné avec " + bot1.getScore() + " points !");
        } else if (i < 0) {
             System.out.println(bot2.getName() + " a gagné avec " + bot2.getScore() + " points !");
         } else {
             System.out.println("Egalité !");
         }
    }

    public boolean checkIfBotCanDrawAnObjective(Bot bot){
        return bot.getObjectives().size() < 5;
    }

    /**
     * int[] numberOfTypeObjectiveDone : - numberOfTypeObjectiveDone[0] -> PARCELLE
     *                                   - numberOfTypeObjectiveDone[1] -> JARDINIER
     *                                   - numberOfTypeObjectiveDone[2] -> PANDA
     *
     * This method checks all the drawable Objectives and counts the amount of Objectives currently done,
     * and increments the array values associated to the TypeObjective of the objectives done.
     * It returns the TypeObjective that is the most done when all the drawable Objectives are checked.
     */
    public TypeObjective chooseTypeObjectiveByCheckingUnknownObjectives(Bot bot){
        int[] numberOfTypeObjectiveDone = new int[NB_LISTES_OBJECTIVES];
        int[] moyennePointsObjectives = new int[NB_LISTES_OBJECTIVES];
        int sizeParcellle = this.getParcelleObjectifs().size();
        int sizeJardinier = this.getJardinierObjectifs().size();
        int sizePanda = this.getPandaObjectifs().size();
        ArrayList<Objective> listOfAllObjectivesDrawable = new ArrayList<>();
        listOfAllObjectivesDrawable.addAll(this.getParcelleObjectifs());
        listOfAllObjectivesDrawable.addAll(this.getJardinierObjectifs());
        listOfAllObjectivesDrawable.addAll(this.getPandaObjectifs());
        int n = listOfAllObjectivesDrawable.size();
        for(Objective objective : listOfAllObjectivesDrawable){
            if(checkOneObjective(objective,bot)){
                switch (objective.getType()){
                    case PARCELLE -> {numberOfTypeObjectiveDone[0]++;moyennePointsObjectives[0]+= objective.getValue();}
                    case JARDINIER -> {numberOfTypeObjectiveDone[1]++;moyennePointsObjectives[1]+= objective.getValue();}
                    case PANDA ->  {numberOfTypeObjectiveDone[2]++;moyennePointsObjectives[2]+= objective.getValue();}
                }
            }
        }
        if(sizeParcellle != 0) {
            if(numberOfTypeObjectiveDone[0] != 0){
                moyennePointsObjectives[0] /= numberOfTypeObjectiveDone[0];
            }
            numberOfTypeObjectiveDone[0] /= sizeParcellle;
        }
        if(sizeJardinier != 0) {
            if(numberOfTypeObjectiveDone[1] != 0){
                moyennePointsObjectives[1] /= numberOfTypeObjectiveDone[1];
            }
            numberOfTypeObjectiveDone[1] /= sizeJardinier;
        }
        if(sizePanda != 0) {
            if(numberOfTypeObjectiveDone[2] != 0){
                moyennePointsObjectives[2] /= numberOfTypeObjectiveDone[2];
            }
            numberOfTypeObjectiveDone[2] /= sizePanda;
        }
        ArrayList<Integer> indices = indiceMax(numberOfTypeObjectiveDone);
        if(indices.size() == 1){
            return switch (indices.get(0)){
                case 0 -> TypeObjective.PARCELLE;
                case 1 -> TypeObjective.JARDINIER;
                default -> TypeObjective.PANDA;
            };
        }
        else{
            ArrayList<Integer> indicesMoyenne = indiceMax(moyennePointsObjectives);
            return switch (indicesMoyenne.get(0)){
                case 0 -> TypeObjective.PARCELLE;
                case 1 -> TypeObjective.JARDINIER;
                default -> TypeObjective.PANDA;
            };
        }
    }

    public ArrayList<Integer> indiceMax(int[] array){
        int max=0;
        ArrayList<Integer> res = new ArrayList<>();
        res.add(max);
        for(int i=0;i< array.length;i++){
            if(array[i]>max){
                max = array[i];
                res.removeAll(res);
                res.add(i);
            }
            if(array[i] == max){
                res.add(i);
            }
        }
        return res;
    }
    public TypeObjective mostPresentTypeObjectiveAvailableToDraw(){
        int[] numberOfTypeObjectiveAvailable = new int[NB_LISTES_OBJECTIVES];
        numberOfTypeObjectiveAvailable[0] = this.getParcelleObjectifs().size();
        numberOfTypeObjectiveAvailable[1] = this.getJardinierObjectifs().size();
        numberOfTypeObjectiveAvailable[2] = this.getPandaObjectifs().size();
        ArrayList<Integer> indices = indiceMax(numberOfTypeObjectiveAvailable);
        if(numberOfTypeObjectiveAvailable[indices.get(0)] !=0){
            return switch (indices.get(0)){
                case 0 -> TypeObjective.PARCELLE;
                case 1 -> TypeObjective.JARDINIER;
                default -> TypeObjective.PANDA;
            };
        }
        return null;
    }
}
