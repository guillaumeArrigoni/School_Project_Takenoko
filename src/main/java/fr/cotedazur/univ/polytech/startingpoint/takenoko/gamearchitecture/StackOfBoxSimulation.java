package fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;

import java.util.ArrayList;

public class StackOfBoxSimulation extends StackOfBox{

    public StackOfBoxSimulation(StackOfBox stackOfBox){
        super();
        super.stackOfBox = (ArrayList<HexagoneBox>) stackOfBox.stackOfBox.clone();
    }
}
