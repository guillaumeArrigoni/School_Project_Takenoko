package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Exception.AdjacenteException;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

public class CoordinateMethod {

    /**
     * Method use to generate the id with the coordinates
     * @param x : first coordinate
     * @param y : second coordinate
     * @param z : third coordinate
     * @return the id associated to the coordinates:
     */
    public static int generateID(int x, int y, int z) {
        int id = 1000000;
        id = id + x * 10000 + y * 100 + z;
        return id;
    }

    /**
     * Method use to separate a id into a tab of 3 int with the coordinates associated to the id
     * @param id : the id we want to get the coordinates
     * @return a tab of 3 int with the coordinates
     */
    public static int[] separateID(int id) {
        int[] coordinates = new int[3];
        String idString = Integer.toString(id);
        for (int i = 3; i > 0; i--) {
            coordinates[i] = Integer.parseInt(idString.substring(i * 2 + 1, i * 2 + 3));
        }
        return coordinates;
    }

    /**
     * Method use to add to a Hashmap all the Integer of the first ArrayList as key and all the Integer of the second one as value for those key
     * (N Integer of the first ArrayList will be the key associated to the N Integer of the second ArrayList)
     * @param dicoToUpdate : The Hashmap we want to update with those 2 ArrayList
     * @param elementToAdd : Size n, same size as the second ArrayList, will be the key in the Hashmap
     * @param valueOfTheTelement : Size n, same size as the first ArrayList, will be the value of the Hashmap
     * @return the updated Hashmap
     */
    public static HashMap<Integer, Integer> Add_Integer_Into_Dico(HashMap dicoToUpdate, ArrayList<Integer> elementToAdd, ArrayList<Integer> valueOfTheTelement) {
        for (int i = 0; i < elementToAdd.size(); i++) {
            dicoToUpdate.put(elementToAdd.get(i), valueOfTheTelement.get(i));
        }
        return dicoToUpdate;
    }

    /**
     * Method use to add each element of an ArrayList as a key into a Hashmap with for all of this key the same value associated
     * @param dicoToUpdate : the Hashmap in witch we want to put the Integer of the ArrayList
     * @param elementToAdd : the ArrayList of Integer
     * @param value : the value add for each new key
     * @return the Hashmap updated
     */
    public static HashMap<Integer, Integer> Add_Integer_Into_Dico_With_Same_Value(HashMap dicoToUpdate, ArrayList<Integer> elementToAdd, int value) {
        for (int i = 0; i < elementToAdd.size(); i++) {
            dicoToUpdate.put(elementToAdd.get(i), value);
        }
        return dicoToUpdate;
    }

    /**
     * Return the range of a Box to the center of the board (lake)
     * @param id : of the box that we want to know the range to the lake
     * @return a int that correspond to the range
     */
    public static int get_range_from_center(int id){
        int[] coordinates = separateID(id);
        int range = abs(coordinates[0]) + abs(coordinates[1]) + abs(coordinates[2]);
        range = range/2;
        return range;
    }

    /**
     * Return the coordinates in common between 2 tab of coordinates
     * @param tab1 the first tab of coordinate
     * @param tab2 the second tab of coordinate
     * @return a tab of 3 int with :
     *      - the coordinates in common
     *      - the index of the coordinate in common in the first tab
     *      - the index of the coordinate in common in the second tab
     * @throws AdjacenteException if the program detect no coordinate adjacente
     */
    public static int[] getCoordinateInCommonBetween2TabOfCoordinates(int[] tab1, int[] tab2) throws AdjacenteException {
        int[] valueAndIndex = new int[3];
        for (int i=0; i<tab1.length;i++){
            for (int j=0; j<tab2.length;j++){
                if (tab1[i] == tab2[j]){
                    valueAndIndex[0] = tab1[i];
                    valueAndIndex[1] = i;
                    valueAndIndex[2] = j;
                    return valueAndIndex;
                }
            }
        }
        throw new AdjacenteException(tab1,tab2);
    }
}
