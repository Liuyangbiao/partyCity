package com.borui.web.mapper;

import java.util.List;
import java.util.Map;

public interface VipCodeMapper {
	
	int add(Map<String, Object> map) throws Exception;
	
	int modify(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> search(Map<String, Object> map) throws Exception;
}