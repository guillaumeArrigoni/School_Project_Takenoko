package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BotTest {
    Bot bot;
    Board board;
    @BeforeEach
    void setUp() {
        board = new Board();
        bot = new Bot("testBot", board);
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