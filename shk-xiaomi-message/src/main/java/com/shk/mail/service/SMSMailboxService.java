package com.shk.mail.service;

import com.alibaba.fastjson.JSONObject;
import com.shk.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SMSMailboxService implements MessageAdapter {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sendMail;
    @Override
    public void distribute(JSONObject jsonObject) {
      String mail = jsonObject.getString("mail");
      String userName = jsonObject.getString("userName");
      log.info("###消费者收到消息...mail:{},userNamae:{}",mail,userName);
      //发送邮件
       // 开始发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendMail);
        message.setTo(mail); // 自己给自己发送邮件
        message.setSubject("恭喜您成为微信商城的信用...");
        message.setText("恭喜您"+userName+"今天成为了微信商城的用户,谢谢您的关注!");
        log.info("###发送短信邮箱 mail:{}", mail);
        mailSender.send(message);

    }
}
