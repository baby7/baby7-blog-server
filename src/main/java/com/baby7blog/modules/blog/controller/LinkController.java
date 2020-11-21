package com.baby7blog.modules.blog.controller;

import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.entity.Link;
import com.baby7blog.modules.blog.service.LinkService;
import com.baby7blog.modules.blog.vo.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

/**
 * 友链
 */
@RestController
@AllArgsConstructor
@RequestMapping("/link")
public class LinkController {

    private final LinkService linkService;

    /**
     * 分页查询
     */
    @GetMapping("/info/page")
    @Cacheable(value = CommonConstants.REDIS_GROUP_LINK, key = "'page/'+#page.current+'/'+#page.size")
    public R getLinkPage(Page page, Link link) {
        return R.ok(linkService.page(page, Wrappers.query(link)));
    }


    /**
     * 通过id查询友链
     */
    @GetMapping("/info/{id}")
    @Cacheable(value = CommonConstants.REDIS_GROUP_LINK, key = "'get'+#id")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(linkService.getById(id));
    }

    /**
     * 新增友链
     */
    @PostMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_LINK, allEntries = true)})
    public R save(@RequestBody Link link) {
        return R.ok(linkService.save(link));
    }

    /**
     * 修改友链
     */
    @PutMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_LINK, allEntries = true)})
    public R updateById(@RequestBody Link link) {
        return R.ok(linkService.updateById(link));
    }

    /**
     * 通过id删除友链
     */
    @DeleteMapping("/change/{id}")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_LINK, allEntries = true)})
    public R removeById(@PathVariable Integer id) {
        return R.ok(linkService.removeById(id));
    }
}
