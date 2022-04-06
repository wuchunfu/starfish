package org.metahut.starfish.ingestion.server.dto;

public class ResultEntity<T> {

    private static final Integer SUCCESS = 200;

    private Integer code;

    private String message;

    private T data;

    public ResultEntity() {

    }

    private ResultEntity(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultEntity(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResultEntity<T> of (Integer code, String message) {
        return new ResultEntity<>(code, message);
    }

    public static <T> ResultEntity<T> success(T data) {
        return new ResultEntity<>(SUCCESS, data);
    }

    public static <T> ResultEntity<T> success() {
        return success(null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
