package com.borui.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.borui.utils.StringUtil;
import com.borui.utils.common.Constance;
import com.borui.web.mapper.PartyMapper;
import com.borui.web.mapper.ScheduleMapper;
import com.borui.web.mapper.UserMapper;
import com.borui.web.model.BaseBean.STATE;
import com.borui.web.service.IScheduleService;

@Service
public class ScheduleServiceImpl<T> implements IScheduleService<T> {

	/**
	 * 声明日志记录
	 */
	private Logger log = Logger.getLogger(ScheduleServiceImpl.class);

	@Resource
	private ScheduleMapper<T> scheduleMapper;

	@Resource
	private UserMapper<T> userMapper;
	
	@Resource
	private PartyMapper<T> partyMapper;

	@Override
	public Map<String, Object> add(Map<String, Object> map) throws Exception {
		log.info(new Date() + "添加事项");
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!map.isEmpty()) {
			if (StringUtil.checkNotNull(map)
					&& StringUtil.checkNotNull(map.get("owner"))
					&& StringUtil.checkNotNull(map.get("title"))
					&& StringUtil.checkNotNull(map.get("start"))
					&& StringUtil.checkNotNull(map.get("end"))) {
				log.info(new Date() + "已经添加了事项");
				Map<String, Object> param = new HashMap<String, Object>();

				// 必传参数
				param.put("owner", map.get("owner"));
				param.put("title", map.get("title"));
				param.put("start", map.get("start"));
				param.put("end", map.get("end"));

				param.put("type", STATE.ENABLE.key);

				param.put("createTimeLong", System.currentTimeMillis());

				int flag = -1;
				try {
					flag = scheduleMapper.add(param);
				} catch (Exception e) {
					log.error(new Date() + "添加数据库失败");
					msg.put("code", Constance.SQL_IS_ERROR);
					msg.put("status", "false");
				}
				if (flag > 0) {
					log.info(new Date() + "添加事项成功");
					msg.put("code", Constance.BASE_SUCCESS_CODE);
					msg.put("status", "true");
				} else {
					log.info(new Date() + "添加事项失败");
					msg.put("code", Constance.SQL_IS_ERROR);
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
		if (!map.isEmpty()) {
			/*
			 * 1、先判定是否存在用户信息 
			 * param.put("pid", map.get("userId")); 
			 * param = userMapper.selectByPK(param); 
			 * if(null != param && !param.isEmpty()){ //存在用户信息 
			 * String uId = param.get("pid"); 
			 * } else { 
			 * msg.put 
			 * } 
			 * 2、查询事项或活动信息，存在继续，否则错误码 
			 * param.clear();
			 * param.put("pid", map.get("pid")); 
			 * param = scheduleMapper.searchByPK(param); 
			 * if(null != param && !param.isEmpty()){
			 *  if(uId.equls(param.get("owner"))){ //判断所有者
			 * 删除操作
			 *  } else {
			 * 
			 * } 
			 * }
			 */
			if (StringUtil.checkNotNull(map)
					&& StringUtil.checkNotNull(map.get("pid"))
					&& StringUtil.checkNotNull(map.get("userId"))) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("pid", map.get("userId"));
				param = userMapper.searchByPK(param);
				if (null != param && !param.isEmpty()) { // 存在用户信息
					Object uId = map.get("userId");
					param.clear();
					param.put("pid", map.get("pid"));
					param = scheduleMapper.searchByPK(param);
					if (null != param && !param.isEmpty()) {
						if (uId.toString().equals(param.get("owner").toString())) {
							int flag = -1;
							try {
								// 删除事项
								flag = scheduleMapper.del(param);
							} catch (Exception e) {
								log.error(new Date() + "删除事项失败" + e);
								msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
								msg.put("status", "false");
							}
							if (flag > 0) {
								msg.put("code", Constance.BASE_SUCCESS_CODE);
								msg.put("status", "true");
							} else {
								msg.put("code", Constance.SQL_IS_ERROR);
								msg.put("status", "false");
							}
						} else {
							msg.put("code", Constance.NO_PERMISSION);
							msg.put("status", "false");
						}
					} else {
						msg.put("code", Constance.USER_IS_NOT_EXIST);
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
	public Map<String, Object> modify(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!map.isEmpty()) {
			if (StringUtil.checkNotNull(map)
					&& StringUtil.checkNotNull(map.get("pid"))
					&& StringUtil.checkNotNull(map.get("userId"))) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("pid", map.get("userId"));
				param = userMapper.searchByPK(param);
				if (null != param && !param.isEmpty()) { // 存在用户信息
					Object uId = map.get("userId");
					param.clear();
					param.put("pid", map.get("pid"));
					param = scheduleMapper.searchByPK(param);
					if (null != param && !param.isEmpty()) {
						
						Object id = param.get("pid");
						if (uId.toString().equals(param.get("owner").toString())) {
							// 存在事项
							param.clear();
							param.put("pid", id);
							param.put("title", null == map.get("title") ? null
									: map.get("title"));
							param.put("start", null == map.get("start") ? null
									: map.get("start"));
							param.put("end", null == map.get("end") ? null
									: map.get("end"));

							int flag = -1;
							try {
								// 修改事项
								flag = scheduleMapper.modify(param);
							} catch (Exception e) {
								log.error(new Date() + "修改事项失败" + e);
								msg.put("code",
										Constance.BASE_SERVER_WRONG_CODE);
								msg.put("status", "false");
							}
							if (flag > 0) {
								msg.put("code", Constance.BASE_SUCCESS_CODE);
								msg.put("status", "true");
							} else {
								msg.put("code", Constance.SQL_IS_ERROR);
								msg.put("status", "false");
							}
						} else {
							msg.put("code", Constance.NO_PERMISSION);
							msg.put("status", "false");
						}
					} else {
						msg.put("code", Constance.USER_IS_NOT_EXIST);
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
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("userId"))){
				try {
					
					/*
					 * 两种情况
					 * 
					 * 1、直接根据用户pid获取当天的日程列表
					 * 
					 * 2、根据用户pid以及选定月份获取日历列表
					 * 
					 * 
					 */
					Map<String, Object> param = new HashMap<String, Object>();
					
					param.put("owner", map.get("userId"));
					
					if(null != map.get("time")){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date time = new Date(Long.parseLong(map.get("time").toString()));
						String now = sdf.format(time);
						now = now.split(" ")[0];
						long start = sdf.parse(now +" 00:00:01").getTime();
						long end = sdf.parse(now +" 23:59:59").getTime();
						param.put("start", start);
						param.put("end", end);
					}
					
					if(null != map.get("pageSize") && null != map.get("pageNo")){
						
						//分页计算
						param.put("pageNo", (Integer.parseInt(map.get("pageNo").toString()) -1 ) * Integer.parseInt(map.get("pageSize").toString()));
						param.put("pageSize", map.get("pageSize"));
						
					}
					
					List<Map<String, Object>> rel = scheduleMapper.search(param);
					
					if(null != rel && rel.size() > 0){

						
						List<Map<String, Object>> rl = new ArrayList<Map<String, Object>>();
						
						for(Map<String, Object> m : rel){
							param = new HashMap<String, Object>();
							param.put("pid", m.get("pid"));
							param.put("push", STATE.ENABLE.key);
							
							scheduleMapper.modify(param);
							
							
							//时间重新封装
							
							if(null != m.get("type") && !m.get("type").toString().equals("0") && !m.get("type").toString().equals("")){
								
								param.clear();
								param.put("pid", m.get("partyId"));
								
								param = partyMapper.searchByPK(param);
								
								if(null != param){
									m.put("pStart", param.get("start"));
									m.put("pEnd", param.get("end"));
								} else {
									m.put("pStart", 0);
									m.put("pEnd", 0);
								}
								
							} else {
								m.put("pStart", 0);
								m.put("pEnd", 0);
							}
							
							rl.add(m);
							
						}
						
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
						msg.put("result", rl);
						
						
						
					} else {
						
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "false");
						msg.put("result", rel);
					}
				
					
					
				} catch (Exception e) {
					log.error(new Date() + "获取日程列表失败" + e);
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
	public Map<String, Object> selectByPK(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("pid", map.get("pid"));
					
					param = scheduleMapper.searchByPK(param);
					
					if(null != param && !param.isEmpty()){
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
						msg.put("result", param);
					} else {
						msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
						msg.put("status", "false");
					}
				} catch (Exception e) {
					log.error(new Date() + "获取日程详情失败" + e);
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
	public Map<String, Object> searchForCalender(Map<String, Object> map) {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("userId")) && StringUtil.checkNotNull(map.get("time"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					
					param.put("owner", map.get("userId"));
					
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date time = new Date(Long.parseLong(map.get("time").toString()));
					String[] now = sdf.format(time).split("-");
					
					long start = 0l, end = 0l;
					
					
					//计算三个月时间
					Calendar cal = Calendar.getInstance();
					cal.clear();
					
					cal.set(Calendar.YEAR, Integer.parseInt(now[0]));
					cal.set(Calendar.MONTH, Integer.parseInt(now[1]) - 1);
					
					start = Long.parseLong(map.get("time").toString()) - cal.getActualMaximum(Calendar.DAY_OF_MONTH)*24*60*60*1000;
					
					cal.clear();
					
					cal.set(Calendar.YEAR, Integer.parseInt(now[0]));
					cal.set(Calendar.MONTH, Integer.parseInt(now[1]) + 1);
					
					end = Long.parseLong(map.get("time").toString()) + cal.getActualMaximum(Calendar.DAY_OF_MONTH)*24*60*60*1000;
					
					param.put("start", end);
					param.put("end", start);
					
					List<Map<String, Object>> rel = scheduleMapper.search(param);
					
					if(null != rel && !rel.isEmpty()){
						
						List<Object> rl = new ArrayList<Object>();
						
						for(Map<String, Object> m : rel){
							
							Date t = new Date(Long.parseLong(m.get("start").toString()));
							String timeStr = sdf.format(t);
							timeStr = timeStr.split(" ")[0];
							long s = sdf.parse(timeStr +" 00:00:00").getTime();
							long e = sdf.parse(timeStr +" 23:59:59").getTime();
							
							if(s < Long.parseLong(m.get("start").toString()) && Long.parseLong(m.get("start").toString()) < e){
								
								if(!rl.contains(s+"")){
									rl.add(s+"");
								}
								
							}
							
						}
						
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
						msg.put("result", rl);
						
					} else {
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "false");
						msg.put("result", rel);
					}
					
					
					
				} catch (Exception e) {
					log.error(new Date() + "获取日历信息失败" + e);
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
