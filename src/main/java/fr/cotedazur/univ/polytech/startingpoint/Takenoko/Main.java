package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import com.beust.jcommander.Parameter;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import java.nio.file.*;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotMCTS;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
//https://www.redblobgames.com/grids/hexagons/#coordinates

public class Main {
    @Parameter(names={"--2thousands"}, arity=0)
    boolean twoThousands;
    @Parameter(names={"--demo"},arity=0)
    boolean demo;
    @Parameter(names={"--csv"}, arity=0)
    boolean csv;

    public static void main(String... args) throws IOException,CloneNotSupportedException {
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath("./stats/", "stats.csv");
        System.out.println(path.toString());
        File file = new File(path.toString());
        CSVWriter writer = new CSVWriter(new FileWriter(file));

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
                MeteoDice meteoDice = new MeteoDice();
                Random random = new Random();
                GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
                gestionnaire.initialize(
                        gestionnaire.ListOfObjectiveParcelleByDefault(),
                        gestionnaire.ListOfObjectiveJardinierByDefault(),
                        gestionnaire.ListOfObjectivePandaByDefault()
                );
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
            //String[] gameStats = {df.format(winPercentageForBot1), df.format(winPercentageForBot2), df.format(meanScoreForBot1), df.format(meanScoreForBot2)};

            //writer.writeNext(gameStats);

        }
        else if (main.demo || (!main.twoThousands && !main.csv)) {
            RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
            Board board = new Board(retrieving, 1);
            MeteoDice meteoDice = new MeteoDice();
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

        writer.close();

    }
}
