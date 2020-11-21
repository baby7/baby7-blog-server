package com.baby7blog.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baby7blog.modules.blog.entity.Label;

import java.util.List;

/**
 * 标签
 */
public interface LabelService extends IService<Label> {

    /**
     * 根据标签id列表获取标签列表
     */
    List<Label> getLabelByIds(List<Integer> labelIds);
}
