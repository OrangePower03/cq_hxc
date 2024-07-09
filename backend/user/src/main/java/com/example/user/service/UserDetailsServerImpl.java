package com.example.user.service;

import com.example.user.mapper.SysUserMapper;
import com.example.webCommon.constants.AppHttpCode;
import com.example.webCommon.domain.entity.LoginUser;
import com.example.webCommon.domain.entity.user.SysUser;
import com.example.webCommon.utils.AssertUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserDetailsServerImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名搜索用户
        SysUser user = userMapper.selectOne(username);
        //找不到用户
        AssertUtils.nonNull(user, AppHttpCode.AUTHENTICATE_ERROR);
        // 权限
        Set<String> roles = userMapper.findRoleByUserId(user.getId());
        return new LoginUser(user, roles);
    }
}
