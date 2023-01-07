package fr.cotedazur.univ.polytech.startingpoint.Takenoko;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



public class Board {

    int numberBoxPlaced ;

    /**
     * PlacedBox is a Hashmap that contain in key all the box's id already place in the board
     * and associate for each one their range to the center of the board (lake)
     * Type :
     *      - int[] : coordinates of the placed box
     *      - Integer : range to the lake
     */

    private ArrayList<HexagoneBox> PlacedBox;
    private HashMap<Integer,HexagoneBox> getBox = new HashMap<>();

    /**
     * AvailableBox is a Hashmap that contain in key all the box's id that can be placed.
     * (= where new box can be place in the board).
     * Associate to each key the range to the center of the board (lake)
     * Type :
     *      - int[] : coordinates of the placed box
     *      - Integer : range to the lake
     */
    private ArrayList<int[]> AvailableBox;

    private int[] gardenerCoords;
    private int[] pandaCoords;

    public Board(){
        HexagoneBox lac = new HexagoneBox(0,0,0, Color.Lac, Special.Classique);
        numberBoxPlaced = 1;

        AvailableBox = new ArrayList<>();
        for (int i=1;i<7;i++){
            AvailableBox.add(lac.getAdjacentBoxOfIndex(i));
        }

        PlacedBox = new ArrayList<>();
        PlacedBox.add(lac);
        gardenerCoords = new int[]{0,0,0};
    }

    public int[] getGardenerCoords() {
        return this.gardenerCoords;
    }

    public int[] getPandaCoords() {return this.pandaCoords;}

    public void setGardenerCoords(int[] coords) {
        this.gardenerCoords = coords;
        growAfterMoveOfTheGardener(getBoxWithCoordinates(coords));
    }

    private void growAfterMoveOfTheGardener(HexagoneBox box){
        if (box.isIrrigate() && !Arrays.equals(box.getCoordinates(), new int[]{0,0,0})) box.growBamboo();
        HashMap<Integer, int[]> adjacentBox = box.getAdjacentBox();
        for (int[] coordinate : adjacentBox.values()){
            HexagoneBox newBox = getBoxWithCoordinates(coordinate);
            if (this.getBox.containsKey(newBox.getId()) && newBox.isIrrigate() && newBox.getColor() == box.getColor()){
                newBox.growBamboo();
            }
        }
    }

    public HexagoneBox getBoxWithCoordinates(int[] coords) {
        return this.getBox.get(HexagoneBox.generateID(coords));
    }

    public void setPandaCoords(int[] newCoords) {
        this.pandaCoords = newCoords;
        HexagoneBox box = getBoxWithCoordinates(newCoords);
        box.eatBamboo();
    }
    public int getNumberBoxPlaced() {
        return numberBoxPlaced;
    }

    public ArrayList<HexagoneBox> getPlacedBox() {
        return PlacedBox;
    }

    public boolean isCoordinateInBoard(int[] Coord) {
        return this.getBox.containsKey(getBoxWithCoordinates(Coord).getId());
    }

    /**
     * Add the HexagoneBox entered into the Hashmap PlacedBox and update the Hasmap AvaiableBox with the new box avalaible and delete the new box add.
     * @param box : the new Hexagone box to add to the board
     */
    public void addBox(HexagoneBox box) {
        int[] coord = box.getCoordinates();
        int[] newCoord1, newCoord2;
        UpdateAvaiableBoxAndPlacedBox(box);
        for (int i=1;i<7;i++){
            int[] adjacentCoord = box.getAdjacentBoxOfIndex(i);
            if (isCoordinateInBoard(adjacentCoord)) {
                //look for every adjacent box to the one we are placing in the board
                int x = coord[0], y = coord[1], z = coord[2];
                int x1 = adjacentCoord[0], y1 = adjacentCoord[1], z1 = adjacentCoord[2];
                if (x==x1) {
                    newCoord1 = new int[]{x+1,Math.min(y,y1),Math.min(z,z1)};
                    newCoord2 = new int[]{x-1,Math.max(y,y1),Math.max(z,z1)};
                }
                else if (y==y1) {
                    newCoord1 = new int[]{Math.min(x,x1),y+1,Math.min(z,z1)};
                    newCoord2 = new int[]{Math.max(x,x1),y-1,Math.max(z,z1)};
                }
                else {
                    newCoord1 = new int[]{Math.min(x,x1),Math.min(y,y1),z+1};
                    newCoord2 = new int[]{Math.max(x,x1),Math.max(y,y1),z-1};
                }
                if (!(isCoordinateInBoard(newCoord1)) && !(AvailableBox.contains(newCoord1))) {
                    AvailableBox.add(newCoord1);
                }
                if (!(isCoordinateInBoard(newCoord2)) && !(AvailableBox.contains(newCoord2))) {
                    AvailableBox.add(newCoord2);
                }
            }
        }
    }

    public HashMap<Integer, HexagoneBox> getGetBox() {
        return getBox;
    }

    /**
     * Check if the number of box placed is equals to 2
     * (correspond to the case when the players add the first HexagoneBox to the booard (the first is the lake)
     * Then remove in the Hasmap AvailableBox the box that we just place now and add the id of this new box into the Hasmap PlacedBox
     * @param box : box that we place in the board.
     */
    private void UpdateAvaiableBoxAndPlacedBox(HexagoneBox box) {
        this.numberBoxPlaced = this.numberBoxPlaced +1;
        if (this.numberBoxPlaced == 2) {
            AvailableBox.clear();
        } else {
            AvailableBox.remove(box.getCoordinates());
        }
        PlacedBox.add(box);
        getBox.put(box.getId(),box);
    }

    public ArrayList<int[]> getAvailableBox(){
        return this.AvailableBox;
    }

}
