package com.example.webCommon.service;

import com.example.webCommon.domain.dto.user.LoginDto;
import com.example.webCommon.domain.dto.user.RegistryDto;
import com.example.webCommon.domain.vo.user.MenuVo;
import com.example.webCommon.domain.vo.user.UserInfoVo;

import java.util.List;

public interface UserService {
    UserInfoVo login(LoginDto loginDto);

    void register(RegistryDto registryDto);

    List<MenuVo> getRouters();
}
