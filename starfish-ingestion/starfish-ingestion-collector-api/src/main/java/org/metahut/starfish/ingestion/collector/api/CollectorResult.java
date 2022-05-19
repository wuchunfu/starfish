package org.metahut.starfish.ingestion.collector.api;

public class CollectorResult {

    private boolean state;

    private String message;

    public CollectorResult() {
    }

    public CollectorResult(boolean state) {
        this.state = state;
    }

    public CollectorResult(boolean state, String message) {
        this.state = state;
        this.message = message;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
