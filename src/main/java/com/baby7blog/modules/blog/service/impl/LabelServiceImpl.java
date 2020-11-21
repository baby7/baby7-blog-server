package com.baby7blog.modules.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baby7blog.modules.blog.entity.Label;
import com.baby7blog.modules.blog.mapper.LabelMapper;
import com.baby7blog.modules.blog.service.LabelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {

    /**
     * 根据标签id列表获取标签列表
     */
    @Override
    public List<Label> getLabelByIds(List<Integer> labelIds) {
        LambdaQueryWrapper<Label> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Label::getId, labelIds);
        if(labelIds.isEmpty()) return new ArrayList<>();
        return baseMapper.selectList(queryWrapper);
    }
}
