package org.metahut.starfish.parser.exception;

/**
 *
 */
public class ModelValidException extends AbstractMetaParserException {

    public ModelValidException() {
    }

    public ModelValidException(String message) {
        super(message);
    }

    public ModelValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelValidException(Throwable cause) {
        super(cause);
    }

    public ModelValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
