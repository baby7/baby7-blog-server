package com.baby7blog.modules.blog.controller;

import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.entity.Blog;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baby7blog.modules.blog.entity.Label;
import com.baby7blog.modules.blog.service.LabelService;
import com.baby7blog.modules.blog.vo.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;


/**
 * 标签
 */
@RestController
@AllArgsConstructor
@RequestMapping("/label")
public class LabelController {

    private final LabelService labelService;

    /**
     * 分页查询
     */
    @GetMapping("/info/page")
    @Cacheable(value = CommonConstants.REDIS_GROUP_LABEL, key = "'page/'+#page.current+'/'+#page.size")
    public R getLabelPage(Page page, Label label) {
        return R.ok(labelService.page(page, Wrappers.query(label)));
    }

    /**
     * 标签列表
     */
    @GetMapping("/info/list")
    @Cacheable(value = CommonConstants.REDIS_GROUP_LABEL, key = "'list/'")
    public R list() {
        return R.ok(labelService.list());
    }

    /**
     * 通过id查询标签
     */
    @GetMapping("/info/{id}")
    @Cacheable(value = CommonConstants.REDIS_GROUP_LABEL, key = "'get'+#id")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(labelService.getById(id));
    }

    /**
     * 新增标签
     */
    @PostMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R save(@RequestBody Label label) {
        return R.ok(labelService.save(label));
    }

    /**
     * 修改标签
     */
    @PutMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R updateById(@RequestBody Label label) {
        return R.ok(labelService.updateById(label));
    }

    /**
     * 通过id删除标签
     */
    @DeleteMapping("/change/{id}")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R removeById(@PathVariable Integer id) {
        return R.ok(labelService.removeById(id));
    }

}
