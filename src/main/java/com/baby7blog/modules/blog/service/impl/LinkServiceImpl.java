package com.baby7blog.modules.blog.service.impl;

import com.baby7blog.modules.blog.entity.Link;
import com.baby7blog.modules.blog.mapper.LinkMapper;
import com.baby7blog.modules.blog.service.LinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
}
