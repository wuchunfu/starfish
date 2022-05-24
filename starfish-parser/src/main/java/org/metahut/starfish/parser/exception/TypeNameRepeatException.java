package org.metahut.starfish.parser.exception;

/**
 *
 */
public class TypeNameRepeatException extends AbstractMetaParserException {

    public TypeNameRepeatException() {
    }

    public TypeNameRepeatException(String message) {
        super(message);
    }

    public TypeNameRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeNameRepeatException(Throwable cause) {
        super(cause);
    }

    public TypeNameRepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
