package com.baby7blog.util.security;

import com.baby7blog.modules.blog.dto.AdminUser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 *
 * @author L.cm
 */
@UtilityClass
@Slf4j
public class SecurityUtils {
	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 *
	 * @param authentication
	 * @return TXtYhgl
	 * <p>
	 * 获取当前用户的全部信息 EnablePigxResourceServer true
	 * 获取当前用户的用户名 EnablePigxResourceServer false
	 */
	public AdminUser getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof AdminUser) {
			return (AdminUser) principal;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public AdminUser getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}

	/**
	 * 获取用户角色信息
	 */
	public List<Integer> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<Integer> roleIds = new ArrayList<>();
		authorities.forEach(granted -> {
					String id = granted.getAuthority();
					roleIds.add(Integer.parseInt(id));
				});
		return roleIds;
	}

}
