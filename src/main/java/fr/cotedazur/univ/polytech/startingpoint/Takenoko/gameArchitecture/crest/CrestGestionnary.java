package fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.CrestNotRegistered;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.crest.ImpossibleToPlaceIrrigationException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.lang.reflect.Array;
import java.util.*;


public class CrestGestionnary {

    protected HashMap<Crest,Integer> rangeFromIrrigated;
    protected HashMap<Crest, ArrayList<Crest>> linkCrestChildrenToCrestParent;
    protected HashMap<Crest, ArrayList<Crest>> linkCrestParentToCrestChildren;
    protected ArrayList<Crest> parentChildless; // parent with no children
    protected ArrayList<Integer> alreadyIrrigated; // for the hexagoneBox not place but that are irrigated
    protected ArrayList<Crest> listOfCrestIrrigated;
    // already irrigated if they were placed
    protected ArrayList<Crest> listOfCrestOneRangeToIrrigated;

    public HashMap<Crest, Integer> getRangeFromIrrigated() {
        return rangeFromIrrigated;
    }
    public int getRangeFromIrrigatedOfCrest(Crest crest) throws CrestNotRegistered {
        if (rangeFromIrrigated.containsKey(crest)){
            return rangeFromIrrigated.get(crest);
        }
        throw new CrestNotRegistered(crest);
    }
    public HashMap<Crest, ArrayList<Crest>> getLinkCrestChildrenToCrestParent() {
        return linkCrestChildrenToCrestParent;
    }
    public HashMap<Crest, ArrayList<Crest>> getLinkCrestParentToCrestChildren() {
        return linkCrestParentToCrestChildren;
    }
    public ArrayList<Crest> getParentChildless() {
        return parentChildless;
    }

    public ArrayList<Crest> getListOfCrestOneRangeToIrrigated() {
        return listOfCrestOneRangeToIrrigated;
    }
    public ArrayList<Crest> getListOfCrestIrrigated() {
        return listOfCrestIrrigated;
    }
    public ArrayList<Integer> getAlreadyIrrigated() {
        return alreadyIrrigated;
    }



    public CrestGestionnary(){
        this(true);
    }

    public CrestGestionnary(boolean bool){
        if (bool){
            setupGeneral();
        }
    }

    public CrestGestionnary copy(){
        CrestGestionnary copy = new CrestGestionnary();
        copy.linkCrestParentToCrestChildren = new HashMap<>(this.linkCrestParentToCrestChildren);
        copy.linkCrestChildrenToCrestParent = new HashMap<>(this.linkCrestChildrenToCrestParent);
        copy.rangeFromIrrigated = new HashMap<>(this.rangeFromIrrigated);
        copy.parentChildless = new ArrayList<>(this.parentChildless);
        copy.alreadyIrrigated = new ArrayList<>(this.alreadyIrrigated);
        copy.listOfCrestOneRangeToIrrigated = new ArrayList<>(this.listOfCrestOneRangeToIrrigated);
        return copy;
    }

    private void setupGeneral(){
        this.linkCrestParentToCrestChildren = new HashMap<>();
        this.linkCrestChildrenToCrestParent = new HashMap<>();
        this.rangeFromIrrigated = new HashMap<>();
        this.parentChildless = new ArrayList<>();
        this.alreadyIrrigated = new ArrayList<>();
        this.listOfCrestOneRangeToIrrigated = new ArrayList<>();
        this.listOfCrestIrrigated = new ArrayList<>();
    }

    public void launchUpdatingCrestWithAddingNewBox(HexagoneBoxPlaced box){
        this.updateCrestVariableWithNewBoxAdded(box);
    }

    private void setRangeToIrrigate(Crest crest, int value){
        this.rangeFromIrrigated.put(crest,value);
        crest.setRange_to_irrigation(value);
        if (value == 0){
            this.listOfCrestIrrigated.add(crest);
        }
    }


