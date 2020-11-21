package com.baby7blog.modules.blog.controller;

import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.common.ConvertBeanUtils;
import com.baby7blog.modules.blog.dto.BlogDTO;
import com.baby7blog.modules.blog.entity.BlogLabel;
import com.baby7blog.modules.blog.entity.Label;
import com.baby7blog.modules.blog.service.BlogLabelService;
import com.baby7blog.modules.blog.vo.BlogData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baby7blog.modules.blog.entity.Blog;
import com.baby7blog.modules.blog.service.BlogService;
import com.baby7blog.modules.blog.vo.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 博客
 */
@RestController
@AllArgsConstructor
@RequestMapping("/blog")
@Slf4j
public class BlogController {

    private final BlogService blogService;
    private final BlogLabelService blogLabelService;

    /**
     * 分页查询
     */
    @GetMapping("/info/page")
    @Cacheable(value = CommonConstants.REDIS_GROUP_BLOG,
            key = "'page/'+#page.current+'/'+#page.size+'/'+#blogDTO.menuId+'/'+#blogDTO.labelId")
    public R getBlogPage(Page page, BlogDTO blogDTO) {
        return R.ok(blogService.getMainBlogPage(page, blogDTO));
    }

    /**
     * 分页查询（实时）
     */
    @GetMapping("/info/pageReal")
    public R getBlogpageReal(Page page, BlogDTO blogDTO) {
        return R.ok(blogService.getMainBlogPage(page, blogDTO));
    }

    /**
     * 博客列表
     */
    @GetMapping("/info/list")
    @Cacheable(value = CommonConstants.REDIS_GROUP_BLOG, key = "'list/'")
    public R list() {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Blog::getId, Blog::getTitle, Blog::getCreateTime);
        return R.ok(blogService.list(queryWrapper));
    }

    /**
     * 博客访问次数+1
     */
    @PostMapping("/info/look")
    public R look(@RequestBody Blog blog){
        blogService.lookBlog(blog);
        return R.ok();
    }


    /**
     * 通过id查询博客
     */
    @GetMapping("/info/{id}")
    @Cacheable(value = CommonConstants.REDIS_GROUP_BLOG, key = "'get'+#id")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(blogService.getById(id));
    }

    /**
     * 新增博客
     */
    @PostMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R save(@RequestBody BlogDTO blogDTO) {
        Blog blog = ConvertBeanUtils.dtoToDo(blogDTO, Blog.class);
        blogService.save(blog);
        List<BlogLabel> userRoleList = blogDTO.getLabelIdList()
                .stream().map(labelId -> {
                    BlogLabel blogLabel = new BlogLabel();
                    blogLabel.setBlogId(blog.getId());
                    blogLabel.setLabelId(labelId);
                    return blogLabel;
                }).collect(Collectors.toList());
        return R.ok(blogLabelService.saveBatch(userRoleList));
    }

    /**
     * 修改博客
     */
    @PutMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R updateById(@RequestBody BlogDTO blogDTO) {
        Blog blog = ConvertBeanUtils.dtoToDo(blogDTO, Blog.class);
        blogService.updateById(blog);
        blogLabelService.remove(Wrappers.<BlogLabel>update().lambda()
                .eq(BlogLabel::getBlogId, blog.getId()));
        blogDTO.getLabelIdList().forEach(labelId -> {
            BlogLabel blogLabel = new BlogLabel();
            blogLabel.setBlogId(blog.getId());
            blogLabel.setLabelId(labelId);
            blogLabel.insert();
        });
        return R.ok(Boolean.TRUE);
    }

    /**
     * 通过id删除博客
     */
    @DeleteMapping("/change/{id}")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R removeById(@PathVariable Integer id) {
        LambdaQueryWrapper<BlogLabel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlogLabel::getBlogId, id);
        blogLabelService.remove(queryWrapper);
        return R.ok(blogService.removeById(id));
    }

}
