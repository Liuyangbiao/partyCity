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
import com.borui.web.model.Place;
import com.borui.web.model.BaseBean.STATE;
import com.borui.web.service.IFiledService;

/**
 * 场地信息控制器
 * @author dio
 *
 */

@SuppressWarnings({"unchecked", "finally"})
@Controller
@RequestMapping(value = "/api/field")
public class FiledController extends BaseController{

	/**
	 * 声明记录日志
	 */
	private Logger log = Logger.getLogger(FiledController.class);
	
	@Resource
	private IFiledService<Place> filedServiceImpl;
	
	/**
	 * 添加场地
	 * <ol>必传参数</ol>
	 * <ol>name					 			姓名</ol>
	 * <ol>owner							拥有者</ol>
	 * <ol>address							地址</ol>
	 * <ol>可选参数</ol>
	 * <ol>froma							座机</ol>
	 * <ol>count							个数</ol>
	 * <ol>phone							电话</ol>
	 * <ol>content							描述</ol>
	 * <ol>avatar							头像</ol>
	 * <ol>createtimelong					时间</ol>
	 * <ol>status							状态</ol>
	 * <ol>type								类型</ol>
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
				map = filedServiceImpl.add(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ?  ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) :  ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/field/add.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	/**
	 * 
	 * 场地列表
	 * 
	 * <ol>必传参数</ol>
	 * <ol>pid							主键</ol>
	 * <ol></ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> search(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = filedServiceImpl.search(map);
				if(null != map && !map.isEmpty()){
					msg =
						null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : 
						ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/field/list.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	/**
	 * 修改场地
	 * <ol>必传参数</ol>
	 * <ol>pid					主键</ol>
	 * <ol>可传参数</ol>
	 * <ol>name					名字</ol>
	 * <ol>address				地址</ol>
	 * <ol>count				个数</ol>
	 * <ol>content				描述</ol>
	 * <ol>avatar				头像</ol>
	 * <ol>phone				电话</ol>
	 * <ol>froma				座机</ol>
	 * <ol>status				状态</ol>
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
				map = filedServiceImpl.modify(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/field/modify.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	/**
	 * 
	 * 删除场地信息(包含多条删除)
	 * 
	 * <ol>必传参数</ol>
	 * <ol>ids						场地主键</ol>
	 * <ol>pid						用户主键</ol>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Map<String, Object> del(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				
				//场地下架
				
				map.put("status", STATE.ENABLE.key);
				
				
				map = filedServiceImpl.del(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/filed/del.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	
	/**
	 * 
	 * 查询场地详细
	 * 
	 * <ol>必传参数</ol>
	 * <ol>pid						场地主键</ol>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/info")
	public Map<String, Object> info(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = filedServiceImpl.selectByPK(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? 
						ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : 
						ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/filed/info.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
}
