package com.borui.web.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.borui.web.model.Schedule;
import com.borui.web.service.IScheduleService;

/**
 * 日程管理控制器
 * @author dio
 *
 */

@Controller
@RequestMapping(value = "/api/schedule")
@SuppressWarnings({"unchecked", "finally"})
public class ScheduleController extends BaseController{

	/**
	 * 声明记录日志
	 */
	private Logger log = Logger.getLogger(ScheduleController.class);
	
	@Resource
	private IScheduleService<Schedule> scheduleServiceImpl;
	
	/**
	 * 添加事项
	 * <ol>必传参数							</ol>
	 * <ol>owner					 		拥有者</ol>
	 * <ol>title							事项</ol>
	 * <ol>start							开始时间</ol>
	 * <ol>end								结束时间</ol>
	 * <ol>createtimelong					时间</ol>
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
				map = scheduleServiceImpl.add(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/schedule/add.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	/**
	 * 修改事项
	 * <ol>必传参数</ol>
	 * <ol>pid					主键</ol>
	 * <ol>可传参数</ol>
	 * <ol>owner					 		拥有者</ol>
	 * <ol>title							事项</ol>
	 * <ol>start							开始时间</ol>
	 * <ol>end								结束时间</ol>
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
				map = scheduleServiceImpl.modify(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/schedule/modify.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	/**
	 * 删除事项
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
				map = scheduleServiceImpl.del(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/schedule/del.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	/**
	 * 
	 * 获取事项列表(当天)
	 * 
	 * <ol>必传参数</ol>
	 * <ol>userId				登录人pid</ol>
	 * <ol>可传参数</ol>
	 * <ol>time					日期</ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = scheduleServiceImpl.search(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : 
						ResponseMessageUtil.listMsgForObject(map.get("code").toString(), map.get("status").toString(), (List<Object>)map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/schedule/list.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	/**
	 * 
	 * 获取事项列表（日历用）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listForCalender")
	public Map<String, Object> listForCalender(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = scheduleServiceImpl.searchForCalender(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : 
						ResponseMessageUtil.listMsg(map.get("code").toString(), map.get("status").toString(), (List<Map<String, Object>>)map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/date/publishDateMatchInfo.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/info")
	public Map<String, Object> info(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = scheduleServiceImpl.selectByPK(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/date/publishDateMatchInfo.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		return msg;
	}
}
