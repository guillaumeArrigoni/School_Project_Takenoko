package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Action {

    public static HexagoneBox drawTile() {
        int random = ThreadLocalRandom.current().nextInt(0,3);
        Color color = switch (random) {
            case 1 -> Color.Vert;
            case 2 -> Color.Rouge;
            default -> Color.Jaune;
        };
        return new HexagoneBox(color, Special.Classique);
    }

    public static ArrayList<int[]> possibleMoveForGardener(Board board, int[] gardenerCoords) {
        int x = gardenerCoords[0], y = gardenerCoords[1], z = gardenerCoords[2];
        ArrayList<int[]> possibleMove = new ArrayList<>();
        boolean possible = true;
        int count = 0;
        int[] newCoord = new int[]{};
        for (int i=0;i<6;i++) {
            while (possible) {
                switch(i) {
                    case 0:
                        newCoord = new int[]{x,y+count,z-count};
                        break;
                    case 1:
                        newCoord = new int[]{x,y-count,z+count};
                        break;
                    case 2:
                        newCoord = new int[]{x+count,y,z-count};
                        break;
                    case 3:
                        newCoord = new int[]{x-count,y+count,z+count};
                        break;
                    case 4:
                        newCoord = new int[]{x-count,y+count,z};
                        break;
                    case 5:
                        newCoord = new int[]{x+count,y-count,z};
                        break;
                    default:
                        newCoord = new int[]{0,0,0};
                        break;
                }

                if (!board.containsKey(board.getPlacedBox(),newCoord)) possible=false;
                else {
                    possibleMove.add(newCoord);
                    count++;
                }
            }
            possible = true;
            count = 0;
        }


        return possibleMove;
    }
}
