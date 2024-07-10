package com.example.webCommon.domain.vo.user;

import com.example.webCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserInfoVo extends BaseVo {
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String token;
}
