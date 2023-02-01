package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveSimulation;

public class HexagoneBoxSimulation extends HexagoneBoxPlaced {
    public HexagoneBoxSimulation(int x, int y, int z, HexagoneBox boxNotPlaced, RetrieveSimulation retrieveBoxIdWithParameters, BoardSimulation board) {
        super(x, y, z, boxNotPlaced, retrieveBoxIdWithParameters, board);
    }

    public HexagoneBoxSimulation(int x, int y, int z, Color color, Special special, RetrieveSimulation retrieveBoxIdWithParameters, BoardSimulation board) {
        super(x, y, z, color, special, retrieveBoxIdWithParameters, board);
    }
}
