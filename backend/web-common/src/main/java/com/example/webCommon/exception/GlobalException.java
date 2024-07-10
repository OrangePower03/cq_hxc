package com.example.webCommon.exception;

import com.example.webCommon.constants.AppHttpCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int code;
    private final String msg;

    public GlobalException(AppHttpCode httpCode) {
        this(httpCode.getCode(), httpCode.getMsg());
    }

    public GlobalException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}