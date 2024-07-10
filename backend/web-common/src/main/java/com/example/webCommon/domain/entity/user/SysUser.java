package com.example.webCommon.domain.entity.user;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.example.webCommon.domain.entity.AbstractEntity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUser extends AbstractEntity {
    Long id;
    // 用户名
    private String username;
    //加密后的密码
    private String password;
    //用户昵称
    private String nickname;
    //用户头像
    private String avatar;
    //用户本次登录ip地址
    private String loginIp;
    
    private String phone;
    //当前用户状态，0表示正常，1表示停用
    private Integer state;
}

