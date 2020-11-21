package com.baby7blog.modules.blog.service.impl;

import com.baby7blog.modules.blog.entity.BlogLabel;
import com.baby7blog.modules.blog.mapper.BlogLabelMapper;
import com.baby7blog.modules.blog.service.BlogLabelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogLabelServiceImpl extends ServiceImpl<BlogLabelMapper, BlogLabel> implements BlogLabelService {

    /**
     * 根据博客ID获取标签ID列表
     */
    @Override
    public List<Integer> getLabelIdsByBlogId(Integer blogId) {
        LambdaQueryWrapper<BlogLabel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogLabel::getBlogId, blogId);
        List<BlogLabel> list = baseMapper.selectList(queryWrapper);
        return list.stream().map(BlogLabel::getLabelId).collect(Collectors.toList());
    }
}
