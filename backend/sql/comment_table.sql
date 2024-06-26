drop database if exists ch_comment;
create database ch_comment;
use ch_comment;

create table user_comment(
    id varchar(255) primary key,
    user_id bigint not null comment '评论的用户id',
    tag smallint not null comment '评论的类型，0为商品，1为商铺',
    target_id varchar(255) not null comment '被评论的商品或商铺id',
    content text not null comment '评论内容',
    create_time datetime default now() comment '评论创建时间',
    update_time datetime default now() comment '评论更新时间',
    create_by bigint not null comment '创建用户id',
    update_by bigint not null comment '更新的用户id',
    del_flag smallint comment '删除状态，0表示正常，1表示删除' default 0
);
create index idx_user_id on user_comment(user_id);
create index idx_tag_target_id on user_comment(tag, target_id);

create table comment_image(
    id varchar(255) primary key,
    comment_id varchar(255) not null comment '评论id',
    image_url varchar(255) not null comment '图片url'
) comment '评论携带的图片，支持多图';
create index idx_comment_id on comment_image(comment_id);