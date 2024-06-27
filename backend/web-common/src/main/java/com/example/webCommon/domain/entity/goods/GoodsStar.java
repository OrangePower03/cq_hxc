package com.example.webCommon.domain.entity.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsStar {
    //商品id
    private String goodsId;
    //用户id
    private Long userId;
}