    /**
     * Ne gère pas le cas où une des 2 tuiles n'est pas placé
     * @param crest
     * @param placedBox
     */
    public void placeIrrigation(Crest crest, HashMap<Integer, HexagoneBoxPlaced> placedBox) throws ImpossibleToPlaceIrrigationException {
        if (irrigationCanBePlace(crest, placedBox)){
            crest.setIrrigated(true);
            setRangeToIrrigate(crest, 0);
            this.listOfCrestOneRangeToIrrigated.remove(crest);
            rewriteRangeToIrrigatedAfterNewIrrigation(crest);
            for (int i = 0; i<2;i++) {
                if (placedBox.containsKey(crest.getIdOfAdjacentBox()[i])) {
                    placedBox.get(crest.getIdOfAdjacentBox()[i]).setIrrigate(true);
                } else {
                    this.alreadyIrrigated.add(crest.getIdOfAdjacentBox()[i]);
                }
            }
        } else {
            throw new ImpossibleToPlaceIrrigationException(crest);
        }
    }

    private boolean irrigationCanBePlace(Crest crest, HashMap<Integer, HexagoneBoxPlaced> placedBox){
        return (this.listOfCrestOneRangeToIrrigated.contains(crest) && (
                placedBox.containsKey(crest.getIdOfAdjacentBox()[0]) ||
                placedBox.containsKey(crest.getIdOfAdjacentBox()[1]))
        );
    }


    /**
     * Method use to change the genealogy of Crest issue from the parent that has been change to irrigated
     * Method recursive :
     *      - call itself until we reach the end of a genealogy branch
     * @param parent : the new Irrigation that has been placed
     */
    private void rewriteRangeToIrrigatedAfterNewIrrigation(Crest parent){
        if (linkCrestParentToCrestChildren.containsKey(parent) && !linkCrestParentToCrestChildren.get(parent).isEmpty()){
            ArrayList<Crest> children = this.linkCrestParentToCrestChildren.get(parent);
            for (int i = 0; i<children.size();i++){
                Crest child = children.get(i);
                int candidateNewRange = rangeFromIrrigated.get(parent)+1;
                updateChildRangeIfLessOrEqualsThanBefore(parent, child, candidateNewRange);
                rewriteRangeToIrrigatedAfterNewIrrigation(child);
            }
        }
    }

