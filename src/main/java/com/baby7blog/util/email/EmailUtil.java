package com.baby7blog.util.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

/**
 * 邮件发送工具类
 */
@Component
public class EmailUtil {

    private static final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void send(Email email) {
        try {
            //设置发件人信息
            javaMailSender.setDefaultEncoding("UTF-8");
            javaMailSender.setHost(email.getHost());
            javaMailSender.setUsername(email.getUsername());
            javaMailSender.setPassword(email.getPassword());
            //取得邮件模板文件
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(email.getUsername());
            helper.setTo(email.getTo());
            helper.setSubject("您在博客的评论有朋友回复啦");
            Context context = new Context();
            context.setVariable("email", email);
            helper.setText(templateEngine.process("email", context), true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
