package fr.cotedazur.univ.polytech.startingpoint.takenoko.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;

public class LogInfoStats extends LoggerMain {
    public LogInfoStats(boolean IsOn) {
        super(Level.INFO, LogInfoStats.class.getName());
        if (!IsOn){
            this.setOff();
        }
    }

    public void printLog(int numberOfPlayer, ArrayList<Float> winPercentageForBots, ArrayList<Float> meanScoreForBots) {
        DecimalFormat df = new DecimalFormat("0.0");
        super.displaySeparator();
        for (int i = 0; i < numberOfPlayer; i++) {
            super.addLog("Bot" + (i + 1) + ":");
            super.addLog(" -Pourcentage de victoire : " + df.format(winPercentageForBots.get(i)) + "%");
            super.addLog(" -Score moyen : " + df.format(meanScoreForBots.get(i)));
            super.displaySeparator();
        }
    }
}
