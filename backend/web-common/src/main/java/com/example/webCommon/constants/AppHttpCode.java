package com.example.webCommon.constants;

import lombok.Getter;

@Getter
public class AppHttpCode {
    // 成功
    public static final AppHttpCode SUCCESS = new AppHttpCode(HttpStatus.SUCCESS,"操作成功");

    // 错误
    public static final AppHttpCode SYSTEM_ERROR = new AppHttpCode(HttpStatus.SYSTEM_ERROR,"系统内部错误");
    public static final AppHttpCode AUTHENTICATE_ERROR = new AppHttpCode(HttpStatus.FORBIDDEN,"用户名密码错误");
    public static final AppHttpCode DIFFERENT_IP_ERROR = new AppHttpCode(HttpStatus.FORBIDDEN,"异地登录");

    private final int code;
    private final String msg;

    public AppHttpCode(int code, String message){
        this.code = code;
        this.msg = message;
    }

    public AppHttpCode(String message){
        this(HttpStatus.SYSTEM_ERROR, message);
    }
}
