package com.borui.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.borui.utils.StringUtil;
import com.borui.utils.common.Constance;
import com.borui.web.mapper.MessageMapper;
import com.borui.web.model.BaseBean.STATE;
import com.borui.web.service.IMessageService;

@Service
public class MessageServiceImpl<T> implements IMessageService<T> {
	
	@Resource
	private MessageMapper messageMapper;

	@Override
	public Map<String, Object> add(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(null != map.get("userId") && null != map.get("info") && StringUtil.checkNotNull(map.get("userId").toString()) && StringUtil.checkNotNull(map.get("info").toString())){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("userId", map.get("userId"));
				param.put("info", map.get("info"));
				param.put("time", System.currentTimeMillis());
				param.put("status", STATE.ENABLE.key);
				param.put("type", null == map.get("type") ? "system" : map.get("type"));
				messageMapper.insert(param);
				
				
				msg.put("code", Constance.BASE_SUCCESS_CODE);
				msg.put("status", "true");
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
	public Map<String,Object> search(Map<String, Object> map) throws Exception{
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(null != map.get("userId") && StringUtil.checkNotNull(map.get("userId").toString())){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("userId", map.get("userId"));
				if(null != map.get("pageNo") && null != map.get("pageSize") && StringUtil.checkNotNull(map.get("pageNo").toString()) && StringUtil.checkNotNull(map.get("pageSize").toString())){
					param.put("pageNo", (Integer.parseInt(map.get("pageNo").toString())-1) * Integer.parseInt(map.get("pageSize").toString()));
					param.put("pageSize", map.get("pageSize"));
				}
				List<Map<String, Object>> rel = new ArrayList<Map<String, Object>>();
				rel = messageMapper.search(param);
				if(null != rel && rel.size() > 0){//存在消息
					msg.put("code", Constance.BASE_SUCCESS_CODE);
					msg.put("status", "true");
					msg.put("result", rel);
				} else {
					msg.put("code", Constance.BASE_SUCCESS_CODE);
					msg.put("status", "false");
					msg.put("result", rel);
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
			if(null != map.get("ids") && StringUtil.checkNotNull(map.get("ids").toString()) && null != map.get("userId") && StringUtil.checkNotNull(map.get("userId").toString())){//存在修改信息
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("chk", map.get("ids"));
				param.put("userId", map.get("userId"));
				param.put("status", 1);
				messageMapper.modify(param);
				msg.put("code", Constance.BASE_SUCCESS_CODE);
				msg.put("status", "true");
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
	public Map<String, Object> searchById(Map<String, Object> map) {
		return null;
	}

	@Override
	public Map<String, Object> delete(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(null != map.get("userId") && StringUtil.checkNotNull(map.get("userId").toString()) && null != map.get("ids") && StringUtil.checkNotNull(map.get("ids").toString())){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("chk", map.get("ids"));
				param.put("userId", map.get("userId"));
				messageMapper.delete(param);
				msg.put("code", Constance.BASE_SUCCESS_CODE);
				msg.put("status", "true");
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
