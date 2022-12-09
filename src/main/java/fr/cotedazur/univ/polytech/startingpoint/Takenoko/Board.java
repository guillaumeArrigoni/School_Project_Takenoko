package fr.cotedazur.univ.polytech.startingpoint.Takenoko;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Exception.AdjacenteException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import java.util.ArrayList;
import java.util.HashMap;
import static fr.cotedazur.univ.polytech.startingpoint.Takenoko.CoordinateMethod.*;

public class Board {

    HashMap<Integer,Integer> AvailableBox;
    int numberBoxPlaced ;
    HashMap<Integer,Integer> PlacedBox;

    public Board(){
        HexagoneBox lac = new HexagoneBox(0,0,0, Color.Lac, Special.Classic);
        numberBoxPlaced = 1;

        AvailableBox = new HashMap<Integer,Integer>();
        ArrayList<Integer> listAdjacenteBox = get_all_adjacente_box(lac.getId());
        AvailableBox = Add_Integer_Into_Dico_With_Same_Value(AvailableBox,listAdjacenteBox,1);

        PlacedBox = new HashMap<Integer,Integer>();
        PlacedBox.put(lac.getId(),0);
    }

    private ArrayList<Integer> get_all_adjacente_box(int id){
        ArrayList<Integer> adjacenteBox = new ArrayList<Integer>();
        int[] coordinates = separateID(id);
        int x = coordinates[0];
        int y = coordinates[1];
        int z = coordinates[2];
        adjacenteBox.add(generateID(x-1,y+1,z));
        adjacenteBox.add(generateID(x-1,y,z+1));
        adjacenteBox.add(generateID(x,y+1,z-1));
        adjacenteBox.add(generateID(x,y-1,z+1));
        adjacenteBox.add(generateID(x+1,y-1,z));
        adjacenteBox.add(generateID(x+1,y,z-1));
        return adjacenteBox;
    }

    public void addBox(HexagoneBox box){
        numberBoxPlaced = numberBoxPlaced +1;
        int id = box.getId();
        if (numberBoxPlaced == 2){
            AvailableBox.clear();
        }
        AvailableBox.remove(id);
        PlacedBox.put(id,get_range_from_center(id));

        ArrayList<Integer> adjacenteBox = get_all_adjacente_box(id);
        for (int i=0;i<adjacenteBox.size();i++){
            if (PlacedBox.containsKey(adjacenteBox.get(i))){
                //cherche toutes les tuiles adjacente à celle que l'on pose
                int[] communAdjacenteBox = get_adjBox_shared_by2AdjBox(id,adjacenteBox.get(i));

                for (int j=0;j<communAdjacenteBox.length;j++){
                    if (!PlacedBox.containsKey(communAdjacenteBox[j])){
                        AvailableBox.put(communAdjacenteBox[j],get_range_from_center(communAdjacenteBox[j]));
                    }
                }
            }
        }
    }

    private int[] get_adjBox_shared_by2AdjBox (int box1id, int box2id){
        int[] coordinatesBox1 = separateID(box1id);
        int[] coordinatesBox2 = separateID(box2id);
        int[] adjacenteBox = new int[2];
        int[] generateCoordinate = new int[3];

        try {
            int[] communCoordinates = getIntegerSharedBy2TabofCoordinate(coordinatesBox1,coordinatesBox2);
            int sameCoordinate = communCoordinates[0];
            int sameIndice = communCoordinates[1];
            int indicePlus1 = (communCoordinates[1]+1)%3;
            int indicePlus2 = (communCoordinates[1]+1)%3;

            generateCoordinate[indicePlus1]=coordinatesBox1[indicePlus1];
            generateCoordinate[indicePlus2]=coordinatesBox2[indicePlus2];
            generateCoordinate[sameIndice]=-generateCoordinate[indicePlus1]-generateCoordinate[indicePlus2];
            adjacenteBox[0] = generateID(generateCoordinate[0],generateCoordinate[1],generateCoordinate[2]);

            generateCoordinate[indicePlus1]=coordinatesBox2[indicePlus1];
            generateCoordinate[indicePlus2]=coordinatesBox1[indicePlus2];
            generateCoordinate[sameIndice]=-generateCoordinate[indicePlus1]-generateCoordinate[indicePlus2];
            adjacenteBox[1] = generateID(generateCoordinate[0],generateCoordinate[1],generateCoordinate[2]);

        } catch (AdjacenteException exception) {
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
        }
        return adjacenteBox;
    }
}
