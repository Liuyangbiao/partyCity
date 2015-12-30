package com.borui.web.controller.api;

import java.util.ArrayList;
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
import com.borui.utils.common.Constant;
import com.borui.utils.common.ResponseMessageUtil;
import com.borui.web.controller.BaseController;
import com.borui.web.model.User;
import com.borui.web.service.IUserService;
import com.borui.web.service.IVipCodeService;

/**
 * 
 * 用户管理控制器
 * 
 * @author Dio
 *
 */
@SuppressWarnings({"unchecked","finally"})
@Controller
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {

	/**
	 * 声明记录日志
	 */
	private Logger log = Logger.getLogger(UserController.class);

	/**
	 * 声明需要使用的service（逻辑实现）
	 */
	@Resource
	private IUserService<User> userServiceImpl;
	
	@Resource
	private IVipCodeService<User> vipCodeServiceImpl;

	/**
	 * 
	 * 注册
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
	 * <b>/api/user/register.do</b>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register")
	public Map<String, Object> register(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if (!map.isEmpty()) {
				map = userServiceImpl.add(map);
				if (null != map && !map.isEmpty()) {
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>) map.get("result")) ;
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch (Exception e) {
			log.error("api/user/register.do is error. info:" + e + "\tnow is :" + new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}

	/**
	 * 登录
	 * <ol>
	 * 必传参数
	 * </ol>
	 * <ol>
	 * phone 手机号
	 * </ol>
	 * <ol>
	 * pwd 加密密码
	 * </ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login")
	public Map<String, Object> login(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if (!map.isEmpty()) {
				map = userServiceImpl.login(map);
				if (null != map && !map.isEmpty()) {
					msg = null == map.get("result")
							? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString())
							: ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(),
									(Map<String, Object>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch (Exception e) {
			log.error("api/user/login.do is error. info:" + e + "\tnow is :" + new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 忘记密码
	 * 
	 * <ol>必传参数</ol>
	 * <ol>phone					手机号</ol>
	 * <ol>pwd						新密码</ol>
	 * 
	 * <ol>/api/user/forgetPwd.do</ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/forget")
	public Map<String, Object> forgetPwd(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.forgetPwd(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/forgetPwd.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 修改个人信息
	 * <ol>必传参数</ol>
	 * <ol>pid					主键</ol>
	 * <ol>可传参数</ol>
	 * <ol>name					昵称</ol>
	 * <ol>birthday				生日（秒数）</ol>
	 * <ol>indystry				行业</ol>
	 * <ol>company				公司</ol>
	 * <ol>position				titile</ol>
	 * <ol>avatar				头像</ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modify")
	public Map<String, Object> modify(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.modify(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : 
						ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/modify.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}

	/**
	 * 
	 * 修改密码
	 * 
	 * <ol>必传参数</ol>
	 * <ol>pid					主键</ol>
	 * <ol>pwd					原密码</ol>
	 * <ol>newPwd				新密码</ol>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Map<String, Object> update(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.update(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/update.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 查看他人详细
	 * <ol>必传参数</ol>
	 * <ol>pid					主键</ol>
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/otherInfo")
	public Map<String, Object> otherInfo(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.selectByPK(map);
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
			log.error("api/user/otherInfo.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 查看通讯录
	 * 
	 * <ol>必传参数</ol>
	 * <ol>pid						主键</ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/links")
	public Map<String, Object> links(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.links(map);
				if(null != map){
					msg = 
						null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : 
						ResponseMessageUtil.listMsg(map.get("code").toString(), map.get("status").toString(), (List<Map<String, Object>>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/links.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 待审核人员
	 * 
	 * <ol>必传参数</ol>
	 * <ol>pid							主键</ol>
	 * <ol></ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/myLink")
	public Map<String, Object> myLink(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.search(map);
				if(null != map && !map.isEmpty()){
					msg =
						null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : 
						ResponseMessageUtil.listMsg(map.get("code").toString(), map.get("status").toString(), (List<Map<String, Object>>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/myLink.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 一键导入
	 * 
	 * <ol>必传参数</ol>
	 * <ol>pid						登陆人的pid</ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/linked")
	public Map<String, Object> linked(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.linked(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : ResponseMessageUtil.listMsg(map.get("code").toString(), map.get("status").toString(), (List<Map<String, Object>>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/linked.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 获取行业信息
	 * 
	 * <ol></ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/industry")
	public Map<String, Object> industry(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		List<Map<String, Object>> rel = null;
		if (null != Constant.INDUSTRY_NAME) {
			rel = new ArrayList<Map<String, Object>>();
			for (String  str : Constant.INDUSTRY_NAME.split("-")) {
				String[] temp = str.split(",");
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("name", temp[1]);
				m.put("id", temp[0]);
				rel.add(m);
			}
			msg = ResponseMessageUtil.listMsg(Constance.BASE_SUCCESS_CODE, "true", rel);
		} else {
			msg = ResponseMessageUtil.respMsg(Constance.DATA_IS_EMPTY, "false");
		}
		return msg;
	}
	
	
	/**
	 * 
	 * 获取邀请码
	 * 
	 * <ol></ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mineCode")
	public Map<String, Object> mineCode(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = vipCodeServiceImpl.search(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : ResponseMessageUtil.listMsg(map.get("code").toString(), map.get("status").toString(), (List<Map<String, Object>>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/mineCode.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	
	/**
	 * 
	 * 获取待审核人员
	 * 
	 * <ol></ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMinePeople")
	public Map<String, Object> getMinePeople(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.search(map);
				if(null != map && !map.isEmpty()){
					msg = null == map.get("result") ? ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString()) : ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString(), (Map<String, Object>) map.get("result"));
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/mineCode.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 审核账户信息
	 * 
	 * <ol></ol>
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/verifyUserInfo")
	public Map<String, Object> verifyUserInfo(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				map = userServiceImpl.checkPeopleInfo(map);
				if(null != map && !map.isEmpty()){
					msg = ResponseMessageUtil.respMsg(map.get("code").toString(), map.get("status").toString());
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/user/verifyUserInfo.do is error. info:"+e+"\tnow is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
}
