package com.baby7blog.config;

import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.dto.AdminUser;
import com.baby7blog.modules.blog.service.UserService;
import com.baby7blog.util.security.JwtUtils;
import com.baby7blog.util.RedisUtil;
import com.baby7blog.util.security.jwt.oConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 权限校验
 */
@Component
@Slf4j
public class ShiroRealm {

    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    /**
     * 校验token的有效性
     */
    public AdminUser checkUserTokenIsEffect(String token) {
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtils.getUsername(token);
        if (username == null) {
            return null;
        }
        // 查询用户信息
        AdminUser adminUser = userService.getUserByUsername(username);
        if (adminUser == null) {
            return null;
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, username, adminUser.getPassword())) {
            return null;
        }
        adminUser.setToken(token);
        return adminUser;
    }

    /**
     * JWTToken刷新生命周期 （实现： 用户在线操作不掉线功能）
     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)，缓存有效期设置为Jwt有效时间的2倍
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
     * 4、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 注意： 前端请求Header中设置Authorization保持不变，校验有效性以缓存中的token为准。
     *       用户过期时间 = Jwt有效时间 * 2。
     */
    public boolean jwtTokenRefresh(String token, String userName, String passWord) {
        String cacheToken = String.valueOf(redisUtil.get(CommonConstants.PREFIX_USER_TOKEN + token));
        if (oConvertUtils.isNotEmpty(cacheToken)) {
            // 校验token有效性
            if (!JwtUtils.verify(cacheToken, userName, passWord)) {
                String newAuthorization = JwtUtils.sign(userName, passWord);
                // 设置超时时间
                redisUtil.set(CommonConstants.PREFIX_USER_TOKEN + token, newAuthorization);
                redisUtil.expire(CommonConstants.PREFIX_USER_TOKEN + token, JwtUtils.EXPIRE_TIME *2 / 1000);
            }
            return true;
        }
        return false;
    }
}
