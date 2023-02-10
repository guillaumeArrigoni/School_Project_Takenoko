package fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gameArchitecture.crest.Crest;

import java.util.ArrayList;
import java.util.List;

public class ActionLogIrrigation extends  ActionLog{
    private final List<ArrayList<Crest>> paramirrig;

    public ActionLogIrrigation(PossibleActions action, List<ArrayList<Crest>> paramirrig, int... parameters) {
        super(action, parameters);
        this.paramirrig = paramirrig;
    }

    public List<ArrayList<Crest>> getParamirrig() {
        return paramirrig;
    }


}
