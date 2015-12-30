package com.borui.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.borui.utils.DateUtil;
import com.borui.utils.StringUtil;
import com.borui.utils.common.Constance;
import com.borui.utils.common.Constant;
import com.borui.web.jpush.JPushUtils;
import com.borui.web.jpush.PushAPI;
import com.borui.web.mapper.PartyMapper;
import com.borui.web.mapper.UserMapper;
import com.borui.web.mapper.VipCodeMapper;
import com.borui.web.model.BaseBean.STATE;
import com.borui.web.service.IUserService;

@Service
public class UserServiceImpl<T> implements IUserService<T> {

	/**
	 * 声明日志记录
	 */
	private Logger log = Logger.getLogger(UserServiceImpl.class);

	@Resource
	private UserMapper<T> userMapper;
	
	@Resource
	private PartyMapper<T> partyMapper;
	
	@Resource
	private VipCodeMapper vipCodeMapper;

	@Override
	public Map<String, Object> add(Map<String, Object> map) throws Exception {
		log.info(new Date() + "用户自助注册帐号!");
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!map.isEmpty()) {
			if (StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("phone")) && StringUtil.checkNotNull(map.get("pwd"))
					//初始设置不包含邀请码
					&& StringUtil.checkNotNull(map.get("code"))
					) {
				log.info(new Date() + "用户已经设置过手机号以及密码");
				// 重新声明一个Map对象，用于存储需要添加的字段
				Map<String, Object> param = new HashMap<String, Object>();
				
				//必传参数
				param.put("phone", map.get("phone"));
				
				param = userMapper.searchByPK(param);
				
				if(null == param || param.isEmpty()){
					
					log.info("user is exist");
					
					if(null == param)
						param = new HashMap<String, Object>();
					param.put("phone", map.get("phone"));

					param.put("pwd", map.get("pwd"));
					
					param.put("status", STATE.ENABLE.key);
					param.put("createTimeLong", System.currentTimeMillis());
					
					Map<String, Object> tmp = new HashMap<String, Object>();
					
					tmp.put("code", map.get("code").toString().toLowerCase());
					
					tmp = vipCodeMapper.search(tmp) == null ? null : vipCodeMapper.search(tmp).get(0);
					
					Object codeId = "";
					Object owner = "";
					
					if(null != tmp){
						
						log.info("vipCode is exist");
						param.put("code", map.get("code").toString().toLowerCase());
						
						codeId = tmp.get("pid");
						owner = tmp.get("owner");
						
						tmp.clear();
						tmp.put("pid", owner);
						
						tmp = userMapper.searchByPK(tmp);
						
						if(null != tmp){
							param.put("parent", tmp.get("name"));
							param.put("parentId", tmp.get("pid"));
						}
						
						//可传参数
						param.put("name", null == map.get("name") ? null : map.get("name"));
						param.put("age", null == map.get("birthday") ? null : DateUtil.calAge(map));
						param.put("sex", null == map.get("sex") ? null : map.get("sex"));
						param.put("avatar", null == map.get("avatar") ? null : map.get("avatar"));
						param.put("birthday",null == map.get("birthday") ? null : map.get("birthday"));
						param.put("industry",null == map.get("industry") ? null : map.get("industry"));
						param.put("company",null == map.get("company") ? null : map.get("company"));
						param.put("position",null == map.get("position") ? null : map.get("position"));
						param.put("city",null == map.get("city") ? null : map.get("city"));
						
						
						
						//用户以完善信息
						if(null != param.get("age"))
							param.put("ext1", STATE.DISABLE.key);
						
						log.info("param is "+ param);
						
						int flag = -1;
						try {
							flag = userMapper.add(param);
						} catch (Exception e) {
							log.error(new Date() + "添加数据库失败");
							msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
							msg.put("status", "false");
						}
						if (flag > 0) {
							log.info(new Date() + "用户自动创建账户成功");
							
							param = userMapper.searchByPK(param);
							
							
							msg.put("code", Constance.BASE_SUCCESS_CODE);
							msg.put("status", "true");
							
							msg.put("result", param);
							

							
							param.clear();
							
							param.put("status", STATE.DISABLE.key);
							param.put("pid", codeId);
							
							vipCodeMapper.modify(param);
							
						} else {
							log.info(new Date() + "用户创建账户失败");
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
						}
					} else {
						
						msg.put("code", Constance.BAR_IS_NOT_EXIST);
						msg.put("status", "false");
						
					}
				} else {
					msg.put("code", Constance.USER_IS_EXIST);
					msg.put("status", "false");
				}
			} else {
				log.info(new Date() + "用户未设置手机号或者密码");
				msg.put("code", Constance.PRIMARY_KEY_IS_NULL);
				msg.put("status", "false");
			}
		} else {
			log.info(new Date() + "前端传递参数错误");
			msg.put("code", Constance.PARAMETERS_ERROR);
			msg.put("status", "false");
		}
		return msg;
	}

	@Override
	public Map<String, Object> del(Map<String, Object> map) throws Exception {
		return null;
	}

	@Override
	public Map<String, Object> modify(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("phone"))){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("phone", map.get("phone"));
				param = userMapper.searchByPK(param);
				//存在用户信息
				if(null != param && !param.isEmpty()){
					String id = param.get("pid").toString();
					String isModify = param.get("isModify").toString();
					int flag = -1;
					//是否超过30天限制
					if((System.currentTimeMillis() - Long.parseLong(param.get("createTimeLong").toString())) <= Long.parseLong(Constant.THIRTY)){
						
						
						if(!isModify.equals("1")){
							param.put("name", null == map.get("name") ? null : map.get("name"));
							param.put("ext2", "1");
						}
						param.put("birthday", null == map.get("birthday") ? null : map.get("birthday"));
						param.put("industry", null == map.get("industry") ? null : map.get("industry"));
						param.put("company", null == map.get("company") ? null : map.get("company"));
						param.put("position", null == map.get("position") ? null : map.get("position"));
						param.put("avatar", null == map.get("avatar") ? null : map.get("avatar"));
						param.put("status", null == map.get("status") ? null : map.get("status"));
						param.put("city", null == map.get("city") ? null : map.get("city"));
						param.put("age", null == map.get("birthday") ? null : DateUtil.calAge(map));
						

						
						//用户以完善信息
						if(null != param.get("age"))
							param.put("ext1", STATE.DISABLE.key);
						
						
						try {
							//修改用户信息
							flag = userMapper.modify(param);
						} catch(Exception e){
							log.error(new Date() + "完善个人信息失败" + e);
							msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
							msg.put("status", "false");
						}
						if(flag > 0) {
							param = userMapper.searchByPK(param);
							msg.put("code", Constance.BASE_SUCCESS_CODE);
							msg.put("status", "true");
							msg.put("result", param);
						} else {
							param.clear();
							param.put("pid", id);
							param = userMapper.searchByPK(param);
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
							msg.put("result", param);
						}
					} else {
						
						try {
							flag = userMapper.del(param);
						} catch(Exception e){
							log.error(new Date() + "完善个人信息失败,已超过完善信息限制时间"+ e);
							msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
							msg.put("status", "false");
						}
						
						
						if(flag > 0){
							msg.put("code", Constance.AREADY_OUT);
							msg.put("status", "true");
						} else {
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
						}
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
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid"))){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("parentId", map.get("pid"));
				
				//是否有条件查询
				if(null != map.get("status"))
					param.put("status", map.get("status"));
				
				param.put("complete", null == map.get("complete") ? null : map.get("complete"));//只有以完善信息的方可进行审核状态
				
				
				if(null != map.get("pageSize") && null != map.get("pageNo")){
					
					//分页计算
					param.put("pageNo", (Integer.parseInt(map.get("pageNo").toString()) -1 ) * Integer.parseInt(map.get("pageSize").toString()));
					param.put("pageSize", map.get("pageSize"));
					
				}
				
				
				List<Map<String, Object>> rel = userMapper.search(param);
				//数据库执行查询正常
				if(null != rel){
					//有返回结果
					if(rel.size() > 0){
						
						param.clear();
						
						param.put("result", rel);
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
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid")) && StringUtil.checkNotNull(map.get("owner"))){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("pid", map.get("pid"));
				param = userMapper.searchByPK(param);
				if(null != param && !param.isEmpty()){
					
					param.remove("pwd");
					
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("owner", map.get("owner"));
					
					List<Map<String, Object>> rel = userMapper.links(param);
					
					if(null != rel && rel.size() > 0){
						
						for(Map<String, Object> tmp : rel){
							if(!tmp.get("who").toString().equals(map.get("pid").toString())){
								param.remove("phone");
							}
						}
						
					} else {
						param.remove("phone");
					}
					
					
					msg.put("code", Constance.BASE_SUCCESS_CODE);
					msg.put("status", "true");
					msg.put("result", param);
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
	public Map<String, Object> forgetPwd(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("phone")) && StringUtil.checkNotNull(map.get("pwd"))){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("phone", map.get("phone"));
				param = userMapper.searchByPK(param);
				//存在用户
				if(null != param && !param.isEmpty()){
					param.put("pwd", map.get("pwd"));
					int flag = -1;
					try {
						flag = userMapper.modify(param);
					} catch(Exception e){
						log.error(new Date() + "用户忘记密码修改失败" + e);
						msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
						msg.put("status", "false");
					}
					if(flag > 0){
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
					} else {
						msg.put("code", Constance.SQL_IS_ERROR);
						msg.put("status", "true");
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
	public Map<String, Object> login(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("phone")) && StringUtil.checkNotNull(map.get("pwd"))){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("phone", map.get("phone"));
				param.put("pwd", map.get("pwd"));
				
				param = userMapper.searchByPK(param);
				if(null != param && !param.isEmpty()){
					
					//审核通过方可登录
//					if(Integer.parseInt(param.get("status").toString()) > 0){
					
						//重新计算年龄，防止跨年
						if(null != param.get("birthday") && !"".equals(param.get("birthday")))
							param.put("age", DateUtil.calAge(param));
						
						
						param.remove("pwd");
						
						msg.put("code", Constance.BASE_SUCCESS_CODE);
						msg.put("status", "true");
						msg.put("result", param);
//					} else {
//						msg.put("code", Constance.NO_PERMISSION);
//						msg.put("status", "false");
//					}
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
	public Map<String, Object> update(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid")) && StringUtil.checkNotNull(map.get("pwd")) && StringUtil.checkNotNull(map.get("newPwd"))){
				if (!map.get("pwd").toString().equals(map.get("newPwd").toString())) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("pid", map.get("pid"));
					param = userMapper.searchByPK(param);
					if (null != param && !param.isEmpty()) {
						param.put("pwd", map.get("newPwd"));
						int flag = -1;
						try {
							flag = userMapper.update(param);
						} catch (Exception e) {
							log.error(new Date() + "修改密码失败" + e);
							msg.put("code",Constance.BASE_SERVER_WRONG_CODE);
							msg.put("status","false");
						}
						if (flag > 0) {
							log.info(new Date() + "修改密码成功");
							msg.put("code", Constance.BASE_SUCCESS_CODE);
							msg.put("status", "true");
						} else {
							log.info(new Date() + "修改密码失败");
							msg.put("code", Constance.SQL_IS_ERROR);
							msg.put("status", "false");
						}
					} else {
						msg.put("code", Constance.USER_IS_NOT_EXIST);
						msg.put("status", "false");
					}
				} else {
					msg.put("code", Constance.PASSWORD_IS_SAME);
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
	public Map<String, Object> links(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid"))){
				linksForOrderBy(map, msg);
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
	
	private void linksForOrderBy(Map<String, Object> map, Map<String, Object> msg) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("owner", map.get("pid"));
		
		if(null != map.get("pageSize") && null != map.get("pageNo")){
			
			//分页计算
			param.put("pageNo", (Integer.parseInt(map.get("pageNo").toString()) -1 ) * Integer.parseInt(map.get("pageSize").toString()));
			param.put("pageSize", map.get("pageSize"));
			
		}
		
		List<Map<String, Object>> rel = userMapper.links(param);
		if(null != rel && rel.size() > 0){
			
			List<Map<String, Object>> lss = new ArrayList<Map<String, Object>>();
			
			for(Map<String, Object> m : rel){
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("pid", m.get("who"));
				tmp = userMapper.searchByPK(tmp);
				if(null != tmp){
					lss.add(tmp);
				}
			}

			List<List<Map<String, Object>>> ls = new ArrayList<List<Map<String, Object>>>();
			
			if(null != lss && lss.size() > 0){
				

				String[] character = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
				
				Map<String, List<Map<String, Object>>> charMap = new HashMap<String, List<Map<String, Object>>>();
				
				for(String str : character){
					charMap.put(str, new ArrayList<Map<String, Object>>());
				}
				
				for(Map<String, Object> m : lss) {
					
					if(null == m.get("name") || "".equals(m.get("name").toString())){
						List<Map<String, Object>> enLs = charMap.get(character[character.length - 1]);
						enLs.add(m);
						charMap.put(character[character.length - 1], enLs);
					} else {
						if(StringUtil.isEnglish(m.get("name").toString())) {//英文
							String tempStr = m.get("name").toString().substring(0, 1);//获取首字母
							if(charMap.containsKey(tempStr.toUpperCase())){
								List<Map<String, Object>> enLs = charMap.get(tempStr.toUpperCase());
								enLs.add(m);
								charMap.put(tempStr.toUpperCase(), enLs);
							} else {
								List<Map<String, Object>> enLs = charMap.get(character[character.length - 1]);
								enLs.add(m);
								charMap.put(character[character.length - 1], enLs);
							}
						} else {//中文
							String tempStr = m.get("name").toString();
							if(charMap.containsKey(converterToFirstSpell(tempStr).substring(0, 1).toUpperCase())){
								List<Map<String, Object>> enLs = charMap.get(converterToFirstSpell(tempStr).substring(0, 1).toUpperCase());
								enLs.add(m);
								charMap.put(converterToFirstSpell(tempStr).substring(0, 1).toUpperCase(), enLs);
							} else {
								List<Map<String, Object>> enLs = charMap.get(character[character.length - 1]);
								enLs.add(m);
								charMap.put(character[character.length - 1], enLs);
							}
						}
					}
					
				}

				
				for(String str : character){
					if(charMap.containsKey(str))
						ls.add(charMap.get(str));
				}
				
				param.clear();
				
				param.put("result", rel);
				param.put("pageNo", null == map.get("pageNo") ? null : map.get("pageNo"));
				
				msg.put("code", Constance.BASE_SUCCESS_CODE);
				msg.put("status", "true");
				msg.put("result", ls);
			} else {

				
				param.clear();
				
				param.put("result", rel);
				param.put("pageNo", null == map.get("pageNo") ? null : map.get("pageNo"));
				
				msg.put("code", Constance.BASE_SUCCESS_CODE);
				msg.put("status", "false");
				msg.put("result", ls);
			}
		} else {
			msg.put("code", Constance.BASE_SUCCESS_CODE);
			msg.put("status", "false");
			msg.put("result", rel);
		}
	}
	
	/** 
     * 汉字转换位汉语拼音首字母，英文字符不变 
     * @param chines 汉字 
     * @return 拼音 
     */  
     private String converterToFirstSpell(String chines){         
         String pinyinName = "";  
         char[] nameChar = chines.toCharArray();  
         HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
         defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
         defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
         for (int i = 0; i < nameChar.length; i++) {  
             if (nameChar[i] > 128) {  
                 try {  
                     pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);  
                 } catch (BadHanyuPinyinOutputFormatCombination e) {  
                     e.printStackTrace();  
                 }  
             }else{  
                 pinyinName += nameChar[i];  
             }  
         }  
         return pinyinName;  
     }

	@Override
	public Map<String, Object> linked(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("pid"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					
					
					param.put("owner", map.get("pid"));
					
					param.put("import", STATE.ENABLE.key);
					
					
					//获取未执行过导入联系人操作的派对信息
					List<Map<String, Object>> rel = partyMapper.search(param);
					
					boolean flag = false;
					
					if(null != rel && rel.size() > 0){
						for(Map<String, Object> m : rel){
							
							Long end = Long.parseLong(m.get("end").toString());
							
							
							//已结束方可导入联系人，否则不执行导入操作
							if(end <= System.currentTimeMillis()){

								param.clear();
								param.put("pid", m.get("pid"));
								
								List<Map<String, Object>> people = partyMapper.searchByFKUser(param);
								
								//存在报名人员
								if(null != people && !people.isEmpty()){
									
									param.clear();
									param.put("owner", m.get("owner"));
									
									List<Map<String, Object>> links = userMapper.links(param);
									
									//存在人员信息
									if(null != links && !links.isEmpty()){
										calcontains(links, people, m, param);
									} else {
										for(Map<String, Object> ls : people)
											addLinks(ls, m, param);
									}
									flag = true;
								}
								/*
								 * 1、查询与会人员信息
								 * 2、判断与会人员是否存在我的通讯录中
								 * 3、根据判断结果执行添加or修改操作
								 */
								
								
								//导入相应派对联系人后需更改派对状态
								
								param.clear();
								param.put("pid", m.get("pid"));
								param.put("import", STATE.DISABLE.key);
								
								partyMapper.modify(param);
								
								
							}
						}
					}
					linksForOrderBy(map, msg);
					if(!flag)
						msg.put("code", Constance.DATA_IS_EMPTY);
				} catch (Exception e) {
					log.error(new Date() + "一键导入联系人失败" + e);
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
	
	private boolean calcontains(List<Map<String, Object>> links, List<Map<String, Object>> people, Map<String, Object> m, Map<String, Object> param){
		boolean flag = true;
		for(Map<String, Object> ls : people){
			for(Map<String, Object> tmp : links)
				if(tmp.get("who").toString().equals(ls.get("userId").toString())){
					flag = false;
					break;
				}
			if(flag)
				addLinks(ls, m, param);
		}
		return flag;
	}
	
	
	private void addLinks(Map<String, Object> m, Map<String, Object> ls, Map<String, Object> param){
		param.clear();
		
		param.put("owner", ls.get("owner"));
		param.put("who", m.get("userId"));
		
		userMapper.addLinks(param);
		
	}

	@Override
	public Map<String, Object> checkPeopleInfo(Map<String, Object> map) throws Exception {
		Map<String, Object> msg = new HashMap<String, Object>();
		if(!map.isEmpty()){
			if(StringUtil.checkNotNull(map) && StringUtil.checkNotNull(map.get("owner")) && StringUtil.checkNotNull(map.get("userId")) && StringUtil.checkNotNull(map.get("status"))){
				try {
					Map<String, Object> param = new HashMap<String, Object>();
					
					param.put("pid", map.get("userId"));
					param.put("parentId", map.get("owner"));
					
					param = userMapper.searchByPK(param);
					
					int flag = -1;
					
					if(null != param && !param.isEmpty()){
						
						Object id = param.get("pid");
						
						Object code = param.get("code");
						

						param.clear();
						
						param.put("owner", map.get("owner"));
						param.put("code", code);
						
						param = vipCodeMapper.search(param).get(0);
						
						//判断邀请码存在
						if(null != param && !param.isEmpty()){
							
							Object codeId = param.get("pid");
							
							//邀请码未使用
							if(param.get("status").toString().equals(String.valueOf(STATE.DISABLE.key))){
								

								//未超过30天
								if((System.currentTimeMillis() - Long.parseLong(param.get("time").toString())) <= Long.parseLong(Constant.THIRTY)){

									param.clear();;
									
									param.put("status", map.get("status"));
									param.put("pid", map.get("userId"));
									
									flag = userMapper.modify(param);
									
									if(flag > 0){
										
										msg.put("code", Constance.BASE_SUCCESS_CODE);
										msg.put("status", "true");
										
										//更改邀请码状态
										param.clear();
										param.put("status", STATE.DISABLE.key);
										param.put("pid", codeId);
										vipCodeMapper.modify(param);
										
										
										List<Map<String, Object>> codes = vipCodeMapper.search(null);
										
										if("1".equals(map.get("status").toString())){
											//审核通过，自动生成三个邀请码到审核账户上
											
											for(int i =0 ;i < 3; i ++){
												String tmp = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
												
												boolean isCal = false;
												
												for(Map<String, Object> m : codes){
													if(!m.get("code").toString().equals(tmp)){
														param = new HashMap<String, Object>();
														
														
														
														param.put("code", tmp);
														param.put("createTimeLong", System.currentTimeMillis());
														param.put("owner", id);
														param.put("status", STATE.ENABLE.key);
														
														vipCodeMapper.add(param);
													} else {
														isCal = true;
													}
													break;
												}
												
												if(isCal){
													i --;
												}
											}
											
											param.clear();
											
											List<String> receiverList = new ArrayList<String>();
											
											receiverList.add(JPushUtils.userTagPrefix + id);
											
											param.put("pid", id);
											param.put("title", Constant.INFO);
											param.put("receivers", receiverList);
											
											//添加推送消息
											PushAPI.getInstance().verifyUserInfo(param);
											
										} else {
											param.clear();
											param.put("status", STATE.ENABLE.key);
											param.put("pid", codeId);
											vipCodeMapper.modify(param);
											
											param.clear();
											param.put("pid", map.get("userId"));
											userMapper.del(param);
										}
										
									} else {
										msg.put("code", Constance.BASE_SERVER_WRONG_CODE);
										msg.put("status", "false");
									}
									
								} else {
									
									//超过30天删除帐号信息
									flag = userMapper.del(param);
									
									if(flag > 0){
										
										param.clear();
										param.put("status", STATE.ENABLE.key);
										param.put("pid", codeId);
										vipCodeMapper.modify(param);
										
										msg.put("code", Constance.AREADY_OUT);
										msg.put("status", "true");
									} else {
										msg.put("code", Constance.SQL_IS_ERROR);
										msg.put("status", "false");
									}
								}
							} else {
								msg.put("code", Constance.BAR_IS_NOT_HAVE_CODE);
								msg.put("status", "false");
							}
							
							
						} else {
							msg.put("code", Constance.BAR_IS_NOT_EXIST);
							msg.put("status", "false");
						}
						
					} else {
						msg.put("code", Constance.USERNAME_IS_NOT_EXIST);
						msg.put("status", "false");
					}
				} catch (Exception e) {
					log.error(new Date() + "执行审核失败" + e);
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
