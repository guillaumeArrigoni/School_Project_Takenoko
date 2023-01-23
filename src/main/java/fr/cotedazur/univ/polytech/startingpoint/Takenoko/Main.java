package fr.cotedazur.univ.polytech.startingpoint.Takenoko;


import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;

import java.util.*;
//https://www.redblobgames.com/grids/hexagons/#coordinates
public class Main {
    public static void main(String... args) {
        RetrieveBoxIdWithParameters retrieving = new RetrieveBoxIdWithParameters();
        Board board = new Board(retrieving);
        MeteoDice meteoDice = new MeteoDice();
        Random random = new Random();
        GestionObjectives gestionnaire = new GestionObjectives(board, retrieving);
        Bot bot1 = new BotRandom("Bot1",board,random, meteoDice,gestionnaire, retrieving, new HashMap<Color,Integer>());
        Bot bot2 = new BotRandom("Bot2",board,random, meteoDice,gestionnaire, retrieving, new HashMap<Color,Integer>());
        List<Bot> playerList = new ArrayList<>();
        playerList.add(bot1);
        playerList.add(bot2);
        Game game = new Game(playerList,board);
        game.play(gestionnaire);
    }
}
