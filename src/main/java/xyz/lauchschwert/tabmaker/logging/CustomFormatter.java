package xyz.lauchschwert.tabmaker.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        return String.format("[%s] %s - %s: %s%n",
                LocalDateTime.now().format(TIMESTAMP_FORMAT),
                record.getLevel(),
                record.getLoggerName().substring(record.getLoggerName().lastIndexOf('.') + 1),
                record.getMessage()
        );
    }
}