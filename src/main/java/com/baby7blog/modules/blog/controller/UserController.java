package com.baby7blog.modules.blog.controller;

import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.common.ConvertBeanUtils;
import com.baby7blog.modules.blog.common.ResultTypeConstants;
import com.baby7blog.modules.blog.entity.User;
import com.baby7blog.modules.blog.service.UserService;
import com.baby7blog.modules.blog.vo.R;
import com.baby7blog.modules.blog.vo.UserData;
import com.baby7blog.util.RedisUtil;
import com.baby7blog.util.security.JwtUtils;
import com.baby7blog.util.security.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 管理用户
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    // 缓存服务
    private final RedisUtil redisUtil;

    /**
     * 登录
     */
    @PostMapping("/info/login")
    public R login(@RequestBody User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User userInfo = userService.getOne(queryWrapper);
        //用户不存在
        if(Objects.isNull(userInfo)) {
            return R.failed(ResultTypeConstants.ACCOUNT_USER_NOT);
        }
        // 验证密码
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bcryptPasswordEncoder.matches(user.getPassword() + CommonConstants.SALT, userInfo.getPassword())) {
            return R.failed(ResultTypeConstants.ACCOUNT_PASSWORD_ERROR);
        }
        // 生成token
        String token = JwtUtils.sign(userInfo.getUsername(), userInfo.getPassword());
        userInfo.setPassword(null);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstants.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstants.PREFIX_USER_TOKEN + token, JwtUtils.EXPIRE_TIME * 2 / 1000);
        UserData userData = new UserData();
        userData.setToken(token);
        return R.ok(userData, "success");
    }

    /**
     * 注销登录
     */
    @PostMapping("/change/logout")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_USER, allEntries = true)})
    public R logout() {
        redisUtil.del(CommonConstants.PREFIX_USER_TOKEN + SecurityUtils.getUser().getToken());
        return R.ok();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/change/info")
    @Cacheable(value = CommonConstants.REDIS_GROUP_USER, key = "'info'")
    public R getInfo() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, "admin");
        User res = userService.getOne(queryWrapper);
        if(Objects.nonNull(res)){
            UserData info = ConvertBeanUtils.doToDto(res, UserData.class);
            return R.ok(info, "success");
        }
        return R.ok();
    }

    /**
     * 修改密码
     */
    @PostMapping("/change/password")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_USER, allEntries = true)})
    public R setPassword(@RequestParam("oldPassword") String oldPassword,
                      @RequestParam("password") String password,
                      @RequestParam("username") String username) {
        if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password) || StringUtils.isBlank(username)){
            return R.failed(ResultTypeConstants.ACCOUNT_INFO_NTO);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User userInfo = userService.getOne(queryWrapper);
        //用户不存在
        if(Objects.isNull(userInfo)) {
            return R.failed(ResultTypeConstants.ACCOUNT_USER_NOT);
        }
        // 验证密码
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bcryptPasswordEncoder.matches(oldPassword + CommonConstants.SALT, userInfo.getPassword())) {
            return R.failed(ResultTypeConstants.ACCOUNT_PASSWORD_ERROR);
        }
        redisUtil.del(CommonConstants.PREFIX_USER_TOKEN + SecurityUtils.getUser().getToken());
        userInfo.setPassword(bcryptPasswordEncoder.encode(password + CommonConstants.SALT));
        return R.returnRest(userService.updateById(userInfo));
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/change/info")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_USER, allEntries = true)})
    public R setInfo(@RequestParam("name") String name,
                  @RequestParam("avatar") String avatar,
                  @RequestParam("username") String username) {
        if(StringUtils.isBlank(name) || StringUtils.isBlank(avatar) || StringUtils.isBlank(username)){
            return R.failed(ResultTypeConstants.ACCOUNT_INFO_NTO);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User userInfo = userService.getOne(queryWrapper);
        //用户不存在
        if(Objects.isNull(userInfo)) {
            return R.failed(ResultTypeConstants.ACCOUNT_PASSWORD_ERROR);
        }
        userInfo.setName(name);
        userInfo.setAvatar(avatar);
        return R.returnRest(userService.updateById(userInfo));
    }
}
