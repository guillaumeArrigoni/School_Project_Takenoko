package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;

public class CoordinateMethod {

    public static int generateID(int x, int y, int z) {
        int id = 1000000;
        id = id + x * 10000 + y * 100 + z;
        return id;
    }

    public static int[] separateID(int id) {
        int[] coordinates = new int[3];
        String idString = Integer.toString(id);
        for (int i = 3; i > 0; i--) {
            coordinates[i] = Integer.parseInt(idString.substring(i * 2 + 1, i * 2 + 3));
        }
        return coordinates;
    }

    public static HashMap<Integer, Integer> Add_Integer_Into_Dico(HashMap dicoToUpdate, ArrayList<Integer> elementToAdd, ArrayList<Integer> valueOfTheTelement) {
        for (int i = 0; i < elementToAdd.size(); i++) {
            dicoToUpdate.put(elementToAdd.get(i), valueOfTheTelement.get(i));
        }
        return dicoToUpdate;
    }

    public static HashMap<Integer, Integer> Add_Integer_Into_Dico_With_Same_Value(HashMap dicoToUpdate, ArrayList<Integer> elementToAdd, int value) {
        for (int i = 0; i < elementToAdd.size(); i++) {
            dicoToUpdate.put(elementToAdd.get(i), value);
        }
        return dicoToUpdate;
    }

    public static int get_range_from_center(int id){
        int[] coordinates = separateID(id);
        int range = abs(coordinates[0]) + abs(coordinates[1]) + abs(coordinates[2]);
        range = range/2;
        return range;
    }

    public static int[] getIntegerSharedBy2TabofCoordinate (int[] tab1, int[] tab2) throws AdjacenteException {
        int[] valueAndIndex = new int[3];
        for (int i=0; i<tab1.length;i++){
            for (int j=0; j<tab2.length;j++){
                if (tab1[i] == tab2[j]){
                    valueAndIndex[0] = tab1[i];
                    valueAndIndex[1] = i;
                    valueAndIndex[2] = j;
                }
            }
        }
        throw new AdjacenteException();
    }
}
