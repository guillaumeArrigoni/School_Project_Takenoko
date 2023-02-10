package fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerSevere;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBox;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Color;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.enumBoxProperties.Special;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StackOfBoxTest {

    static ElementOfTheBoard elementOfTheBoard;
    static StackOfBox stackOfBox;
    static HexagoneBox hexagoneBox;

    @BeforeEach
    @Order(1)
    public void setupGeneral(){
        elementOfTheBoard = new ElementOfTheBoard(new LoggerSevere(true));
        stackOfBox = new StackOfBox(elementOfTheBoard.defaultInstructionBox);
        hexagoneBox = new HexagoneBox(Color.VERT, Special.CLASSIQUE);
    }

    @Test
    void testAddNewBox() {
        stackOfBox.addNewBox(hexagoneBox);
        ArrayList<HexagoneBox> listBox = stackOfBox.stackOfBox;
        assertEquals(hexagoneBox,listBox.get(listBox.size()-1));
    }

    @Test
    void testGetFirstBox() {
        stackOfBox.addNewBox(hexagoneBox);
        final int nbTour = stackOfBox.stackOfBox.size()-1;
        for (int i=0;i<nbTour;i++){
            stackOfBox.getFirstBox();
        }
        assertEquals(hexagoneBox,stackOfBox.getFirstBox());
    }
}