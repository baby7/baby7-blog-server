package com.baby7blog.config;

import com.baby7blog.modules.blog.common.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Spring Security 适配器
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthFilter authFilter;

    /**
     * 1.【登录、评论、获取文件、获取信息】不需要认证
     * 2.【其他的均需要认证】
     * 3.禁用CRSF、禁用session
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers(CommonConstants.PATH_USER_COMMENT).permitAll()
                .antMatchers(CommonConstants.PATH_INFO_ALL).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable().sessionManagement().disable();
        //添加权限过滤器
        httpSecurity.addFilterBefore(authFilter, LogoutFilter.class);
    }
}