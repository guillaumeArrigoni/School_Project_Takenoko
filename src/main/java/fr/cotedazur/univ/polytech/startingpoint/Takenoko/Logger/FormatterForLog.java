package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger;

import java.util.logging.*;

public class FormatterForLog extends Formatter {

    private final String ANSI_WHITE = "\u001B[37m";
    private final String ANSI_WHITE_Brighter = "\033[97m";

    public static final String ANSI_RED = "\033[1;31m";
    public static final String ANSI_ORANGE = "\033[38;5;208m";

    private Level level;

    public FormatterForLog(Level level){
        this.level = level;
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        switch (this.level.getName()){
            case "INFO" -> builder.append(ANSI_WHITE_Brighter);
            case "SEVERE" -> builder.append(ANSI_RED);
            case "WARNING" -> builder.append(ANSI_RED);
        }
        builder.append(ANSI_WHITE_Brighter);
        builder.append(record.getMessage());
        builder.append("\n");
        return builder.toString();
    }
}
