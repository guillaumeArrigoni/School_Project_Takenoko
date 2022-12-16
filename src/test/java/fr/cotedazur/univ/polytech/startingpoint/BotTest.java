package fr.cotedazur.univ.polytech.startingpoint;

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
        bot = new Bot("testBot");
    }

    @Test
    void placeRandomTile() {
        int size = board.getAvailableBox().size();
        assertEquals(6, board.getAvailableBox().size());
        HexagoneBox test = bot.placeRandomTile(board);
        assertEquals(2, board.getAvailableBox().size());
        HexagoneBox test1 = bot.placeRandomTile(board);
        assertEquals(3, board.getAvailableBox().size());
    }
}