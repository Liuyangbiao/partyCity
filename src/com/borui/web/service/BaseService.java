package com.borui.web.service;

import java.util.Map;

import com.borui.web.mapper.BaseMapper;

/**
 * @Description base service CRUD
 * @Create Date：2014-12-3 下午3:02:36    
 * @version 1.0
 */
public abstract class BaseService<T>{
	
	public abstract BaseMapper<T> getMapper();

	
	public void add(T t)  throws Exception{
		getMapper().add(t);
	}
	
	public void update(T t)  throws Exception{
		getMapper().update(t);
	}
	
	public void delete(Object... ids) throws Exception{
		if(ids == null || ids.length < 1){
			return;
		}
		for(Object id : ids ){
			getMapper().delete(id);
		}
	}

	public T queryById(Map<String, Object> map) throws Exception{
		return getMapper().queryById(map);
	}
}
