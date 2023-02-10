package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.exception.TakenokoException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;

public class LoggerSevere extends LoggerMain {

    public LoggerSevere(boolean IsOn) {
        super(Level.SEVERE, LogInfoStats.class.getName());
        if (!IsOn){
            this.setOff();
        }
    }

    public void logErrorTitle(TakenokoException e){
        super.addLog("\n  -> An error has occurred : " + e.getErrorTitle() + "\n");
    }
}