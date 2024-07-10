package com.example.webCommon.domain.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.example.webCommon.domain.entity.AbstractEntity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysRole extends AbstractEntity {
    Long id;
    //角色名称
    private String roleName;
    //权限英文名称，用于作为标识
    private String roleKey;
    //角色的排序，越大权限越小
    private Integer orderNum;
    //当前角色状态，0表示开启，1表示停用
    private Integer state;
}

