package main.java.fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;
import main.java.fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class HexagoneBox {

    private ArrayList<Integer> coordinates ;
    /**
     * Form : 1*1 000 000 + x*10 000 + y*100 + z
     * Example : 1020301 -> x = 02, y = 03, z = 01
     */
    private int id ;
    private Color color;
    private Special special;
    private boolean irrigate;
    private int heightBamboo;



    /**
     *      1
     *  6       2
     *  5       3
     *      4
     * @param x : 5-4 (or 1-2) edge
     * @param y : 6-5 (or 2-3) edge
     * @param z : 1-6 (or 3-4) edge
     * @param color : the color of the box
     * @param special : the particularity of the box
     */
    public HexagoneBox (int x, int y, int z, Color color, Special special){
        this.coordinates = new ArrayList<Integer>(Arrays.asList(x, y, z));
        this.id = generateID(x,y,z);
        this.color = color;
        this.special = special;
        this.irrigate = true;
        this.heightBamboo = 0;
    }

    private int generateID(int x, int y, int z){
        int id = 1000000;
        id = id + x*10000 + y*100 + z;
        return id;
    }

    public ArrayList<Integer> getCoordinates(){
        return this.coordinates;
    }
    
    public int getId(){
        return this.id;
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

    public int getHeightBamboo() {
        return heightBamboo;
    }
}
