package com.baby7blog.modules.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 用户类【登录及校验用】
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser implements UserDetails {
    private static final long serialVersionUID = 5259850103440957658L;

    private Integer id;
    private String username;
    private String password;
    private boolean enable;
    private String token;

    @Override
    @SuppressWarnings("unchecked")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 这里可以定制化权限列表
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 这里设置账号是否已经过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 这里设置账号是否已经被锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 这里设置凭证是否过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 是否可用
        return enable;
    }
}