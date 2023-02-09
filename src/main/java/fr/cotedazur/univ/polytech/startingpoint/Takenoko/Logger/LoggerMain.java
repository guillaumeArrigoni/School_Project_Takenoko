package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger;

import fr.cotedazur.univ.polytech.startingpoint.Takenoko.bot.Bot;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.board.Board;
import fr.cotedazur.univ.polytech.startingpoint.Takenoko.gameArchitecture.hexagoneBox.HexagoneBoxPlaced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class LoggerMain {

    protected final java.util.logging.Logger logger;
    protected final ConsoleHandler handler;
    private Level level;
    private FormatterForLog formatterForLog;

    public LoggerMain(Level level, String name){
        this.logger = java.util.logging.Logger.getLogger(name);
        this.handler = new ConsoleHandler();
        this.level = level;
        this.formatterForLog = new FormatterForLog();
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
        handler.setFormatter(formatterForLog);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public void addLog(String msg){
        logger.log(level,msg);
    }

    public void displaySeparator(){
        this.addLog("------------------------------------------------");
    }
}
