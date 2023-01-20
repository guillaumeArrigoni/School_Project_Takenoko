package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * List of all the possible actions
 */
public enum PossibleActions {
    DRAW_AND_PUT_TILE(0),
    MOVE_GARDENER(1),
    DRAW_OBJECTIVE(2);

    /**
     * The value of the action
     */
    PossibleActions(int i) {
    }

    /**
     * This method return the list of all the possible actions
     * @return the list of all the possible actions
     */
    public static List<PossibleActions> getAllActions() {
        return new ArrayList<>(Arrays.asList(PossibleActions.values()));
    }
}
