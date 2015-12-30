package com.borui.web.service;

import java.util.Map;

public interface IVipCodeService<T> {
	
	/**
	 * 添加一条记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> add(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改记录（包含多条）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> modify(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询多条记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> search(Map<String, Object> map) throws Exception;
	
}
