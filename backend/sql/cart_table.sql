create database ch_cart;
use ch_cart;

create table cart(
    id varchar(255) primary key,
    user_id bigint not null comment '用户id',
    goods_id bigint not null comment '商品id',
    goods_quantity int not null comment '商品数量',
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '更新时间',
    create_by bigint not null comment '创建用户id',
    update_by bigint not null comment '更新的用户id',
    del_flag smallint comment '删除状态，0表示正常，1表示删除' default 0
) comment '购物车表';

