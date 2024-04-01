package com.phoenixhell.authbackend.entity.vo;

import lombok.Data;

/**
 * @author phoenixhell
 * @since 2022/2/7 0007-上午 11:22
 */

@Data
public class UserRoleMenuVo {
    private Long userId;
    private String username;
    private String nickName;

    private Long roleId;
    private String roleName;

    private Long menuId;
    /**
     * 上级菜单ID
     */
    private Long pid;

    /**
     * 菜单标题
     */
    private String title;
    /**
     * 组件名称
     */

    private String menuName;
    /**
     * 组件
     */
    private String component;
    /**
     * 排序
     */
    private Integer menuSort;
    /**
     * 图标
     */
    private String icon;
    /**
     * 链接地址
     */
    private String path;
    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 隐藏
     */
    private Integer hidden;
    /**
     * 权限
     */
    private String permission;
}
