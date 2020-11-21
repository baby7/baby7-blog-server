package com.baby7blog.modules.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.baby7blog.modules.blog.entity.Setting;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统设置
 */
public interface SettingService extends IService<Setting> {

    /**
     * 获取设置
     */
    JSONObject getSetting();

    /**
     * 写入设置
     */
    boolean setSetting(JSONObject settingData);

    /**
     * 获取博主信息
     */
    Setting getBlogger();

    /**
     * 写入博主信息
     */
    boolean setBlogger(Setting setting);
}
