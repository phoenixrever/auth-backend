create table sys_users_roles
(
    user_id bigint not null comment '用户ID',
    role_id bigint not null comment '角色ID',
    primary key (user_id, role_id)
)
    comment '用户角色关联' charset = utf8;

create index FKq4eq273l04bpu4efj0jd0jb98
    on sys_users_roles (role_id);

INSERT INTO sys_users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO sys_users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO sys_users_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO sys_users_roles (user_id, role_id) VALUES (10, 2);
