package com.phoenixhell.authbackend.config;

import com.phoenixhell.authbackend.filter.CustomAuthenticationEntryPoint;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;
import static org.springframework.security.authorization.AuthorizationManagers.allOf;

/**
 * PasswordEncoder   加密器  userdetailservice 需要用他加密用户输入的密码(自动)和数据库的比较
 * userDetails 取出的是已经加密完成存在数据库的密码 passwordEncoder加密原始密码
 * <p>
 * 新版本不用继承  WebSecurityConfigurerAdapter了 使用@EnableWebSecurity注解
 */
@Configuration
@EnableWebSecurity // the annotation imports the HttpSecurityConfiguration configuration class.
public class MyWebSecurityConfig {
    @Autowired
    private Filter jwtTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /*
     * 注意 配置了 context-path: /api 的情况下 requestMatchers 是不需要加 /api 的
     *  即下面的代码是【不需要】的 同理 controller 有而不要 /api
     * 一句话 除了访问路径要待/api 代码里面的一切照旧
     * @Value("${server.servlet.context-path:''}")
        private String contextPath; d
     */

    /**
     * permitAll() 方法表示允许所有用户（包括未经身份验证的用户）访问 "/login" 路径，这通常用于登录页面或其他公开的页面。
     * .anyRequest().authenticated() 配置了对除了 "/login" 路径之外的所有请求都要求用户进行身份验证。
     *
     * @EnableWebSecurity 不启用 http 会报错 当然 不影响运行
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 关闭了 cors() 允许跨域
        // 关闭了CSRF防护，“/logout” 需要用post的方式提交
        http.cors(cors -> cors.disable())
                .csrf((csrf) -> csrf.disable())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**", "/signup", "/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/db/**").access(allOf(hasAuthority("db"), hasRole("ADMIN")))
                        .anyRequest().authenticated()
                )
                // Add JWT token filter 必须在UsernamePasswordAuthenticationFilter前面
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                //授权异常
                .exceptionHandling(handler -> {
                    handler.authenticationEntryPoint(authenticationEntryPoint);
                });

        return http.build();
    }

    /**
     * 可以自定义一个  NoOpPasswordEncoder.getInstance(); 不加密存储密码
     * 或者用默认的加密器 不过数据库密码需要这样写  {noop}123456 不要加密的用户的密码
     * 默认的加密器会根据sql的密碼格式  {id}password 来判断需不需要对用户输入的密码加密来和数据库密码比对
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance(); //不加密存储密码
        return new BCryptPasswordEncoder();
    }

    //新版本写法
    //https://docs.spring.io/spring-security/reference/6.1/servlet/authentication/passwords/index.html#publish-authentication-manager-bean
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }
}
