package com.baby7blog.modules.blog.service;

import com.baby7blog.modules.blog.dto.BlogDTO;
import com.baby7blog.modules.blog.vo.BlogData;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baby7blog.modules.blog.entity.Blog;


/**
 * 博客
 */
public interface BlogService extends IService<Blog> {

	/**
	 * 分页查询（去除内容）
	 */
	IPage getMainBlogPage(Page page, BlogDTO blogDTO);

	/**
	 * 获取博客内容
	 */
	BlogData getById(Integer id);

	/**
	 * 博客访问+1
	 */
	void lookBlog(Blog blog);

}
