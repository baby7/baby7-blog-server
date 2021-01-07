package com.baby7blog.modules.blog.vo;


import com.baby7blog.modules.blog.entity.Blog;
import com.baby7blog.modules.blog.entity.Label;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 博客数据
 */
@Data
public class BlogData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 菜单ID
     */
    private String menuId;
    /**
     * 博客被访问次数
     */
    private Integer lookNum;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片
     */
    private String img;
    /**
     * 内容
     */
    private String content;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 标签列表
     */
    List<Label> labelList;

    /**
     * 上一篇博客
     */
    Blog prev;

    /**
     * 下一篇博客
     */
    Blog next;
}
