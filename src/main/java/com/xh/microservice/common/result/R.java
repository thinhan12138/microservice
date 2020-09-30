package com.xh.microservice.common.result;

import java.io.Serializable;

public class R<T> implements Serializable {

    private static final long serialVersion = 1L;

    private int code;
    private String message;
    private boolean success;
    private T data;

    private R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = code == Result.SUCCESS.code;
    }

    private R() {
    }

    private R(IResult result) { this(result, null, result.getMessage()); }

    private R(IResult result, String message) { this(result, null, message); }

    private R(IResult result, T data) { this(result, data, result.getMessage()); }

    private R(IResult result, T data, String message) { this(result.getCode(), message, data); }

    public static <T> R<T> data(int code, T data, String message){
        return new R(code, data == null ? "无数据": message, data);
    }
    public static <T> R<T> data(T data, String message){ return data(200, data, message); }
    public static <T> R<T> data(T data){ return data(data, "操作成功"); }
    public static <T> R<T> data(int code, String message){ return data(code, null, message); }

    public static <T> R<T> success(){ return new R(Result.SUCCESS); }
    public static <T> R<T> success(String message){ return new R(Result.SUCCESS, message); }
    public static <T> R<T> success(IResult result){ return new R(result, Result.SUCCESS); }
    public static <T> R<T> success(IResult result, String message){ return new R(result, message); }
    public static <T> R<T> success(Object o){ return new R(Result.SUCCESS, o); }
    public static <T> R<T> success(Object o, String message){ return new R(Result.SUCCESS, o, message); }

    public static <T> R<T> fail(){ return new R(Result.FAILURE); }
    public static <T> R<T> fail(String message){ return new R(Result.FAILURE, message); }
    public static <T> R<T> fail(int code, String message){ return new R(code, message, null); }
    public static <T> R<T> fail(IResult result){ return new R(result); }
    public static <T> R<T> fail(IResult result, String message){ return new R(result, message); }

    public static <T> R<T> status(boolean status){ return status ? success(): fail(); }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
