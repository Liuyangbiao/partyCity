package com.borui.web.mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper<T> extends BaseMapper<T> {
    
	/**
	 * 添加一条记录
	 * 
	 * 
	 * <ol>必传参数</ol>
	 * <ol>phone 				电话</ol>
	 * <ol>pwd 					密码（前端加密）</ol>
	 * <ol>code 				邀请码</ol>
	 * 
	 * 
	 * <ol>可传参数</ol>
	 * <ol>name 				名字</ol>
	 * <ol>sex 					性别</ol>
	 * <ol>birthday 			生日（秒数）</ol>
	 * <ol>industry 			行业</ol>
	 * <ol>company 				公司</ol>
	 * <ol>position 			title</ol>
	 * <ol>avatar 				头像</ol>
	 * <ol>city 				城市</ol>
	 * 
	 * 
	 * @param map
	 * @return			flag > 0 添加成功, flag < 0 添加失败
	 * @throws Exception
	 */
	int add(Map<String, Object> map) throws Exception;
	
	/**
	 * 删除记录（包含多条）
	 * @param map
	 * @return			flag > 0 删除成功, flag < 0 删除失败
	 * @throws Exception
	 */
	int del(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改记录（包含多条）
	 * @param map
	 * @return			flag > 0 修改成功, flag < 0 修改失败
	 * @throws Exception
	 */
	int modify(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询记录（包含分页）
	 * @param map
	 * @return				List<Map<String, Object>>
	 * @throws Exception
	 */
	List<Map<String, Object>> search(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取某些行业的用户列表
	 * @param map
	 * @return				List<Map<String, Object>>
	 * @throws Exception
	 */
	List<Map<String, Object>> searchByIndustry(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询详细
	 * @param map
	 * @return		Map<String, Object>
	 * @throws Exception
	 */
	Map<String, Object> searchByPK(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改密码
	 * @param map
	 * @return	flag > 0 修改成功，flag < 0 修改失败
	 * @throws Exception
	 */
	int update(Map<String, Object> map) throws Exception;

	/**
	 * 根据主键查询通讯录
	 * @param param
	 * @return	List<Map<String, Object>>
	 * @throws Exception
	 */
	List<Map<String, Object>> links(Map<String, Object> param) throws Exception;

	/**
	 * 查询派对信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> searchParty(Map<String, Object> param) throws Exception;

	int addLinks(Map<String, Object> param);
	
}