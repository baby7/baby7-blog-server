package com.baby7blog.util.email;

import lombok.Data;

@Data
public class Email {

    //链接服务器
    private String host;
    //账号
    private String username;
    //授权码
    private String password;
    //收件人邮箱
    private String to;

    //评论人头像
    private String avatar;
    //评论人昵称
    private String nickname;
    //评论人个人网站
    private String url;
    //评论时间
    private String date;
    //评论内容
    private String content;

    //网站具体地址
    private String page;
    //邮件标题
    private String title;
}
