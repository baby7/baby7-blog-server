package com.baby7blog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 评论
 */
@Data
@TableName("comment")
@EqualsAndHashCode(callSuper = true)
public class Comment extends Model<Comment> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 博客ID
     */
    private Integer blogId;
    /**
     * 回复的祖先评论id
     */
    private Integer ancestorId;
    /**
     * 直接回复的评论id
     */
    private Integer replyId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户地址
     */
    private String url;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 评论用户的浏览器
     */
    private String browser;
    /**
     * 评论用户的系统
     */
    private String system;
}
