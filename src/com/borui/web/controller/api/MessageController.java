package com.borui.web.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.borui.utils.JsonPUtil;
import com.borui.utils.common.Constance;
import com.borui.utils.common.ResponseMessageUtil;
import com.borui.web.controller.BaseController;
import com.borui.web.model.Message;
import com.borui.web.service.IMessageService;


@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/api/message")
public class MessageController extends BaseController {
	
	/**
	 * 声明记录日志
	 */
	private Logger log = Logger.getLogger(MessageController.class);
	
	@Resource
	private IMessageService<Message> messageServiceImpl;
	
	/**
	 * 获取消息列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list")
	public Map<String, Object> list(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = messageServiceImpl.search(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : ResponseMessageUtil.listMsg(map.get("code").toString(), map.get("status").toString(), (List<Map<String, Object>>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/date/publishDateMatchInfo.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value="/del")
	public Map<String, Object> del(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = messageServiceImpl.delete(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/date/publishDateMatchInfo.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		return msg;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/modify")
	public Map<String, Object> modify(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = messageServiceImpl.modify(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/date/publishDateMatchInfo.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		return msg;
		
	}

}
