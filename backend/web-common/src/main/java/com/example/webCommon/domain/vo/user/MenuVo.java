package com.example.webCommon.domain.vo.user;

import com.example.webCommon.domain.vo.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuVo extends BaseVo implements Comparable<MenuVo> {
    //菜单ID
    private Long id;
    //菜单名称
    private String menuName;
    //菜单的排序，用于固定界面
    private Integer orderNum;
    //菜单类型，M表示目录，C表示菜单，F表示按钮
    private String menuType;
    //菜单访问路径
    private String component;
    // 父菜单
    private Long parentId;
    // 子菜单
    private List<MenuVo> children = new ArrayList<>();

    @Override
    public int compareTo(MenuVo menu) {
        return this.orderNum - menu.orderNum;
    }
}
