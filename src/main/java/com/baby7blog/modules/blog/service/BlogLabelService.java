package com.baby7blog.modules.blog.service;

import com.baby7blog.modules.blog.entity.BlogLabel;
import com.baby7blog.modules.blog.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 博客标签关联
 */
public interface BlogLabelService extends IService<BlogLabel> {

    /**
     * 根据博客ID获取标签ID列表
     */
    List<Integer> getLabelIdsByBlogId(Integer blogId);
}
