package org.metahut.starfish.api.exception;

import org.metahut.starfish.api.enums.Status;

import org.springframework.lang.Nullable;

public class BusinessException extends RuntimeException {

    private Integer code;

    private Object[] args;

    public BusinessException(Status status, @Nullable Object... args) {
        super(status.getMessage());
        this.code = status.getCode();
        this.args = args;
    }

    public BusinessException(Throwable cause, Status status) {
        super(status.getMessage(), cause);
        this.code = status.getCode();
    }

    public BusinessException(Throwable cause, Status status, @Nullable Object... args) {
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
