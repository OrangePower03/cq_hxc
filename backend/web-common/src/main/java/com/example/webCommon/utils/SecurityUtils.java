package com.example.webCommon.utils;

import com.example.webCommon.constants.AppHttpCode;
import com.example.webCommon.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SecurityUtils {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AssertUtils.nonNull(authentication, AppHttpCode.AUTHENTICATE_ERROR);
        return authentication;
    }

    /**
     * 是否是内部的代码执行的，redis定时任务刷入数据库要加的
     * @return 是否为内部代码
     */
    public static boolean isInternal() {
        return Objects.isNull(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * 用户不能是anonymousUser
     * @return 是否为匿名用户，匿名表示未登录，不能获取到信息
     */
    public static boolean isAnonymous() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof String || principal.equals("anonymousUser");
    }

    public static  boolean isAdmin() {
        return getUserId().equals(1L);
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }

    public static Set<String> getUserRole() {
        return getLoginUser().getRoles();
    }
}
