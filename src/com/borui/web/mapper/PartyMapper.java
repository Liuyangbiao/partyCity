package com.borui.web.mapper;

import java.util.List;
import java.util.Map;

public interface PartyMapper<T> {
    
	/**
	 * 添加一条记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int add(Map<String, Object> map) throws Exception;
	
	/**
	 * 删除记录（包含多条）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int del(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改记录（包含多条）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int modify(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询记录（包含分页）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> search(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询详细
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> searchByPK(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * 查询我参加的派对信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> searchByFKUser(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * 报名派对
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int addPartyLog(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * 取消报名
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	int delPartyLog(Map<String, Object> param) throws Exception;
	
	/**
	 * 计算派对参与行业人数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> countNumByPartyId(Map<String, Object> param) throws Exception;
}