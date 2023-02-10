package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.opencsv.exceptions.CsvException;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoStats;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotDFS;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRuleBased;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;
//https://www.redblobgames.com/grids/hexagons/#coordinates

public class Main {
    //parameters for JCommander
    @Parameter(names={"--2thousands"}, arity=0)
    boolean twoThousands;
    @Parameter(names={"--demo"},arity=0)
    boolean demo;
    @Parameter(names={"--csv"}, arity=0)
    boolean csv;

    public static void main(String... args) throws IOException, CloneNotSupportedException, CsvException {
        //detection of arg for JCommander
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        LogInfoDemo logDemo = new LogInfoDemo(main.demo || (!main.twoThousands && !main.csv));
        LogInfoStats logInfoStats = new LogInfoStats(main.twoThousands || main.csv);
        LoggerError loggerError = new LoggerError(main.demo || (!main.twoThousands && !main.csv));
        LoggerSevere loggerSevere = new LoggerSevere(main.demo || (!main.twoThousands && !main.csv));

        if (main.twoThousands || main.csv) {
            int numberOfGame = 0;
            int numberOfPlayer = 4;
            Log log = new Log();
            log.logInit(numberOfPlayer,logInfoStats);
            for (int i = 0; i < 50; i++) {
                numberOfGame++;
                RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
                Board board = new Board(retrieving, 1, 2,loggerSevere);
                Random random = new Random();
                GestionObjectives gestionnaire = new GestionObjectives(board, retrieving, loggerError);
                gestionnaire.initialize(
                        gestionnaire.ListOfObjectiveParcelleByDefault(),
                        gestionnaire.ListOfObjectiveJardinierByDefault(),
                        gestionnaire.ListOfObjectivePandaByDefault()
                );
                Bot bot1 = new BotDFS("BotDFS",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                Bot bot2 = new BotRuleBased("BotRB",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                Bot bot3 = new BotRandom("BotRandom1",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                Bot bot4 = new BotRandom("BotRandom2",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                List<Bot> playerList = new ArrayList<>();
                playerList.add(bot1);
                playerList.add(bot2);
                playerList.add(bot3);
                playerList.add(bot4);
                Game game = new Game(playerList,board,logDemo);
                int winner = game.play(gestionnaire, "twoThousands");

                int[] scoreForBots = new int[]{bot1.getScore(), bot2.getScore(), bot3.getScore(), bot4.getScore()};
                log.logResult(winner, scoreForBots);
            }

            ArrayList<Float> winPercentageForBots = new ArrayList<>();
            ArrayList<Float> meanScoreForBots = new ArrayList<>();

            for (int i = 0; i < numberOfPlayer; i++) {
                winPercentageForBots.add(log.getWinPercentageForIndex(i));
                meanScoreForBots.add(log.getMeanScoreForIndex(i));
            }

            log.printLog(numberOfPlayer, winPercentageForBots, meanScoreForBots);

            if (main.csv) {
                //creation of a writer for csv
                FileSystem fs = FileSystems.getDefault();
                Path path = fs.getPath("./stats/", "stats.csv");
                File file = new File(path.toString());
                File tempfile = new File("temp_" + file.getName());
                CSVWriter writer = new CSVWriter(new FileWriter(tempfile, true));

                CSVReader reader = new CSVReader(new FileReader(file));
                List<String[]> lines = new ArrayList<>();

                int lineCount = 0;
                String line = null;
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                    while ((line = bufferedReader.readLine()) != null) {
                        lineCount++;
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }

                String[] currentWinPercentage;
                String[] currentMeanScore;
                String[] currentGamePlayed;

                String[] winPercentage = winPercentageForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] meanScore = meanScoreForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] header = new String[] {"", "BotDFS", "BotRB", "BotRandom1", "BotRandom2"};
                String[] firstLine = new String[5];
                String[] secondLine = new String[5];
                String[] thirdLine = new String[5];

                firstLine[0] = "Winrate";
                secondLine[0] = "Score moyen";
                thirdLine[0] = "Nombre de partie";

                if (lineCount > 0) {
                    lines = reader.readAll();
                    lines.remove(0);

                    for (int i = 0; i < lines.size(); i++) {
                        ArrayList<String> tempLine = new ArrayList<>(Arrays.asList(lines.get(i)));
                        tempLine.remove(0);
                        lines.set(i, tempLine.toArray(new String[0]));
                    }

                    currentWinPercentage = lines.get(0);
                    currentMeanScore = lines.get(1);
                    currentGamePlayed = lines.get(2);

                    for (int i = 1; i < numberOfPlayer+1; i++) {
                        firstLine[i] = String.valueOf((Double.parseDouble(winPercentage[i-1])*numberOfGame + Double.parseDouble(currentWinPercentage[i-1])*Double.parseDouble(currentGamePlayed[i-1]))/(numberOfGame + Double.parseDouble(currentGamePlayed[i-1])));
                        secondLine[i] = String.valueOf((Double.parseDouble(meanScore[i-1])*numberOfGame + Double.parseDouble(currentMeanScore[i-1])*Double.parseDouble(currentGamePlayed[i-1]))/(numberOfGame + Double.parseDouble(currentGamePlayed[i-1])));
                        thirdLine[i] = String.valueOf((Double.parseDouble(currentGamePlayed[i-1])+numberOfGame));
                    }
                }
                else {
                    for (int i = 1; i < numberOfPlayer+1; i++) {
                        firstLine[i] = winPercentage[i-1];
                        secondLine[i] = meanScore[i-1];
                        thirdLine[i] = String.valueOf(numberOfGame);
                    }
                }

                writer.writeNext(header);
                writer.writeNext(firstLine);
                writer.writeNext(secondLine);
                writer.writeNext(thirdLine);
                writer.close();
                reader.close();
                if (!file.delete()) logInfoStats.addLog("Erreur de suppression du fichier");
                if (!tempfile.renameTo(file)) logInfoStats.addLog("Erreur rename du fichier");
            }
        }
        else {
            RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
            Board board = new Board(retrieving, 1,2,loggerSevere);
            Random random = new Random();
            GestionObjectives gestionnaire = new GestionObjectives(board, retrieving,loggerError);
            Bot bot1 = new BotDFS("BotDFS",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
            Bot bot2 = new BotRuleBased("BotRB",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
            Bot bot3 = new BotRandom("BotRandom1",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
            Bot bot4 = new BotRandom("BotRandom2",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
            List<Bot> playerList = new ArrayList<>();
            playerList.add(bot1);
            playerList.add(bot2);
            playerList.add(bot3);
            playerList.add(bot4);
            Game game = new Game(playerList,board,logDemo);
            game.play(gestionnaire, "demo");
        }
    }
}
