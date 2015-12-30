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
import com.borui.web.mapper.VipCodeMapper;
import com.borui.web.model.BaseBean.STATE;
import com.borui.web.service.IVipCodeService;

@Service
public class VipCodeServiceImpl<T> implements IVipCodeService<T> {

	/**
	 * 声明日志记录
	 */
	private Logger log = Logger.getLogger(VipCodeServiceImpl.class);
	
	@Resource
	private VipCodeMapper vipCodeMapper;

	@Override
	public Map<String, Object> add(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("owner", map.get("owner"));
					param.put("status", STATE.ENABLE.key);
					param.put("createTimeLong", System.currentTimeMillis());
					param.put("code", map.get("code"));
					
					
					int flag = vipCodeMapper.add(param);
					
					if(flag > 0 ){
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
					} else {
						msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
						msg.put("status", "false");
					}
					
				} catch (Exception e) {
					log.error(new Date() + "生成邀请码错误" + e);
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
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					
					param.put("pid", map.get("pid"));
					param.put("status", null == map.get("status") ? null : map.get("status"));
					int flag = vipCodeMapper.modify(param);
					
					if(flag > 0 ){
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
					} else {
						msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
						msg.put("status", "false");
					}
					
				} catch (Exception e) {
					log.error(new Date() + "修改邀请码错误" + e);
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
	public Map<String, Object> search(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map)){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					
					param.put("owner", null == map.get("owner") ? null : map.get("owner"));
					
					List<Map<String, Object>> rel = new ArrayList<Map<String, Object>>();
					
					rel = vipCodeMapper.search(param);
					
					if(null != rel && rel.size() > 0){
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
						msg.put("result", rel);
					} else {
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "false");
						msg.put("result", rel);
					}
					
					
				} catch (Exception e) {
					log.error(new Date() + "查询邀请码错误" + e);
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
