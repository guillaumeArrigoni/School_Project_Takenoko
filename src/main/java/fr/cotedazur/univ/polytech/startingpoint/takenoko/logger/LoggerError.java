package fr.cotedazur.univ.polytech.startingpoint.takenoko.logger;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.exception.TakenokoException;

import java.util.logging.Level;

public class LoggerError extends LoggerMain {

    public LoggerError(boolean IsOn) {
        super(Level.WARNING, LogInfoStats.class.getName());
        if (!IsOn){
            this.setOff();
        }
    }

    public void logErrorTitle(TakenokoException e){
        super.addLog("\n  -> An error has occurred : " + e.getErrorTitle() + "\n");
    }
}