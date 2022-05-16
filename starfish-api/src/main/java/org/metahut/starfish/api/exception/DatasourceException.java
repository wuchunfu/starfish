package org.metahut.starfish.api.exception;

import org.metahut.starfish.api.enums.Status;

import org.springframework.lang.Nullable;

public class DatasourceException extends RuntimeException {

    private Integer code;

    private Object[] args;

    public DatasourceException(Status status, @Nullable Object[] args) {
        super(status.getMessage());
        this.code = status.getCode();
        this.args = args;
    }

    public DatasourceException(Status status, Throwable cause) {
        super(status.getMessage(), cause);
        this.code = status.getCode();
    }

    public DatasourceException(Status status, @Nullable Object[] args, Throwable cause) {
        super(status.getMessage(), cause);
        this.code = status.getCode();
        this.args = args;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
