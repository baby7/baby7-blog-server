package com.baby7blog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统设置
 */
@Data
@AllArgsConstructor
@TableName("setting")
@EqualsAndHashCode(callSuper = true)
public class Setting extends Model<Setting> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * json格式博客数据
     */
    private String content;
    /**
     * markdown博主介绍
     */
    private String blogger;
}
