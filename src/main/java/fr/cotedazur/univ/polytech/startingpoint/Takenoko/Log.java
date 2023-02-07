package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Log {
    private int[] winsForBots;
    private int[] scoreForBots;
    private int numberOfGame;

    public void logInit(int numberOfPlayer) {
        winsForBots = new int[numberOfPlayer];
        scoreForBots = new int[numberOfPlayer];
        Arrays.fill(winsForBots, 0);
        Arrays.fill(scoreForBots, 0);
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
        DecimalFormat df = new DecimalFormat("0.0");
        System.out.println("------------------------------------------------");
        for (int i = 0; i < numberOfPlayer; i++) {
            System.out.println("Bot" + (i+1) + ":");
            System.out.println(" -Pourcentage de victoire : " + df.format(winPercentageForBots.get(i)) + "%");
            System.out.println(" -Score moyen : " + df.format(meanScoreForBots.get(i)));
            System.out.println("------------------------------------------------");
        }
    }
}
