package com.baby7blog.modules.blog.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录返回
 */
@Data
public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * token
     */
    private String token;
}
