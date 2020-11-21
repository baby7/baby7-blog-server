package com.baby7blog.modules.blog.controller;

import com.baby7blog.modules.blog.common.CommonConstants;
import com.baby7blog.modules.blog.common.ConvertBeanUtils;
import com.baby7blog.modules.blog.entity.Comment;
import com.baby7blog.modules.blog.service.CommentService;
import com.baby7blog.modules.blog.vo.CommentData;
import com.baby7blog.modules.blog.vo.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.util.*;


/**
 * 评论
 */
@RestController
@AllArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    /**
     * 分页查询
     */
    @GetMapping("/info/page")
    @Cacheable(value = CommonConstants.REDIS_GROUP_COMMENT, key = "'page/'+#page.current+'/'+#page.size")
    public R getCommentPage(Page page, Comment comment) {
        return R.ok(commentService.page(page, Wrappers.query(comment)));
    }


    /**
     * 通过id查询标签
     */
    @GetMapping("/info/{id}")
    @Cacheable(value = CommonConstants.REDIS_GROUP_COMMENT, key = "'get'+#id")
    public R getById(@PathVariable("id") Integer id) {
        return R.ok(commentService.getById(id));
    }


    /**
     * 通过博客id查询标签列表
     */
    @GetMapping("/info/getTree/{id}")
    @Cacheable(value = CommonConstants.REDIS_GROUP_COMMENT, key = "'get'+#id")
    public R getTree(@PathVariable("id") Integer id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getBlogId, id);
        List<Comment> commentList = commentService.list(queryWrapper);
        commentList.sort(Comparator.comparingLong(x -> x.getCreateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli()));
        List<CommentData> commentDataList = new ArrayList<>();
        for (Comment comment : commentList) {
            if(comment.getAncestorId().equals(0) && comment.getReplyId().equals(0)){
                CommentData commentData = ConvertBeanUtils.doToDto(comment, CommentData.class);
                commentData.setChildrenList(new ArrayList<>());
                commentDataList.add(commentData);
            }
        }
        Collections.reverse(commentList);
        for (Comment comment : commentList) {
            for (CommentData commentData : commentDataList) {
                if((!comment.getReplyId().equals(0)) && (!comment.getAncestorId().equals(0)) &&
                    (comment.getReplyId().equals(commentData.getId()) || comment.getAncestorId().equals(commentData.getId()))){
                    commentData.getChildrenList().add(comment);
                }
            }
        }
        return R.ok(commentDataList);
    }

    /**
     * 新增评论
     */
    @PostMapping("/change/comment")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_COMMENT, allEntries = true)})
    public R save(@RequestBody Comment comment) {
        if(StringUtils.isBlank(comment.getNickname())){
            return R.failed("评论失败，昵称不能为空");
        }
        if(StringUtils.isBlank(comment.getEmail())){
            return R.failed("评论失败，邮箱不能为空");
        }
        if(StringUtils.isBlank(comment.getContent())){
            return R.failed("评论失败，评论不能为空");
        }
        return R.ok(commentService.comment(comment), "评论成功");
    }

    /**
     * 修改评论
     */
    @PutMapping("/change")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_COMMENT, allEntries = true)})
    public R updateById(@RequestBody Comment comment) {
        return R.ok(commentService.updateById(comment));
    }

    /**
     * 通过id删除评论
     */
    @DeleteMapping("/change/{id}")
    @Caching(evict = {@CacheEvict(value = CommonConstants.REDIS_GROUP_COMMENT, allEntries = true)})
    public R removeById(@PathVariable Integer id) {
        return R.ok(commentService.removeById(id));
    }

}
