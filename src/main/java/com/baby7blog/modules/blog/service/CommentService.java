package com.baby7blog.modules.blog.service;

import com.baby7blog.modules.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 评论
 */
public interface CommentService extends IService<Comment> {

    /**
     * 进行评论
     */
    boolean comment(Comment comment);
}
