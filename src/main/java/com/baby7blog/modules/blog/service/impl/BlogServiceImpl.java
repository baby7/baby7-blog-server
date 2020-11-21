package com.baby7blog.modules.blog.service.impl;

import com.baby7blog.modules.blog.common.ConvertBeanUtils;
import com.baby7blog.modules.blog.dto.BlogDTO;
import com.baby7blog.modules.blog.entity.Blog;
import com.baby7blog.modules.blog.entity.Label;
import com.baby7blog.modules.blog.mapper.BlogMapper;
import com.baby7blog.modules.blog.service.BlogLabelService;
import com.baby7blog.modules.blog.service.BlogService;
import com.baby7blog.modules.blog.service.LabelService;
import com.baby7blog.modules.blog.vo.BlogData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
	@Autowired
	private LabelService labelService;
	@Autowired
	private BlogLabelService blogLabelService;

	/**
	 * 分页查询（去除内容）
	 */
	@Override
	public IPage getMainBlogPage(Page page, BlogDTO blogDTO) {
		return baseMapper.getMainBlogPage(page, blogDTO);
	}

	@Override
	@Cacheable(value="blogRedisManager", key="'get'+#id")
	public BlogData getById(Integer id){
		Blog blog = baseMapper.selectById(id);
		List<Integer> labelIds = blogLabelService.getLabelIdsByBlogId(id);
		List<Label> labelList = labelService.getLabelByIds(labelIds);
		BlogData blogData = ConvertBeanUtils.doToDto(blog, BlogData.class);
		blogData.setLabelList(labelList);
		blogData.setPrev(baseMapper.getPrevBlog(blog));
		blogData.setNext(baseMapper.getNextBlog(blog));
		return blogData;
	}

	/**
	 * 博客访问+1
	 */
	@Async
	@Override
	public void lookBlog(Blog blog) {
		baseMapper.lookBlog(blog);
	}

}
