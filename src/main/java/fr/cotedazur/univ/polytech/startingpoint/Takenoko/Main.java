package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import com.beust.jcommander.Parameter;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import java.nio.file.*;

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


    @Parameter(names={"--2thousands"}, arity=0)
    boolean twoThousands;
    @Parameter(names={"--demo"},arity=0)
    boolean demo;
    @Parameter(names={"--csv"}, arity=0)
    boolean csv;

    public static void main(String... args) throws IOException {
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
            Log log = new Log();
            for (int i = 0; i < 10; i++) {
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
                int winner = game.play(gestionnaire, "twoThousands");

                log.logResult(winner, bot1.getScore(), bot2.getScore());
            }

            float winPercentageForBot1 = log.getWinPercentageForBot1();
            float winPercentageForBot2 = log.getWinPercentageForBot2();

            float meanScoreForBot1 = log.getMeanScoreForBot1();
            float meanScoreForBot2 = log.getMeanScoreForBot2();

            DecimalFormat df = new DecimalFormat("0.0");

            System.out.println("------------------------------------------------");
            System.out.println("Bot1:");
            System.out.println(" -Pourcentage de victoire : " + df.format(winPercentageForBot1) + "%");
            System.out.println(" -Score moyen : " + df.format(meanScoreForBot1));
            System.out.println("------------------------------------------------");
            System.out.println("Bot2:");
            System.out.println(" -Pourcentage de victoire : " + df.format(winPercentageForBot2) + "%");
            System.out.println(" -Score moyen : " + df.format(meanScoreForBot2));
            System.out.println("------------------------------------------------");

            String[] gameStats = {df.format(winPercentageForBot1), df.format(winPercentageForBot2), df.format(meanScoreForBot1), df.format(meanScoreForBot2)};

            writer.writeNext(gameStats);

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
