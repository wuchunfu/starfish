package org.metahut.starfish.parser.exception;

/**
 *
 */
public class InstanceNotPresentException extends AbstractMetaParserException {

    public InstanceNotPresentException() {
    }

    public InstanceNotPresentException(String message) {
        super(message);
    }

    public InstanceNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceNotPresentException(Throwable cause) {
        super(cause);
    }

    public InstanceNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
