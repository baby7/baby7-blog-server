package com.baby7blog.modules.blog.vo;

import com.baby7blog.modules.blog.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论数据
 */
@Data
public class CommentData implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
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

    /**
     * 子评论列表
     */
    private List<Comment> childrenList;
}
