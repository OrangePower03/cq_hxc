package com.example.webCommon.domain.entity.goods;

import com.example.webCommon.domain.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsPrice {
    
    private String id;
    //商品id
    private String goodsId;
    //价格
    private Double price;
    //创建时间
    private Date createTime;
    //创建的用户id
    private Long createBy;
    //删除标志，0表示未删除，1表示已删除
    private Short delFlag;
}

