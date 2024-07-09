package com.example.webCommon.domain.entity.user;

import com.example.webCommon.domain.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends AbstractEntity {
    Long id;
    //菜单名称
    private String menuName;
    //菜单权限标识，后端校验用
    private String menuKey;
    //菜单类型，M表示目录，C表示菜单，F表示按钮
    private String menuType;
    //菜单的排序，用于固定界面
    private Integer orderNum;
    //菜单访问路径
    private String component;
    //父菜单ID，menu_id的外键，一级菜单为0
    private Long parentId;
    //当前菜单状态，0表示开启，1表示停用
    private Integer state;
}

