package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoStats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Log {
    private int[] winsForBots;
    private int[] scoreForBots;
    private int numberOfGame;
    private LogInfoStats logInfoStats;

    public void logInit(int numberOfPlayer, LogInfoStats logInfoStats) {
        winsForBots = new int[numberOfPlayer];
        scoreForBots = new int[numberOfPlayer];
        Arrays.fill(winsForBots, 0);
        Arrays.fill(scoreForBots, 0);
        this.logInfoStats = logInfoStats;
    }

    public void logResult(int winner, int[] score) {
        for (int i = 0; i < score.length; i++) {
            if (i+1==winner) {
                winsForBots[i]++;
            }
            scoreForBots[i] += score[i];
        }
        numberOfGame++;
    }

    public float getWinPercentageForIndex(int index) {
        return (float) winsForBots[index] / numberOfGame * 100;
    }

    public float getMeanScoreForIndex(int index) {
        return (float) scoreForBots[index] / numberOfGame;
    }

    public void printLog(int numberOfPlayer, ArrayList<Float> winPercentageForBots, ArrayList<Float> meanScoreForBots) {
        logInfoStats.printLog(numberOfPlayer,winPercentageForBots,meanScoreForBots);
    }
}
