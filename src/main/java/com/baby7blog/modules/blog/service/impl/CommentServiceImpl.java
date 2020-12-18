package com.baby7blog.modules.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baby7blog.modules.blog.entity.Comment;
import com.baby7blog.modules.blog.mapper.CommentMapper;
import com.baby7blog.modules.blog.service.CommentService;
import com.baby7blog.modules.blog.service.SettingService;
import com.baby7blog.modules.file.service.FileService;
import com.baby7blog.util.AvatarHelper;
import com.baby7blog.util.email.Email;
import com.baby7blog.util.email.EmailUtil;
import com.baby7blog.util.file.Base64Converter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 评论
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private FileService fileService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 进行评论
     */
    @Override
    public boolean comment(Comment comment) {
        //判断是否需要重新生成头像（使用头像和邮箱双重判断）
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getNickname, comment.getNickname())
                    .eq(Comment::getEmail, comment.getEmail());
        List<Comment> checkList = baseMapper.selectList(queryWrapper);
        //如果需要重新生成
        if(checkList.isEmpty()){
            //生成base64格式头像
            String avatarBase64String = AvatarHelper.getAvatar(comment.getNickname() + comment.getEmail());
            //转为MultipartFile格式
            MultipartFile avatar = Base64Converter.converter(avatarBase64String);
            //保存到本地/云
            String imgUrl = fileService.uploadFile(avatar);
            comment.setAvatar(imgUrl);
        }
        else {
            comment.setAvatar(checkList.get(0).getAvatar());
        }
//        sendEmail(comment);
        return baseMapper.insert(comment) > 0;
    }

    private void sendEmail(Comment comment){
        Email email = new Email();
        email.setAvatar(comment.getAvatar());
        email.setNickname(comment.getNickname());
        email.setUrl(comment.getUrl());
        email.setDate(DateUtil.formatLocalDateTime(comment.getCreateTime()));
        email.setContent(comment.getContent().substring(0,25) + "...");
        email.setTo(comment.getEmail());
        //取得系统设置
        JSONObject setting = settingService.getSetting();
        JSONObject emailSetting = setting.getJSONObject("email");
        email.setHost(emailSetting.getString("host"));
        email.setUsername(emailSetting.getString("username"));
        email.setPassword(emailSetting.getString("password"));
        if(comment.getBlogId().equals(0)){
            email.setPage(emailSetting.getString("url") + "comment");
        }
        else {
            email.setPage(emailSetting.getString("url") + "/myBlog?id=" + comment.getBlogId());
        }
        emailUtil.send(email);
    }
}
