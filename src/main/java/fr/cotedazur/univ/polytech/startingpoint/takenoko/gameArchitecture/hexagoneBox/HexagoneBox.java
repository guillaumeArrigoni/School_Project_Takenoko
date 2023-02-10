package fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;

import java.util.Objects;


public class HexagoneBox implements Comparable<HexagoneBox> {

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

    /**
     * Method use to generate the id with the coordinates
     * @param coordinates : the list of coordinates with in index 0 : x, index 1 : y, index 2 : z
     * @return the id associated to the coordinates:
     */
    public static int generateID(int[] coordinates) {
        int id = 1000000;
        int tempo;
        for (int i=0;i<3;i++){
            if (coordinates[i]<0){
                tempo = 100 + coordinates[i];
            } else {
                tempo = coordinates[i];
            }
            id = id + tempo * (int) Math.pow(100,i);
        }
        return id;
    }

    /**
     * Method use to separate an id into a tab of 3 int with the coordinates associated to the id
     * @param id : the id we want to get the coordinates
     * @return a tab of 3 int with the coordinates
     */
    public static int[] separateID(int id) {
        int[] tab = new int[3];
        tab[2] = (id % 1000000) / 10000;
        tab[1] = (id % 10000) / 100;
        tab[0] = id % 100;
        for (int i=0; i<3; i++) {
            if (tab[i] > 50) tab[i]=tab[i]-100;
        }
        return tab;
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

    @Override
    public int compareTo(HexagoneBox hexagoneBox) {
        if (this.color == hexagoneBox.color && this.special == hexagoneBox.special){
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object object){
        if (object == this) {
            return true;
        }
        if (!(object instanceof HexagoneBox)) {
            return false;
        }
        HexagoneBox hexagoneBox = (HexagoneBox) object;

        return (hexagoneBox.color == this.color && hexagoneBox.special == this.special);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color.getRank()*10 + special.getRank());
    }
}