    /**
     * Method use to change the child Crest if the potential new range to Irrigation is less or equals than before :
     *      - if equals :
     *              - add the parent to the list of parent in linkCrestChildrenToCrestParent
     *      - if less :
     *              - check if the parent range is 0 (he is irrigated) :
     *                      - add the child to the list of the next crest that can be irrigated
     *              - change rangeFromIrrigatedReversed with the new range
     *              - change linkCrestChildrenToCrestParent with only this parent associated
     * @param parent : the Crest parent
     * @param child : the Crest child
     * @param candidateNewValue : the new range that may be added to the child if the condition are passed
     */
    private int updateChildRangeIfLessOrEqualsThanBefore(Crest parent, Crest child, int candidateNewValue) {
        if (candidateNewValue == this.rangeFromIrrigated.get(child)){
            ArrayList<Crest> listOfParent = this.linkCrestChildrenToCrestParent.get(child);
            listOfParent.add(parent);
            listOfParent = eleminateDuplicate(listOfParent);
            this.linkCrestChildrenToCrestParent.put(child,listOfParent);
            if (rangeFromIrrigated.get(parent) == 0 && !this.listOfCrestOneRangeToIrrigated.contains(child)){ //second condition should never be false
                this.listOfCrestOneRangeToIrrigated.add(child);
                this.listOfCrestOneRangeToIrrigated = eleminateDuplicate(this.listOfCrestOneRangeToIrrigated);
            }
            return 0;
        } else if (candidateNewValue < this.rangeFromIrrigated.get(child)){
            if (rangeFromIrrigated.get(parent) == 0 && !this.listOfCrestOneRangeToIrrigated.contains(child)){ //second condition should never be false
                this.listOfCrestOneRangeToIrrigated.add(child);
                this.listOfCrestOneRangeToIrrigated = eleminateDuplicate(this.listOfCrestOneRangeToIrrigated);
            }
            setRangeToIrrigate(child, candidateNewValue);
            this.linkCrestChildrenToCrestParent.put(child, new ArrayList<Crest>(Arrays.asList(parent)));
            return 1;
        } else {
            return -1;
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
                newParentChildless = createAndImplementTheChildCrestOfTheParent(allCrestImplemented, newParentChildless, i);
            }
            newParentChildless = eleminateDuplicate(newParentChildless);
            this.parentChildless = newParentChildless;
        }
        for (Crest crest : newCrestToImplement){
            crest.setRange_to_irrigation(this.rangeFromIrrigated.get(crest));
        }
    }

    /**
     * Method use to
     * @param allCrestImplemented
     * @param newParentChildless
     * @param i
     * @return
     */
    private ArrayList<Crest> createAndImplementTheChildCrestOfTheParent(Set<Crest> allCrestImplemented, ArrayList<Crest> newParentChildless, int i) {
        Crest parent = this.parentChildless.get(i);
        ArrayList<Crest> listOfChildrenForParent = new ArrayList<>();
        for (int k = 0;k<parent.getListOfCrestChildren().size();k++){
            ArrayList<Integer> listOfParametersOfChild = parent.getListOfCrestChildren().get(k);
            Crest child = new Crest(listOfParametersOfChild.get(0),listOfParametersOfChild.get(1),listOfParametersOfChild.get(2));
            listOfChildrenForParent.add(child);
            newParentChildless = makeImplementationNeededForChildCrest(allCrestImplemented, newParentChildless, parent, listOfChildrenForParent, child);
        }
        listOfChildrenForParent = eleminateDuplicate(listOfChildrenForParent);
        this.linkCrestParentToCrestChildren.put(parent,listOfChildrenForParent);
        return newParentChildless;
    }

    /**
     *
     * @param allCrestImplemented
     * @param newParentChildless
     * @param parent
     * @param listOfChildrenForParent
     * @param child
     * @return
     */
    private ArrayList<Crest> makeImplementationNeededForChildCrest(Set<Crest> allCrestImplemented, ArrayList<Crest> newParentChildless, Crest parent, ArrayList<Crest> listOfChildrenForParent, Crest child) {
        if (allCrestImplemented.contains(child)){
            int candidateNewValue = this.rangeFromIrrigated.get(parent)+1;
            int compareTo = updateChildRangeIfLessOrEqualsThanBefore(parent, child, candidateNewValue);
            if (compareTo==-1){
                listOfChildrenForParent.remove(child);
            }
        } else {
            this.linkCrestChildrenToCrestParent.put(child,new ArrayList<>(Arrays.asList(parent)));
            int rangeOfParent = this.rangeFromIrrigated.get(parent);
            setRangeToIrrigate(child, rangeOfParent + 1);
            this.linkCrestParentToCrestChildren.put(child,new ArrayList<>());
            newParentChildless.add(child);
        }
        return newParentChildless;
    }

    /**
     *
     * @param box
     */
    private void updateCrestVariableWithNewBoxAdded(HexagoneBoxPlaced box){
        if (box.getColor() == Color.Lac){
            Crest fakeCrest = new Crest(99,99,1);
            for (int i=0;i<box.getListOfCrestAroundBox().size();i++){
                Crest crest = box.getListOfCrestAroundBox().get(i);
                linkCrestParentToCrestChildren.put(crest, new ArrayList<>());
                linkCrestChildrenToCrestParent.put(crest,new ArrayList<>(Arrays.asList(fakeCrest)));
                parentChildless.add(crest);
                listOfCrestOneRangeToIrrigated.add(crest);
                parentChildless = eleminateDuplicate(parentChildless);
                listOfCrestOneRangeToIrrigated = eleminateDuplicate(listOfCrestOneRangeToIrrigated);
                setRangeToIrrigate(crest, 0);
                crest.setIrrigated(true);
            }
            box.setIrrigate(true);
            ArrayList<Crest> newParentChildless = new ArrayList<>();
            //actualizeCrestVariable(box.getListOfCrestAroundBox());
            for (int i=0;i<box.getListOfCrestAroundBox().size();i++){
                newParentChildless = createAndImplementTheChildCrestOfTheParent(this.linkCrestParentToCrestChildren.keySet(),newParentChildless,i);
            }
            newParentChildless = eleminateDuplicate(newParentChildless);
            this.parentChildless = newParentChildless;
            //actualizeCrestVariable(new ArrayList<>(Arrays.asList(new Crest(5,-10,2))));
        } else {
            actualizeCrestVariable(box.getListOfCrestAroundBox());
        }
    }

    private ArrayList<Crest> eleminateDuplicate(ArrayList<Crest> listWithDuplicate){
        LinkedHashSet<Crest> set = new LinkedHashSet<>(listWithDuplicate);
        listWithDuplicate.clear();
        listWithDuplicate.addAll(set);
        return listWithDuplicate;
    }
}
