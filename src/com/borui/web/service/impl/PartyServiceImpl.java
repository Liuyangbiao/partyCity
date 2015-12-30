package com.borui.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.borui.utils.DateUtil;
import com.borui.utils.StringUtil;
import com.borui.utils.common.Constance;
import com.borui.web.jpush.JPushUtils;
import com.borui.web.jpush.PushAPI;
import com.borui.web.mapper.AddressMapper;
import com.borui.web.mapper.MessageMapper;
import com.borui.web.mapper.PartyMapper;
import com.borui.web.mapper.PlaceMapper;
import com.borui.web.mapper.ScheduleMapper;
import com.borui.web.mapper.UserMapper;
import com.borui.web.model.BaseBean.STATE;
import com.borui.web.service.IPartyService;

@Service
public class PartyServiceImpl<T> implements IPartyService<T> {

	private Logger log = Logger.getLogger(PartyServiceImpl.class);

	@Resource
	private PartyMapper<T> partyMapper;

	@Resource
	private UserMapper<T> userMapper;
	
	@Resource
	private PlaceMapper<T> placeMapper;
	
	@Resource
	private ScheduleMapper<T> scheduleMapper;
	
	@Resource
	private AddressMapper addressMapper;
	
	@Resource
	private MessageMapper messageMapper;
	
