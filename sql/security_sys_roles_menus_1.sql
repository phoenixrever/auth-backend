create table sys_roles_menus
(
    menu_id bigint not null comment '菜单ID',
    role_id bigint not null comment '角色ID',
    primary key (menu_id, role_id)
)
    comment '角色菜单关联' charset = utf8;

create index FKcngg2qadojhi3a651a5adkvbq
    on sys_roles_menus (role_id);

INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (1, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (2, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (3, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (4, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (6, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (7, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (8, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (10, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (11, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (12, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (14, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (15, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (16, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (20, 1);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (1, 2);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (2, 2);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (3, 2);
INSERT INTO sys_roles_menus (menu_id, role_id) VALUES (4, 2);
