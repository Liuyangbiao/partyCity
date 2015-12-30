package com.borui.utils.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.borui.web.model.ResponseMessage;

public class ResponseMessageUtil {

	public static Map<String, Object> responseMsg(ResponseMessage responseMessage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("responseMessage", responseMessage);
		return map;
	}
	
	public static Map<String, Object> respMsg(String code,String status){
		Map<String, Object> stringMap = new HashMap<String,Object>();
		stringMap.put("code", code);
		stringMap.put("status", status);
		stringMap.put("result", "");
		return stringMap;
	}
	
	public static Map<String, Object> respMsg(String code,String status,Map<String, Object> map){
		Map<String, Object> msgMap = new HashMap<String,Object>();
		msgMap.put("code", code);
		msgMap.put("status", status);
		msgMap.put("result", map);
		return msgMap;
	}
	
	public static Map<String, Object> respMsg(String code,String status,String trade_no){
		Map<String, Object> msgMap = new HashMap<String,Object>();
		msgMap.put("code", code);
		msgMap.put("status", status);
		msgMap.put("trade_no", trade_no);
		return msgMap;
	}
	
	public static Map<String, Object> listMsg(String code,String status,List<Map<String, Object>> list){
		Map<String, Object> msgMap = new HashMap<String,Object>();
		msgMap.put("code", code);
		msgMap.put("status", status);
		msgMap.put("result", list);
		return msgMap;
	}

	public static Map<String, Object> listMsgForObject(String code, String status, List<Object> list) {
		Map<String, Object> msgMap = new HashMap<String,Object>();
		msgMap.put("code", code);
		msgMap.put("status", status);
		msgMap.put("result", list);
		return msgMap;
	}
}
