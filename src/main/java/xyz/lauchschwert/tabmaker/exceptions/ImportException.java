package xyz.lauchschwert.tabmaker.exceptions;

import xyz.lauchschwert.tabmaker.logging.TmLogger;

public class ImportException extends Throwable {
    public ImportException(String message) {
        super(message);
    }
}
