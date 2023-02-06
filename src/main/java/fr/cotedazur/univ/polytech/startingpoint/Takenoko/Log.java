package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

public class Log {
    private int winsForBot1;
    private int winsForBot2;
    private int scoreForBot1;
    private int scoreForBot2;
    private int nubmerOfGame;

    public void logResult(int winner, int scoreBot1, int scoreBot2) {
        if (winner == 1) {
            winsForBot1++;
        } else if (winner == 2) {
            winsForBot2++;
        }
        scoreForBot1 += scoreBot1;
        scoreForBot2 += scoreBot2;

        nubmerOfGame++;
    }

    public float getWinPercentageForBot1() {
        return (float) winsForBot1 / nubmerOfGame * 100;
    }

    public float getWinPercentageForBot2() {
        return (float) winsForBot2 / nubmerOfGame * 100;
    }

    public float getMeanScoreForBot1() {
        return (float) scoreForBot1 / nubmerOfGame;
    }

    public float getMeanScoreForBot2() {
        return (float) scoreForBot2 / nubmerOfGame;
    }

}
