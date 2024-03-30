package com.phoenixhell.authbackend.entity.vo;


import com.phoenixhell.authbackend.valid.AddGroup;
import com.phoenixhell.authbackend.valid.ListValue;
import com.phoenixhell.authbackend.valid.UpdateGroup;
import com.phoenixhell.authbackend.valid.UpdateStatusGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author phoenixhell
 * @since 2022/2/19 0019-下午 3:36
 */
@Data
public class SignVo {
    /**
     * 用户名
     *
     */
    @NotBlank(message = "用戶名不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    @NotBlank(message = "密码不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    //只能是0,1,2
    @ListValue(values = {0,1,2},groups = {AddGroup.class,UpdateGroup.class, UpdateStatusGroup.class})
    private Integer gender;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    @Email
    private String email;
    /**
     * 头像地址
     */
    private String avatar;

    private String code;
    private String captchaKey;
}
