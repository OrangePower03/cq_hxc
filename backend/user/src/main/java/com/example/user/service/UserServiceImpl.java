package com.example.user.service;

import com.example.user.mapper.SysUserMapper;
import com.example.webCommon.constants.AppHttpCode;
import com.example.webCommon.constants.CacheConstants;
import com.example.webCommon.constants.HttpStatus;
import com.example.webCommon.constants.UserConstants;
import com.example.webCommon.domain.dto.user.LoginDto;
import com.example.webCommon.domain.dto.user.RegistryDto;
import com.example.webCommon.domain.entity.LoginUser;
import com.example.webCommon.domain.entity.user.SysMenu;
import com.example.webCommon.domain.entity.user.SysUser;
import com.example.webCommon.domain.vo.user.MenuVo;
import com.example.webCommon.domain.vo.user.UserInfoVo;
import com.example.webCommon.exception.GlobalException;
import com.example.webCommon.service.UserService;
import com.example.webCommon.utils.AssertUtils;
import com.example.webCommon.utils.JwtUtils;
import com.example.webCommon.utils.PasswordUtils;
import com.example.webCommon.utils.SecurityUtils;
import com.example.webCommon.utils.bean.BeanCopyUtils;
import com.example.webCommon.utils.object.ObjectUtils;
import com.example.webCommon.utils.object.StringUtils;
import com.example.webCommon.utils.redis.StringRedisUtils;
import com.example.webCommon.utils.web.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private StringRedisUtils redisUtils;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserInfoVo login(LoginDto loginDto) {
        var authentication=new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword());
        Authentication authenticateResult = manager.authenticate(authentication);
        if(ObjectUtils.isNull(authenticateResult)){
            throw new GlobalException(AppHttpCode.AUTHENTICATE_ERROR);
        }

        LoginUser loginUser = (LoginUser) authenticateResult.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        //获取令牌
        Map<String, Object> payload = new HashMap<>();
        payload.put(UserConstants.JwtPayload.IP, WebUtils.getIp());
        String token = JwtUtils.createJWT(id, payload);
        redisUtils.setWithExpire(CacheConstants.LOGIN_PREFIX + id, loginUser, CacheConstants.LOGIN_EXPIRE);

        // 返回用户信息
        UserInfoVo userInfo = BeanCopyUtils.copy(loginUser.getUser(), UserInfoVo.class);
        userInfo.setToken(token);
        return userInfo;
    }

    @Override
    @Transactional
    public void register(RegistryDto registryDto) {
        AssertUtils.isFalse(userMapper.selectCount(registryDto.getUsername()) >= 1, HttpStatus.BAD_REQUEST, "用户名已存在");
        AssertUtils.isEquals(registryDto.getPassword(), registryDto.getRepeatPassword(), HttpStatus.BAD_REQUEST, "两次输入的密码不一致");
        if(StringUtils.isEmpty(registryDto.getPhone())) {
            registryDto.setPhone(UserConstants.EMPTY_PHONE);
        }
        registryDto.setPassword(PasswordUtils.encode(registryDto.getPassword()));
        SysUser user = BeanCopyUtils.copy(registryDto, SysUser.class);
        user.setLoginIp(WebUtils.getIp());
        userMapper.addOne(user);
        userMapper.addRoleRelation(user.getId(), Set.of(UserConstants.DEFAULT_ROLE_ID));
    }

    /**
     * 获取用户路由信息，注意，数据库中的order_num要按照层级设计，每一层允许有100个节点
     * @return 编辑成树的菜单信息
     */
    @Override
    public List<MenuVo> getRouters() {
        Set<String> roleKeys = SecurityUtils.getUserRole();
        List<SysMenu> menus = userMapper.selectMenuByRoles(roleKeys);
        List<MenuVo> menuVos = BeanCopyUtils.copy(menus, MenuVo.class);
        Collections.sort(menuVos);
        List<MenuVo> res = new ArrayList<>();
        Map<Long, MenuVo> parents = new HashMap<>();
        for (MenuVo menuVo : menuVos) {
            if(UserConstants.DEFAULT_PARENT_ID.equals(menuVo.getParentId())) {
                res.add(menuVo);
                parents.put(menuVo.getId(), menuVo);
            }
            else {
                parents.get(menuVo.getParentId()).getChildren().add(menuVo);
                parents.put(menuVo.getId(), menuVo);
            }
        }
        return res;
    }
}
