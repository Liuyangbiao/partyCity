package com.borui.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * 
 * session工具类
 * 
 * @author Dio
 *
 */
public class SessionUtils {
	
	private static SessionUtils instance;
	
	private Map<String, Object> sessionMap;
	
	public SessionUtils(){
		sessionMap = new HashMap<String, Object>();
	}
	
	public static SessionUtils getInstance(){
		if(instance == null)
			instance = new SessionUtils();
		return instance;
	}
	
	/**
	 * 
	 * 新添加session
	 * 
	 * @param session
	 */
	public synchronized void addSession(HttpSession session){
		if(session != null)
			sessionMap.put(session.getId(), session);
	}
	
	/**
	 * 
	 * 删除session
	 * 
	 * @param session
	 */
	public synchronized void delSession(HttpSession session){
		if(session != null)
			sessionMap.remove(session.getId());
	}
	
	/**
	 * 
	 * 校验session
	 * 
	 * @param session
	 * @return
	 */
	public boolean checkSession(HttpSession session){
		boolean flag = false;
		if(session != null && sessionMap.containsKey(session.getId()))
			flag = true;
		return flag;
	}

}
