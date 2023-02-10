package fr.cotedazur.univ.polytech.startingpoint.takenoko.logger;

import fr.cotedazur.univ.polytech.startingpoint.takenoko.MeteoDice;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.bot.PossibleActions;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.gamearchitecture.hexagonebox.HexagoneBoxPlaced;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.logger.LoggerMain;
import fr.cotedazur.univ.polytech.startingpoint.takenoko.objectives.Objective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class LogInfoDemo extends LoggerMain {

    public LogInfoDemo(boolean IsOn) {
        super(Level.INFO,LogInfoDemo.class.getName());
        if (!IsOn){
            this.setOff();
        }
    }

    public void printBoardState(Board board) {
        super.addLog("Voici l'état du board : ");
        ArrayList<HexagoneBoxPlaced> placedBox = board.getAllBoxPlaced();
        for (HexagoneBoxPlaced box : placedBox) {
            super.addLog(Arrays.toString(box.getCoordinates()) + " : bamboo de hauteur " + box.getHeightBamboo());
        }
        logger.log(Level.INFO," ");
    }

    public void printWinner(List<Bot> botWinnerList){
        String str = botWinnerList.get(0).getName();
        if(botWinnerList.size() >1){
            for(int i=1; i<botWinnerList.size(); i++){
                str += " et " + botWinnerList.get(i).getName();
            }
        }
        switch (botWinnerList.size()){
            case 1 -> super.addLog(str + " a gagné avec " + botWinnerList.get(0).getScore() + " points !");
            default -> super.addLog(str + " sont à égalité avec " + botWinnerList.get(0).getScore() + " points !");
        }
    }

    public void displayTextAction(PossibleActions action){
        switch (action){
            case DRAW_AND_PUT_TILE:
                super.addLog("Le bot a choisi : PiocherPoserTuile");
                break;
            case MOVE_GARDENER:
                super.addLog("Le bot a choisi : BougerJardinier");
                break;
            case DRAW_OBJECTIVE:
                super.addLog("Le bot a choisi : PiocherObjectif");
                break;
            case MOVE_PANDA:
                super.addLog("Le bot a choisi : BougerPanda");
                break;
            case TAKE_IRRIGATION :
                super.addLog("Le bot a choisi : PrendreIrrigation");
                break;
            case PLACE_IRRIGATION :
                super.addLog("Le bot a choisi : Placer une irrigation");
                break;
            case GROW_BAMBOO :
                super.addLog("Le bot a choisi : Faire pousser des bambou");
                break;
            case ADD_AUGMENT :
                super.addLog("Le bot a choisi : Prendre un jeton special");
                break;
            default :
                super.addLog("Le bot a choisi : BougerPanda");
        }
    }

    public void displayTextMeteo(MeteoDice.Meteo meteo){
        switch (meteo){
            case VENT -> {
                super.addLog("Le dé a choisi : VENT");
            }
            case PLUIE -> {
                super.addLog("Le dé a choisi : PLUIE");
            }
            case NUAGES -> {
                super.addLog("Le dé a choisi : NUAGES");
            }
            case ORAGE -> {
                super.addLog("Le dé a choisi : ORAGE");
            }
            default/*SOLEIL*/ -> {
                super.addLog("Le dé a choisi : SOLEIL");
            }
        }
    }

    public void displayMovementGardener(String name,Board board){
        super.addLog(name + " a déplacé le jardinier en " + Arrays.toString(board.getGardenerCoords()));
    }

    public void displayMovementPanda(String name,Board board){
        super.addLog(name + " a déplacé le panda en " + Arrays.toString(board.getPandaCoords()));
    }

    public void displayPlacementBox(String name, HexagoneBoxPlaced box){
        super.addLog(name + " a placé une tuile " + box.getColor() + " en " + Arrays.toString(box.getCoordinates()));
    }

    public void displayPickPatternObj(String name){
        super.addLog(name + " a pioché un objectif de parcelle");
    }

    public void displayPickGardenerObj(String name){
        super.addLog(name + " a pioché un objectif de panda");
    }

    public void displayPickPandaObj(String name){
        super.addLog(name + " a pioché un objectif de jardinier");
    }

    public void displayTurn(int turn){
        super.addLog("Tour n°" + turn + " :");;
    }

    public void displayObjFinish(Objective obj){
        super.addLog(obj.toString() + ", a été réalisé");;
    }

    public void displayPickObj(String name,Objective obj){
        super.addLog(name + " a pioché un nouvel objectif. ");
        super.addLog(obj.toString());
    }

    public void displayScore(Bot bot){
        super.addLog("Score de " + bot.getName() + " : " + bot.getScore());
    }
}
