package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Action {

    public static HexagoneBox drawTile(Random random) {
        Color color = switch (random.nextInt(0,3)) {
            case 1 -> Color.Vert;
            case 2 -> Color.Rouge;
            default -> Color.Jaune;
        };
        System.out.println("drawTile : " + color);
        return new HexagoneBox(color, Special.Classique);
    }

    public static ArrayList<int[]> possibleMoveForGardenerOrPanda(Board board, int[] coord) {
        int x = coord[0];
        int y = coord[1];
        int z = coord[2];
        ArrayList<int[]> possibleMove = new ArrayList<>();
        boolean possible = true;
        int count = 1;
        int[] newCoord;
        for (int i=0;i<6;i++) {
            while (possible) {
                newCoord = switch (i) {
                    case 0 -> new int[]{x, y + count, z - count};
                    case 1 -> new int[]{x, y - count, z + count};
                    case 2 -> new int[]{x + count, y, z - count};
                    case 3 -> new int[]{x - count, y, z + count};
                    case 4 -> new int[]{x - count, y + count, z};
                    case 5 -> new int[]{x + count, y - count, z};
                    default -> new int[]{0, 0, 0};
                };

                if (!board.coordInBoard(newCoord)) possible=false;
                else {
                    possibleMove.add(newCoord);
                    count++;
                }
            }
            possible = true;
            count = 1;
        }
        return possibleMove;
    }

    public static void moveGardener(Board board, int[] coords) {
        board.setGardenerCoords(coords);
    }

    public static void movePanda(Board board, int[] coords) {
        board.setPandaCoords(coords);
    }



}
