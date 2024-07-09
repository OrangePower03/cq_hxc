package com.example.webCommon.filter;

import com.alibaba.fastjson.JSON;
import com.example.webCommon.constants.AppHttpCode;
import com.example.webCommon.constants.CacheConstants;
import com.example.webCommon.constants.UserConstants;
import com.example.webCommon.domain.entity.LoginUser;
import com.example.webCommon.utils.AssertUtils;
import com.example.webCommon.utils.JwtUtils;
import com.example.webCommon.utils.object.ObjectUtils;
import com.example.webCommon.utils.object.StringUtils;
import com.example.webCommon.utils.redis.StringRedisUtils;
import com.example.webCommon.utils.web.ResponseResult;
import com.example.webCommon.utils.web.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = null;
        try {
            claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            WebUtils.render(response, 403, "token无效，请重新登录");
            return;
        }
        String userId = claims.getSubject();
        String ip = (String) claims.get(UserConstants.JwtPayload.IP);
        // 持有令牌异地登录错误
        AssertUtils.isEquals(ip, request.getRemoteAddr(), AppHttpCode.DIFFERENT_IP_ERROR);
        LoginUser loginUser = (LoginUser) redisUtils.get(CacheConstants.LOGIN_PREFIX + userId);
        if(ObjectUtils.isNull(loginUser)){
            //说明登录过期  提示重新登录
            WebUtils.render(response, 403, "认证过期，请重新登录");
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
