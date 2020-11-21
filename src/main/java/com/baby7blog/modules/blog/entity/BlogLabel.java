package com.baby7blog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 博客标签关联
 */
@Data
@TableName("blog_label")
@EqualsAndHashCode(callSuper = true)
public class BlogLabel extends Model<BlogLabel> {
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
     * 标签ID
     */
    private Integer labelId;
}