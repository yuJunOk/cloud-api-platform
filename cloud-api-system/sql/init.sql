create database db_api;

create table tb_user
(
    id          bigint auto_increment comment 'id'
        primary key,
    user_name   varchar(256)                           null comment '用户名',
    user_role   varchar(256) default 'user'            null comment '角色',
    user_avatar varchar(1024)                          null comment '头像链接',
    gender      tinyint                                null comment '性别',
    login_name  varchar(256)                           null comment '账号',
    login_pwd   varchar(512)                           not null comment '密码',
    access_key  varchar(512)                           not null comment 'accessKey',
    secret_key  varchar(512)                           not null comment 'secretKey',
    phone       varchar(128)                           null comment '电话',
    email       varchar(512)                           null comment '邮箱',
    create_time datetime     default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime     default CURRENT_TIMESTAMP null comment '更新时间',
    deleted     tinyint      default 0                 not null comment '是否删除'
)
    comment '用户表';