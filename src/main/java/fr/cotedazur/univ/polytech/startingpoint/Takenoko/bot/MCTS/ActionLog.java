package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.MCTS;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.PossibleActions;

import java.lang.reflect.Parameter;
import java.util.List;

public class ActionLog {
    PossibleActions action;
    int[] parameters;


    public ActionLog(PossibleActions action,int... parameters) {
        this.action = action;
        this.parameters = parameters;
    }

    public PossibleActions getAction() {
        return action;
    }

    public int[] getParameters() {
        return parameters;
    }
}
