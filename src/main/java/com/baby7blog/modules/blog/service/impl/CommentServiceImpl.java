package com.baby7blog.modules.blog.service.impl;

import com.baby7blog.config.WebConfig;
import com.baby7blog.modules.blog.entity.Comment;
import com.baby7blog.modules.blog.mapper.CommentMapper;
import com.baby7blog.modules.blog.service.CommentService;
import com.baby7blog.modules.file.local.FileUploadUtils;
import com.baby7blog.modules.file.service.FileService;
import com.baby7blog.util.AvatarHelper;
import com.baby7blog.util.Base64Converter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 评论
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    FileService fileService;

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
        return baseMapper.insert(comment) > 0;
    }


}
