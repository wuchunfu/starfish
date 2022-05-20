package org.metahut.starfish.parser.exception;

/**
 *
 */
public class TypeExistsException extends AbstractMetaParserException {

    public TypeExistsException() {
    }

    public TypeExistsException(String message) {
        super(message);
    }

    public TypeExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeExistsException(Throwable cause) {
        super(cause);
    }

    public TypeExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
