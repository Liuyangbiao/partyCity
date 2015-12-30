package com.borui.web.mapper;

import java.util.List;
import java.util.Map;

public interface MessageMapper {
	
	void insert(Map<String, Object> map) throws Exception;

	List<Map<String, Object>> search(Map<String, Object> param) throws Exception;

	void modify(Map<String, Object> param) throws Exception;

	void delete(Map<String, Object> param) throws Exception;
	
	
}