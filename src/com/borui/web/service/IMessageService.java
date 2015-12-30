package com.borui.web.service;

import java.util.Map;


public interface IMessageService<T> {

	Map<String, Object> add(Map<String, Object> map) throws Exception;
	
	Map<String, Object> search(Map<String, Object> map) throws Exception;
	
	Map<String, Object> modify(Map<String, Object> map) throws Exception;
	
	Map<String, Object> searchById(Map<String, Object> map) throws Exception;
	
	Map<String, Object> delete(Map<String, Object> map) throws Exception;
}
