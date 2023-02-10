package fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotSimulator;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.DeletingBotBambooException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.Bot;

import java.util.*;

public class GestionObjectives {

    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private final Board board;
    private ArrayList<ObjectiveParcelle> parcelleObjectives;
    private ArrayList<ObjectiveJardinier> jardinierObjectives;
    private ArrayList<ObjectivePanda> pandaObjectives;
    private static final int NB_LISTES_OBJECTIVES = 3;
    private boolean aBotHasEnoughObjectivesDone;
    private final LoggerError loggerError;

    PatternParcelle poserTriangle = new PatternParcelle("TRIANGLE");
    PatternParcelle poserLigne = new PatternParcelle("LIGNE");
    PatternParcelle poserCourbe = new PatternParcelle("COURBE");
    PatternParcelle poserLosange = new PatternParcelle("LOSANGE");
    PatternJardinier planterSurSourceEau = new PatternJardinier(1,4, Special.SOURCE_EAU);
    PatternJardinier planterSurEngrais = new PatternJardinier(1,4,Special.ENGRAIS);
    PatternJardinier planterSurProteger = new PatternJardinier(1,4,Special.PROTEGER);
    PatternJardinier planterSurClassique = new PatternJardinier(1,4,Special.CLASSIQUE);
    PatternJardinier planterDeuxRouges = new PatternJardinier(2,3,null);
    PatternJardinier planterTroisJaunes = new PatternJardinier(3,3,null);
    PatternJardinier planterQuatreVerts = new PatternJardinier(4,3,null);
    PatternPanda mangerDeuxBambous = new PatternPanda(2,1,null);
    PatternPanda mangerTroisBambous = new PatternPanda(3,1,null);
    ObjectiveParcelle poserTriangleVert = new ObjectiveParcelle("POSER_TRIANGLE_VERT",2, poserTriangle, new ArrayList<>(List.of(Color.VERT)));
    ObjectiveParcelle poserTriangleJaune = new ObjectiveParcelle("POSER_TRIANGLE_JAUNE",3, poserTriangle, new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveParcelle poserTriangleRouge = new ObjectiveParcelle("POSER_TRIANGLE_ROUGE",4, poserTriangle, new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveParcelle poserLigneVerte = new ObjectiveParcelle("POSER_LIGNE_VERTE",2, poserLigne, new ArrayList<>(List.of(Color.VERT)));
    ObjectiveParcelle poserLigneJaune = new ObjectiveParcelle("POSER_LIGNE_JAUNE",3, poserLigne, new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveParcelle poserLigneRouge = new ObjectiveParcelle("POSER_LIGNE_ROUGE",4, poserLigne, new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveParcelle poserCourbeVerte = new ObjectiveParcelle("POSER_COURBE_VERTE",2, poserCourbe, new ArrayList<>(List.of(Color.VERT)));
    ObjectiveParcelle poserCourbeJaune = new ObjectiveParcelle("POSER_COURBE_JAUNE",3, poserCourbe, new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveParcelle poserCourbeRouge = new ObjectiveParcelle("POSER_COURBE_ROUGE",4, poserCourbe, new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveParcelle poserLosangeVert = new ObjectiveParcelle("POSER_LOSANGE_VERT",3, poserLosange, new ArrayList<>(List.of(Color.VERT)));
    ObjectiveParcelle poserLosangeJaune = new ObjectiveParcelle("POSER_LOSANGE_JAUNE",4, poserLosange, new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveParcelle poserLosangeRouge = new ObjectiveParcelle("POSER_LOSANGE_ROUGE",5, poserLosange, new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveParcelle poserLosangeVertJaune = new ObjectiveParcelle("POSER_LOSANGE_VERT_JAUNE",3, poserLosange, new ArrayList<>(Arrays.asList( Color.VERT,Color.JAUNE)));
    ObjectiveParcelle poserLosangeVertRouge = new ObjectiveParcelle("POSER_LOSANGE_VERT_ROUGE",4, poserLosange, new ArrayList<>(Arrays.asList( Color.VERT,Color.ROUGE)));
    ObjectiveParcelle poserLosangeRougeJaune = new ObjectiveParcelle("POSER_LOSANGE_ROUGE_JAUNE",5, poserLosange, new ArrayList<>(Arrays.asList( Color.ROUGE,Color.JAUNE)));
    ObjectiveJardinier planterSurSourceEauBambouVert = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_VERT",4, planterSurSourceEau,new ArrayList<>(List.of(Color.VERT)));
    ObjectiveJardinier planterSurSourceEauBambouJaune = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_JAUNE",4, planterSurSourceEau,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveJardinier planterSurSourceEauBambouRouge = new ObjectiveJardinier("PLANTER_SUR_SOURCE_EAU_BAMBOU_ROUGE",4, planterSurSourceEau,new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveJardinier planterSurEngraisBambouVert = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_VERT",3, planterSurEngrais,new ArrayList<>(List.of(Color.VERT)));
    ObjectiveJardinier planterSurEngraisBambouJaune = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE",4, planterSurEngrais,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveJardinier planterSurEngraisBambouRouge = new ObjectiveJardinier("PLANTER_SUR_ENGRAIS_BAMBOU_ROUGE",5, planterSurEngrais,new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveJardinier planterSurProtegerBambouVert = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_VERT",4, planterSurProteger,new ArrayList<>(List.of(Color.VERT)));
    ObjectiveJardinier planterSurProtegerBambouJaune = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_JAUNE",5, planterSurProteger,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveJardinier planterSurProtegerBambouRouge = new ObjectiveJardinier("PLANTER_SUR_PROTEGER_BAMBOU_ROUGE",6, planterSurProteger,new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveJardinier planterSurClassiqueBambouVert = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_VERT",5, planterSurClassique,new ArrayList<>(List.of(Color.VERT)));
    ObjectiveJardinier planterSurClassiqueBambouJaune = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE",6, planterSurClassique,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveJardinier planterSurClassiqueBambouRouge = new ObjectiveJardinier("PLANTER_SUR_CLASSIQUE_BAMBOU_ROUGE",7, planterSurClassique,new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveJardinier planterDeuxBambousRouges = new ObjectiveJardinier("PLANTER_DEUX_BAMBOUS_ROUGES",6, planterDeuxRouges,new ArrayList<>(List.of(Color.ROUGE)));
    ObjectiveJardinier planterTroisBambousJaunes = new ObjectiveJardinier("PLANTER_TROIS_BAMBOUS_JAUNES",7, planterTroisJaunes,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectiveJardinier planterQuatreBambousVerts = new ObjectiveJardinier("PLANTER_QUATRE_BAMBOUS_VERTS",8, planterQuatreVerts,new ArrayList<>(List.of(Color.VERT)));
    ObjectivePanda mangerDeuxVerts1 = new ObjectivePanda("MANGER_DEUX_VERTS_1",3, mangerDeuxBambous,new ArrayList<>(List.of(Color.VERT)));
    ObjectivePanda mangerDeuxVerts2 = new ObjectivePanda("MANGER_DEUX_VERTS_2",3, mangerDeuxBambous,new ArrayList<>(List.of(Color.VERT)));
    ObjectivePanda mangerDeuxVerts3 = new ObjectivePanda("MANGER_DEUX_VERTS_3",3, mangerDeuxBambous,new ArrayList<>(List.of(Color.VERT)));
    ObjectivePanda mangerDeuxVerts4 = new ObjectivePanda("MANGER_DEUX_VERTS_4",3, mangerDeuxBambous,new ArrayList<>(List.of(Color.VERT)));
    ObjectivePanda mangerDeuxVerts5 = new ObjectivePanda("MANGER_DEUX_VERTS_5",3, mangerDeuxBambous,new ArrayList<>(List.of(Color.VERT)));
    ObjectivePanda mangerDeuxJaunes1 = new ObjectivePanda("MANGER_DEUX_JAUNES_1",4, mangerDeuxBambous,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectivePanda mangerDeuxJaunes2 = new ObjectivePanda("MANGER_DEUX_JAUNES_2",4, mangerDeuxBambous,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectivePanda mangerDeuxJaunes3 = new ObjectivePanda("MANGER_DEUX_JAUNES_3",4, mangerDeuxBambous,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectivePanda mangerDeuxJaunes4 = new ObjectivePanda("MANGER_DEUX_JAUNES_4",4, mangerDeuxBambous,new ArrayList<>(List.of(Color.JAUNE)));
    ObjectivePanda mangerDeuxRouges1 = new ObjectivePanda("MANGER_DEUX_ROUGES_1",5, mangerDeuxBambous,new ArrayList<>(List.of(Color.ROUGE)));
    ObjectivePanda mangerDeuxRouges2 = new ObjectivePanda("MANGER_DEUX_ROUGES_2",5, mangerDeuxBambous,new ArrayList<>(List.of(Color.ROUGE)));
    ObjectivePanda mangerDeuxRouges3 = new ObjectivePanda("MANGER_DEUX_ROUGES_3",5, mangerDeuxBambous,new ArrayList<>(List.of(Color.ROUGE)));
    ObjectivePanda mangerTricolore1 = new ObjectivePanda("MANGER_TRICOLORE_1",6, mangerTroisBambous,new ArrayList<>(Arrays.asList(Color.VERT,Color.JAUNE,Color.ROUGE)));
    ObjectivePanda mangerTricolore2 = new ObjectivePanda("MANGER_TRICOLORE_2",6, mangerTroisBambous,new ArrayList<>(Arrays.asList(Color.VERT,Color.JAUNE,Color.ROUGE)));
    ObjectivePanda mangerTricolore3 = new ObjectivePanda("MANGER_TRICOLORE_3",6, mangerTroisBambous,new ArrayList<>(Arrays.asList(Color.VERT,Color.JAUNE,Color.ROUGE)));

    /**
     * This Constructor creates an instance witch contains the different ArrayLists of Objective.
     * This instance rolls the Objectives to draw for the bots, and checks if the Objectives are achieved.
     */
    public GestionObjectives(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, LoggerError loggerError){
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.board = board;
        this.parcelleObjectives = new ArrayList<>();
        this.jardinierObjectives = new ArrayList<>();
        this.pandaObjectives = new ArrayList<>();
        this.aBotHasEnoughObjectivesDone = false;
        this.loggerError = loggerError;
    }

    /**
     * This method creates the ArrayList of ObjectiveParcelle by default, using all the objectives with the type PARCELLE.
     * @return An ArrayList with all the ObjectiveParcelle.
     */
    public ArrayList<ObjectiveParcelle> listOfObjectiveParcelleByDefault(){
        return new ArrayList<>(Arrays.asList(
                poserTriangleVert,
                poserTriangleJaune,
                poserTriangleRouge,
                poserLigneVerte,
                poserLigneJaune,
                poserLigneRouge,
                poserCourbeVerte,
                poserCourbeJaune,
                poserCourbeRouge,
                poserLosangeVert,
                poserLosangeJaune,
                poserLosangeRouge,
                poserLosangeVertRouge,
                poserLosangeVertJaune,
                poserLosangeRougeJaune));
    }

    /**
     * This method creates the ArrayList of ObjectiveJardinier by default, using all the objectives with the type JARDINIER.
     * @return An ArrayList with all the ObjectiveJardinier.
     */
    public ArrayList<ObjectiveJardinier> listOfObjectiveJardinierByDefault(){
        return new ArrayList<>(Arrays.asList(
                planterSurSourceEauBambouVert,
                planterSurSourceEauBambouJaune,
                planterSurSourceEauBambouRouge,
                planterSurEngraisBambouVert,
                planterSurEngraisBambouJaune,
                planterSurEngraisBambouRouge,
                planterSurProtegerBambouVert,
                planterSurProtegerBambouJaune,
                planterSurProtegerBambouRouge,
                planterSurClassiqueBambouVert,
                planterSurClassiqueBambouJaune,
                planterSurClassiqueBambouRouge,
                planterDeuxBambousRouges,
                planterTroisBambousJaunes,
                planterQuatreBambousVerts));
    }

    /**
     * This method creates the ArrayList of ObjectivePanda by default, using all the objectives with the type PANDA.
     * @return An ArrayList with all the ObjectivePanda.
     */
    public ArrayList<ObjectivePanda> listOfObjectivePandaByDefault(){
        return new ArrayList<>(Arrays.asList(
                mangerDeuxVerts1,
                mangerDeuxVerts2,
                mangerDeuxVerts3,
                mangerDeuxVerts4,
                mangerDeuxVerts5,
                mangerDeuxJaunes1,
                mangerDeuxJaunes2,
                mangerDeuxJaunes3,
                mangerDeuxJaunes4,
                mangerDeuxRouges1,
                mangerDeuxRouges2,
                mangerDeuxRouges3,
                mangerTricolore1,
                mangerTricolore2,
                mangerTricolore3
        ));
    }

    /**
     * This method creates a copy of the instance of GestionObjectives.
     * @return a new instance of GestionObjectives witch is a copy of the instance this method is applied to.
     */
    public GestionObjectives copy(Board board, RetrieveBoxIdWithParameters retrieveBoxIdWithParameters){
        GestionObjectives gestionObjectives = new GestionObjectives(board, retrieveBoxIdWithParameters, this.loggerError);
        gestionObjectives.parcelleObjectives = new ArrayList<>(this.parcelleObjectives);
        gestionObjectives.jardinierObjectives = new ArrayList<>(this.jardinierObjectives);
        gestionObjectives.pandaObjectives = new ArrayList<>(this.pandaObjectives);
        return gestionObjectives;
    }

    /**
     * @param objectiveParcelleArrayList is the Arraylist of the Objectives with the type PARCELLE that will be used for the game.
     * @param objectiveJardinierArrayList is the Arraylist of the Objectives with the type JARDINIER that will be used for the game.
     * @param objectivePandaArrayList is the Arraylist of the Objectives with the type PANDA that will be used for the game.
     * This method initializes the fields of the instance corresponding to the different Arraylists of Objectives.
     */
    public void initialize(ArrayList<ObjectiveParcelle> objectiveParcelleArrayList,ArrayList<ObjectiveJardinier> objectiveJardinierArrayList, ArrayList<ObjectivePanda> objectivePandaArrayList) {
        this.parcelleObjectives = objectiveParcelleArrayList;
        this.jardinierObjectives = objectiveJardinierArrayList;
        this.pandaObjectives = objectivePandaArrayList;
    }

    /**
     * This method is a getter for the field ParcelleObjectives.
     * @return an ArrayList witch correspond to the field ParcelleObjectives.
     */
    public ArrayList<ObjectiveParcelle> getParcelleObjectives() {
        return parcelleObjectives;
    }

    /**
     * This method is a getter for the field JardinierObjectives.
     * @return an ArrayList witch correspond to the field JardinierObjectives.
     */
    public ArrayList<ObjectiveJardinier> getJardinierObjectives() {
        return jardinierObjectives;
    }

    /**
     * This method is a getter for the field PandaObjectives.
     * @return an ArrayList witch correspond to the field PandaObjectives.
     */
    public ArrayList<ObjectivePanda> getPandaObjectives() {
        return pandaObjectives;
    }

    /**
     * This method is a getter for the field ABotHasEnoughObjectivesDone.
     * @return a boolean witch correspond to the field ABotHasEnoughObjectivesDone.
     */
    public boolean doesABotHaveEnoughObjectivesDone() {
        return aBotHasEnoughObjectivesDone;
    }

    /**
     * @param bot corresponds to the bot who rolls the objective to draw.
     * This method rolls (random) an objective among the objectives available with the TypeObjective chosen by the bot.
     */

    public void rollObjective(Bot bot, String arg, int id){
        TypeObjective typeObjective = bot.choseTypeObjectiveToRoll(id);
        switch (typeObjective){
        case PARCELLE -> rollParcelleObjective(bot);
        case JARDINIER -> rollJardinierObjective(bot);
        case PANDA -> rollPandaObjective(bot);
        }
    }

    /**
     * @param bot corresponds to the bot who rolls the objective to draw.
     *            This method rolls (random) an objective among the objectives available with the TypeObjective PARCELLE.
     */
    public void rollParcelleObjective(Bot bot){
        int i = new Random().nextInt(0, getParcelleObjectives().size());
        Objective objective = this.getParcelleObjectives().get(i);
        this.getParcelleObjectives().remove(i);
        bot.getObjectives().add(objective);
        displayPickObj(bot,objective);
    }

    /**
     * @param bot corresponds to the bot who rolls the objective to draw.
     *            This method rolls (random) an objective among the objectives available with the TypeObjective JARDINIER.
     */
    public void rollJardinierObjective(Bot bot){
        int i = new Random().nextInt(0, getJardinierObjectives().size());
        Objective objective = this.getJardinierObjectives().get(i);
        this.getJardinierObjectives().remove(i);
        bot.getObjectives().add(objective);
        displayPickObj(bot,objective);
    }

    /**
     * @param bot corresponds to the bot who rolls the objective to draw.
     *            This method rolls (random) an objective among the objectives available with the TypeObjective PANDA.
     */
    public void rollPandaObjective(Bot bot){
        int i = new Random().nextInt(0, getPandaObjectives().size());
        Objective objective = this.getPandaObjectives().get(i);
        this.getPandaObjectives().remove(i);
        bot.getObjectives().add(objective);
        displayPickObj(bot,objective);
    }

    private void displayPickObj(Bot bot, Objective objective){
        if (!(bot instanceof BotSimulator)) {
            bot.getLogInfoDemo().displayPickObj(bot.getName(),objective);
        }
    }

    /**
     * This method checks all the objectives of the bots, and gives him the points if they are achieved.
     * If an objective is achieved, it will be removed from the bot's ArrayList of objective.
     * @param bot corresponds to the bot to whom the objectives are checked.
     * @param sizePlayerList corresponds to the number of players in the game.
     */
    public void checkObjectives(Bot bot, String arg,int sizePlayerList){
        ArrayList<Objective> listOfObjectifDone = new ArrayList<>();
        for(Objective objective : bot.getObjectives()){
            if(checkOneObjective(objective, bot)){
                bot.addScore(objective);
                bot.incrementNumberObjectiveDone();
                addPointsIfEnoughObjectivesDone(bot, sizePlayerList);
                if(objective.getType() == TypeObjective.PANDA){
                    bot.addScorePanda(objective);
                }
                if (!(bot instanceof BotSimulator)) {
                    bot.getLogInfoDemo().displayObjFinish(objective);
                }
                listOfObjectifDone.add(objective);
            }
        }
        List<Objective> listOfAllObjectivesFromABot = bot.getObjectives();
        listOfAllObjectivesFromABot.removeAll(listOfObjectifDone);
        bot.setObjectives(listOfAllObjectivesFromABot);
    }

    /**
     * @param sizePlayerList sizePlayerList corresponds to the number of players in the game.
     * @return the necessary amount of objectives that have to be achieved in order to start the last turn,
     * witch depends on the number of players in the game.
     */
    public int getNumberOfObjectivesDoneToStartLastTurn(int sizePlayerList) {
        return switch (sizePlayerList){
            case 2 -> 9;
            case 3 -> 8;
            default -> 7;
        };
    }

    /**
     * If the bot is the first to achieve the necessary amount of objectives to start the last turn,
     * the Emperor rewards him and the bot wins 2 points.
     * @param bot corresponds to the bot to whom the objectives are checked.
     * @param sizePlayerList corresponds to the number of players in the game.
     */
    public void addPointsIfEnoughObjectivesDone(Bot bot, int sizePlayerList){
        if(bot.getNumberObjectiveDone() >= this.getNumberOfObjectivesDoneToStartLastTurn(sizePlayerList) && !this.aBotHasEnoughObjectivesDone){
            bot.setScore(bot.getScore() + 2);
            this.aBotHasEnoughObjectivesDone = true;
        }
    }

    /**
     * @param objective corresponds to the objective that is being checked.
     * @param bot corresponds to the bot to whom the objectives are checked.
     * @return a boolean corresponding to if the objective is achieved or not.
     */
    public boolean checkOneObjective(Objective objective, Bot bot){
        return switch(objective.getType()) {
            case PARCELLE -> checkParcelleObjectives(objective);
            case JARDINIER -> checkJardinierObjectives(objective);
            case PANDA -> checkPandaObjectives(objective, bot);
        };

    }

    /**
     * @param objective corresponds to the objective that is being checked.
     * @param bot corresponds to the bot to whom the objectives are checked.
     * @return a boolean corresponding to if the objectivePanda is achieved or not.
     */
    public boolean checkPandaObjectives(Objective objective, Bot bot) {
        boolean isDone = false;
        try{
            if (objective.getPattern().getNbBambou() == 3){
                if (bot.getBambooEaten().get(Color.JAUNE)>=1 &&
                        bot.getBambooEaten().get(Color.VERT)>=1 &&
                        bot.getBambooEaten().get(Color.ROUGE)>=1){
                    bot.deleteBambooEaten(new ArrayList<>(Arrays.asList(Color.VERT,Color.JAUNE,Color.ROUGE)));
                    isDone = true;
                }
            } else {
                if (bot.getBambooEaten().get(objective.getColors().get(0))>=2){
                    bot.deleteBambooEaten(new ArrayList<>(Arrays.asList(objective.getColors().get(0),objective.getColors().get(0))));
                    isDone = true;
                }
            }
        } catch (DeletingBotBambooException e) {
            loggerError.logErrorTitle(e);
        }
        return isDone;
    }

    /**
     * @param objective corresponds to the objective that is being checked.
     * @return a boolean corresponding to if the objectiveJardinier is achieved or not.
     */
    public boolean checkJardinierObjectives(Objective objective) {
        ArrayList<Integer> listOfIdAvailable;
        if(objective.getPattern().getSpecial() == null){
            listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(),Optional.of(new ArrayList<>(List.of(objective.getPattern().getHauteurBambou()))),Optional.empty());
        }
        else {
            listOfIdAvailable = retrieveBoxIdWithParameters.getAllIdThatCompleteCondition(Optional.of(objective.getColors()), Optional.empty(), Optional.of(new ArrayList<>(List.of(objective.getPattern().getHauteurBambou()))), Optional.of(new ArrayList<>(Collections.singletonList(objective.getPattern().getSpecial()))));
        }
        return listOfIdAvailable.size() == objective.getPattern().getNbBambou();
    }

    /**
     * @param objective corresponds to the objective that is being checked.
     * @return a boolean corresponding to if the objectiveParcelle is achieved or not.
     */
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
            HexagoneBoxPlaced box = board.getPlacedBox().get(listOfIdAvailable.get(i));
            if(box == null) return false;
            ArrayList<Integer> idOfAdjacentBoxCorrect = new ArrayList<>();
            for (int j=1;j<box.getAdjacentBox().keySet().size()+1;j++){
                if (listOfIdAvailable.contains(HexagoneBox.generateID(box.getAdjacentBox().get(j)))){
                    idOfAdjacentBoxCorrect.add(j);
                }
            }
            if (parcelleLosangeObjectiveCondition(box, idOfAdjacentBoxCorrect)) return true;
        }
        return false;
    }

    /**
     * Method with the rhombus parcel condition
     * @param box which we want to know if, with the adjacent box, the rhombus parcel is completed
     * @param idOfAdjacentBoxCorrect contains all the adjacent box of the previous box that complete the objective condition (color, irrigated ...)
     * @return true if the rhombus parcel is completed, false if it does not
     */
    private boolean parcelleLosangeObjectiveCondition(HexagoneBoxPlaced box, ArrayList<Integer> idOfAdjacentBoxCorrect) {
        for (int j = 0; j< idOfAdjacentBoxCorrect.size(); j++){
            int adjIndice1 = (idOfAdjacentBoxCorrect.get(j)+1)%7;
            int adjIndice2 = (idOfAdjacentBoxCorrect.get(j)+2)%7;
            if (adjIndice1 == 0) adjIndice1 = 1;
            if (adjIndice2 == 0) adjIndice2 = 1;
            if (board.isCoordinateInBoard(box.getAdjacentBox().get(adjIndice1)) &&
                    board.isCoordinateInBoard(box.getAdjacentBox().get(adjIndice2)) &&
                    idOfAdjacentBoxCorrect.contains(adjIndice1) &&
                    board.getBoxWithCoordinates(box.getAdjacentBox().get(adjIndice1)).getColor() == box.getColor() &&
                    idOfAdjacentBoxCorrect.contains(adjIndice2) &&
                    board.getBoxWithCoordinates(box.getAdjacentBox().get(adjIndice2)).getColor() == box.getColor()){
                return true;
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
            if (parcelleObjectiveCondition(idOfAdjacentBoxCorrect, x)) return true;
        }
        return false;
    }


    /**
     * Method with the triangle, line and curve condition
     * @param listOfIdAvailable contains all the box placed in the board that complete the requirement of the objective (color, irrigated,...)
     * @param i that have the value as before
     * @return true if the parcel is completed, false if it does not
     */
    private ArrayList<Integer> getAllAdjacentBoxThatCompleteTheCondition(ArrayList<Integer> listOfIdAvailable, int i) {
        HexagoneBoxPlaced box = board.getPlacedBox().get(listOfIdAvailable.get(i));
        if(box == null) return new ArrayList<>();
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
     * @param idOfAdjacentBoxCorrect contains all the box that filled the objective's requirement and are adjacent of another box that also complete the objective's requirement
     * @param x that have the same value as before
     * @return true if the objective is completed or false if it does not.
     */
    private boolean parcelleObjectiveCondition(ArrayList<Integer> idOfAdjacentBoxCorrect, int x) {
        for (int j = 0; j< idOfAdjacentBoxCorrect.size(); j++){
            int adjIndice = idOfAdjacentBoxCorrect.get(j)+ x;
            if (adjIndice > 6) adjIndice = adjIndice - 6;
            if (idOfAdjacentBoxCorrect.contains(adjIndice)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param bots corresponds to the list of bot playing the game.
     * @return a list of bot who win the game.
     */
    public List<Bot> getWinner(List<Bot> bots){
        int[] scores = new int[bots.size()];
        for(int i=0; i<bots.size(); i++){
            scores[i] = bots.get(i).getScore();
        }
        ArrayList<Integer> indicesBestScore = indiceMax(scores);
        List<Bot> botWinnerList = new ArrayList<>();
        if(indicesBestScore.size() == 1){
            botWinnerList.add(bots.get(indicesBestScore.get(0)));
        }
        else{
            if(indicesBestScore.size() > 1){
                int[] scoresPanda = new int[indicesBestScore.size()];
                for(int i=0; i<indicesBestScore.size();i++){
                    scoresPanda[i] = bots.get(indicesBestScore.get(i)).getScorePanda();
                }
                ArrayList<Integer> indicesBestScorePanda = indiceMax(scoresPanda);
                for (Integer integer : indicesBestScorePanda) {
                    botWinnerList.add(bots.get(indicesBestScore.get(integer)));
                }
            }
        }
        return botWinnerList;
    }

    /**
     * @param bot corresponds to the bot who wants to draw an objective.
     * @return a boolean corresponding to if the bot can draw an objective or if he has already too many objectives.
     */
    public boolean checkIfBotCanDrawAnObjective(Bot bot){
        return bot.getObjectives().size() < 5;
    }

    /**
     * int[] numberOfTypeObjectiveDone : - numberOfTypeObjectiveDone[0] -> PARCELLE
     *                                   - numberOfTypeObjectiveDone[1] -> JARDINIER
     *                                   - numberOfTypeObjectiveDone[2] -> PANDA
     * This method checks all the drawable Objectives and counts the amount of Objectives currently done,
     * and increments the array values associated to the TypeObjective of the objectives done.
     * It returns the TypeObjective that is the most done when all the drawable Objectives are checked.
     */
    public TypeObjective chooseTypeObjectiveByCheckingUnknownObjectives(Bot bot){
        int[] numberOfTypeObjectiveDone = new int[NB_LISTES_OBJECTIVES];
        int[] moyennePointsObjectives = new int[NB_LISTES_OBJECTIVES];
        int sizeParcelle = this.getParcelleObjectives().size();
        int sizeJardinier = this.getJardinierObjectives().size();
        int sizePanda = this.getPandaObjectives().size();
        ArrayList<Objective> listOfAllObjectivesDrawable = new ArrayList<>();
        listOfAllObjectivesDrawable.addAll(this.getParcelleObjectives());
        listOfAllObjectivesDrawable.addAll(this.getJardinierObjectives());
        listOfAllObjectivesDrawable.addAll(this.getPandaObjectives());
        for(Objective objective : listOfAllObjectivesDrawable){
            if(checkOneObjective(objective,bot)){
                switch (objective.getType()){
                    case PARCELLE -> {numberOfTypeObjectiveDone[0]++;moyennePointsObjectives[0]+= objective.getValue();}
                    case JARDINIER -> {numberOfTypeObjectiveDone[1]++;moyennePointsObjectives[1]+= objective.getValue();}
                    case PANDA ->  {numberOfTypeObjectiveDone[2]++;moyennePointsObjectives[2]+= objective.getValue();}
                }
            }
        }
        return chooseTypeObjective(numberOfTypeObjectiveDone, moyennePointsObjectives, sizeParcelle, sizeJardinier, sizePanda);
    }

    private TypeObjective chooseTypeObjective(int[] numberOfTypeObjectiveDone, int[] moyennePointsObjectives, int sizeParcelle, int sizeJardinier, int sizePanda) {
        if(sizeParcelle != 0) {
            updateNumberOfTypeObjectiveDone(numberOfTypeObjectiveDone, 0, moyennePointsObjectives, sizeParcelle);
        }
        if(sizeJardinier != 0) {
            updateNumberOfTypeObjectiveDone(numberOfTypeObjectiveDone, 1, moyennePointsObjectives, sizeJardinier);
        }
        if(sizePanda != 0) {
            updateNumberOfTypeObjectiveDone(numberOfTypeObjectiveDone, 2, moyennePointsObjectives, sizePanda);
        }
        ArrayList<Integer> indices = indiceMax(numberOfTypeObjectiveDone);
        if(indices.size() == 1){
            return switch (indices.get(0)) {
                case 0 -> TypeObjective.PARCELLE;
                case 1 -> TypeObjective.JARDINIER;
                default -> TypeObjective.PANDA;
            };
        }
        else{
            ArrayList<Integer> indicesMoyenne = indiceMax(moyennePointsObjectives);
            return switch (indicesMoyenne.get(0)) {
                case 0 -> TypeObjective.PARCELLE;
                case 1 -> TypeObjective.JARDINIER;
                default -> TypeObjective.PANDA;
            };
        }
    }

    private void updateNumberOfTypeObjectiveDone(int[] numberOfTypeObjectiveDone, int x, int[] moyennePointsObjectives, int sizeParcellle) {
        if(numberOfTypeObjectiveDone[x] != 0){
            moyennePointsObjectives[x] /= numberOfTypeObjectiveDone[x];
        }
        numberOfTypeObjectiveDone[x] /= sizeParcellle;
    }

    /**
     * @param array corresponds to an array of int.
     * @return an ArrayList corresponding to the position(s) of the maximum int value in the array.
     */
    public ArrayList<Integer> indiceMax(int[] array){
        int max = array[0];
        ArrayList<Integer> res = new ArrayList<>();
        for(int i=0;i< array.length;i++){
            if(array[i]>max){
                max = array[i];
                res.clear();
                res.add(i);
            }
            else {
                if (array[i] == max) {
                    res.add(i);
                }
            }
        }
        return res;
    }
}
