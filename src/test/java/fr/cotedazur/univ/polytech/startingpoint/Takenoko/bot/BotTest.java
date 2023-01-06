package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.RetrieveBoxIdWithParameters;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BotTest {
    Bot bot;
    Board board;
    RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    @BeforeEach
    void setUp() {
        board = new Board();
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        bot = new Bot("testBot", board, retrieveBoxIdWithParameters);
    }

    @Test
    void placeRandomTile() {
        assertEquals(6, board.getAvailableBox().size());
        bot.placeRandomTile();
        assertEquals(2, board.getAvailableBox().size());
        bot.placeRandomTile();
        assertEquals(3, board.getAvailableBox().size());
    }
}