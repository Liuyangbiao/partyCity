package com.borui.web.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.borui.utils.JsonPUtil;
import com.borui.utils.common.Constance;
import com.borui.utils.common.ResponseMessageUtil;
import com.borui.web.controller.BaseController;
import com.borui.web.model.Party;
import com.borui.web.service.IPartyService;

/**
 * 派对管理控制器
 * @author dio
 *
 */
@SuppressWarnings({"unchecked","finally"})
@Controller
@RequestMapping(value = "/api/party")
public class PartyController extends BaseController{

	/**
	 * 声明记录日志
	 */
	private Logger log = Logger.getLogger(PartyController.class);
	
	@Resource
	private IPartyService<Party> partyServiceImpl;
	
	/**
	 * 添加派对
	 * <ol>必传参数								</ol>
	 * <ol>name									主题</ol>
	 * <ol>type									类型</ol>
	 * <olstart									开始时间</ol>
	 * <ol>end									结束时间</ol>
	 * <ol>address								地点</ol>
	 * <ol>可传参数								</ol>
	 * <ol>city									城市</ol>
	 * <ol>count								人数</ol>
	 * <ol>cost									费用</ol>
	 * <ol>inviteobject							邀请条件</ol>
	 * <ol>content								描述</ol>
	 * <ol>createtimelong						时间</ol>
	 * <ol>ext3						     		活动海报</ol>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public Map<String, Object> add(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = partyServiceImpl.add(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/party/add.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 修改派对信息
	 * <ol>必传参数								</ol>
	 * <ol>pid									主键</ol>
	 * <ol>createtimelong						时间</ol>
	 * <ol>可传参数								</ol>
	 * <ol>name									主题</ol>
	 * <ol>type									类型</ol>
	 * <ol>start								开始时间</ol>
	 * <ol>end									结束时间</ol>
	 * <ol>address								地点</ol>
	 * <ol>city									城市</ol>
	 * <ol>count								人数</ol>
	 * <ol>cost									费用</ol>
	 * <ol>inviteobject							邀请条件</ol>
	 * <ol>content								描述</ol>
	 * <ol>ext3						     		活动海报</ol>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify")
	public Map<String, Object> modify(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = partyServiceImpl.modify(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/party/modify.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 删除派对信息
	 * <ol>必传参数</ol>
	 * <ol>pid					主键</ol>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public Map<String, Object> del(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = partyServiceImpl.del(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/party/del.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}

	}
	
	/**
	 * 
	 * 获取派对列表
	 * 
	 * <ol>必传参数</ol>
	 * <ol>无</ol>
	 * <ol>可传参数</ol>
	 * <ol>join							标志查询活动列表的来源，用于区分我报名的、我发布的以及首页的列表，登录人的pid</ol>
	 * <ol>name							关键字查询</ol>
	 * <ol>owner						我发布的约会</ol>
	 * <ol>city							根据城市编号遍历列表</ol>
	 * <ol>type							活动类型</ol>
	 * <ol>time							1：今天；7:一周内；30:一个月</ol>
	 * <ol>pageNo						</ol>
	 * <ol>pageSize						</ol>
	 * 
	 * 
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list")
	public Map<String, Object> list(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = partyServiceImpl.search(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : 
						ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>)map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/party/list.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	
	/**
	 * 
	 * 获取派对详情（根据派对pid）
	 * 
	 * <ol>必传参数</ol>
	 * <ol>partyId				派对pid</ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info")
	public Map<String, Object> info(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = partyServiceImpl.selectByPK(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/party/info.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	
	/**
	 * 
	 * 报名派对
	 * 
	 * <ol>必传参数</ol>
	 * <ol>partyId				派对pid</ol>
	 * <ol>userId				报名人pid</ol>
	 * <ol>可传参数</ol>
	 * <ol>partyOwner			派对所有者pid</ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/joinParty")
	public Map<String, Object> joinParty(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = partyServiceImpl.joinParty(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/party/joinParty.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
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
	@ResponseBody
	@RequestMapping(value = "/delPartyLog")
	public Map<String, Object> delPartyLog(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = partyServiceImpl.delPartyLog(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()); 
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/party/delPartyLog.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
}
