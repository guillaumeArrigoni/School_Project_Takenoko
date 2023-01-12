package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;


public class Board {

    /**
     * Return the number of box placed in the board
     * WARNING : the lake is counted in the number of box placed
     */
    int numberBoxPlaced ;
    
    /**
     * PlacedBox is a Hashmap that contain in key all the box's id already place in the board
     * and associate for each one their range to the center of the board (lake)
     * Type :
     *      - int[] : coordinates of the placed box
     *      - Integer : range to the lake
     */
    private HashMap<Integer, HexagoneBox> placedBox;
    
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
    private final RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;

    private HashMap<Integer,ArrayList<Crest>> rangeFromIrrigated;
    private HashMap<Crest,Integer> rangeFromIrrigated2;
    private HashMap<Crest, ArrayList<Crest>> linkCrestChildrenToCrestParent;
    private HashMap<Crest, ArrayList<Crest>> linkCrestParentToCrestChildren;
    private ArrayList<Crest> parentChildless; // parent with no children
    private ArrayList<HexagoneBox> alreadyIrrigated; // for the hexagoneBox not place but that are
                                                    // already irrigated if they were placed


    public ArrayList<HexagoneBox> getAlreadyIrrigated() {
        return alreadyIrrigated;
    }

    /**
     * TODO put parent value to 0, to do in the method that call this one
     * @param parent : the new Irrigation that has been placed
     */
    private void rewriteRangeToIrrigatedAfterNewIrrigation(Crest parent){
        ArrayList<Crest> children = this.linkCrestParentToCrestChildren.get(parent);
        if (!children.isEmpty()){
            for (int i = 0; i<children.size();i++){
                Crest child = children.get(i);
                int candidateNewValue = rangeFromIrrigated2.get(parent)+1;
                if (candidateNewValue<this.rangeFromIrrigated2.get(child)){
                    this.rangeFromIrrigated2.put(child,candidateNewValue);
                }
                rewriteRangeToIrrigatedAfterNewIrrigation(child);
            }
        }
    }

    /**
     * Method use to add to the different Hashmap about Crest the last Crest add thanks to the newBox placed.
     * In order to do that :
     *      - The method add new Crest from the parent crest with their children not Implemented
     *      until the new list of Crest is Implemented
     *      - The Hashmap to link the parent to the children is updated
     *      - The Hashmap to link the Crest to its range to the irrigation is updated
     *
     * @param newCrestToImplement : the listOfCrest link to the last box add
     *                            and that have to be implemented in the different Hashmap of Crest
     */
    private void actualizeCrestVariable(ArrayList<Crest> newCrestToImplement){
        Set<Crest> allCrestImplemented = this.linkCrestParentToCrestChildren.keySet();
        while (!this.linkCrestParentToCrestChildren.keySet().containsAll(newCrestToImplement)){
            ArrayList<Crest> newParentChildless = new ArrayList<>();
            for(int i = 0; i< this.parentChildless.size(); i++){
                Crest parent = this.parentChildless.get(i);
                ArrayList<Crest> listOfChildForParent = new ArrayList<>();
                for (int k = 0;k<parent.getListOfCrestChildren().size();k++){
                    ArrayList<Integer> listOfParameters = parent.getListOfCrestChildren().get(k);
                    listOfChildForParent.add(new Crest(listOfParameters.get(0),listOfParameters.get(1),listOfParameters.get(2)));
                }
                listOfChildForParent.removeAll(allCrestImplemented);
                this.linkCrestParentToCrestChildren.put(parent,listOfChildForParent);
                for (int j=0;j<listOfChildForParent.size();j++){
                    Crest child = listOfChildForParent.get(j);
                    int rangeOfParent = this.rangeFromIrrigated2.get(parent);
                    this.rangeFromIrrigated2.put(child,rangeOfParent + 1);
                    this.linkCrestParentToCrestChildren.put(child,new ArrayList<>());
                    newParentChildless.add(child);
                }
            }
            this.parentChildless = newParentChildless;
        }
    }

    private void updateCrestVariableWithNewBoxAdded(HexagoneBox box){
        actualizeCrestVariable(box.getListOfCrestAroundBox());
    }


    /**
     * Ne gère pas le cas où une des 2 tuiles n'est pas placé
     * @param crest
     */
    public void placeIrrigation(Crest crest){
        crest.setIrrigated(true);
        this.rangeFromIrrigated2.put(crest,0);
        rewriteRangeToIrrigatedAfterNewIrrigation(crest);
        for (int i = 0; i<2;i++) {
            if (this.placedBox.containsKey(crest.getCoordinateAdjacent()[i])) {
                this.placedBox.get(crest.getCoordinateAdjacent()[i]).setIrrigate(true);
            } else {
                this.alreadyIrrigated.add(placedBox.get(crest.getCoordinateAdjacent()[i]));
            }
        }
    }


