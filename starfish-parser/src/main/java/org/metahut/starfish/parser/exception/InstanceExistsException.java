package org.metahut.starfish.parser.exception;

/**
 *
 */
public class InstanceExistsException extends AbstractMetaParserException {

    public InstanceExistsException() {
    }

    public InstanceExistsException(String message) {
        super(message);
    }

    public InstanceExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceExistsException(Throwable cause) {
        super(cause);
    }

    public InstanceExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
