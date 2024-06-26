create database ch_goods;
use ch_goods;

/*
  一个商品只能是一个分类，但能有多个标签
*/
create table goods_category(
    id bigint primary key,
    name varchar(255) not null comment '分类名字',
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '更新时间',
    create_by bigint not null comment '创建的用户id',
    update_by bigint not null comment '更新的用户id',
    del_flag tinyint default 0 comment '删除标志，0表示未删除，1表示已删除'
) comment '分类表';


create table goods(
    id varchar(255) primary key,
    shop_id varchar(255) comment '商铺id',
    category_id bigint comment '分类id',
    name varchar(255) comment '商品名字',
    price decimal(10,2) comment '商品当前价格',
    stock int comment '商品库存',
    score double comment '商品评分，显示只取0.0-5.0' default 0,
    evaluate_count int comment '评价数量，用于计算评分' default 0,
    sales bigint comment '销量' default 0,
    intro text comment '商品介绍',
    main_image varchar(255) comment '商品主页图片' default 'https://cqyyds.oss-cn-hangzhou.aliyuncs.com/2023/11/24/0b13ea31-8bf2-4dfb-93b0-51053670b33e_default.png',
    state smallint comment '商品状态，0下架，1上架，2售罄' default 1,
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '更新时间',
    create_by bigint not null comment '创建的用户id',
    update_by bigint not null comment '更新商品的用户id',
    del_flag smallint comment '删除标志，0表示未删除，1表示已删除' default 0
) comment '商品表';


create table goods_image(
    id varchar(255) primary key,
    goods_id varchar(255) not null comment '商品id',
    imag_url varchar(255) not null comment '图片地址'
) comment '商品图片表，商品支持多图片展示';
create index idx_goods_id on goods_image(goods_id);



create table goods_star(
    goods_id varchar(255) not null comment '商品id',
    user_id bigint not null comment '用户id',
    primary key (goods_id, user_id)
) comment '用户收藏商品表';
create index idx_user_id on goods_star(user_id);


create table goods_tag(
    id varchar(255) primary key,
    goods_id varchar(255) not null comment '商品id',
    name varchar(255) not null comment '标签名称',
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '更新时间',
    create_by bigint not null comment '创建的用户id',
    update_by bigint not null comment '更新的用户id',
    del_flag tinyint default 0 comment '删除标志，0表示未删除，1表示已删除'
) comment '标签表';

create table goods_price(
    id varchar(255) primary key,
    goods_id varchar(255) not null comment '商品id',
    price decimal(10,2) not null comment '价格',
    create_time datetime default now() comment '创建时间',
    create_by bigint not null comment '创建的用户id',
    del_flag smallint default 0 comment '删除标志，0表示未删除，1表示已删除'
) comment '商品价格变动表，这里存放的都是已经过期的价格';