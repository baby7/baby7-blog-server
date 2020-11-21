package com.baby7blog.modules.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.entity.Blog;
import com.baby7blog.modules.blog.entity.Setting;
import com.baby7blog.modules.blog.service.BlogService;
import com.baby7blog.modules.blog.service.LabelService;
import com.baby7blog.modules.blog.service.MenuService;
import com.baby7blog.modules.blog.service.SettingService;
import com.baby7blog.modules.blog.vo.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 系统设置
 */
@RestController
@AllArgsConstructor
@RequestMapping("/setting")
public class SettingController {

    private final SettingService settingService;
    private final BlogService blogService;
    private final MenuService menuService;
    private final LabelService labelService;

    /**
     * 查询系统设置
     */
    @GetMapping("/info/get")
    @Cacheable(value = CommonConstants.REDIS_GROUP_SETTING, key = "'get'")
    public R getById() {
        return R.ok(settingService.getSetting());
    }

    /**
     * 修改系统设置
     */
    @PutMapping("/change")
    @CacheEvict(value = CommonConstants.REDIS_GROUP_SETTING, allEntries=true)
    public R updateById(@RequestBody JSONObject settingData) {
        return R.ok(settingService.setSetting(settingData));
    }

    /**hhh
     * 查询博主信息
     */
    @GetMapping("/info/getBlogger")
    @Cacheable(value = CommonConstants.REDIS_GROUP_SETTING, key = "'getBlogger'")
    public R getBlogger() {
        return R.ok(settingService.getBlogger());
    }

    /**
     * 修改博主信息
     */
    @PostMapping("/change/setBlogger")
    @CacheEvict(value = CommonConstants.REDIS_GROUP_SETTING, allEntries=true)
    public R setBlogger(@RequestBody Setting setting) {
        return R.ok(settingService.setBlogger(setting));
    }

    /**
     * 博客统计
     */
    @GetMapping("/change/getMetric")
    public R getMetric() {
        Map<String, Integer> metric = new HashMap<>();
        metric.put("blogCount", blogService.count());
        metric.put("menuCount", menuService.count());
        metric.put("labelCount", labelService.count());
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("SUM(look_num) as lookNum");
        Blog blog = blogService.getOne(queryWrapper);
        if (blog== null){
            metric.put("lookCount", 0);
        }else{
            metric.put("lookCount", blog.getLookNum());
        }
        return R.ok(metric);
    }
}
