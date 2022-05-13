package org.metahut.starfish.api.enums;

public enum Status {

    SUCCESS(200, "success"),
    UNKNOWN_EXCEPTION(10000, "UNKNOWN_EXCEPTION"),

    DATASOURCE_TEST_FAIL(20001, "DATASOURCE_TEST_FAIL"),
    DATASOURCE_PARAMETER_NULL(20002, "DATASOURCE_PARAMETER_NULL"),
    DATASOURCE_CONVERT_PARAMETER_NULL(20003, "DATASOURCE_CONVERT_PARAMETER_NULL"),
    DATASOURCE_CONVERT_PARAMETER_ERROR(20004, "DATASOURCE_CONVERT_PARAMETER_ERROR"),
    DATASOURCE_CREATE_ERROR(20005, "DATASOURCE_CREATE_ERROR"),
    DATASOURCE_CODE_NULL(20006, "DATASOURCE_CODE_NULL"),
    DATASOURCE_UPDATE_ERROR(20007, "DATASOURCE_UPDATE_ERROR"),
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
