package com.baby7blog.modules.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baby7blog.modules.blog.entity.Setting;
import com.baby7blog.modules.blog.mapper.SettingMapper;
import com.baby7blog.modules.blog.service.SettingService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 系统设置
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

    @Override
    public JSONObject getSetting() {
        Setting setting = baseMapper.selectById(1);
        return JSON.parseObject(setting.getContent());
    }

    @Override
    public boolean setSetting(JSONObject settingData) {
        settingData.put("updateTime", DateUtil.formatDateTime(new Date()));
        LambdaUpdateWrapper<Setting> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Setting::getId, 1)
                     .set(Setting::getContent, settingData.toJSONString());
        return baseMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public Setting getBlogger() {
        LambdaQueryWrapper<Setting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setting::getId, 1);
        Setting setting = baseMapper.selectOne(queryWrapper);
        setting.setContent(null);
        return setting;
    }

    @Override
    public boolean setBlogger(Setting setting) {
        LambdaUpdateWrapper<Setting> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Setting::getId, 1)
                .set(Setting::getBlogger, setting.getBlogger());
        return baseMapper.update(null, updateWrapper) > 0;
    }
}
