package com.borui.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.borui.utils.HtmlUtil;
import com.borui.utils.SessionUtils;
import com.borui.utils.TokenUtil;
import com.borui.utils.URLUtils;
import com.borui.web.edit.MyEditor;

public class BaseController{
	
	public final static String SUCCESS ="success";  
	
	public final static String MSG ="msg";  
	
	public final static String DATA ="data";  
	
	public final static String LOGOUT_FLAG = "logoutFlag";  
	
	public final static SessionUtils instance = SessionUtils.getInstance();
	
	
   @InitBinder  
   protected void initBinder(WebDataBinder binder) {  
		 binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));  
		 binder.registerCustomEditor(int.class,new MyEditor()); 
   }  
	 
	 /**
	  * 获取IP地址
	  * @param request
	  * @return
	  */
	 public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	 
	 /**
	  * 所有ActionMap 统一从这里获取
	  * @return
	  */
	public Map<String,Object> getRootMap(){
		Map<String,Object> rootMap = new HashMap<String, Object>();
		//添加url到 Map中
		rootMap.putAll(URLUtils.getUrlMap());
		return rootMap;
	}
	
	/**
	 * 
	 * 
	 * @param null
	 * @return null
	 */
	public ModelAndView forword(String viewName,Map<String, Object> context){
		return new ModelAndView(viewName,context); 
	}
	
	public ModelAndView error(String errMsg){
		return new ModelAndView("error"); 
	}
	
	/**
	 *
	 * 提示成功信息
	 *
	 * @param message
	 *
	 */
	public void sendSuccessMessage(HttpServletResponse response,  String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, true);
		result.put(MSG, message);
		HtmlUtil.writerJson(response, result);
	}

	/**
	 *
	 * 提示失败信息
	 *
	 * @param message
	 *
	 */
	public void sendFailureMessage(HttpServletResponse response,String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, false);
		result.put(MSG, message);
		HtmlUtil.writerJson(response, result);
	}
	
	/**
	 * 
	 * 添加token
	 * 
	 * @param request
	 * @param response
	 * @return				token
	 */
	public long addToken(HttpServletRequest request, HttpServletResponse response){
		return TokenUtil.addToken(request, response);
	}
	
	/**
	 * 
	 * 验证token
	 * 
	 * @param token
	 * @return		0-未传递token，不合法
	 * 				1-不存在token，在处理
	 * 				2-不合法token，不处理
	 * 				3-正常token，继续操作
	 */
	public int checkToken(String token){
		return TokenUtil.checkToken(token);
	}
	
	/**
	 * 
	 * 添加session
	 * 
	 * @param session
	 */
	public void addSession(HttpSession session){
		instance.addSession(session);
	}
	
	/**
	 * 
	 * 删除session
	 * 
	 * @param session
	 */
	public void delSession(HttpSession session){
		instance.delSession(session);
	}
}
