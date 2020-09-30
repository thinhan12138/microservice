package com.xh.microservice.common.result;

public enum Result implements IResult {
    SUCCESS(200, "操作成功"),
    FAILURE(400, "操作失败");

    final int code;
    final String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}
