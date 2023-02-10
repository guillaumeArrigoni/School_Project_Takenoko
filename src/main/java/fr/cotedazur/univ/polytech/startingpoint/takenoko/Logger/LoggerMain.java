package fr.cotedazur.univ.polytech.startingpoint.takenoko.Logger;

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
        this.formatterForLog = new FormatterForLog(this.level);
        setup();
    }

    public LoggerMain(String name){
        this(Level.INFO,name);
    }

    protected void setOff(){
        this.level = Level.OFF;
        setup();
    }

    private void setup(){
        for (Handler handler : logger.getHandlers()){
            logger.removeHandler(handler);
        }
        handler.setLevel(level);
        handler.setFormatter(formatterForLog);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }


    public void addLog(String msg){
        setup();
        logger.log(level,msg);
    }

    public void displaySeparator(){
        setup();
        this.addLog("------------------------------------------------");
    }
}
