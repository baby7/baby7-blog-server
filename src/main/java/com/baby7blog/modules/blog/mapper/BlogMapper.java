package com.baby7blog.modules.blog.mapper;

import com.baby7blog.modules.blog.dto.BlogDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baby7blog.modules.blog.entity.Blog;

import java.util.List;

/**
 * 博客
 */
public interface BlogMapper extends BaseMapper<Blog> {

	/**
	 * 分页查询（去除内容）
	 */
	IPage<Blog> getMainBlogPage(Page page, BlogDTO blogDTO);

	/**
	 * 获取上一个博客
	 */
	Blog getPrevBlog(Blog blog);

	/**
	 * 获取下一个博客
	 */
	Blog getNextBlog(Blog blog);

	/**
	 * 博客访问+1
	 */
	void lookBlog(Blog blog);
}
