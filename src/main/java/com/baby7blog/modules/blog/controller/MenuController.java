package com.baby7blog.modules.blog.controller;

import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.entity.Menu;
import com.baby7blog.modules.blog.service.MenuService;
import com.baby7blog.modules.blog.vo.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;


/**
 * 菜单
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/info/page")
    @Cacheable(value = CommonConstants.REDIS_GROUP_MENU, key = "'page/'+#page.current+'/'+#page.size")
    public R getMenuPage(Page page, Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getSort);
        return R.ok(menuService.page(page, queryWrapper));
    }

    /**
     * 菜单列表
     */
    @GetMapping("/info/list")
    @Cacheable(value = CommonConstants.REDIS_GROUP_MENU, key = "'list/'")
    public R list() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getSort);
        return R.ok(menuService.list(queryWrapper));
    }

    /**
     * 通过ID查询菜单的详细信息
     */
    @GetMapping("/info/{id}")
    @Cacheable(value = "menuRedisManager", key = "'get'+#id")
    public R getById(@PathVariable Integer id) {
        return R.ok(menuService.getById(id));
    }

    /**
     * 新增菜单
     */
    @PostMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R save(@RequestBody Menu menu) {
        return R.ok(menuService.save(menu));
    }

    /**
     * 更新菜单
     */
    @PutMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R update(@RequestBody Menu menu) {
        return R.ok(menuService.updateById(menu));
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/change/{id}")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_BLOG, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_LABEL, allEntries = true),
            @CacheEvict(value = CommonConstants.REDIS_GROUP_MENU, allEntries = true)})
    public R removeById(@PathVariable Integer id) {
        return R.ok(menuService.removeById(id));
    }

}
