package com.example.webCommon.constants;

public interface UserConstants {
    String EMPTY_PHONE = "";   // 空手机号
    Long DEFAULT_ROLE_ID = 4L; // 注册的默认角色
    Long DEFAULT_PARENT_ID = 0L; // 默认父菜单的ID

    interface JwtPayload {
        String IP = "ip";
    }
}
