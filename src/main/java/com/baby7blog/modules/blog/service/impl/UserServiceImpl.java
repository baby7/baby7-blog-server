package com.baby7blog.modules.blog.service.impl;

import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.dto.AdminUser;
import com.baby7blog.modules.blog.entity.User;
import com.baby7blog.modules.blog.mapper.UserMapper;
import com.baby7blog.modules.blog.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 根据用户名获取用户
    @Override
    @Cacheable(value = CommonConstants.REDIS_GROUP_USER, key = "'auth/'+#username")
    public AdminUser getUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = baseMapper.selectOne(queryWrapper);
        if(user != null){
            AdminUser adminUser = new AdminUser();
            adminUser.setUsername(username);
            adminUser.setPassword(user.getPassword());
            adminUser.setEnable(Boolean.TRUE);
            return adminUser;
        }
        return null;
    }
}
