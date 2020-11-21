package com.baby7blog.modules.blog.service.impl;

import com.baby7blog.modules.blog.entity.Menu;
import com.baby7blog.modules.blog.mapper.MenuMapper;
import com.baby7blog.modules.blog.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 菜单
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
