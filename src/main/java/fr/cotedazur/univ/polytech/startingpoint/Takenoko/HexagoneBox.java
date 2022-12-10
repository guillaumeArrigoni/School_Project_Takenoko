package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Exception.AdjacenteException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.CoordinateMethod.generateID;
import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.CoordinateMethod.separateID;

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
    private HashMap<Integer,Integer> AdjacentBox;



    /**
     *      1
     *  6       2
     *  5       3
     *      4
     * @param z : 5-4 (or 1-2) edge
     * @param y : 6-5 (or 2-3) edge
     * @param x : 1-6 (or 3-4) edge
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
        get_all_adjacente_box();
    }

    public HexagoneBox (Color color, Special special){
        this.coordinates = new ArrayList<Integer>(Arrays.asList(null, null, null));
        this.id = -1;
        this.color = color;
        this.special = special;
        this.irrigate = true;
        this.heightBamboo = 0;
        get_all_adjacente_box();
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

    public int getAdjacentBoxOfIndex(int index){
        return this.AdjacentBox.get(index);
    }

    public void setId(int id){
        this.id = id;
        int[] tempoCoordinates = separateID(id);
        this.coordinates = new ArrayList(Arrays.asList(tempoCoordinates));
    }

    public void setAdjacentBox(ArrayList<Integer> ListOfAdjacentBox){
        for (int i=0;i<ListOfAdjacentBox.size();i++){

        }
    }

    /**
     * A method to get the 6 possible adjacente box of a box
     *      6       1
     *  5       x       2
     *      4       3
     * with x the box in question and 1,2,3,4,5,6 the adjacente box
     */
    private void get_all_adjacente_box(){
        this.AdjacentBox = new HashMap<Integer,Integer>();
        int x = this.coordinates.get(0);
        int y = this.coordinates.get(1);
        int z = this.coordinates.get(2);
        this.AdjacentBox.put(1,generateID(x+1,y-1,z));
        this.AdjacentBox.put(2,generateID(x+1,y,z-1));
        this.AdjacentBox.put(3,generateID(x,y+1,z-1));
        this.AdjacentBox.put(4,generateID(x-1,y+1,z));
        this.AdjacentBox.put(5,generateID(x-1,y,z+1));
        this.AdjacentBox.put(6,generateID(x,y-1,z+1));
    }
}
