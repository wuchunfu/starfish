package org.metahut.starfish.parser.exception;

/**
 *
 */
public class InstanceNameNullException extends AbstractMetaParserException {

    public InstanceNameNullException() {
    }

    public InstanceNameNullException(String message) {
        super(message);
    }

    public InstanceNameNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceNameNullException(Throwable cause) {
        super(cause);
    }

    public InstanceNameNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
