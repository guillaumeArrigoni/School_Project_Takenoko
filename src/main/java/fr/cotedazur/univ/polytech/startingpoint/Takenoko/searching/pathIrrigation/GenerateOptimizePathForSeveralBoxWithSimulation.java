package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination.Combination;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination.CombinationSortedOf_P_ElementAmong_N;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

public class GenerateOptimizePathForSeveralBoxWithSimulation {

    private ArrayList<Combination<HexagoneBoxPlaced>> listCombination;
    private BoardSimulation boardSimulation;
    private CrestGestionnarySimulation crestGestionnarySimulation;
    private Combination<HexagoneBoxPlaced> bestCombination;
    ArrayList<Crest> chosenPath = new ArrayList<>();


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

    private void setupNewSimulation(HexagoneBoxPlaced box){
        this.boardSimulation = new BoardSimulation(box.getBoard());
        this.crestGestionnarySimulation = boardSimulation.getCrestGestionnary();
    }

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
                System.out.println("\n\n\n" + pathToIrrigateTheBox.get(0).get(0) + pathToIrrigateTheBox.get(1).get(0) + "\n\n\n");
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
    }
}
