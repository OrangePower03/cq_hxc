package com.example.webCommon.domain.entity;

import java.io.Serializable;

public interface BaseEntity extends Serializable {
    long serialVersionUID = 1314520L;
    short DEL_FLAG_NORMAL = 0; // 正常状态
    short DEL_FLAG_DELETED = 1; // 删除状态
}
