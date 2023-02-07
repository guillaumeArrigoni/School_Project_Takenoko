package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import com.beust.jcommander.Parameter;
import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.opencsv.ICSVWriter;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotMCTS;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.text.DecimalFormat;
import java.util.*;

import com.beust.jcommander.JCommander;
//https://www.redblobgames.com/grids/hexagons/#coordinates

public class Main {

    //parameters for JCommander
    @Parameter(names={"--2thousands"}, arity=0)
    boolean twoThousands;
    @Parameter(names={"--demo"},arity=0)
    boolean demo;
    @Parameter(names={"--csv"}, arity=0)
    boolean csv;

    public static void main(String... args) throws IOException {

        //detection of arg for JCommander
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        if (main.twoThousands || main.csv) {
            int numberOfPlayer = 2;
            Log log = new Log();
            log.logInit(numberOfPlayer);
            for (int i = 0; i < 10; i++) {
                RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
                Board board = new Board(retrieving, 1);
                Random random = new Random();
                GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
                Bot bot1 = new BotMCTS("Bot1",board,gestionnaire, retrieving, new HashMap<Color,Integer>());
                Bot bot2 = new BotRandom("Bot2",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>());
                List<Bot> playerList = new ArrayList<>();
                playerList.add(bot1);
                playerList.add(bot2);
                Game game = new Game(playerList,board);
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
                CSVWriter writer = new CSVWriter(new FileWriter(file, true),
                        ' ',
                        ICSVWriter.NO_QUOTE_CHARACTER,
                        ICSVWriter.NO_ESCAPE_CHARACTER,
                        ICSVWriter.DEFAULT_LINE_END);


                int lineCount = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    while (reader.readLine() != null) {
                        lineCount++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] separator = new String[20];
                Arrays.fill(separator, "-");
                String[] winPercentage = winPercentageForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] meanScore = meanScoreForBots.stream().map(String::valueOf).toArray(String[]::new);
                String[] header = new String[numberOfPlayer+1];
                String[] firstLine = new String[numberOfPlayer+1];
                String[] secondLine = new String[numberOfPlayer+1];

                header[0] = "          ";
                firstLine[0] = "Winrate   ";
                secondLine[0] = "Score     ";
                String space;

                for (int i = 1; i < numberOfPlayer+1; i++) {
                    header[i] = "Bot" + i + "   ";
                    if (winPercentage[i-1].length() > 4) {
                        space = " ";
                    }
                    else if (winPercentage[i-1].length() == 4) {
                        space = "  ";
                    }
                    else {
                        space = "   ";
                    }
                    firstLine[i] = winPercentage[i-1] + "%" + space;
                    if (meanScore[i-1].length() > 3) {
                        space = "   ";
                    }
                    else {
                        space = "    ";
                    }
                    secondLine[i] = meanScore[i-1] + space;
                }
                writer.writeNext(new String[]{"Simulation " + (lineCount/5 + 1) + " :"});
                writer.writeNext(header);
                writer.writeNext(firstLine);
                writer.writeNext(secondLine);
                writer.writeNext(separator);
                writer.close();
            }
        }
        else if (main.demo || (!main.twoThousands && !main.csv)) {
            RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
            Board board = new Board(retrieving, 1);
            Random random = new Random();
            GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
            Bot bot1 = new BotMCTS("Bot1",board,gestionnaire, retrieving, new HashMap<Color,Integer>());
            Bot bot2 = new BotRandom("Bot2",board,random,gestionnaire, retrieving, new HashMap<Color,Integer>());
            List<Bot> playerList = new ArrayList<>();
            playerList.add(bot1);
            playerList.add(bot2);
            Game game = new Game(playerList,board);
            System.out.println(bot1.getBoard().getElementOfTheBoard().getStackOfBox());
            game.play(gestionnaire, "demo");
        }


    }
}
