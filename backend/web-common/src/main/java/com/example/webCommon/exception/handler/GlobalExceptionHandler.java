package com.example.webCommon.exception.handler;

import com.example.webCommon.exception.GlobalException;
import com.example.webCommon.utils.web.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 全局异常捕获，暂时先不考虑太多异常
     */
    @ExceptionHandler(GlobalException.class)
    public ResponseResult handleGlobalException(GlobalException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生异常，异常信息为{}", requestURI, e.getMsg());
        return ResponseResult.error(e.getCode(), e.getMsg());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return ResponseResult.error();
    }
}
