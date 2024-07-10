package com.example.user.mapper;


import com.example.webCommon.domain.entity.user.SysMenu;
import com.example.webCommon.domain.entity.user.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.awt.*;
import java.util.List;
import java.util.Set;

@Mapper
public interface SysUserMapper {

    SysUser selectOne(String username);

    int selectCount(String username);

    int addOne(SysUser user);

    int addRoleRelation(Long userId, Set<Long> roleIds);

    Set<String> findRoleByUserId(Long userId);

    List<SysMenu> selectMenuByRoles(Set<String> roleKeys);
}
