package com.borui.web.service;

import java.util.Map;

public interface IPartyService<T> {

	/**
	 * 添加一条记录
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> add(Map<String, Object> map) throws Exception;

	/**
	 * 删除记录（包含多条）
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> del(Map<String, Object> map) throws Exception;

	/**
	 * 修改记录（包含多条）
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> modify(Map<String, Object> map) throws Exception;

	/**
	 * 查询多条记录（包含分页信息）
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> search(Map<String, Object> map) throws Exception;

	/**
	 * 查询单挑记录详细
	 * 
	 * <ol>
	 * PK的含义是：主键，沿用数据库的设计
	 * </ol>
	 * <ol>
	 * FK的含义是：外键，沿用数据库的设计
	 * </ol>
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> selectByPK(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * 报名活动
	 * 
	 * <ol>必传参数</ol>
	 * <ol>partyId				派对pid</ol>
	 * <ol>userId				报名人pid</ol>
	 * <ol>可传参数</ol>
	 * <ol>partyOwner			派对所有者pid</ol>
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> joinParty(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * 取消报名派对
	 * 
	 * <ol>必传参数</ol>
	 * <ol>partyId					派对pid</ol>
	 * <ol>userId					登录人pid</ol>
	 * 
	 * 
	 * @param request
	 * @return
	 */
	Map<String, Object> delPartyLog(Map<String, Object> map) throws Exception;
}
