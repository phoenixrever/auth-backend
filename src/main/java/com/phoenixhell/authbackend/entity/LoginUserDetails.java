package com.phoenixhell.authbackend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * DaoAuthenticationProvider处理了web表单的认证逻辑:
 * 认证成功后得到一个Authentication(UsernamePasswordAuthenticationToken实现)，里面包含了身份信息（Principal）
 * 这个身份信息就是一个 Object ，大多数情况下它可以被强转为UserDetails对象。
 * <p>
 * DaoAuthenticationProvider中包含了一个UserDetailsService实例
 * DaoAuthenticationProvider会去对比UserDetailsService提取的用户密码与用户提交的密码(Authentication)是否匹配
 * <p>
 * <p>
 * 角色时需要加前缀ROLE_，而在controller使用时不需要加ROLE_前缀
 *
 * @PreAuthorize("hasAuthority('read')") @PreAuthorize("hasRole('admin')")
 * <p>
 * 权限和角色目前看来没啥区别
 */

/**
 使用 ObjectMapper 将该 JSON 数据转换为 User 对象： 忽略未知属性而不抛出异常。
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserDetails implements UserDetails {
    public static final String ROLE_PREFIX = "ROLE_";

    private UserEntity userEntity;



    //这是我们自己的permission 不要和grantedAuthorities搞混,
    // 也千万不要取名authorities 会和这个接口的 authorities(自带) 和getAuthorities 冲突
    //permissions 必须要有get set 方法不然序列化不成功
    private List<String> permissions;


    //一定要是这个名字 才会覆盖实现类UserDetails 的authorities JsonIgnore 才会起作用
    @JsonIgnore  //指定了@JsonIgnoreProperties 类级别 就不用指定属性级别了
    List<GrantedAuthority> authorities;


    //反序列化一定要有构造函数
    public LoginUserDetails() {
    }

    public LoginUserDetails(UserEntity User, List<String> permissions) {
        this.userEntity = User;
        this.permissions = permissions;
    }

    //permissions 必须要有get set 方法不然序列化不成功
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    public List<String> getPermissions() {
        return permissions;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity User) {
        this.userEntity = User;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
       // List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(authorities.toArray(new String[0]));

        //MyUserDetailService 会设置默认权限visitor
        authorities= AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", permissions));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    public void removePassword() {
        userEntity.setPassword("");
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
