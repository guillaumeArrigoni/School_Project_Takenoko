package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger;

import java.util.logging.Level;

public class LogInfoStats extends LoggerMain {
    public LogInfoStats(boolean IsOn) {
        super(Level.INFO, LogInfoStats.class.getName());
        if (!IsOn){
            this.setOff();
        }
    }
}
