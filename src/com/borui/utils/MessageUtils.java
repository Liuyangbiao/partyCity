package com.borui.utils;

import java.util.HashMap;
import java.util.Map;

import com.borui.web.model.Message;
import com.borui.web.service.IMessageService;


/**
 * 
 * 站内消息
 *
 */
public class MessageUtils {
	
	/**
	 * 
	 * 发送站内消息
	 * 
	 * @param from		发送消息的人
	 * @param to		接收消息的人
	 * @param param		messageType_contentType_content
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static void sendSystemMessage(Map<String, Object> from, Map<String, Object> to, String param) throws Exception{
		if(from != null && to != null && param != null){
			IMessageService<Message> messageService = (IMessageService<Message>) ApplicationContextHelper.getBean("messageServiceImpl");
			Map<String, Object> msg = new HashMap<String, Object>();
			messageService.add(msg);
		} else {
			return;
		}
	}

}
