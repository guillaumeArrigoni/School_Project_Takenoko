package fr.cotedazur.univ.polytech.startingpoint.Takenoko.Logger;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class FormatterForLog extends Formatter {

    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_WHITE2 = "\033[97m";

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(ANSI_WHITE2);
        //builder.append(record.getMessage());
        //builder.append("\n");
        return builder.toString();
    }
}
