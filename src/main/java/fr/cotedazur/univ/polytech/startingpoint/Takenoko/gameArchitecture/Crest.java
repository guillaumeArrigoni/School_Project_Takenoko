package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;

import java.util.ArrayList;
import java.util.Arrays;

public class Crest implements GenerateMethods {

    private int range_from_origin;
    private int id;
    private int[] coordinates;
    private ArrayList<ArrayList<Integer>> listOfCrestChildren;
    private int order;
    private boolean isIrrigated;

    public int getRange_from_origin() {
        return range_from_origin;
    }

    public int getId() {
        return id;
    }

    /**
     * x = [0]
     * y = [1]
     * @return
     */
    public int[] getCoordinates() {
        return coordinates;
    }

    public ArrayList<ArrayList<Integer>> getListOfCrestChildren() {
        return listOfCrestChildren;
    }

    public int getOrder() {
        return order;
    }

    public boolean isIrrigated() {
        return isIrrigated;
    }

    public void setIrrigated(boolean irrigated) {
        isIrrigated = irrigated;
    }

    /**
     *
     * @param x, if x = 1 : input is 10, if x = 1.5 : input is 15..
     * @param y same as x
     * @param order
     */
    public Crest(int x, int y, int order){
        this.coordinates = new int[]{x,y};
        this.order = order;
        this.id = generateID(this.coordinates);
        this.range_from_origin = generate_range_from_origin(this.coordinates);
        this.isIrrigated = false;
        generateNewAdjacentCrest();
    }


    /*private ArrayList<int[]> generateCoordinatesThanksToBox(HexagoneBox box){
        ArrayList<int[]> listOfCoordiante = new ArrayList<>();
        int x = box.getCoordinates()[0];
        int y = box.getCoordinates()[1];
        listOfCoordiante.add(new int[]{ (x+15), (y+05)});
        listOfCoordiante.add(new int[]{(x+15), (y)});
        listOfCoordiante.add(new int[]{(x), (y-05)});
        listOfCoordiante.add(new int[]{(x-15), (y-05)});
        listOfCoordiante.add(new int[]{(x-15), (y)});
        listOfCoordiante.add(new int[]{(x), (y+05)});
        return listOfCoordiante;
    }*/


    public void setOrder(int x){
        this.order = x;
    }

    @Override
    public int generateID(int[] coordinate){
        int id = 0;
        for (int i=0;i<2;i++){
            if (coordinate[i]<0){
                id = id + (1000 -  coordinate[i]) * (int) Math.pow(1000,i);
            }
        }
        return id;
    }

    @Override
    public int[] separateID(int id) {
        int[] tab = new int[2];
        tab[1] = (id % 1000000) / 1000;
        tab[0] = id % 1000;
        for (int i=0; i<2; i++) {
            if (tab[i] > 500) tab[i]=tab[i]-1000;
        }
        return tab;
    }

    public int generate_range_from_origin(int[] coordinate){
        /*int x = coordinate[0];
        int y = coordinate[1];
        if (coordinate[0]<0){
            x = x * (-1);
        }
        if (coordinate[1]<0){
            y = y * (-1);
        }
        return (x>y) ? x : y;*/
        return 0;
    }

    private void generateNewAdjacentCrest(){
        ArrayList<ArrayList<Integer>> newCrest = new ArrayList<>();
        int x = this.coordinates[0];
        int y = this.coordinates[1];
        switch(this.order){
            case 1 : case 4 :
                newCrest.add(new ArrayList<>(Arrays.asList(x-5,y,3)));
                newCrest.add(new ArrayList<>(Arrays.asList(x,y-5,2)));
                newCrest.add(new ArrayList<>(Arrays.asList(x+5,y,3)));
                newCrest.add(new ArrayList<>(Arrays.asList(x,y+5,2)));
                break;
            case 2 : case 5 :
                newCrest.add(new ArrayList<>(Arrays.asList(x,y-5,1)));
                newCrest.add(new ArrayList<>(Arrays.asList(x+5,y-5,3)));
                newCrest.add(new ArrayList<>(Arrays.asList(x,y+5,1)));
                newCrest.add(new ArrayList<>(Arrays.asList(x-5,y+5,3)));
                break;
            default:
                newCrest.add(new ArrayList<>(Arrays.asList(x+5,y-5,2)));
                newCrest.add(new ArrayList<>(Arrays.asList(x+5,y,1)));
                newCrest.add(new ArrayList<>(Arrays.asList(x-5,y,2)));
                newCrest.add(new ArrayList<>(Arrays.asList(x-5,y+5,1)));
        }
        this.listOfCrestChildren = newCrest;
    }

    /*private void generateNewAdjacentCrest(int indexOfTheCrest,int[] coords){
        ArrayList<CrestIrrigation> newCrest = new ArrayList<>();
        int x = coords[0];
        int y = coords[1];
        switch(indexOfTheCrest){
            case 1 : case 4 :
                newCrest.add(new CrestIrrigation(x-5,y,3));
                newCrest.add(new int[]{x,y-5});
                newCrest.add(new int[]{x+5,y});
                newCrest.add(new int[]{x,y+5});
                break;
            case 2 : case 5 :
                newCrest.add(new int[]{x,y-5});
                newCrest.add(new int[]{x+5,y-5});
                newCrest.add(new int[]{x,y+5});
                newCrest.add(new int[]{x-5,y+5});
                break;
            default:
                newCrest.add(new int[]{x+5,y-5});
                newCrest.add(new int[]{x+5,y});
                newCrest.add(new int[]{x-5,y});
                newCrest.add(new int[]{x-5,y+5});
        }
        this.listOfCrestChildren = newCrest;
    }*/

    public int[] getCoordinateAdjacent(){
        int[] tabOfTheCoords = new int[3];
        int[] idOfTheThwBoxThatAreSharingThisCrest = new int[2];
        switch(this.order){
            case 1 :
                tabOfTheCoords[0] = this.coordinates[0]-5;
                tabOfTheCoords[1] = this.coordinates[1]+5;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[0] = HexagoneBox.generateID(tabOfTheCoords);

                tabOfTheCoords[0] = this.coordinates[0]+5;
                tabOfTheCoords[1] = this.coordinates[1]-5;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[1] = HexagoneBox.generateID(tabOfTheCoords);
                break;
            case 2 :
                tabOfTheCoords[0] = this.coordinates[0]-5;
                tabOfTheCoords[1] = this.coordinates[1];
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[0] = HexagoneBox.generateID(tabOfTheCoords);

                tabOfTheCoords[0] = this.coordinates[0]+5;
                tabOfTheCoords[1] = this.coordinates[1];
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[1] = HexagoneBox.generateID(tabOfTheCoords);
                break;
            default :
                tabOfTheCoords[0] = this.coordinates[0];
                tabOfTheCoords[1] = this.coordinates[1]-5;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[0] = HexagoneBox.generateID(tabOfTheCoords);

                tabOfTheCoords[0] = this.coordinates[0];
                tabOfTheCoords[1] = this.coordinates[1]+5;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[1] = HexagoneBox.generateID(tabOfTheCoords);
        }
        return idOfTheThwBoxThatAreSharingThisCrest;
    }
}
