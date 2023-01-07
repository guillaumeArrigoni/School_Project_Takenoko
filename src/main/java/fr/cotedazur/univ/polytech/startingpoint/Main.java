package fr.cotedazur.univ.polytech.startingpoint;

public class Main {

    public static String hello() {
        return "Hello World!";
    }

    /*public static void printBoardState(Board board) {
        int nbLigne = 5;
        ArrayList<HexagoneBox> placedBox = board.getPlacedBox();
        for (Map.Entry tile : placedBox.entrySet()) {
            System.out.print(Arrays.toString((int[])tile.getKey()));
        }
        System.out.println(" ");
    }

    public static void main(String... args) {
        Board board = new Board();
        Random random = new Random();
        Bot bot1 = new Bot("Bot1",board,random);
        Bot bot2 = new Bot("Bot2",board,random);
        MeteoDice meteoDice = new MeteoDice();
        System.out.println("Que la partie commence !");
        boolean playing = true;
        int turn = 0;

        while (playing) {
            MeteoDice.Meteo meteo = meteoDice.roll();
            System.out.println("Le dé a choisi : " + meteo);
            if (turn == 0) {
                bot1.placeRandomTile();
                bot1.placeRandomTile();
            }
            else {
                bot2.placeRandomTile();
                bot2.placeRandomTile();
            }
            turn = 1 - turn;
            if (board.getNumberBoxPlaced() == 11) {playing = false;}
            System.out.println("------------------------------------------");
        }

        printBoardState(board);
        System.out.println(Action.possibleMoveForGardener(board, board.getGardenerCoords()));
    }
*/

}
