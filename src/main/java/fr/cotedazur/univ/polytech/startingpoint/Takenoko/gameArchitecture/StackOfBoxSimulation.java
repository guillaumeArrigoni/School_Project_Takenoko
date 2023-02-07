package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;

import java.util.ArrayList;

public class StackOfBoxSimulation extends StackOfBox{

    public StackOfBoxSimulation(StackOfBox stackOfBox){
        super();
        super.stackOfBox = (ArrayList<HexagoneBox>) stackOfBox.stackOfBox.clone();
    }
}
