package org.metahut.starfish.parser.exception;

/**
 *
 */
public class DataValidException extends AbstractMetaParserException {

    public DataValidException() {
    }

    public DataValidException(String message) {
        super(message);
    }

    public DataValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidException(Throwable cause) {
        super(cause);
    }

    public DataValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
