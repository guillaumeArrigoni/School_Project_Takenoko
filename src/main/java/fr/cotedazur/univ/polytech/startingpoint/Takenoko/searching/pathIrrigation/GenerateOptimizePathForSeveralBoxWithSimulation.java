package fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.pathIrrigation;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.CrestGestionnarySimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination.Combination;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.combination.CombinationSortedOf_P_ElementAmong_N;

import java.util.ArrayList;

public class GenerateOptimizePathForSeveralBoxWithSimulation {

    private ArrayList<Combination<HexagoneBoxPlaced>> listCombination;
    private BoardSimulation boardSimulation;
    private CrestGestionnarySimulation crestGestionnarySimulation;
    private Combination<HexagoneBoxPlaced> bestCombination;


    public GenerateOptimizePathForSeveralBoxWithSimulation(ArrayList<HexagoneBoxPlaced> listBox){
        this.listCombination = new CombinationSortedOf_P_ElementAmong_N(listBox,listBox.size()).getListOfCombination();
    }

    public Combination<HexagoneBoxPlaced> getBestCombination() {
        return bestCombination;
    }

    private void setupNewSimulation(HexagoneBoxPlaced box){
        this.boardSimulation = new BoardSimulation(box.getBoard());
        this.crestGestionnarySimulation = boardSimulation.getCrestGestionnary();
    }

    private void GenerateBestPath() throws CrestNotRegistered {
        int nbTour = -1;
        Combination<HexagoneBoxPlaced> chosenCombination = new Combination<>();
        for (Combination<HexagoneBoxPlaced> combination :  this.listCombination){
            int count = 0;
            setupNewSimulation(combination.getListOfElementInTheCombination().get(0));
            for (HexagoneBoxPlaced box : combination.getListOfElementInTheCombination()){
                GenerateAWayToIrrigateTheBox generateAWayToIrrigateTheBox = new GenerateAWayToIrrigateTheBox(box, this.boardSimulation);
                ArrayList<ArrayList<Crest>> pathToIrrigateTheBox = generateAWayToIrrigateTheBox.getPathToIrrigation();
                for (Crest crest : pathToIrrigateTheBox.get(0)){
                    boardSimulation.placeIrrigation(crest);
                     count = count + 1;
                }
            }
            if (nbTour == -1 || nbTour>count){
                nbTour = count;
                chosenCombination = combination;
            }
        }
        this.bestCombination = chosenCombination;
    }
}
