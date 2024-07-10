package com.example.user.controller;

import com.example.webCommon.domain.dto.user.LoginDto;
import com.example.webCommon.domain.dto.user.RegistryDto;
import com.example.webCommon.domain.vo.user.MenuVo;
import com.example.webCommon.domain.vo.user.UserInfoVo;
import com.example.webCommon.service.UserService;
import com.example.webCommon.utils.web.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult<UserInfoVo> login(@RequestBody LoginDto loginDto) {
        return ResponseResult.ok(userService.login(loginDto));
    }

    @PostMapping("/register")
    public ResponseResult<Void> register(@RequestBody RegistryDto registryDto) {
        userService.register(registryDto);
        return ResponseResult.ok();
    }

    @GetMapping("/getRouters")
    public ResponseResult<List<MenuVo>> getRouters() {
        return ResponseResult.ok(userService.getRouters());
    }
}
