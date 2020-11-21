package com.baby7blog.modules.blog.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 社交账号
 */
@Data
public class SocialAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    private String type;
    /**
     * 图标
     */
    private String icon;
    /**
     * 二维码
     */
    private String qr;
}
