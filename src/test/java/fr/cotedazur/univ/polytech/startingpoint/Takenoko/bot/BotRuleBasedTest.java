package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Game;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.enumBoxProperties.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives.GestionObjectives;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class BotRuleBasedTest {
    BotRuleBased botRB;
    Board board;
    Random r;
    MeteoDice meteoDice;
    RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    final String arg = "demo";

    GestionObjectives gestionObjectives;

    @BeforeEach
    void setUp() {
        this.retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        board = new Board(retrieveBoxIdWithParameters, 1);
        gestionObjectives = new GestionObjectives(board, retrieveBoxIdWithParameters);
        gestionObjectives.initialize(
                gestionObjectives.ListOfObjectiveParcelleByDefault(),
                gestionObjectives.ListOfObjectiveJardinierByDefault(),
                gestionObjectives.ListOfObjectivePandaByDefault());
        r = mock(Random.class);
        meteoDice = mock(MeteoDice.class);
        botRB = new BotRuleBased("testBot", board, r,  gestionObjectives, retrieveBoxIdWithParameters, new HashMap<Color,Integer>());
    }

    @Test
    void choseMoveForPanda() {
        HexagoneBoxPlaced hexagoneBoxPlaced = new HexagoneBoxPlaced(1,-1,0,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced2 = new HexagoneBoxPlaced(1,0,-1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced3 = new HexagoneBoxPlaced(0,1,-1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced4 = new HexagoneBoxPlaced(-1,1,0,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced5 = new HexagoneBoxPlaced(-1,0,1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        HexagoneBoxPlaced hexagoneBoxPlaced6 = new HexagoneBoxPlaced(0,-1,1,Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        board.addBox(hexagoneBoxPlaced);
        board.addBox(hexagoneBoxPlaced2);
        board.addBox(hexagoneBoxPlaced3);
        board.addBox(hexagoneBoxPlaced4);
        board.addBox(hexagoneBoxPlaced5);
        board.addBox(hexagoneBoxPlaced6);
        hexagoneBoxPlaced.setHeightBamboo(0);
        hexagoneBoxPlaced2.setHeightBamboo(0);
        hexagoneBoxPlaced3.setHeightBamboo(0);
        hexagoneBoxPlaced4.setHeightBamboo(0);
        hexagoneBoxPlaced6.setHeightBamboo(0);
        assertEquals(Arrays.toString((new int[]{-1, 0, 1})), Arrays.toString(botRB.choseMoveForPanda()));
    }
}
