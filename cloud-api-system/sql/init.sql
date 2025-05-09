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

INSERT INTO `db_api`.`tb_user` (`user_name`, `user_role`, `user_avatar`, `gender`, `login_name`, `login_pwd`, `access_key`, `secret_key`, `phone`, `email`, `create_time`, `update_time`, `deleted`) VALUES ('欧阳华超', 'admin', NULL, NULL, 'admin', '3371e00731ba0313ea06d12d97f0844e', '9cbe75ba85f73a1b9f2fba26acb0bd2a', '30edb7c87584cea87b476d5c3ff295a1', NULL, '1375841038@qq.com', '2025-05-08 17:07:19', '2025-05-08 17:07:19', 0);