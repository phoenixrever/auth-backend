create table sys_role
(
    role_id      bigint auto_increment comment 'ID'
        primary key,
    name         varchar(255) not null comment '名称',
    description  varchar(255) null comment '描述',
    created_by   varchar(255) null comment '创建者',
    updated_by   varchar(255) null comment '更新者',
    created_time datetime     null comment '创建日期',
    updated_time datetime     null comment '更新时间',
    constraint uniq_name
        unique (name)
)
    comment '角色表' charset = utf8
                  auto_increment = 3;

create index role_name_index
    on sys_role (name);

INSERT INTO sys_role (role_id, name, description, created_by, updated_by, created_time, updated_time) VALUES (1, 'admin', 'admin', 'admin', 'admin', '2018-11-23 11:04:37', '2020-08-06 16:10:24');
INSERT INTO sys_role (role_id, name, description, created_by, updated_by, created_time, updated_time) VALUES (2, '一般ユーザー', 'これは超ロングロングロングロングロングロングロングロングロングロングロングロングロングの説明です', 'admin', 'admin', '2018-11-23 13:09:06', '2020-09-05 10:45:12');
