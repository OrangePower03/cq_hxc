drop database if exists ch_order;
create database ch_order;
use ch_order;

create table goods_order(
    id varchar(255) primary key,
    goods_id varchar(255) not null comment '订单选购的商品id',
    receive_message_id varchar(255) not null comment '收货信息的id，里面包含用户信息',
    status smallint not null comment '订单状态，0表示未支付，1表示已支付，2表示取消，3表示收货，4表示退款',
    payment_method smallint not null comment '支付方式，0表示积分支付，1表示微信支付，2表示支付宝支付' default 0,
    goods_quantity int not null comment '订单选购的商品数量',
    payment_amount decimal(10,2) not null comment '支付金额',
    create_time datetime default now() comment '创建时间',
    update_time datetime default now() comment '更新时间',
    create_by bigint not null comment '创建用户id',
    update_by bigint not null comment '更新的用户id',
    del_flag smallint comment '删除状态，0表示正常，1表示删除' default 0
) comment '订单信息';
create index idx_goods_id on goods_order(goods_id);
create index idx_receive_message_id on goods_order(receive_message_id);
create index idx_status on goods_order(status);
create index idx_payment_method on goods_order(payment_method);


create table receive_message(
    id varchar(255) primary key,
    user_id bigint not null comment '用户id',
    name varchar(255) not null comment '收货人姓名',
    phone varchar(20) not null comment '收货人电话',
    receive_address text not null comment '收货地址'
) comment '收货信息，一个用户可以有多个';
create index idx_user_id on receive_message(user_id);
create index idx_name on receive_message(name);
create index idx_phone on receive_message(phone);