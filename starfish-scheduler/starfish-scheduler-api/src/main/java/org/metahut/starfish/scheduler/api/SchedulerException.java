package org.metahut.starfish.scheduler.api;

public class SchedulerException extends RuntimeException {

    private Integer code;

    public SchedulerException(String message) {
        super(message);
    }

    public SchedulerException(String message, Throwable cause) {
        super(message, cause);
    }
}
