package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;


public class HexagoneBox {

    protected Color color;
    protected Special special;
    protected boolean irrigate;

    public HexagoneBox (Color color, Special special){
        this.color = color;
        this.special = special;
        this.irrigate = special==Special.SourceEau;
    }

    public HexagoneBox (HexagoneBox box){
        this(box.getColor(),box.getSpecial());
    }

    public Color getColor(){
        return this.color;
    }

    public Special getSpecial() {
        return special;
    }

    public boolean isIrrigate() {
        return irrigate;
    }

    @Override
    public String toString() {
        return "Box of color : " + color +
                " and is " + special;
    }
}
