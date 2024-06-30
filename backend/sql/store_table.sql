drop database if exists ch_store;
create database ch_store;
use ch_store;

create table store(
    id varchar(255) primary key,
    name varchar(255) not null comment '店铺名称',
    logo_url varchar(255) comment '店铺logo的图片地址' default 'https://cqyyds.oss-cn-hangzhou.aliyuncs.com/2023/11/24/0b13ea31-8bf2-4dfb-93b0-51053670b33e_default.png',
    description text comment '店铺描述' default '这个店家很懒，什么也没留下',
    follow_count bigint default 0 comment '关注数',
    score double comment '店铺评分，显示只取0.0-5.0' default 0,
    evaluate_count int comment '评价数量，用于计算评分' default 0,
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '更新时间',
    create_by bigint not null comment '创建的用户id',
    update_by bigint not null comment '更新商铺的用户id',
    del_flag smallint comment '删除状态，0表示正常，1表示删除' default 0
) comment '店铺信息';
create index idx_name on store(name);
create index idx_follow_count on store(follow_count);
create index idx_score on store(score);


create table store_user(
    store_id varchar(255) not null comment '店铺id',
    user_id bigint not null comment '管理店铺的用户',
    role smallint comment '角色，0表示创始人，可以邀请和删除用户，还有删除商铺，1表示普通商铺管理员，只能上架下架' default 0,
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '更新时间',
    create_by bigint not null comment '邀请这个用户的用户id',
    update_by bigint not null comment '更新这个用户状态的用户id',
    del_flag smallint comment '删除状态，0表示正常，1表示删除' default 0,
    primary key (store_id, user_id)
) comment '店铺拥有者';
create index idx_manager_user_id on store_user(user_id);

create table store_follower(
    store_id varchar(255) not null comment '店铺id',
    user_id bigint not null comment '关注店铺的用户',
    primary key (store_id, user_id)
) comment '店铺关注者';
create index idx_follower_user_id on store_follower(user_id);