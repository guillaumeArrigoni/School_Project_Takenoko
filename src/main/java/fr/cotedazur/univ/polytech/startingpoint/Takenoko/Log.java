package fr.cotedazur.univ.polytech.startingpoint.Takenoko;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    protected final Logger logger;
    protected final ConsoleHandler handler;
    private Level level;

    public Log(Level level, String name){
        this.logger = Logger.getLogger(name);
        this.handler = new ConsoleHandler();
        this.level = level;
        setup();
    }

    public Log(String name){
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
