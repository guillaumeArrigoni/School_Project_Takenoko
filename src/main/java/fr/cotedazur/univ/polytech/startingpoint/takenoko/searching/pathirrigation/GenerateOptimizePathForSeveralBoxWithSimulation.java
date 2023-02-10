package fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.pathirrigation;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.combination.Combination;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.combination.CombinationSortedOf_P_ElementAmong_N;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class GenerateOptimizePathForSeveralBoxWithSimulation {

    private ArrayList<Combination<HexagoneBoxPlaced>> listCombination;
    private BoardSimulation boardSimulation;
    private CrestGestionnarySimulation crestGestionnarySimulation;
    private Combination<HexagoneBoxPlaced> bestCombination;
    private ArrayList<Crest> chosenPath = new ArrayList<>();
    private int nbTour;



    /**
     * Method use to get the best Combination with the shortest path
     * @param listBox : the list of box that have to be irrigated
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    public GenerateOptimizePathForSeveralBoxWithSimulation(ArrayList<HexagoneBoxPlaced> listBox) throws CrestNotRegistered, CloneNotSupportedException {
        this.listCombination = new CombinationSortedOf_P_ElementAmong_N(listBox,listBox.size()).getListOfCombination();
        GenerateBestPath();
    }



    public Combination<HexagoneBoxPlaced> getBestCombination() {
        return bestCombination;
    }

    public ArrayList<Crest> getChosenPath() {
        return chosenPath;
    }

    public int getNbTour() {
        return nbTour;
    }


    /**
     * Method use to set up the Simulation in order to generate the path
     * @param box any box given at the initialization of the class
     */
    private void setupNewSimulation(HexagoneBoxPlaced box){
        this.boardSimulation = new BoardSimulation(box.getBoard());
        this.crestGestionnarySimulation = boardSimulation.getCrestGestionnary();
    }

    /**
     * Method use to generate the path
     * Will try all the combination sorted available that contain all the box given at the initialization of the class
     * Will generate a simulation in order to generate the path for each of them
     * Will retain only the best path and thus the best combination
     *      <=> (the best order of the box)
     *      <=> Irrigate the first one, then the second..
     *      <=> For instance it can be easier to irrigate the second box starting from the first box
     *              and not from the irrigation placed in the board
     * @throws CrestNotRegistered
     * @throws CloneNotSupportedException
     */
    private void GenerateBestPath() throws CrestNotRegistered, CloneNotSupportedException {
        int nbTour = -1;
        Combination<HexagoneBoxPlaced> chosenCombination = new Combination<>();
        ArrayList<Crest> chosenPath = new ArrayList<>();
        for (Combination<HexagoneBoxPlaced> combination :  this.listCombination){
            int count = 0;
            setupNewSimulation(combination.getListOfElementInTheCombination().get(0));
            ArrayList<Crest> listCrestCandidate = new ArrayList<>();
            for (HexagoneBoxPlaced box : combination.getListOfElementInTheCombination()){
                GenerateAWayToIrrigateTheBox generateAWayToIrrigateTheBox = new GenerateAWayToIrrigateTheBox(box, this.boardSimulation);
                ArrayList<ArrayList<Crest>> pathToIrrigateTheBox = (ArrayList<ArrayList<Crest>>) generateAWayToIrrigateTheBox.getPathToIrrigation().clone();
                for (int i=0;i<pathToIrrigateTheBox.size();i++){
                    Crest crest = pathToIrrigateTheBox.get(i).get(0);
                    if (!boardSimulation.getCrestGestionnary().getListOfCrestIrrigated().contains(crest)){
                        boardSimulation.placeIrrigation(crest);
                        count = count + 1;
                        listCrestCandidate.add(pathToIrrigateTheBox.get(i).get(0));
                        LinkedHashSet<Crest> set = new LinkedHashSet<>(listCrestCandidate);
                        listCrestCandidate.clear();
                        listCrestCandidate.addAll(set);
                    }
                }
            }
            if (nbTour == -1 || nbTour>count){
                nbTour = count;
                chosenCombination = combination.clone();
                chosenPath.addAll((ArrayList<Crest>) listCrestCandidate.clone());
            }
        }
        this.chosenPath = chosenPath;
        this.bestCombination = chosenCombination;
        this.nbTour = nbTour;
    }
}

