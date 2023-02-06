package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import java.util.logging.Level;

public class LogInfoStats extends Log{
    public LogInfoStats(boolean IsOn) {
        super(Level.INFO, LogInfoStats.class.getName());
        if (!IsOn){
            this.setOff();
        }
    }
}