	@Override
	public Map<String, Object> add(Map<String, Object> map) throws Exception {
		log.info(new Date() + "添加派对!");
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!map.isEmpty()) {
			if (StringUtil.checkNotNull(map)
					&& StringUtil.checkNotNull(map.get("name"))
					&& StringUtil.checkNotNull(map.get("type"))
					&& StringUtil.checkNotNull(map.get("start"))
					&& StringUtil.checkNotNull(map.get("end"))
					&& StringUtil.checkNotNull(map.get("address"))
					&& StringUtil.checkNotNull(map.get("owner"))) {
				log.info(new Date() + "已经添加了派对");
				Map<String, Object> param = new HashMap<String, Object>();
				
				
				/*
				 * 第一个任务
				 * 1、查询场地信息，并判断场地状态（上架、临时）
				 * 2、临时场地，发布完活动之后，直接删除
				 * 3、普通上架场地不用处理
				 * 
				 * 
				 * 第二个任务
				 * 在活动详情表中添加一个字段AddressName以及标志位，用于判断是否显示中文地址
				 * 
				 * 
				 * 需要检查searchByFK以及search方法
				 * 
				 * 查询到活动信息之后，判断标志位是否存在第二个任务添加的值，如果存在，直接返回，否则需要联合查询显示活动详情以及列表
				 * 
				 * 
				 * 第三个任务
				 * 场地操作成功之后，在日程表中添加记录
				 * 
				 * 
				 */
				param.put("pid", map.get("address"));
				param = placeMapper.searchByPK(param);
				if (null != param && !param.isEmpty()) {
					String stu = param.get("status").toString();
					Object name = param.get("address");
					Object title = param.get("name");
					Object lat = param.get("lat");
					Object lng = param.get("lng");
					Object id = param.get("pid");
					boolean temp = false;
					if (stu.equals("2")) {
						temp = true;
					}
					param.clear();
					// 必传参数
					param.put("name", map.get("name"));
					param.put("type", map.get("type"));
					param.put("start", map.get("start"));
					param.put("end", map.get("end"));
					param.put("address", map.get("address"));
					param.put("addressName", title);
					param.put("addressTitle", name);
					param.put("lng", lng);
					param.put("lat", lat);
					param.put("owner", map.get("owner"));
					
					if(temp){
						param.put("state", STATE.DISABLE.key);
					}

					param.put("status", STATE.ENABLE.key+"");
					param.put("createTimeLong", System.currentTimeMillis());

					// 可传参数
					param.put("city", null == map.get("city") ? null : map.get("city"));
					param.put("count", null == map.get("count") ? null : map.get("count"));
					param.put("cost", null == map.get("cost") ? null : map.get("cost"));
					param.put("inviteobject", null == map.get("inviteobject") ? null : map.get("inviteobject"));
					param.put("content", null == map.get("content") ? null : map.get("content"));
					param.put("ext3", null == map.get("ext3") ? null : map.get("ext3"));
					int flag = -1;
					try {
						flag = partyMapper.add(param);
						
					} catch (Exception e) {
						log.error(new Date() + "添加数据库失败" + e);
						msg.put("code", Constance.SQL_IS_ERROR);
						msg.put("status", "false");
					}
					if (flag > 0) {
						
						param.remove("start");
						param.remove("end");
						
						param = partyMapper.searchByPK(param);
						
						Object pid = null;
						
						if(null != param)
							pid = param.get("pid");
						log.info(new Date() + "添加派对成功");
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
						
						// push party creation msg.
						if(pid != null){
							PushAPI.getInstance().pushCreationMessage(param, userMapper, addressMapper, scheduleMapper, messageMapper);
						}
						
						//如果为临时场地，在添加活动成功之后则删除信息
						if(temp){
							flag = -1;
							try {
								param.clear();
								param.put("pid", map.get("address"));
								flag = placeMapper.del(param);
							} catch (Exception e) {
								log.error(new Date() + "执行数据库失败");
								msg.put("code", Constance.SQL_IS_ERROR);
								msg.put("status", "false");
							}
							if (flag > 0) {
								log.info(new Date() + "删除临时场地成功");
								
								
								param.clear();
								
								param.put("owner", map.get("owner"));
								param.put("title", map.get("name"));
								param.put("start", map.get("start"));
								param.put("end", map.get("end"));

								param.put("type", 2);
								
								param.put("partyId", pid);

								param.put("createTimeLong", System.currentTimeMillis());

								flag = -1;
								try {
									flag = scheduleMapper.add(param);
								} catch (Exception e) {
									log.error(new Date() + "添加数据库失败");
									msg.put("code", Constance.SQL_IS_ERROR);
									msg.put("status", "false");
									schedule(param, map, flag, name, msg);
								}
								
								if(flag > 0){

									msg.put("code", Constance.BASE_SUCCESS_CODE);
									msg.put("status", "true");
								} else {
									
									schedule(param, map, flag, name, msg);
									
								}
							} else {
								schedule(param, map, flag, name, msg);
								log.info(new Date() + "删除临时场地失败");
								msg.put("code", Constance.SQL_IS_ERROR);
								msg.put("status", "false");
							}
						} else {

							param = new HashMap<String, Object>();
							
							param.put("owner", map.get("owner"));
							param.put("title", map.get("name"));
							param.put("start", map.get("start"));
							param.put("end", map.get("end"));

							param.put("type", 2);
							
							param.put("partyId", pid);

							param.put("createTimeLong", System.currentTimeMillis());

							flag = -1;
							try {
								flag = scheduleMapper.add(param);
							} catch (Exception e) {
								log.error(new Date() + "添加数据库失败");
								msg.put("code", Constance.SQL_IS_ERROR);
								msg.put("status", "false");
								schedule(param, map, flag, name, msg);
							}
							
							if(flag > 0){

								msg.put("code", Constance.BASE_SUCCESS_CODE);
								msg.put("status", "true");
							} else {
								
								schedule(param, map, flag, name, msg);
								
							}
						}
					} else {
						log.info(new Date() + "添加派对失败");
						msg.put("code", Constance.SQL_IS_ERROR);
						msg.put("status", "false");
					}
						
				} else {
					msg.put("code", Constance.PRIMARY_KEY_IS_NULL);
					msg.put("status", "false");
				}
			}
		} else {
			msg.put("code", Constance.PARAMETERS_ERROR);
			msg.put("status", "false");
		}
		return msg;
	}


	private void schedule(Map<String, Object> param, Map<String, Object> map, int flag, Object name, Map<String, Object> msg) throws Exception{
		param.clear();
		param.put("name", map.get("name"));
		param.put("type", map.get("type"));
		param.put("start", map.get("start"));
		param.put("end", map.get("end"));
		param.put("address", map.get("address"));
		param.put("addressName", name);
		param.put("owner", map.get("owner"));
		
		param = scheduleMapper.searchByPK(param);
		
		flag = -1;
		if(null != param && !param.isEmpty()){
			flag = scheduleMapper.del(param);
		}
		
		if(flag > 0){

			msg.put("code", Constance.BASE_SUCCESS_CODE);
			msg.put("status", "true");
		} else {

			msg.put("code", Constance.CREATE_SCHEDULE_ERROR);
			msg.put("status", "false");
		}
	}

	@Override
	public Map<String, Object> del(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!map.isEmpty()) {
			/*
			 * 1、先判定是否存在用户信息 param.put("pid", map.get("userId")); param =
			 * userMapper.selectByPK(param); if(null != param &&
			 * !param.isEmpty()){ //存在用户信息 String uId = param.get("pid"); } else
			 * { msg.put } 2、查询活动信息，存在继续，否则错误码 param.clear(); param.put("pid",
			 * map.get("pid")); param = scheduleMapper.searchByPK(param);
			 * if(null != param && !param.isEmpty()){
			 * if(uId.equls(param.get("owner"))){ //判断所有者 删除操作 } else {
			 * 
			 * } }
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
					param = partyMapper.searchByPK(param);
					if (null != param && !param.isEmpty()) {
						if (uId.toString().equals(param.get("owner").toString())) {
							int flag = -1;
							try {
								// 删除派对
								flag = partyMapper.del(param);
							} catch (Exception e) {
								log.error(new Date() + "删除派对失败" + e);
								msg.put("code",
										Constance.BASE_SERVER_WRONG_CODE);
								msg.put("status", "false");
							}
							if (flag > 0) {
								msg.put("code", Constance.BASE_SUCCESS_CODE);
								msg.put("status", "true");
								
								// send out party-cancel msg
								Object id = param.get("pid");
								Object title = param.get("name");
								Map<String, Object> pushMap = JPushUtils.getPartyMap(id, title);
								param.clear();
								param.put("pid", id);
								param.put("partyOwner", uId);
								List<String> receiverList = JPushUtils.getJoinedUserIdList(param, partyMapper);
								if (receiverList != null && receiverList.size() > 0){
									pushMap.put("receivers", receiverList);
									PushAPI.getInstance().pushCancelMessage(pushMap);
									for(String str : receiverList){

										param = new HashMap<String, Object>();
										param.put("userId", str);
										param.put("info", title);
										param.put("type", 3);
										
										messageMapper.insert(param);
									}
								}
								
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
					param = partyMapper.searchByPK(param);
					if (null != param && !param.isEmpty()) {
						if (uId.toString().equals(param.get("owner").toString())) {
							
							Object id = param.get("pid");
							// 存在派对
							param.clear();
							param.put("name", null == map.get("name") ? null : map.get("name"));
							param.put("type", null == map.get("type") ? null : map.get("type"));
							param.put("start", null == map.get("start") ? null : map.get("start"));
							param.put("end", null == map.get("end") ? null : map.get("end"));
							param.put("address", null == map.get("address") ? null : map.get("address"));
							param.put("city", null == map.get("city") ? null : map.get("city"));
							param.put("count", null == map.get("count") ? null : map.get("count"));
							param.put("cost", null == map.get("cost") ? null : map.get("cost"));
							param.put("inviteobject", null == map.get("inviteobject") ? null : map.get("inviteobject"));
							param.put("content", null == map.get("content") ? null : map.get("content"));
							param.put("ext3", null == map.get("ext3") ? null : map.get("ext3"));
							param.put("status", null == map.get("status") ? null : map.get("status"));
							param.put("pid", id);

							int flag = -1;
							try {
								// 修改派对信息
								flag = partyMapper.modify(param);
							} catch (Exception e) {
								log.error(new Date() + "修改派对信息失败" + e);
								msg.put("code",
										Constance.BASE_SERVER_WRONG_CODE);
								msg.put("status", "false");
							}
							if (flag > 0) {
								
								
								flag = -1;
								
								Object title = null == param.get("name") ? null : param.get("name");
								param.clear();
								param.put("pid", id);
								param.put("partyOwner", uId);
								
								// push modify-party message
								Map<String, Object> pushMap = JPushUtils.getPartyMap(id, title);
								ArrayList<String> receiverList = new ArrayList<String>();
								
								List<Map<String, Object>> rel = partyMapper.searchByFKUser(param);
								
								//存在报名人员
								if(null != rel && rel.size() > 0){
									
									for(Map<String, Object> m: rel){

										//push消息
										param.clear();
										
										param.put("owner", m.get("userId"));
										param.put("partyId", id);

										param.put("push", STATE.DISABLE.key);
										
										flag = scheduleMapper.modify(param);
										flag += flag;
										
										receiverList.add(JPushUtils.userTagPrefix
												+ m.get("userId").toString());
										
									}
									if(flag > 0){
										msg.put("code", Constance.BASE_SUCCESS_CODE);
										msg.put("status", "true");
										
										// call api to send out msg
										if (receiverList != null && receiverList.size() > 0){
											pushMap.put("receivers", receiverList);
											PushAPI.getInstance().pushModifyMessage(pushMap);
											for(String str: receiverList){

												param = new HashMap<String, Object>();
												param.put("userId", str);
												param.put("info", title);
												param.put("type", 2);
												
												messageMapper.insert(param);
												
											}
										}
									} else {
										msg.put("code", Constance.PUSH_MSG_ERROR);
										msg.put("status", "false");
									}
								} else {
									msg.put("code", Constance.BASE_SUCCESS_CODE);
									msg.put("status", "true");
								}
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
			if(StringUtil.checkNotNull(map) ){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					
					/*
					 * 共四种情况
					 * 1、首页按城市查询派对列表
					 * 2、首页根据关键字查询派对列表
					 * 3、我的派对中我发起的派对
					 * 4、我的派对中我参与的派对
					 * 
					 */
					
					
					
					if(null != map.get("pageSize") && null != map.get("pageNo")){
						
						//分页计算
						param.put("pageNo", (Integer.parseInt(map.get("pageNo").toString()) -1 ) * Integer.parseInt(map.get("pageSize").toString()));
						param.put("pageSize", map.get("pageSize"));
						
					}
					
					//普通查询，派对表
					if(null == map.get("join")){

						//模糊匹配关键字
						param.put("name", null ==  map.get("name") ? null : map.get("name"));
						
						
						//首页查询城市派对
						param.put("city", null == map.get("city") ? null : map.get("city"));
						
						//我发起的派对
						param.put("owner", null == map.get("owner") ? null : map.get("owner"));
						
						//派对类型
						param.put("type", null == map.get("type") ? null : map.get("type"));
						
						//时间
						if(null != map.get("time")){
							SimpleDateFormat sdf = null;
							long start = 0, end = 0;
							//今天
							sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							if("1".equals(map.get("time").toString())){
								start = sdf.parse(DateUtil.getCurrDate()+" 00:00:01").getTime();
								end = sdf.parse(DateUtil.getCurrDate()+" 23:59:59").getTime();
								param.put("start", start);
								param.put("end", end);
							}
							if("7".equals(map.get("time").toString())){
								start = System.currentTimeMillis();
								String[] now = DateUtil.getCurrDate().split("-");
								if(Integer.parseInt(now[2]) > 7)
									end = start + 7*24*60*60*1000;
								else
									end = start + (Integer.parseInt(now[2])-1)*24*60*60*1000;
								param.put("start", start);
								param.put("end", end);
							}
							if("30".equals(map.get("time").toString())){
								
								start = System.currentTimeMillis();
								String now = DateUtil.getCurrDate();
								now = now.substring(0, now.lastIndexOf("-"));
								start = sdf.parse(now+"-01 00:00:01").getTime();
								
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
								
								Calendar ca = Calendar.getInstance();    
						        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
						        end = sdf.parse(format.format(ca.getTime()) + " 23:59:59").getTime();
								param.put("start", start);
								param.put("end", end);
							}
						}
						
						
						List<Map<String, Object>> rel = partyMapper.search(param);
						
						if(null != rel){
							
							if(rel.size() > 0){
								
								List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();

								
								for(Map<String, Object> m : rel){
									
									
									//我发起的
									if(null != map.get("owner")){
										
										
										party(param, m, ls);
										
									} else {
										//过期不显示
										if(Long.parseLong(m.get("start").toString()) >= System.currentTimeMillis()){
											
											party(param, m, ls);
										}
									}
									
								}
								
								
								param.clear();
								
								List<Map<String, Object>> l1 = new ArrayList<Map<String, Object>>();//需排序
								List<Map<String, Object>> l2 = new ArrayList<Map<String, Object>>();//无需排序
								if(null == map.get("owner")) {
									Collections.reverse(ls);
								} else {
									//排序
									
									
									for(Map<String, Object> tmp: ls){
										if(Long.parseLong(tmp.get("start").toString()) > System.currentTimeMillis()){
											l1.add(tmp);
										} else {
											l2.add(tmp);
										}
									}
									
									
									Collections.sort(l1,new Comparator<Map<String,Object>>() {  
										  
								           public int compare(Map<String, Object> o1,Map<String, Object> o2) {  
								  
								            //o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值  
								               Long s1 = (Long) o1.get("start");  
								               Long s2 = (Long) o2.get("start");  
								  
								            if(s1 > s2) {  
								             return 1;  
								            }else {  
								             return -1;  
								            }  
								           }  
								          });
									
									
									for(Map<String, Object> tmp : l2){
										l1.add(tmp);
									}
									
								}
								
								List<Map<String, Object>> partys = new ArrayList<Map<String, Object>>();
								
								if(null == map.get("owner")){
									partys = ls;
								} else {
									partys = l1;
								}
								
								param.put("result", partys);
								param.put("pageNo", null == map.get("pageNo") ? null : map.get("pageNo"));
								
								msg.put("code", Constance.BASE_SUCCESS_CODE);
								msg.put("status", "true");
								msg.put("result", param);
							} else {
								
								param.clear();
								
								param.put("result", rel);
								param.put("pageNo", null == map.get("pageNo") ? null : map.get("pageNo"));
								msg.put("code", Constance.BASE_SUCCESS_CODE);
								msg.put("status", "false");
								msg.put("result", param);
							}
							
							
						} else {
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
						}
						
					} else {
						//查询参加日志表

						//我参加的派对
						param.put("join", null == map.get("join") ? null : map.get("join"));
						
						List<Map<String, Object>> rel = partyMapper.searchByFKUser(param);
						
						
						if(null != rel){
							
							if(rel.size() > 0){
								
								List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
								for(Map<String, Object> m : rel){
									
									
									//创建查询所用Map对象
									Map<String, Object> args = new HashMap<String, Object>();
									
									//赋值
									args.put("pid", m.get("partyId"));
									
									args = partyMapper.searchByPK(args);
									
									
									//判断是否存在查询结果
									if(null != args){
										ls.add(args);
									}
									
									
								}
								List<Map<String, Object>> l1 = new ArrayList<Map<String, Object>>();//需排序
								List<Map<String, Object>> l2 = new ArrayList<Map<String, Object>>();//无需排序
								for(Map<String, Object> tmp: ls){
									if(Long.parseLong(tmp.get("start").toString()) > System.currentTimeMillis()){
										l1.add(tmp);
									} else {
										l2.add(tmp);
									}
								}
								
								
								Collections.sort(l1,new Comparator<Map<String,Object>>() {  
									  
							           public int compare(Map<String, Object> o1,Map<String, Object> o2) {  
							  
							            //o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值  
							               Long s1 = (Long) o1.get("start");  
							               Long s2 = (Long) o2.get("start");  
							  
							            if(s1 > s2) {  
							             return 1;  
							            }else {  
							             return -1;  
							            }  
							           }  
							          });
								
								
								for(Map<String, Object> tmp : l2){
									l1.add(tmp);
								}
								
								param.clear();
								
								param.put("pageNo", null == map.get("pageNo") ? null : map.get("pageNo"));
								param.put("result", l1);
								
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
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
						}
					}
					
					
				} catch (Exception e) {
					log.error(new Date() + "获取派对列表失败" + e);
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
	
	private void party(Map<String, Object> param, Map<String, Object> m, List<Map<String, Object>> ls) throws Exception{

		param.clear();
		param.put("pid", m.get("owner"));
		
		param = userMapper.searchByPK(param);
		
		//封装用户信息到列表中
		if(null != param){
			m.put("userAvatar", param.get("avatar"));
		} else {
			m.put("userAvatar", null);
			param = new HashMap<String, Object>();
		}
		
		//如果为临时场地，则显示表中存储数据；否则需要对应场地信息
		if(null == m.get("state") || "".equals(m.get("state")) || Integer.parseInt(m.get("state").toString()) != STATE.DISABLE.key){

			param.clear();
			
			param.put("pid", m.get("address"));
			
			param = placeMapper.searchByPK(param);
			
			//封装地址信息
			if(null != param){
				m.put("addressContent", param.get("address"));
				m.put("addressName", param.get("name"));
			} else {
				m.put("addressContent", null);
				m.put("addressName", null);
				param = new HashMap<String, Object>();
			}
		}
		
		ls.add(m);
	}

	@Override
	public Map<String, Object> selectByPK(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("partyId")) && StringUtil.checkNotNull(map.get("pid"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("pid", map.get("partyId"));
					param = partyMapper.searchByPK(param);
					if(null != param && !param.isEmpty()){
						
						Object pid = param.get("pid");
						
						Map<String, Object> m = new HashMap<String, Object>();
						//是否为临时场地
						if(null == param.get("state") || "".equals(param.get("state")) || Integer.parseInt(param.get("state").toString()) != STATE.DISABLE.key){
							m.put("pid", param.get("address"));
							m = placeMapper.searchByPK(m);
							
							if(null != m && !m.isEmpty()){
								param.put("addressContent", m.get("address"));
								param.put("addressName", m.get("name"));
								param.put("lng", m.get("lng"));
								param.put("lat", m.get("lat"));
							} else {
								param.put("addressContent", null);
								param.put("addressName", null);
							}
						}

						
						
						Map<String, Object> temp = new HashMap<String, Object>();
						
						
						temp.put("pid", param.get("owner"));
						//根据用户主键查询用户信息，并赋值到派对信息上
						m = userMapper.searchByPK(temp);
						
						//封装用户信息到派对详情中
						if(null != m && !m.isEmpty()){
							param.put("owner", m.get("name"));
							param.put("ownerId", m.get("pid"));
							param.put("fkAvatar", m.get("avatar"));
							param.put("phone", m.get("phone"));
						}
						
						
						
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("partyId", pid);
						p.put("owner", map.get("pid"));
						
						p = scheduleMapper.searchByPK(p);
						
						if(null != p){
							param.put("sId", p.get("pid").toString());
						} else {
							param.put("sId", null);
							p = new HashMap<String, Object>();
						}
						p.clear();
						p.put("pid", pid);
						
						
						List<Map<String, Object>> rel = partyMapper.searchByFKUser(p);
						
						
						//封装已报名信息到派对详情中
						if(null != rel && rel.size() > 0){
							
							List<Map<String, Object>> rl = new ArrayList<Map<String, Object>>();
							
							for(Map<String, Object> rm : rel){
								Map<String, Object> tmp = new HashMap<String, Object>();
								
								tmp.put("pid", rm.get("userId"));
								tmp = userMapper.searchByPK(tmp);
								
								rl.add(tmp);
							}
							
							param.put("num", rl.size());
							param.put("joined", rl);
						}else{
							param.put("num", 0);
							param.put("joined", rel);
						}
						
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
						msg.put("result", param);
					} else {
						msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
						msg.put("status", "false");
					}
				} catch (Exception e) {
					log.error(new Date() + "" + e);
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
	public Map<String, Object> joinParty(Map<String, Object> map)
			throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("partyId")) && StringUtil.checkNotNull(map.get("userId"))){
				try {
					
					/*
					 * 1、根据partyId查询相应的派对信息，存在继续，否则错误码
					 * 2、校验派对有效期
					 * 3、校验用户信息
					 * 4、校验总人数信息
					 * 5、校验行业标签或者通讯录标签
					 * 6、行业标签人数
					 * 7、添加加入派对信息
					 * 8、添加日程计划
					 * 
					 */
					
					Map<String, Object> param = new HashMap<String, Object>();
					
					param.put("pid", map.get("partyId"));
					
					param = partyMapper.searchByPK(param);
					
					
					if(null != param && !param.isEmpty()){
						
						Object pid = param.get("pid");
						
						Object partyOwner = param.get("owner");
						
						
						Object start = param.get("start");
						Object end = param.get("end");
						Object title = param.get("name");
						
						//行业
						Object inviteobject = param.get("inviteobject");
						
						//总人数
						Object count = param.get("count");
						
						
						//有效期内
						if(Long.parseLong(param.get("start").toString()) >= System.currentTimeMillis()){
							
							
							param.clear();
							param.put("pid", pid);
							List<Map<String, Object>> num = partyMapper.countNumByPartyId(param);
							
							int sum = 0;
							for(Map<String, Object> m : num){
								if(!m.get("num").toString().equals("SB"))
									sum += Integer.parseInt(m.get("num").toString());
							}
							
							//未报满
							if(sum < Integer.parseInt(count.toString())){
								
								
								param.clear();
								param.put("pid", map.get("userId"));
								
								param = userMapper.searchByPK(param);
								
								//校验用户信息
								if(null != param && !param.isEmpty()){
									
									Object userInviteobject = param.get("industry");
									Object userPid = param.get("pid");
									
									//行业标签选择
									if(null != inviteobject && !"".equals(inviteobject.toString()) && !"SB".equals(inviteobject)){
										
										//行业标准0-20,1-50,2-30
										
										String[] invites = inviteobject.toString().split(",");
										
										boolean inviFlag = false;
										
										for(String str : invites){
											String[] invite = str.split("-");
											if(userInviteobject.toString().indexOf(invite[0]) != -1){
												inviFlag = true;
											}
										}
										
										//标签符合要求
										if(inviFlag){
											
											addLog(param, map, msg, pid, partyOwner, start, end, title);
											
										//标签不符合要求
										} else {
											msg.put("code", Constance.IS_NOT_MEET);
											msg.put("status", "false");
										}
									//通讯录标签选择
									} else if("SB".equals(inviteobject.toString())) {
										
										param.clear();
										param.put("owner", partyOwner); 
										
										List<Map<String, Object>> links = userMapper.links(param);
										
										boolean flag = false;
										
										for(Map<String, Object> m : links){
											
											if(m.get("who").toString().equals(userPid.toString())){
												flag = true;
											}
											
										}
										
										//在发起人通讯录中
										if(flag){
											
											addLog(param, map, msg, pid, partyOwner, start, end, title);
											
										//不再发起人通讯录中
										} else {
											msg.put("code", Constance.IS_NOT_MEET);
											msg.put("status", "false");
										}
										
									//无条件限制选择
									} else {

										addLog(param, map, msg, pid, partyOwner, start, end, title);
									}
								} else {
									msg.put("code", Constance.USER_IS_NOT_EXIST);
									msg.put("status", "false");
								}
							} else {
								//已报满
								msg.put("code", Constance.JOINED_DATE_IS_FULL);
								msg.put("status", "false");
							}
						} else {
							msg.put("code", Constance.HAS_BEEN_CUT_OFF);
							msg.put("status", "false");
						}
						
					} else {
						msg.put("code", Constance.PARTY_IS_NOT_EXIST);
						msg.put("status", "false");
					}
				} catch (Exception e) {
					log.error(new Date() + "报名派对失败" + e);
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
	
	private void addLog(Map<String, Object> param, Map<String, Object> map, Map<String, Object> msg, Object pid, Object partyOwner, Object start, Object end, Object title) throws Exception{
		param.clear();
		
		param.put("join", map.get("userId"));
		param.put("partyId", pid);
		param.put("partyOwner", partyOwner);
		param.put("createTimeLong", System.currentTimeMillis());
		int flag = partyMapper.addPartyLog(param);
		
		if(flag > 0){
			
			//添加到日程计划中
			
			param.clear();
			
			
			param.put("owner", map.get("userId"));
			param.put("start", start);
			param.put("end", end);
			param.put("title", title);
			param.put("createTimeLong", System.currentTimeMillis());
			param.put("type", STATE.DISABLE.key);
			param.put("partyId", pid);
			
			
			flag = scheduleMapper.add(param);
			
			
			if(flag > 0){
				//报名成功
				msg.put("code", Constance.BASE_SUCCESS_CODE);
				msg.put("status", "true");
			} else {
				//报名失败
				param.clear();
				param.put("userId", map.get("userId"));
				param.put("partyId", pid);
				
				partyMapper.delPartyLog(param);
				
				
				msg.put("code", Constance.JOINED_IS_ERROR);
				msg.put("status", "false");
				
			}
		} else {
			msg.put("code", Constance.SQL_IS_ERROR);
			msg.put("status", "false");
		}
	}

	@Override
	public Map<String, Object> delPartyLog(Map<String, Object> map)
			throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("partyId")) && StringUtil.checkNotNull(map.get("userId"))){
				try {
					
					/*
					 * 1、验证派对信息
					 * 2、验证用户信息
					 * 3、取消报名
					 * 
					 * 
					 */
					Map<String, Object> param = new HashMap<String, Object>();
					
					param.put("pid", map.get("partyId"));
					param = partyMapper.searchByPK(param);
					if(null != param && !param.isEmpty()){
						
						param.clear();
						param.put("pid", map.get("userId"));
						
						param = userMapper.searchByPK(param);
						
						if(null != param && !param.isEmpty()){
							
							//删除日程信息
							
							param.clear();
							
							param.put("owner", map.get("userId"));
							param.put("partyId", map.get("partyId"));
							
							if(scheduleMapper.del(param) > 0){

								param.clear();
								
								param.put("partyId", map.get("partyId"));
								param.put("userId", map.get("userId"));
								int flag = -1;
								flag = partyMapper.delPartyLog(param);
								if(flag > 0){
									msg.put("code", Constance.BASE_SUCCESS_CODE);
									msg.put("status", "true");
								} else {
									msg.put("code", Constance.SQL_IS_ERROR);
									msg.put("status", "false");
								}
							} else {
								msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
								msg.put("status", "false");
							}
							
						} else {
							msg.put("code", Constance.USER_IS_NOT_EXIST);
							msg.put("status", "false");
						}
						
						
					} else {
						msg.put("code", Constance.PARTY_IS_NOT_EXIST);
						msg.put("status", "false");
					}
					
					
					
				} catch (Exception e) {
					log.error(new Date() + "撤销报名派对信息失败" + e);
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
