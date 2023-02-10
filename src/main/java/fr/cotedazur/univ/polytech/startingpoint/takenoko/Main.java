package fr.cotedazur.univ.polytech.startingpoint.takenoko;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.opencsv.exceptions.CsvException;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoDemo;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LogInfoStats;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerError;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotDFS;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.BotRuleBased;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.searching.RetrieveBoxIdWithParameters;

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
            int numberOfSimulation;
            int numberOfGame = 0;
            int numberOfPlayer = 4;
            Log log = new Log();
            log.logInit(numberOfPlayer,logInfoStats);
            if (main.twoThousands) numberOfSimulation = 1000;
            else numberOfSimulation = 100;
            for (int i = 0; i < numberOfSimulation; i++) {
                numberOfGame++;
                RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
                Board board = new Board(retrieving, 1, 2,loggerSevere);
                Random random = new Random();
                GestionObjectives gestionnaire = new GestionObjectives(board, retrieving, loggerError);
                gestionnaire.initialize(
                        gestionnaire.listOfObjectiveParcelleByDefault(),
                        gestionnaire.listOfObjectiveJardinierByDefault(),
                        gestionnaire.listOfObjectivePandaByDefault()
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
                ((BotRuleBased) bot2).setGame(game);
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

            if (main.twoThousands) {
                log = new Log();
                log.logInit(numberOfPlayer,logInfoStats);
                for (int i = 0; i < numberOfSimulation; i++) {
                    numberOfGame++;
                    RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
                    Board board = new Board(retrieving, 1, 2,loggerSevere);
                    GestionObjectives gestionnaire = new GestionObjectives(board, retrieving, loggerError);
                    gestionnaire.initialize(
                            gestionnaire.listOfObjectiveParcelleByDefault(),
                            gestionnaire.listOfObjectiveJardinierByDefault(),
                            gestionnaire.listOfObjectivePandaByDefault()
                    );
                    Bot bot1 = new BotDFS("BotDFS1",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                    Bot bot2 = new BotDFS("BotDFS2",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                    Bot bot3 = new BotDFS("BotDFS3",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);
                    Bot bot4 = new BotDFS("BotDFS4",board,gestionnaire, retrieving, new HashMap<Color,Integer>(),logDemo);

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
                winPercentageForBots = new ArrayList<>();
                meanScoreForBots = new ArrayList<>();

                for (int i = 0; i < numberOfPlayer; i++) {
                    winPercentageForBots.add(log.getWinPercentageForIndex(i));
                    meanScoreForBots.add(log.getMeanScoreForIndex(i));
                }

                log.printLog(numberOfPlayer, winPercentageForBots, meanScoreForBots);
            }

            if (main.csv) {
                String[] winPercentage = winPercentageForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] meanScore = meanScoreForBots.stream().map(String::valueOf).toArray(String[]::new);
                ArrayList<String[]> newData = new ArrayList<>();
                newData.add(winPercentage);
                newData.add(meanScore);
                CSVHandler csvHandler = new CSVHandler();
                csvHandler.writeNewData(newData,numberOfPlayer,numberOfGame,logInfoStats);
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
            ((BotRuleBased) bot2).setGame(game);
            game.play(gestionnaire, "demo");
        }
    }
}
