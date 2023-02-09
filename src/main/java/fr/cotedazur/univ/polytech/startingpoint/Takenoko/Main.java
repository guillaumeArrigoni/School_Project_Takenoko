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
//TODO remettre quand botRuleBased marche
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
    @Parameter(names={"--2thousands"}, arity=1)
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
            int numberOfGame = 0;
            int numberOfPlayer = 4;
            Log log = new Log();
            log.logInit(numberOfPlayer,logInfoStats);
            for (int i = 0; i < 10; i++) {
                numberOfGame++;
                RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
                Board board = new Board(retrieving, 1, 2);
                Random random = new Random();
                GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
                gestionnaire.initialize(
                        gestionnaire.ListOfObjectiveParcelleByDefault(),
                        gestionnaire.ListOfObjectiveJardinierByDefault(),
                        gestionnaire.ListOfObjectivePandaByDefault()
                );
                Bot bot1 = new BotMCTS("Bot1",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                Bot bot2 = new BotRuleBased("Bot2",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                Bot bot3 = new BotRandom("Bot3",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                Bot bot4 = new BotRandom("Bot4",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
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

                Scanner scanner = new Scanner(file);
                List<String[]> lines = new ArrayList<>();

                while (scanner.hasNextLine()) {
                    lines.add(scanner.nextLine().split(";"));
                }
                lines.remove(0);

                for (int i = 0; i < lines.size(); i++) {
                    List<String> tempLine = Arrays.asList(lines.get(i));
                    tempLine.remove(0);
                    lines.set(i, (String[]) tempLine.toArray());
                }

                String[] currentWinPercentage = lines.get(0);
                String[] currentMeanScore = lines.get(1);
                String[] currentGamePlayed = lines.get(2);

                String[] winPercentage = winPercentageForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] meanScore = meanScoreForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] header = new String[4];
                String[] firstLine = new String[4];
                String[] secondLine = new String[4];
                String[] thirdLine = new String[4];
                firstLine[0] = "";
                secondLine[0] = "";
                thirdLine[0] = "";

                for (int i = 1; i < 5; i++) {
                    header[i] = "Bot" + i;
                }
                for (int i = 1; i < numberOfPlayer+1; i++) {
                    firstLine[i] = ((Double.parseDouble(winPercentage[i-1])*numberOfGame + Double.parseDouble(currentWinPercentage[i-1])*Double.parseDouble(currentGamePlayed[i-1]))/(numberOfGame + Double.parseDouble(currentGamePlayed[i-1])) + "%");
                    secondLine[i] = ((Double.parseDouble(meanScore[i-1])*numberOfGame + Double.parseDouble(currentMeanScore[i-1])*Double.parseDouble(currentGamePlayed[i-1]))/(numberOfGame + Double.parseDouble(currentGamePlayed[i-1])) + "");
                    thirdLine[i] = ((Double.parseDouble(currentGamePlayed[i-1])+numberOfGame) + "");
                }
                writer.writeNext(header);
                writer.writeNext(firstLine);
                writer.writeNext(secondLine);
                writer.writeNext(thirdLine);
                writer.close();
                file.delete();
                tempfile.renameTo(file);
            }
        }
        else if (main.demo || (!main.csv && !main.twoThousands)) {
            RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
            Board board = new Board(retrieving, 1,2);
            Random random = new Random();
            GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
            Bot bot1 = new BotMCTS("BotMCTS",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
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
