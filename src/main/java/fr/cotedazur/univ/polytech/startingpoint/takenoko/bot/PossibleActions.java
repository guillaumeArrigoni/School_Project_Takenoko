package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * List of all the possible actions
 */
public enum PossibleActions {
    DRAW_AND_PUT_TILE(),
    MOVE_GARDENER(),
    DRAW_OBJECTIVE(),
    MOVE_PANDA(),
    TAKE_IRRIGATION(),
    PLACE_IRRIGATION(),
    GROW_BAMBOO(),
    ADD_AUGMENT();

    /**
     * The value of the action
     */
    PossibleActions() {}

    /**
     * This method return the list of all the possible actions
     * @return the list of all the possible actions
     */
    public static List<PossibleActions> getAllActions() {
        return new ArrayList<>(Arrays.asList(PossibleActions.values()));
    }
}
