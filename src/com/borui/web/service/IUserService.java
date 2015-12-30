package com.borui.web.service;

import java.util.Map;

public interface IUserService<T> {
	
	/**
	 * 添加一条记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> add(Map<String, Object> map) throws Exception;
	
	/**
	 * 删除记录（包含多条）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> del(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改记录（包含多条）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> modify(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询多条记录（包含分页信息）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> search(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询单挑记录详细
	 * 
	 * <ol>PK的含义是：主键，沿用数据库的设计</ol>
	 * <ol>FK的含义是：外键，沿用数据库的设计</ol>
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> selectByPK(Map<String, Object> map) throws Exception;
	
	/**
	 * 忘记密码（根据手机号重置密码）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> forgetPwd(Map<String, Object> map) throws Exception;
	
	/**
	 * 登录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> login(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改密码
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> update(Map<String, Object> map) throws Exception;

	/**
	 * 查看通讯录
	 * @param map
	 * @return	list<Map<String, Object>>
	 * 
	 * @throws Exception
	 */
	Map<String, Object> links(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * 导入联系人（根据举办活动集体导入）
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> linked(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 
	 * 审核联系人
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> checkPeopleInfo(Map<String, Object> map) throws Exception;
	
}
