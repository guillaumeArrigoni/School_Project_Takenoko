package fr.cotedazur.univ.polytech.startingpoint.Takenoko.objectives;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.allInterface.Special;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.BotRandom;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.ElementOfTheGame;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.searching.RetrieveBoxIdWithParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GestionObjectivesTest {


    private static HexagoneBox Greenbox1;
    private static HexagoneBox Greenbox2;
    private static HexagoneBox Greenbox3;
    private static HexagoneBox Greenbox4;
    private static HexagoneBox Greenbox5;
    private static HexagoneBox Greenbox6;
    private static HexagoneBox Yellowbox;
    private static HexagoneBox Yellowbox2;
    private static HexagoneBox YellowEngraisbox;
    private static HexagoneBox RedProtegerbox;
    private static HexagoneBox Redbox;
    private static Objective triangleVert;
    private static Objective ligneVert;
    private static Objective courbeVert;
    private static Objective losangeVert;
    private static Objective losangeVertJaune;
    private static Objective planterBambouJauneClassique;
    private static Objective planterBambouJauneEngrais;
    private static Objective planterBambouRougeProteger;
    private static Objective planterDeuxBambousRouges;
    private static Objective pandaVert;
    private static Objective pandaJaune;
    private static Objective pandaRouge;
    private static Objective pandaTricolore;

    private static Board board;
    private static Bot bot;
    private static MeteoDice meteoDice;
    private static Random random;
    private static GestionObjectives gestionObjectives;
    private static RetrieveBoxIdWithParameters retrieveBoxIdWithParameters;
    private static ElementOfTheGame elementOfTheGame;

    @BeforeAll
    public static void setupBox() {
        retrieveBoxIdWithParameters = new RetrieveBoxIdWithParameters();
        elementOfTheGame = new ElementOfTheGame();
        board = new Board(retrieveBoxIdWithParameters);
        meteoDice = new MeteoDice();
        random = new Random();
        gestionObjectives = new GestionObjectives(board,retrieveBoxIdWithParameters);
        gestionObjectives.initialize();
        bot = new BotRandom("Bot",board,random, meteoDice,gestionObjectives, retrieveBoxIdWithParameters, new HashMap<Color,Integer>());
        Greenbox1 = new HexagoneBox(-1, 1, 0, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        Greenbox2 = new HexagoneBox(0, 1, -1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        Greenbox3 = new HexagoneBox(-1, 2, -1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        Greenbox4 = new HexagoneBox(-1, 0, 1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        Greenbox5 = new HexagoneBox(0, -1, 1, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        Greenbox6 = new HexagoneBox(-2, 2, 0, Color.Vert, Special.Classique, retrieveBoxIdWithParameters,board);
        Yellowbox = new HexagoneBox(0, 2, -2, Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        Yellowbox2 = new HexagoneBox(1, 1, -2, Color.Jaune, Special.Classique, retrieveBoxIdWithParameters,board);
        Redbox = new HexagoneBox(1,-1,0,Color.Rouge,Special.Classique,retrieveBoxIdWithParameters,board);
        YellowEngraisbox = new HexagoneBox(1,0,-1,Color.Jaune,Special.Engrais,retrieveBoxIdWithParameters,board);
        RedProtegerbox = new HexagoneBox(-2,1,1,Color.Rouge,Special.Protéger,retrieveBoxIdWithParameters,board);
        triangleVert = Objective.POSER_TRIANGLE_VERT;
        ligneVert = Objective.POSER_LIGNE_VERTE;
        courbeVert = Objective.POSER_COURBE_VERTE;
        losangeVert = Objective.POSER_LOSANGE_VERT;
        losangeVertJaune = Objective.POSER_LOSANGE_VERT_JAUNE;
        planterBambouJauneClassique = Objective.PLANTER_SUR_CLASSIQUE_BAMBOU_JAUNE;
        planterBambouJauneEngrais = Objective.PLANTER_SUR_ENGRAIS_BAMBOU_JAUNE;
        planterBambouRougeProteger = Objective.PLANTER_SUR_PROTEGER_BAMBOU_ROUGE;
        planterDeuxBambousRouges = Objective.PLANTER_DEUX_BAMBOUS_ROUGES;
        bot.getObjectives().add(triangleVert);
        bot.getObjectives().add(ligneVert);
        bot.getObjectives().add(planterBambouJauneClassique);
        bot.getObjectives().add(planterBambouRougeProteger);
        bot.getObjectives().add(planterDeuxBambousRouges);
        board.addBox(Greenbox1);
        board.addBox(Greenbox2);
        board.addBox(Greenbox3);
        board.addBox(Greenbox4);
        board.addBox(Greenbox5);
        board.addBox(Greenbox6);
        board.addBox(Yellowbox);
        board.addBox(Yellowbox2);
        board.addBox(Redbox);
        board.addBox(YellowEngraisbox);
        board.addBox(RedProtegerbox);
        Yellowbox.growBamboo();
        Yellowbox.growBamboo();
        Yellowbox.growBamboo();
        Yellowbox.growBamboo();
        YellowEngraisbox.growBamboo();
        YellowEngraisbox.growBamboo();
        YellowEngraisbox.growBamboo();
        YellowEngraisbox.growBamboo();
        RedProtegerbox.growBamboo();
        RedProtegerbox.growBamboo();
        RedProtegerbox.growBamboo();
        RedProtegerbox.growBamboo();
        Redbox.growBamboo();
        Redbox.growBamboo();
        Redbox.growBamboo();

        bot.addBambooEaten(Color.Vert);
        bot.addBambooEaten(Color.Vert);
        bot.addBambooEaten(Color.Vert);
        bot.addBambooEaten(Color.Jaune);
        bot.addBambooEaten(Color.Jaune);
        bot.addBambooEaten(Color.Jaune);
        bot.addBambooEaten(Color.Rouge);
        bot.addBambooEaten(Color.Rouge);
        bot.addBambooEaten(Color.Rouge);
        pandaJaune = Objective.MANGER_DEUX_JAUNES_1;
        pandaRouge = Objective.MANGER_DEUX_ROUGES_1;
        pandaVert = Objective.MANGER_DEUX_VERTS_1;
        pandaTricolore = Objective.MANGER_TRICOLORE_1;
    }

    private static Stream<Arguments> provideParcelleObjectiveChecking(){
        return Stream.of(
                Arguments.of(ligneVert,true),
                Arguments.of(triangleVert,true),
                Arguments.of(courbeVert,true),
                Arguments.of(losangeVert,true),
                Arguments.of(losangeVertJaune,true)
                );
    }

    private static Stream<Arguments> providePandaObjectiveChecking(){
        return Stream.of(
                Arguments.of(pandaJaune,true),
                Arguments.of(pandaJaune,false),
                Arguments.of(pandaVert,true),
                Arguments.of(pandaVert,false),
                Arguments.of(pandaRouge,true),
                Arguments.of(pandaRouge,false),
                Arguments.of(pandaTricolore,true),
                Arguments.of(pandaTricolore,false)
        );
    }

    @Test
    void initialize() {
        GestionObjectives gestionObjectives2 = new GestionObjectives(board, retrieveBoxIdWithParameters);
        gestionObjectives2.initialize();
        assertEquals(15, gestionObjectives2.getParcelleObjectifs().size());
        assertEquals(15, gestionObjectives2.getJardinierObjectifs().size());
        assertEquals(15, gestionObjectives2.getPandaObjectifs().size());

    }

    @Test
    void getParcelleObjectifs() {
        GestionObjectives gestionObjectives2 = new GestionObjectives(board, retrieveBoxIdWithParameters);
        gestionObjectives2.initialize();
        assertEquals(15, gestionObjectives2.getParcelleObjectifs().size());
    }

    @Test
    void getJardinierObjectifs() {
        GestionObjectives gestionObjectives2 = new GestionObjectives(board, retrieveBoxIdWithParameters);
        gestionObjectives2.initialize();
        assertEquals(15, gestionObjectives2.getJardinierObjectifs().size());
    }

    @Test
    void getPandaObjectifs() {
        GestionObjectives gestionObjectives2 = new GestionObjectives(board, retrieveBoxIdWithParameters);
        gestionObjectives2.initialize();
        assertEquals(15, gestionObjectives2.getPandaObjectifs().size());
    }

    @Test
    void rollObjective() {
        Bot botRoll = new BotRandom("botRoll", board,random,meteoDice,gestionObjectives,retrieveBoxIdWithParameters,new HashMap<Color,Integer>());
        for(int i = 0;i<5; i++){
            gestionObjectives.rollObjective(botRoll);
        }
        assertEquals(5, botRoll.getObjectives().size());

    }

    @Test
    void rollParcelleObjective() {
        Bot botRoll = new BotRandom("botRoll", board,random,meteoDice,gestionObjectives,retrieveBoxIdWithParameters,new HashMap<Color,Integer>());
        gestionObjectives.rollParcelleObjective(botRoll);
        assertEquals(TypeObjective.PARCELLE, botRoll.getObjectives().get(0).getType());

    }

    @Test
    void rollJardinierObjective() {
        Bot botRoll = new BotRandom("botRoll", board,random,meteoDice,gestionObjectives,retrieveBoxIdWithParameters,new HashMap<Color,Integer>());
        gestionObjectives.rollJardinierObjective(botRoll);
        assertEquals(TypeObjective.JARDINIER,botRoll.getObjectives().get(0).getType());
    }

    @Test
    void rollPandaObjective() {
        Bot botRoll = new BotRandom("botRoll", board,random,meteoDice,gestionObjectives,retrieveBoxIdWithParameters,new HashMap<Color,Integer>());
        gestionObjectives.rollPandaObjective(botRoll);
        assertEquals(TypeObjective.PANDA,botRoll.getObjectives().get(0).getType());
    }
    @Test
    void checkIfBotCanDrawAnObjective() {
        bot.getObjectives().add(triangleVert);
        bot.getObjectives().add(ligneVert);
        bot.getObjectives().add(planterBambouJauneClassique);
        bot.getObjectives().add(planterBambouRougeProteger);
        bot.getObjectives().add(planterDeuxBambousRouges);
        assertFalse(gestionObjectives.checkIfBotCanDrawAnObjective(bot));
    }

    @Test
    void checkObjectives() {
        assertEquals(5, bot.getObjectives().size());
        gestionObjectives.checkObjectives(bot);
        assertEquals(1, bot.getObjectives().size());
    }

    @ParameterizedTest
    @MethodSource("providePandaObjectiveChecking")
    void testCheckPandaObjectives(Objective objective,boolean isCorrect) {
        assertEquals(gestionObjectives.checkPandaObjectives(objective,this.bot),isCorrect);
    }

    @Test
    void checkJardinierObjectives() {
        assertTrue(gestionObjectives.checkJardinierObjectives(planterBambouJauneClassique));
        assertTrue(gestionObjectives.checkJardinierObjectives(planterBambouJauneEngrais));
        assertTrue(gestionObjectives.checkJardinierObjectives(planterBambouRougeProteger));
        assertFalse(gestionObjectives.checkJardinierObjectives(planterDeuxBambousRouges));

    }


    @ParameterizedTest
    @MethodSource("provideParcelleObjectiveChecking")
    void testCheckParcelleObjectives(Objective objective,boolean isCorrect) {
        assertEquals(gestionObjectives.checkParcelleObjectives(objective),isCorrect);
    }

    @Test
    void chooseTypeObjectiveByCheckingUnknownObjectives() {
        TypeObjective res = gestionObjectives.chooseTypeObjectiveByCheckingUnknownObjectives(bot);
        assertTrue(res == TypeObjective.PARCELLE);
    }
}