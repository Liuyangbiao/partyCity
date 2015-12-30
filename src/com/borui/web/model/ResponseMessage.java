package com.borui.web.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 响应数据的ResponseMessage类
 *
 */
public class ResponseMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	private String message;
	
	private String code;
	
	private Map<String, Object> map;
	
	@SuppressWarnings("rawtypes")
	private List list;
	
	private List<Map<String, Object>> listMaps;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}

	public List<Map<String, Object>> getListMaps() {
		return listMaps;
	}

	public void setListMaps(List<Map<String, Object>> listMaps) {
		this.listMaps = listMaps;
	}
}
