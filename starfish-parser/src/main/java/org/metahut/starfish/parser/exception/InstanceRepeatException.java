package org.metahut.starfish.parser.exception;

/**
 *
 */
public class InstanceRepeatException extends AbstractMetaParserException {

    public InstanceRepeatException() {
    }

    public InstanceRepeatException(String message) {
        super(message);
    }

    public InstanceRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceRepeatException(Throwable cause) {
        super(cause);
    }

    public InstanceRepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
