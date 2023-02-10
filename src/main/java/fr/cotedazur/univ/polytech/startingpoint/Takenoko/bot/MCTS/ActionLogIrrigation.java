package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.crest.Crest;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an action and its parameters for irrigation
 */
public class ActionLogIrrigation extends  ActionLog{
    /**
     * The parameters of the action
     */
    private final List<ArrayList<Crest>> paramirrig;

    public ActionLogIrrigation(PossibleActions action, List<ArrayList<Crest>> paramirrig, int... parameters) {
        super(action, parameters);
        this.paramirrig = paramirrig;
    }

    public List<ArrayList<Crest>> getParamirrig() {
        return paramirrig;
    }


}
