package fr.cotedazur.univ.polytech.startingpoint.Takenoko;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Exception.*;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.TypeOfStackBox;

import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.CoordinateMethod.*;

import java.util.ArrayList;
import java.util.HashMap;



public class Board {

    int numberBoxPlaced ;

    /**
     * PlacedBox is a Hashmap that contain in key all the box's id already place in the board
     * and associate for each one their range to the center of the board (lake)
     * Type :
     *      - first Integer : id of the box placed
     *      - second Integer : range to the lake
     */
    private HashMap<Integer,Integer> PlacedBox;

    /**
     * AvailableBox is a Hashmap that contain in key all the box's id that can be placed.
     * (= where new box can be place in the board).
     * Associate to each key the range to the center of the board (lake)
     * Type :
     *      - first Integer : id of the box that can be place
     *      - second Integer : range to the lake
     */
    private HashMap<Integer,Integer> AvailableBox;

    /**
     * StackOfAvailableBox is a Hashmap that contain in key all the box that can be picked and placed.
     * Associate to each key the number of same box available
     * Type :
     *      - TypeOfStackBox : the type of the box category
     *      - Integer : the number of box from this type in the stack that can be picked
     */
    private HashMap<TypeOfStackBox,Integer> StackOfAvailableBox;

    public Board(){
        HexagoneBox lac = new HexagoneBox(0,0,0, Color.Lac, Special.Classique);
        numberBoxPlaced = 1;

        AvailableBox = new HashMap<Integer,Integer>();
        ArrayList<Integer> listAdjacenteBox = get_all_adjacente_box(lac.getId());
        AvailableBox = Add_Integer_Into_Dico_With_Same_Value(AvailableBox,listAdjacenteBox,1);

        PlacedBox = new HashMap<Integer,Integer>();
        PlacedBox.put(lac.getId(),0);
    }

    /**
     * A method to get the 6 possible adjacente box of a box
     * @param id of the box witch we want to get all the adjacente box
     * @return an ArrayList<Integer> with the id of the adjacente box following the order :
     *      6       1
     *  5       x       2
     *      4       3
     * with x the box in question and 1,2,3,4,5,6 the adjacente box
     */
    private ArrayList<Integer> get_all_adjacente_box(int id){
        ArrayList<Integer> adjacenteBox = new ArrayList<Integer>();
        int[] coordinates = separateID(id);
        int x = coordinates[0];
        int y = coordinates[1];
        int z = coordinates[2];
        adjacenteBox.add(generateID(x+1,y-1,z));
        adjacenteBox.add(generateID(x+1,y,z-1));
        adjacenteBox.add(generateID(x,y+1,z-1));
        adjacenteBox.add(generateID(x-1,y+1,z));
        adjacenteBox.add(generateID(x-1,y,z+1));
        adjacenteBox.add(generateID(x,y-1,z+1));
        return adjacenteBox;
    }

    /**
     * Add the HexagoneBox entered into the Hashmap PlacedBox and update the Hasmap AvaiableBox with the new box avalaible and delete the new box add.
     * @param box : the new Hexagone box to add to the board
     */
    public void addBox(HexagoneBox box){
        int id = box.getId();
        UpdateAvaiableBoxAndPlacedBox(id);
        ArrayList<Integer> adjacenteBox = get_all_adjacente_box(id);
        for (int i=0;i<6;i++){
            if (PlacedBox.containsKey(adjacenteBox.get(i))){
                //cherche toutes les tuiles adjacente à celle que l'on pose
                AddNewAvailableBoxToDico(id,adjacenteBox.get(i));
            }
        }
    }

    /**
     * Check if the number of box placed is equals to 2
     * (correspond to the case when the players add the first HexagoneBox to the booard (the first is the lake)
     * Then remove in the Hasmap AvailableBox the box that we just place now and add the id of this new box into the Hasmap PlacedBox
     * @param id : of the new box that we place in the board.
     */
    private void UpdateAvaiableBoxAndPlacedBox(int id){
        this.numberBoxPlaced = this.numberBoxPlaced +1;
        if (this.numberBoxPlaced == 2){
            AvailableBox.clear();
        } else {
            AvailableBox.remove(id);
        }
        PlacedBox.put(id,get_range_from_center(id));
    }

    /**
     * Get the 2 potential new available box common with 2 adjacent box
     * Check for each potential new available box if the hashmap PlacedBox or AvailableBox already contain this Box
     * (= boxID already place or already available)
     * @param idNewBox : id of the new box that we place (and add to the board)
     * @param idAdjacentBox :id of on of the adjacent box of the new one
     */
    private void AddNewAvailableBoxToDico(int idNewBox, int idAdjacentBox){
        int[] communAdjacenteBox = get_adjBox_shared_by2AdjBox(idNewBox,idAdjacentBox);

        for (int j=0;j<communAdjacenteBox.length;j++){
            if (!PlacedBox.containsKey(communAdjacenteBox[j]) && !AvailableBox.containsKey(communAdjacenteBox[j])){
                AvailableBox.put(communAdjacenteBox[j],get_range_from_center(communAdjacenteBox[j]));
            }
        }
    }

    /**
     * Method use to get the new box that can be place thanks to 2 adjacent box
     * @param box1id : the first box's id
     * @param box2id : the second box's id (adjacent to the first box)
     * @return : a tab of 2 int that contain the both box adjacent to the 2 Box in question.
     */
    private int[] get_adjBox_shared_by2AdjBox (int box1id, int box2id){
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
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
        }
        return adjacenteBox;
    }

    /**
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
    private int adjBox_of2OtherBox(int sameIndice, int indicePlus1, int indicePlus2, int[] coordinatesOfBox1, int[] coordinatesOfBox2, boolean firstProceed){
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
    }

    public HashMap<Integer,Integer> getAvailableBox(){
        return this.AvailableBox;
    }

}