    public Board(RetrieveBoxIdWithParameters retrieveBoxIdWithParameters){
        this.retrieveBoxIdWithParameters = retrieveBoxIdWithParameters;
        this.placedBox = new HashMap<>();
        this.linkCrestParentToCrestChildren = new HashMap<>();
        this.rangeFromIrrigated2 = new HashMap<>();
        this.parentChildless = new ArrayList<>();
        this.alreadyIrrigated = new ArrayList<>();
        HexagoneBox lac = new HexagoneBox(0,0,0, Color.Lac, Special.Classique, retrieveBoxIdWithParameters,this);
        this.numberBoxPlaced = 1;

        this.AvailableBox = new ArrayList<>();
        for (int i=1;i<7;i++){
            this.AvailableBox.add(lac.getAdjacentBoxOfIndex(i));
        }
        
        this.placedBox.put(lac.getId(),lac);
        this.gardenerCoords = new int[]{0,0,0};
    }

    public int[] getGardenerCoords() {
        return this.gardenerCoords;
    }
    public int[] getPandaCoords() {return this.pandaCoords;}
    public HexagoneBox getBoxWithCoordinates(int[] coords) {
        return this.placedBox.get(HexagoneBox.generateID(coords));
    }
    public int getNumberBoxPlaced() {
        return numberBoxPlaced;
    }
    public HashMap<Integer, HexagoneBox> getPlacedBox() {
        return placedBox;
    }
    public ArrayList<HexagoneBox> getAllBoxPlaced() {
        return new ArrayList<>(this.placedBox.values());
    }
    public ArrayList<int[]> getAvailableBox(){
        return this.AvailableBox;
    }
    
    public void setPandaCoords(int[] newCoords, Bot bot) {
        this.pandaCoords = newCoords;
        HexagoneBox box = getBoxWithCoordinates(newCoords);
        if (box.getSpecial()!=Special.Protéger && box.getHeightBamboo()>0) {
            Optional<Color> bambooEatedColor = box.eatBamboo();
            if (bambooEatedColor.isPresent()){
                bot.addBambooAte(bambooEatedColor.get());
            }
        }
    }
    public void setGardenerCoords(int[] coords) {
        this.gardenerCoords = coords;
        growAfterMoveOfTheGardener(getBoxWithCoordinates(coords));
    }

    public boolean isCoordinateInBoard(int[] Coord) {
        return this.placedBox.containsKey(HexagoneBox.generateID(Coord));
    }

    /**
     * Method use to :
     *      Add the HexagoneBox entered into the Hashmap PlacedBox.
     *      Update the ArrayList AvaiableBox with the new box avalaible
     *      Delete the new box add in AvailableBox.
     *      Add the new adjacent box from the box placed and any other box placed before in the ArrayList AvailableBox
     * @param box : the new Hexagone box to add to the board
     */
    public void addBox(HexagoneBox box) {
        int[] coord = box.getCoordinates();
        int[] newCoord1, newCoord2;
        UpdateAvaiableBoxAndPlacedBox(box);
        for (int i=1;i<7;i++){
            int[] adjacentCoord = box.getAdjacentBoxOfIndex(i);
            if (isCoordinateInBoard(adjacentCoord)) {
                generateNewAdjacentBox(coord, adjacentCoord);
            }
        }
    }

    /**
     * Method use to :
     *      generate the adjacent box of 2 box (that are put as parameters in this method)
     *      check if the nex box can be add in the ArrayList AvailableBox
     * @param coord the coordinate of a box from which we want the new adjacent box
     * @param adjacentCoord the coordinate of the second box in order to get the adjacent box
     */
    private void generateNewAdjacentBox(int[] coord, int[] adjacentCoord) {
        int[] newCoord1;
        int[] newCoord2;
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
        addNewBoxInAvailableBox(newCoord1);
        addNewBoxInAvailableBox(newCoord2);
    }

    private void generateNewAdjacentBox2(int[] coord, int[] adjacentCoord) {
        int[] newCoord1;
        int[] newCoord2;
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
        addNewBoxInAvailableBox(newCoord1);
        addNewBoxInAvailableBox(newCoord2);
    }

    /**
     * Method use to add in AvailableBox a box if it is possible
     * @param newCoord the coordinate of the box we want to add in AvailableBox
     */
    private void addNewBoxInAvailableBox(int[] newCoord) {
        if (!(isCoordinateInBoard(newCoord)) && !(AvailableBox.contains(newCoord))) {
            AvailableBox.add(newCoord);
        }
    }

    /**
     * Method use to grow the bamboo in the adjacentBox of the one where the gardener is placed
     * @param box where the gardener is placed
     */
    private void growAfterMoveOfTheGardener(HexagoneBox box){
        if (box.isIrrigate() &&
                !Arrays.equals(box.getCoordinates(), new int[]{0,0,0}) &&
                box.getHeightBamboo()<4) box.growBamboo();
        HashMap<Integer, int[]> adjacentBox = box.getAdjacentBox();
        for (int[] coordinate : adjacentBox.values()) {
            if (isCoordinateInBoard(coordinate)) {
                HexagoneBox newBox = getBoxWithCoordinates(coordinate);
                if (this.placedBox.containsKey(newBox.getId()) &&
                        newBox.isIrrigate() &&
                        newBox.getColor() == box.getColor() &&
                        newBox.getHeightBamboo() < 4) newBox.growBamboo();

            }
        }
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
        placedBox.put(box.getId(),box);
    }







}
