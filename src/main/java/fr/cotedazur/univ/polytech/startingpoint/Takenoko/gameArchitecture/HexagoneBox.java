package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class HexagoneBox {

    private int[] coordinates ;
    /**
     * Form : 1*1 000 000 + x*10 000 + y*100 + z
     * Example : 1020301 -> x = 02, y = 03, z = 01
     */
    private int id ;
    private Color color;
    private Special special;
    private boolean irrigate;
    private int heightBamboo;
    private HashMap<Integer,int[]> AdjacentBox;
    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private final Board board;

    private ArrayList<Crest> listOfCrestAroundBox;

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
    public HexagoneBox (int x, int y, int z, Color color, Special special,RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Board board){
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.board = board;
        this.coordinates = new int[]{x,y,z};
        this.id = generateID(this.coordinates);
        this.color = color;
        this.special = special;
        this.heightBamboo = 0;
        setAutoIrrigation();
        getAllAdjacenteBox();
        updateRetrieveBox();
        generateCoordinatesThanksToBox();
    }

    private void updateRetrieveBox() {
        retrieveBoxIdWithParameters.setBoxColor(this.id,this.color);
        retrieveBoxIdWithParameters.setBoxHeight(this.id, this.heightBamboo);
        retrieveBoxIdWithParameters.setBoxIsIrrigated(this.id,this.irrigate);
        retrieveBoxIdWithParameters.setBoxSpeciality(this.id, this.special);
    }

    public HexagoneBox (Color color, Special special,RetrieveBoxIdWithParameters retrieveBoxIdWithParameters, Board board){
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.board = board;
        this.coordinates = null;
        this.color = color;
        this.special = special;
        this.irrigate = true;
        this.heightBamboo = 0;
    }

    public int[] getCoordinates(){
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

    public void growBamboo() {
        retrieveBoxIdWithParameters.setBoxHeightDelete(this.id,this.heightBamboo);
        if (this.heightBamboo < 4) this.heightBamboo++;
        retrieveBoxIdWithParameters.setBoxHeight(this.id,this.heightBamboo);
    }

    public Optional<Color> eatBamboo() {
        retrieveBoxIdWithParameters.setBoxHeightDelete(this.id,this.heightBamboo);
        Optional<Color> bambooEatedColor = Optional.empty();
        if (this.heightBamboo > 0) {
            this.heightBamboo--;
            bambooEatedColor = Optional.of(this.color);
        }
        retrieveBoxIdWithParameters.setBoxHeight(this.id,this.heightBamboo);
        return bambooEatedColor;
    }

    public HashMap<Integer, int[]> getAdjacentBox() {
        return this.AdjacentBox;
    }

    public int[] getAdjacentBoxOfIndex(int index){
        return this.AdjacentBox.get(index);
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
        this.id = generateID(this.coordinates);
        getAllAdjacenteBox();
        updateRetrieveBox();
        generateCoordinatesThanksToBox();
        setAutoIrrigation();
    }

    public void setSpecial(Special special) {
        this.special = special;
        retrieveBoxIdWithParameters.setBoxSpeciality(this.id,this.special);
    }

    public void setIrrigate(boolean irrigate) {
        this.irrigate = irrigate;
        retrieveBoxIdWithParameters.setBoxIsIrrigated(this.id,this.irrigate);
    }

    public void setHeightBamboo(int heightBamboo) {
        this.heightBamboo = heightBamboo;
        retrieveBoxIdWithParameters.setBoxHeight(this.id,this.heightBamboo);
    }


    /**
     * A method to get the 6 possible adjacente box of a box
     *      6       1
     *  5       x       2
     *      4       3
     * with x the box in question and 1,2,3,4,5,6 the adjacente box
     */
    private void getAllAdjacenteBox(){
        this.AdjacentBox = new HashMap<Integer,int[]>();
        int x = this.coordinates[0];
        int y = this.coordinates[1];
        int z = this.coordinates[2];
        this.AdjacentBox.put(1,new int[]{x+1,y-1,z});
        this.AdjacentBox.put(2,new int[] {x+1,y,z-1});
        this.AdjacentBox.put(3,new int[] {x,y+1,z-1});
        this.AdjacentBox.put(4,new int[] {x-1,y+1,z});
        this.AdjacentBox.put(5,new int[] {x-1,y,z+1});
        this.AdjacentBox.put(6,new int[] {x,y-1,z+1});
    }

    private void getAllAdjacenteBoxOfRange(int range){
        this.AdjacentBox = new HashMap<Integer,int[]>();
        int x = this.coordinates[0];
        int y = this.coordinates[1];
        int z = this.coordinates[2];
        for (int i = 0;i<5;i++){
        }
        this.AdjacentBox.put(1,new int[]{x+1,y-1,z});
        this.AdjacentBox.put(2,new int[] {x+1,y,z-1});
        this.AdjacentBox.put(3,new int[] {x,y+1,z-1});
        this.AdjacentBox.put(4,new int[] {x-1,y+1,z});
        this.AdjacentBox.put(5,new int[] {x-1,y,z+1});
        this.AdjacentBox.put(6,new int[] {x,y-1,z+1});
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

    @Override
    public String toString() {
        return "Box of id : " + id +
                ", color : " + color +
                " and is " + special;
    }



    private void generateCoordinatesThanksToBox(){
        ArrayList<Crest> listOfCoordiante = new ArrayList<>();
        int x = this.coordinates[0];
        int y = this.coordinates[1];
        listOfCoordiante.add(new Crest(x+15,y+05,1));
        listOfCoordiante.add(new Crest(x+15,y,2));
        listOfCoordiante.add(new Crest(x,y-05,3));
        listOfCoordiante.add(new Crest(x-15,y-05,1));
        listOfCoordiante.add(new Crest(x-15,y,2));
        listOfCoordiante.add(new Crest(x,y+05,3));
        this.listOfCrestAroundBox =  listOfCoordiante;
    }

    public ArrayList<Crest> getListOfCrestAroundBox() {
        return listOfCrestAroundBox;
    }

    private void setAutoIrrigation(){
        if (board.getAlreadyIrrigated().contains(this)){
            board.getAlreadyIrrigated().removeAll(Arrays.asList(this));
            this.irrigate = true;
        } else {
            this.irrigate = false;
        }
    }
}
