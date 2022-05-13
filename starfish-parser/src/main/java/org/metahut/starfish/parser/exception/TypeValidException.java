package org.metahut.starfish.parser.exception;

/**
 *
 */
public class TypeValidException extends AbstractMetaParserException {

    public TypeValidException() {
    }

    public TypeValidException(String message) {
        super(message);
    }

    public TypeValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeValidException(Throwable cause) {
        super(cause);
    }

    public TypeValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
