package com.example.webCommon.domain.entity.goods;

import com.example.webCommon.domain.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCategory extends AbstractEntity<String> {
    //分类名字
    private String name;
}
