package org.metahut.starfish.api.enums;

public enum Status {

    SUCCESS(200, "success"),
    UNKNOWN_EXCEPTION(10000, "UNKNOWN_EXCEPTION"),

    COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL(20001, "COLLECTOR_ADAPTER_TEST_CONNECTION_FAIL")

    ;

    private int code;

    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
