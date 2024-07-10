package com.example.webCommon.domain.entity.goods;

import com.example.webCommon.domain.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Goods extends AbstractEntity {
    String id;
    //商铺id
    private String storeId;
    //分类id
    private Long categoryId;
    //商品名字
    private String name;
    //商品当前价格
    private Double price;
    //商品库存
    private Integer stock;
    //商品评分，显示只取0.0-5.0
    private Double score;
    //评价数量，用于计算评分
    private Integer evaluateCount;
    //销量
    private Long sales;
    //商品介绍
    private String intro;
    //商品主页图片
    private String mainImage;
    //商品状态，0下架，1上架，2售罄
    private Integer state;
}
