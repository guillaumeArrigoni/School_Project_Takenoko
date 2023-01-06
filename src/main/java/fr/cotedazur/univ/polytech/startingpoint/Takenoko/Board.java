package fr.cotedazur.univ.polytech.startingpoint.Takenoko;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Exception.*;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.TypeOfStackBox;

import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.CoordinateMethod.*;

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
        HexagoneBox box;
        box = getBoxWithCoordinates(coords);
        if (box.isIrrigate() ) box.growBamboo();
        HashMap<Integer, int[]> adjacentBox = box.getAdjacentBox();
        ArrayList<HexagoneBox> placedBox = this.getPlacedBox();
        for (HexagoneBox newBox : placedBox) {
            if (this.containsValue(adjacentBox, box.getCoordinates())) {
                if (newBox.isIrrigate() && !Arrays.equals(newBox.getCoordinates(), new int[]{0,0,0})) newBox.growBamboo();
            }
        }
    }

    public HexagoneBox getBoxWithCoordinates(int[] coords) {
        for (HexagoneBox newBox : this.PlacedBox) {
            if (Arrays.equals(newBox.getCoordinates(), coords)) return newBox;
        }
        return null;
    }

    public void setPandaCoords(int[] newCoords) {
        this.pandaCoords = newCoords;
        HexagoneBox box;
        box = getBoxWithCoordinates(newCoords);
        box.eatBamboo();
    }
    public int getNumberBoxPlaced() {
        return numberBoxPlaced;
    }

    public ArrayList<HexagoneBox> getPlacedBox() {
        return PlacedBox;
    }

    public boolean coordInBoard(int[] Coord) {
        for (HexagoneBox box : this.PlacedBox) {
            if (Arrays.equals(Coord,box.getCoordinates())) return true;
        }
        return false;
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
            if (coordInBoard(adjacentCoord)) {
                //cherche toutes les tuiles adjacente à celle que l'on pose
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
                if (!(coordInBoard(newCoord1)) && !(AvailableBox.contains(newCoord1))) {
                    AvailableBox.add(newCoord1);
                }
                if (!(coordInBoard(newCoord2)) && !(AvailableBox.contains(newCoord2))) {
                    AvailableBox.add(newCoord2);
                }
            }
        }
    }

    public boolean containsKey(HashMap<int[], Integer> dico, int[] coord) {
        for (int[] key : dico.keySet()) {
            if (Arrays.equals(key, coord)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(HashMap<Integer, int[]> dico, int coord[]) {
        for (int[] value : dico.values()) {
            if (Arrays.equals(value, coord)) {
                return true;
            }
        }
        return false;
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
    }

    /*
     * Get the 2 potential new available box common with 2 adjacent box
     * Check for each potential new available box if the hashmap PlacedBox or AvailableBox already contain this Box
     * (= boxID already place or already available)
     * @param idNewBox : id of the new box that we place (and add to the board)
     * @param idAdjacentBox :id of on of the adjacent box of the new one
     */
    /*private void AddNewAvailableBoxToDico(int idNewBox, int idAdjacentBox){
        int[] communAdjacenteBox = get_adjBox_shared_by2AdjBox(idNewBox,idAdjacentBox);

        for (int j=0;j<communAdjacenteBox.length;j++){
            if (!PlacedBox.containsKey(communAdjacenteBox[j]) && !AvailableBox.containsKey(communAdjacenteBox[j])){
                AvailableBox.put(communAdjacenteBox[j],get_range_from_center(communAdjacenteBox[j]));
            }
        }
    }*/

    /*
     * Method use to get the new box that can be place thanks to 2 adjacent box
     * @param box1id : the first box's id
     * @param box2id : the second box's id (adjacent to the first box)
     * @return : a tab of 2 int that contain the both box adjacent to the 2 Box in question.
     */
    /*private int[] get_adjBox_shared_by2AdjBox (int box1id, int box2id){
        int[] coordinatesBox1 = separateID(box1id);
        int[] coordinatesBox2 = separateID(box2id);
        int[] adjacenteBox = new int[2];
        try {
            int[] communCoordinates = getCoordinateInCommonBetween2TabOfCoordinates(coordinatesBox1,coordinatesBox2);
            int sameIndice = communCoordinates[1];
            int indicePlus1 = (communCoordinates[1]+1)%3;
            int indicePlus2 = (communCoordinates[1]+1)%3;
            adjacenteBox[0] = adjBox_of2OtherBox(sameIndice,indicePlus1,indicePlus2,coordinatesBox1,coordinatesBox2,true);
            adjacenteBox[1] = adjBox_of2OtherBox(sameIndice,indicePlus1,indicePlus2,coordinatesBox1,coordinatesBox2,false);
        } catch (AdjacenteException exception) {
            System.err.println("\n  -> An error has occurred :\n" + exception.getClass().getName() + " : " + exception.getErrorTitle() + "\n");
        }
        return adjacenteBox;
    }*/

    /*
     * Method use to get one of the 2 box adjacent to the 2 box entered
     * @param sameIndice : the indice of the coordinate in common between the box1 and box2
     * @param indicePlus1 : the indice following "sameindice" (modulo 3)
     * @param indicePlus2 : the indice following "indicePlus1" (modulo 3)
     * @param coordinatesOfBox1 : the tab of 3 int that correspond to the coordinates of the box 1
     * @param coordinatesOfBox2 : the tab of 3 int that correspond to the coordinates of the box 1
     * @param firstProceed : true if it is the first time we launch this method in order to take one of the 2 adjacent box
     *                     false if we want to take the second one adjacent box.
     * @return one of the 2 adjacent box (depending of the boolean firstProceed)
     */
    /*private int adjBox_of2OtherBox(int sameIndice, int indicePlus1, int indicePlus2, int[] coordinatesOfBox1, int[] coordinatesOfBox2, boolean firstProceed){
        int[] generateCoordinate = new int[3];
        if (firstProceed){
            indicePlus2 = indicePlus2 + indicePlus1;
            indicePlus1 = indicePlus2 - indicePlus1;
            indicePlus2 = indicePlus2 - indicePlus1;
        }
        generateCoordinate[indicePlus1]=coordinatesOfBox1[indicePlus2];
        generateCoordinate[indicePlus2]=coordinatesOfBox2[indicePlus1];
        generateCoordinate[sameIndice]=-generateCoordinate[indicePlus1]-generateCoordinate[indicePlus2];
        return generateID(generateCoordinate[0],generateCoordinate[1],generateCoordinate[2]);
    }*/

    public ArrayList<int[]> getAvailableBox(){
        return this.AvailableBox;
    }

}
