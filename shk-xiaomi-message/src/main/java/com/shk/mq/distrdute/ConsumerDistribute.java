package com.shk.mq.distrdute;

import com.alibaba.fastjson.JSONObject;
import com.shk.adapter.MessageAdapter;
import com.shk.constants.MQInterfaceType;
import com.shk.mail.service.SMSMailboxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消费消息 从对列中获取最新消息
 */
@Slf4j
@Component
public class ConsumerDistribute {
    @Autowired
    private SMSMailboxService smsMailboxService;

    @JmsListener(destination = "email_queue")
    public void distribute(String json){
     log.info("###消息服务###收到消息，消息内容json:{}",json);
     if(StringUtils.isEmpty(json)){
         return;
     }
        JSONObject jsonObject = new JSONObject().parseObject(json);
        JSONObject header = jsonObject.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");
        MessageAdapter messageAdapter = null;
        switch (interfaceType){
            case MQInterfaceType.SMS_MAIL:
                messageAdapter = smsMailboxService;
                break;
            default:
                break;

        }
        JSONObject content = jsonObject.getJSONObject("content");
        messageAdapter.distribute(content);
    }

}
