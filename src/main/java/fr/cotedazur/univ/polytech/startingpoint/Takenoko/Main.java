package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.nio.file.*;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger.LogInfoStats;
import com.opencsv.ICSVWriter;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotMCTS;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRuleBased;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.text.DecimalFormat;
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
    public static void main(String... args) throws IOException,CloneNotSupportedException {
        //detection of arg for JCommander
        Main main = new Main();
        LogInfoDemo logDemo = new LogInfoDemo(main.demo || (!main.twoThousands && !main.csv));
        LogInfoStats logInfoStats = new LogInfoStats(main.twoThousands || main.csv);
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        if (main.twoThousands || main.csv) {
            int numberOfPlayer = 2;
            Log log = new Log();
            log.logInit(numberOfPlayer,logInfoStats);
            for (int i = 0; i < 10; i++) {
                RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
                Board board = new Board(retrieving, 1);
                Random random = new Random();
                GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
                gestionnaire.initialize(
                        gestionnaire.ListOfObjectiveParcelleByDefault(),
                        gestionnaire.ListOfObjectiveJardinierByDefault(),
                        gestionnaire.ListOfObjectivePandaByDefault()
                );
                Bot bot1 = new BotMCTS("Bot1",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                Bot bot2 = new BotRandom("Bot2",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                List<Bot> playerList = new ArrayList<>();
                playerList.add(bot1);
                playerList.add(bot2);
                Game game = new Game(playerList,board,logDemo);
                int winner = game.play(gestionnaire, "twoThousands");

                int[] scoreForBots = new int[]{bot1.getScore(), bot2.getScore()};
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
                CSVWriter writer = new CSVWriter(new FileWriter(file, true));


                int lineCount = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        lineCount++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] winPercentage = winPercentageForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] meanScore = meanScoreForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] header = new String[numberOfPlayer+1];
                String[] firstLine = new String[numberOfPlayer+1];
                String[] secondLine = new String[numberOfPlayer+1];
                firstLine[0] = "";
                secondLine[0] = "";

                for (int i = 1; i < numberOfPlayer+1; i++) {
                    header[i] = "Bot" + i;
                    firstLine[i] = winPercentage[i-1] + "%";
                    secondLine[i] = meanScore[i-1];
                }
                writer.writeNext(new String[]{"Simulation " + (lineCount/5 + 1) + " :"});
                writer.writeNext(header);
                writer.writeNext(firstLine);
                writer.writeNext(secondLine);
                writer.close();
            }
        }
        else if (main.demo || (!main.csv && !main.twoThousands)) {
            RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
            Board board = new Board(retrieving, 1);
            Random random = new Random();
            GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
            //Bot bot1 = new BotMCTS("Bot1",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
            Bot bot1 = new BotRuleBased("Bot1",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
            Bot bot2 = new BotRandom("Bot2",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
            List<Bot> playerList = new ArrayList<>();
            playerList.add(bot1);
            playerList.add(bot2);
            Game game = new Game(playerList,board,logDemo);
            game.play(gestionnaire, "demo");
        }
    }
}
