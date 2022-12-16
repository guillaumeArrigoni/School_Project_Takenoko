package fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Color;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.Interface.Special;

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

}
