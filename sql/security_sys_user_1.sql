create table sys_user
(
    user_id        bigint auto_increment comment 'ID'
        primary key,
    username       varchar(255)         not null comment '用户名',
    nick_name      varchar(255)         null comment '昵称',
    gender         tinyint(1) default 1 null comment '性别1,男,0:女,2:人妖',
    phone          varchar(255)         null comment '手机号码',
    email          varchar(255)         null comment '邮箱',
    avatar         varchar(255)         null comment '头像地址',
    password       varchar(255)         null comment '密码',
    roles          varchar(255)         null comment '角色',
    enabled        tinyint(1) default 0 not null comment '状态：1启用、0禁用',
    created_by     varchar(255)         null comment '创建者',
    updated_by     varchar(255)         null comment '更新者',
    pwd_reset_time datetime             null comment '修改密码的时间',
    created_time   datetime             null comment '创建日期',
    updated_time   datetime             null comment '更新时间',
    constraint UK_kpubos9gc2cvtkb0thktkbkes
        unique (email),
    constraint uniq_email
        unique (email),
    constraint uniq_username
        unique (username),
    constraint username
        unique (username)
)
    comment '系统用户' charset = utf8
                   auto_increment = 3;

create index FKpq2dhypk2qgt68nauh2by22jb
    on sys_user (avatar);

create index inx_enabled
    on sys_user (enabled);

INSERT INTO sys_user (user_id, username, nick_name, gender, phone, email, avatar, password, roles, enabled, created_by, updated_by, pwd_reset_time, created_time, updated_time) VALUES (1, 'admin', '管理员', 1, '18888888888', '201507802@qq.com', 'none', '$2a$10$RogrjMDgHPMP7z7v4wyjQu/ioMsXiCWwD7ys8mDr8Vjf9eTanb0G6', '1', 1, null, 'admin', '2020-05-03 16:38:31', '2018-08-23 09:11:56', '2021-11-20 10:14:32');
INSERT INTO sys_user (user_id, username, nick_name, gender, phone, email, avatar, password, roles, enabled, created_by, updated_by, pwd_reset_time, created_time, updated_time) VALUES (2, 'test', '测试', 1, '19999999999', '231@qq.com', '5555', '$2a$10$RogrjMDgHPMP7z7v4wyjQu/ioMsXiCWwD7ys8mDr8Vjf9eTanb0G6', '2', 1, 'admin', 'admin', null, '2020-05-05 11:15:49', '2022-02-15 11:17:38');
