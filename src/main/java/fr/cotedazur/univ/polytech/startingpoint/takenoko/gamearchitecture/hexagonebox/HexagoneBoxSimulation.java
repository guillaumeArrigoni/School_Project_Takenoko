package fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.BoardSimulation;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumboxproperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveSimulation;

public class HexagoneBoxSimulation extends HexagoneBoxPlaced {
    public HexagoneBoxSimulation(int x, int y, int z, HexagoneBox boxNotPlaced, RetrieveSimulation retrieveBoxIdWithParameters, BoardSimulation board) {
        super(x, y, z, boxNotPlaced, retrieveBoxIdWithParameters, board);
    }

    public HexagoneBoxSimulation(int x, int y, int z, Color color, Special special, RetrieveSimulation retrieveBoxIdWithParameters, BoardSimulation board) {
        super(x, y, z, color, special, retrieveBoxIdWithParameters, board);
    }
}
