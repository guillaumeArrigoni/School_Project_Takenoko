package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class LoggerMain {

    protected final java.util.logging.Logger logger;
    protected final ConsoleHandler handler;
    private Level level;

    public LoggerMain(Level level, String name){
        this.logger = java.util.logging.Logger.getLogger(name);
        this.handler = new ConsoleHandler();
        this.level = level;
        setup();
    }

    public LoggerMain(String name){
        this(Level.INFO,name);
    }

    protected void setOff(){
        this.level = Level.OFF;
    }

    private void setup(){
        handler.setLevel(level);
        handler.setLevel(level);
        logger.addHandler(handler);
    }

    public void addLog(String msg){
        logger.log(level,msg);
    }

}

