create table sys_menu
(
    menu_id      bigint auto_increment comment 'ID'
        primary key,
    pid          bigint               null comment '上级菜单ID',
    title        varchar(255)         null comment '菜单标题',
    name         varchar(255)         null comment '组件名称',
    component    varchar(255)         null comment '组件',
    menu_sort    int                  null comment '排序',
    icon         varchar(255)         null comment '图标',
    path         varchar(255)         null comment '链接地址',
    redirect     varchar(255)         null comment '重定向地址',
    i_frame      tinyint(1) default 0 null comment '是否外链',
    cache        tinyint(1) default 0 null comment '缓存',
    hidden       tinyint(1) default 0 null comment '隐藏',
    permission   varchar(255)         null comment '权限',
    created_by   varchar(255)         null comment '创建者',
    updated_by   varchar(255)         null comment '更新者',
    created_time datetime             null comment '创建日期',
    updated_time datetime             null comment '更新时间',
    constraint uniq_name
        unique (name),
    constraint uniq_title
        unique (title)
)
    comment '系统菜单' charset = utf8
                   auto_increment = 21;

create index inx_pid
    on sys_menu (pid);

INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (1, 0, 'システム管理', 'System', 'Layout', 1, 'el-icon-s-tools', '/system', 'User', 0, 0, 0, null, null, null, '2018-12-18 15:11:29', '2021-11-20 14:56:36');
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (2, 1, 'ユーザー管理', 'User', 'User', 0, 'el-icon-userEntity-solid', 'userEntity', null, 0, 0, 0, 'userEntity:list', null, 'admin', '2018-12-18 15:14:44', '2022-06-04 18:32:30');
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (3, 1, 'ロール管理', 'Role', 'Role', 1, 'el-icon-s-management', 'role', null, 0, 0, 0, 'role:list', null, null, '2018-12-18 15:16:07', '2021-11-20 16:16:33');
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (4, 1, 'メニュー管理', 'Menu', 'Menu', 2, 'el-icon-menu', 'menu', null, 0, 0, 0, 'menu:list', null, 'admin', '2018-12-18 15:17:28', '2022-06-04 18:48:37');
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (6, 2, 'ユーザー追加', 'UserAdd', null, 1, 'tree', 'add', null, 0, 0, 1, 'userEntity:add', null, null, '2019-10-29 10:59:46', null);
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (7, 2, 'ユーザー編集', 'UserEdit', null, 2, 'tree', 'edit', null, 0, 0, 1, 'userEntity:edit', null, null, '2019-10-29 11:00:08', null);
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (8, 2, 'ユーザー削除', 'UserDelete', null, 3, null, 'delete', null, 0, 0, 1, 'userEntity:delete', null, null, '2019-10-29 11:00:23', null);
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (10, 3, 'ロールの作成', 'RoleAdd', null, 2, 'el-icon-circle-plus', 'add', null, 0, 0, 1, 'role:add', null, 'admin', '2019-10-29 12:45:34', '2022-02-16 20:58:18');
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (11, 3, 'ロールの編集', 'RoleEdit', null, 3, 'tree', 'edit', null, 0, 0, 1, 'role:edit', null, null, '2019-10-29 12:46:16', null);
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (12, 3, 'ロールの削除', 'RoleDelete', null, 4, 'tree', 'delete', null, 0, 0, 1, 'role:delete', null, null, null, '2021-11-20 16:07:15');
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (14, 4, 'メニュー追加', 'MenuAdd', null, 2, 'tree', 'add', null, 0, 0, 1, 'menu:add', null, null, '2019-10-29 12:55:07', null);
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (15, 4, 'メニュー編集', 'MenuEdit', null, 3, 'tree', 'edit', null, 0, 0, 1, 'menu:edit', null, null, '2019-10-29 12:55:40', null);
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (16, 4, 'メニュー削除', 'MenuDelete', null, 4, 'tree', 'delete', null, 0, 0, 1, 'menu:delete', null, null, '2019-10-29 12:56:00', null);
INSERT INTO sys_menu (menu_id, pid, title, name, component, menu_sort, icon, path, redirect, i_frame, cache, hidden, permission, created_by, updated_by, created_time, updated_time) VALUES (20, 1, 'アクセスIP', 'Ip', 'Ip', 4, 'el-icon-camera-solid', 'ip', null, 0, 0, 0, '', 'admin', 'admin', '2022-06-04 18:46:59', '2022-06-04 18:48:19');
