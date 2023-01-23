package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Crest implements Comparable<Crest> {

    private int range_to_irrigation;
    private int id;
    private int[] coordinates;
    private ArrayList<ArrayList<Integer>> listOfCrestChildren;
    private int order;
    private boolean isIrrigated;
    private int[] idOfAdjacentBox;

    public int getRange_to_irrigation() {
        return range_to_irrigation;
    }

    public int getId() {
        return id;
    }

    public int[] getIdOfAdjacentBox(){
        return this.idOfAdjacentBox;
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
        this.range_to_irrigation = -1;
        this.isIrrigated = false;
        generateNewAdjacentCrest();
        generateCoordinatesOfAdjacentBoxToACrest();
    }

    public void setRange_to_irrigation(int range){
        this.range_to_irrigation = range;
    }
    public void setOrder(int x){
        this.order = x;
    }

    public int generateID(int[] coordinate){
        int id = 1000000;
        for (int i=0;i<2;i++){
            if (coordinate[i]<0){
                id = id + (1000 +  coordinate[i]) * (int) Math.pow(1000,i);
            } else {
                id = id + coordinate[i] * (int) Math.pow(1000,i);
            }
        }
        return id;
    }

    public int[] separateID(int id) {
        int[] tab = new int[2];
        tab[1] = (id % 1000000) / 1000;
        tab[0] = id % 1000;
        for (int i=0; i<2; i++) {
            if (tab[i] > 500) tab[i]=tab[i]-1000;
        }
        return tab;
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
                newCrest.add(new ArrayList<>(Arrays.asList(x-5,y+5,2)));
                newCrest.add(new ArrayList<>(Arrays.asList(x-5,y,1)));
        }
        this.listOfCrestChildren = newCrest;
    }

    private void generateCoordinatesOfAdjacentBoxToACrest(){
        int[] tabOfTheCoords = new int[3];
        int[] idOfTheThwBoxThatAreSharingThisCrest = new int[2];
        switch(this.order){
            case 1 :
                tabOfTheCoords[0] = (this.coordinates[0]-5)/10;
                tabOfTheCoords[1] = (this.coordinates[1]+5)/10;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[0] = HexagoneBox.generateID(tabOfTheCoords);

                tabOfTheCoords[0] = (this.coordinates[0]+5)/10;
                tabOfTheCoords[1] = (this.coordinates[1]-5)/10;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[1] = HexagoneBox.generateID(tabOfTheCoords);
                break;
            case 2 :
                tabOfTheCoords[0] = (this.coordinates[0]-5)/10;
                tabOfTheCoords[1] = (this.coordinates[1])/10;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[0] = HexagoneBox.generateID(tabOfTheCoords);

                tabOfTheCoords[0] = (this.coordinates[0]+5)/10;
                tabOfTheCoords[1] = (this.coordinates[1])/10;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[1] = HexagoneBox.generateID(tabOfTheCoords);
                break;
            default :
                tabOfTheCoords[0] = (this.coordinates[0])/10;
                tabOfTheCoords[1] = (this.coordinates[1]-5)/10;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[0] = HexagoneBox.generateID(tabOfTheCoords);

                tabOfTheCoords[0] = (this.coordinates[0])/10;
                tabOfTheCoords[1] = (this.coordinates[1]+5)/10;
                tabOfTheCoords[2] = -tabOfTheCoords[0] - tabOfTheCoords[1];
                idOfTheThwBoxThatAreSharingThisCrest[1] = HexagoneBox.generateID(tabOfTheCoords);
        }
        this.idOfAdjacentBox =  idOfTheThwBoxThatAreSharingThisCrest;
    }

    @Override
    public int compareTo(Crest crest) {
        if (this.id == crest.id){
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object object){
        if (object == this) {
            return true;
        }
        if (!(object instanceof Crest)) {
            return false;
        }
        Crest secondCrest = (Crest) object;

        return (secondCrest.id == this.id);
    }

    @Override
    public String toString(){
        return String.valueOf(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
