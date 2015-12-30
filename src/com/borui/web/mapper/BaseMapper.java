package com.borui.web.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

	public void add(T t);
	
	public void update(T t);
	
	public void delete(Object id);

	public List<T> queryByList(Map<String, Object> map);
	
	public T queryById(Map<String, Object> map);
}
