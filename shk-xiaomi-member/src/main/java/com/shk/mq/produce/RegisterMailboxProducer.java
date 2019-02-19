package com.shk.mq.produce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * 
 * @classDesc: 功能描述:(使用MQ调用消息服务,发送注册邮件)
 *
 */
@Service("registerMailboxProducer")
public class RegisterMailboxProducer {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	/**
	 * 
	 * @methodDesc: 功能描述:(消息服务发送者)
	 * @param: @param
	 *             destination
	 * @param: @param
	 *             json
	 */
	public void sendMess(Destination destination, String json) {
		jmsMessagingTemplate.convertAndSend(destination, json);
	}


}




