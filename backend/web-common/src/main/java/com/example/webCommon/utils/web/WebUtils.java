package com.example.webCommon.utils.web;

import com.alibaba.fastjson.JSON;
import com.example.webCommon.utils.object.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils {
    /**
     * 获取请求的HttpServletRequest对象
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ObjectUtils.requireNonNull(requestAttributes).getRequest();
    }

    /**
     * 获取请求的HttpServletResponse对象
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return ObjectUtils.requireNonNull(requestAttributes).getResponse();
    }

    public static String getIp() {
        return getRequest().getRemoteAddr();
    }

    /**
     * 渲染响应
     */
    public static void render(HttpServletResponse response, int code, String msg) {
        response.setStatus(code);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(JSON.toJSONString(ResponseResult.error(code, msg)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
