package org.metahut.starfish.parser.exception;

/**
 *
 */
public class TypeConvertException extends AbstractMetaParserException {

    public TypeConvertException() {
    }

    public TypeConvertException(String message) {
        super(message);
    }

    public TypeConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeConvertException(Throwable cause) {
        super(cause);
    }

    public TypeConvertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
