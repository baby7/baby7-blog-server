package com.baby7blog.modules.blog.service;

import com.baby7blog.modules.blog.dto.AdminUser;
import com.baby7blog.modules.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 管理用户
 */
public interface UserService extends IService<User> {

    // 根据用户名获取用户
    AdminUser getUserByUsername(String username);
}
