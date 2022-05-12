package org.metahut.starfish.parser.exception;

/**
 *
 */
public class TypeNotPresentException extends AbstractMetaParserException {

    public TypeNotPresentException() {
    }

    public TypeNotPresentException(String message) {
        super(message);
    }

    public TypeNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeNotPresentException(Throwable cause) {
        super(cause);
    }

    public TypeNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
