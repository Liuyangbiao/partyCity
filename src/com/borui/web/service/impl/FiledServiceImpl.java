package com.borui.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.borui.utils.StringUtil;
import com.borui.utils.common.Constance;
import com.borui.web.mapper.PlaceMapper;
import com.borui.web.mapper.UserMapper;
import com.borui.web.model.BaseBean.STATE;
import com.borui.web.service.IFiledService;


@Service
public class FiledServiceImpl<T> implements IFiledService<T> {

	/**
	 * 声明日志记录
	 */
	private Logger log = Logger.getLogger(FiledServiceImpl.class);
	
	@Resource
	private PlaceMapper<T> placeMapper;
	
	@Resource
	private UserMapper<T> userMapper;
	
	//添加场地
	@Override
	public Map<String, Object> add(Map<String, Object> map) throws Exception {
		log.info(new Date() + "添加场地");
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("owner")) && StringUtil.checkNotNull(map.get("name")) && StringUtil.checkNotNull(map.get("address"))){
				
				/*
				 * 1、根据owner查询是否存在用户信息，存在继续，否则返回错误码
				 * 2、根据name查询是否已存在此场地，不存在继续，否则返回错误码
				 * 
				 */
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("pid", map.get("owner"));
				param = userMapper.searchByPK(param);
				
				if (null != param && !param.isEmpty()) {
					//必传参数
					
					param.clear();
					param.put("name", map.get("name"));
					List<Map<String, Object>> rel= placeMapper.search(param);
					
					//不存在同名场地
					if (null == rel || rel.size() <= 0) {
						param.clear();
						param.put("owner", map.get("owner"));
						param.put("name", map.get("name"));
						param.put("address", map.get("address"));
						//可传参数
						param.put("count", null == map.get("count") ? null : map.get("count"));
						param.put("phone", null == map.get("phone") ? null : map.get("phone"));
						param.put("froma", null == map.get("froma") ? null : map.get("froma"));
						param.put("content", null == map.get("content") ? null : map.get("content"));
						param.put("avatar", null == map.get("avatar") ? null : map.get("avatar"));
						param.put("type", null == map.get("type") ? null : map.get("type"));
						param.put("telCode", null == map.get("telCode") ? null : map.get("telCode"));
						param.put("lng", null == map.get("lng") ? null : map.get("lng"));
						param.put("lat", null == map.get("lat") ? null : map.get("lat"));
						param.put("photos", null == map.get("photos") ? null : map.get("photos"));
						
						//状态，上架：1，下架：0
						param.put("status", null == map.get("status") ? STATE.ENABLE.key : map.get("status"));
						
						
						param.put("createtimelong", System.currentTimeMillis());
						
						int flag = -1;
						try {
							flag = placeMapper.add(param);
						} catch (Exception e) {
							log.error(new Date() + "添加数据库失败");
							msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
							msg.put("status", "false");
						}
						if (flag > 0) {
							
							param = placeMapper.searchByPK(param);
							
							
							log.info(new Date() + "添加场地成功");
							msg.put("code", Constance.BASE_SUCCESS_CODE);
							msg.put("status", "true");
							msg.put("result", param);
						} else {
							log.info(new Date() + "添加场地失败");
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
						}
						
					} else {
						msg.put("code", Constance.FILED_NAME_IS_EXIST);
						msg.put("status", "false");
					}	
				} else {
					msg.put("code", Constance.USER_IS_NOT_EXIST);
					msg.put("status", "false");
				}
			} else {
				msg.put("code", Constance.PRIMARY_KEY_IS_NULL);
				msg.put("status", "false");
			}
		} else {
			msg.put("code", Constance.PARAMETERS_ERROR);
			msg.put("status", "false");
		}
		return msg;
	}

	@Override
	public Map<String, Object> del(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("ids")) && StringUtil.checkNotNull(map.get("pid"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("ids", map.get("ids"));
					List<Map<String, Object>> rel = placeMapper.search(param);
					if(null != rel && rel.size() > 0){
						
						
						//挑选自己的场地删除
						String ids = "";
						for(Map<String, Object> m : rel)
							if(map.get("pid").toString().equals(m.get("owner").toString()))
								ids += m.get("pid") + ",";
						
						int flag = -1;
						ids = ids.substring(0, ids.length()-1);
						param.clear();
						param.put("ids", ids);
						param.put("status", STATE.ENABLE.key);
						param.put("pid", map.get("pid"));
						flag = placeMapper.modifyStatus(param);
						
						if(flag > 0){
							msg.put("code", Constance.BASE_SUCCESS_CODE);
							msg.put("status", "true");
						} else {
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
						}
						
					} else {
						msg.put("code", Constance.DATA_IS_EMPTY);
						msg.put("status", "false");
					}
				} catch(Exception e) {
					log.error(new Date() + "删除场地信息失败" + e);
					msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
					msg.put("status", "false");
				}
			} else {
				msg.put("code", Constance.PRIMARY_KEY_IS_NULL);
				msg.put("status", "false");
			}
		} else {
			msg.put("code", Constance.PARAMETERS_ERROR);
			msg.put("status", "false");
		}
		return msg;
	}

	@Override
	public Map<String, Object> modify(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid")) && StringUtil.checkNotNull(map.get("owner"))){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("pid", map.get("owner"));
				param = userMapper.searchByPK(param);
				//存在用户信息
				if(null != param && !param.isEmpty()){
					
						param.clear();
						//选填参数
						param.put("name", null == map.get("name") ? null : map.get("name"));
						param.put("address", null == map.get("address") ? null : map.get("address"));
						param.put("count", null == map.get("count") ? null : map.get("count"));
						param.put("phone", null == map.get("phone") ? null : map.get("phone"));
						param.put("froma", null == map.get("froma") ? null : map.get("froma"));
						param.put("avatar", null == map.get("avatar") ? null : map.get("avatar"));
						param.put("content", null == map.get("content") ? null : map.get("content"));
						param.put("status", null == map.get("status") ? null : map.get("status"));
						param.put("type", null == map.get("type") ? null : map.get("type"));
						param.put("telCode", null == map.get("telCode") ? null : map.get("telCode"));
						param.put("lng", null == map.get("lng") ? null : map.get("lng"));
						param.put("lat", null == map.get("lat") ? null : map.get("lat"));
						param.put("photos", null == map.get("photos") ? null : map.get("photos"));
						
						
						
						//必填参数
						param.put("pid", map.get("pid"));
						param.put("owner", map.get("owner"));
						
						
						
						int flag = -1;
						try {
							//修改场地信息
							flag = placeMapper.modify(param);
						} catch(Exception e){
							log.error(new Date() + "修改场地信息失败" + e);
							msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
							msg.put("status", "false");
						}
						if(flag > 0) {
							msg.put("code", Constance.BASE_SUCCESS_CODE);
							msg.put("status", "true");
						} else {
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
						}					
				} else {
					msg.put("code", Constance.USER_IS_NOT_EXIST);
					msg.put("status", "false");
				}
			} else {
				msg.put("code", Constance.PRIMARY_KEY_IS_NULL);
				msg.put("status", "false");
			}
		} else {
			msg.put("code", Constance.PARAMETERS_ERROR);
			msg.put("status", "false");
		}
		return msg;
	}

	@Override
	public Map<String, Object> search(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		
		//组合查询条件
		Map<String, Object> param = new HashMap<String, Object>();
		
		//查询个人场地信息
		param.put("owner", null == map.get("owner") ? null : map.get("owner"));
		
		
		
		param.put("status", null == map.get("status") ? null : map.get("status"));
		
		
		//查询城市场地信息
		param.put("city", null == map.get("city") ? null : map.get("city"));
		
		
		
		param.put("type", null == map.get("type") ? null : map.get("type"));
		
		
		if(null != map.get("pageSize") && null != map.get("pageNo")){
			
			//分页计算
			param.put("pageNo", (Integer.parseInt(map.get("pageNo").toString()) -1 ) * Integer.parseInt(map.get("pageSize").toString()));
			param.put("pageSize", map.get("pageSize"));
			
		}
		List<Map<String, Object>> rel = placeMapper.search(param);
		//数据库执行查询正常
		if(null != rel){
			//有返回结果
			if(rel.size() > 0){
				
				
				param.clear();
				param.put("pageNo", null == map.get("pageNo") ? null : map.get("pageNo"));
				param.put("result", rel);
				
				
				msg.put("code", Constance.BASE_SUCCESS_CODE);
				msg.put("status", "true");
				msg.put("result", param);
			} else {
				param.clear();
				param.put("pageNo", null == map.get("pageNo") ? null : map.get("pageNo"));
				param.put("result", rel);
				msg.put("code", Constance.BASE_SUCCESS_CODE);
				msg.put("status", "false");
				msg.put("result", param);
			}
		} else {
			msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
			msg.put("status", "false");
		}
		return msg;
	}

	@Override
	public Map<String, Object> selectByPK(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("pid", map.get("pid"));
					param = placeMapper.searchByPK(param);
					if(null != param && !param.isEmpty()){
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
						msg.put("result", param);
					} else {
						msg.put("code", Constance.DATA_IS_EMPTY);
						msg.put("status", "false");
					}
				} catch (Exception e) {
					log.error(new Date() + "获取场地详细失败" + e);
					msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
					msg.put("status", "false");
				}
			} else {
				msg.put("code", Constance.PRIMARY_KEY_IS_NULL);
				msg.put("status", "false");
			}
		} else {
			msg.put("code", Constance.PARAMETERS_ERROR);
			msg.put("status", "false");
		}
		return msg;
	}

}
