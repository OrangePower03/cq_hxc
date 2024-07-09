create database ch_user;
use ch_user;

create table sys_user
(
    user_id     bigint primary key auto_increment,
    username    varchar(255) not null unique,
    password    varchar(255) not null comment '加密后的密码',
    nickname    varchar(255) default 'sb' comment '用户昵称',
    avatar      varchar(255) default 'https://cqyyds.oss-cn-hangzhou.aliyuncs.com/2023/11/24/0b13ea31-8bf2-4dfb-93b0-51053670b33e_default.png' comment '用户头像',
    login_ip    varchar(255) not null comment '用户本次登录ip地址',
    phone       varchar(255) not null unique,
    state       int          default 0 comment '当前用户状态，0表示正常，1表示停用',
    create_by   bigint       default 0 comment '创建者',
    update_by   bigint       default 0 comment '更新者',
    create_time datetime     default now(),
    update_time datetime     default now() on update now(),
    del_flag    int          default 0 comment '用户删除标记'
);
create index idx_user_phone on sys_user(phone);
create index idx_user_username on sys_user(username);
create index idx_user_nickname on sys_user(nickname);


create table sys_role
(
    role_id     bigint primary key auto_increment,
    role_name   varchar(255) not null unique comment '角色名称',
    role_key    varchar(255) not null unique comment '权限英文名称，用于作为标识',
    order_num   int          not null comment '角色的排序，越大权限越小',
    state       int default 0 comment '当前角色状态，0表示开启，1表示停用',
    create_time datetime     default now(),
    update_time datetime     default now(),
    del_flag    int default 0 comment '角色删除标记'
);
create index idx_role_role_name on sys_role(role_name);
create index idx_role_role_key on sys_role(role_key);
create index idx_role_order_num on sys_role(order_num);


create table sys_menu
(
    menu_id     bigint primary key auto_increment,
    menu_name   varchar(255) not null comment '菜单名称',
    menu_key    varchar(255) not null comment '菜单权限标识，后端校验用',
    menu_type   char         not null comment '菜单类型，M表示目录，C表示菜单，F表示按钮',
    order_num   int          not null comment '菜单的排序，用于固定界面',
    component   varchar(255) not null comment '菜单访问路径',
    parent_id   bigint       not null comment '父菜单ID，menu_id的外键，一级菜单为0',
    state       int default 0         comment '当前菜单状态，0表示开启，1表示停用',
    create_time datetime     default now(),
    update_time datetime     default now(),
    del_flag    int default 0         comment '菜单删除标记'
);
create index idx_role_menu_name on sys_menu(menu_name);
create index idx_role_menu_key on sys_menu(menu_key);
create index idx_role_order_num on sys_menu(order_num);

create table sys_user_role(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

create table sys_role_menu
(
    role_id bigint not null,
    menu_id bigint not null,
    primary key (role_id, menu_id)
);
