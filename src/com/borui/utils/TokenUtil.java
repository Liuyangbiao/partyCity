package com.borui.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 防止重复提交工具类
 * 
 * @author Dio
 *
 */
public class TokenUtil {
	
	private static Map<String, Object> TOKEN_MAP;
	
	public TokenUtil(){
		if(TOKEN_MAP == null)
			TOKEN_MAP = new HashMap<String, Object>();
	}

	/**
	 * 
	 * 在token中添加新的值
	 * 
	 * @param request
	 * @param response
	 * @return 返回token
	 * 
	 */
	public static long addToken(HttpServletRequest request, HttpServletResponse response){
		long now = System.currentTimeMillis();
		TOKEN_MAP.put(String.valueOf(now), now);
		return now;
	}
	
	/**
	 * 
	 * 验证是否存在token
	 * 
	 * @param token
	 * @return		0-未传递token，不合法
	 * 				1-不存在token，在处理
	 * 				2-不合法token，不处理
	 * 				3-正常token，继续操作
	 */
	public static int checkToken(String token){
		int flag = 0;
		if(token != null){
			if(TOKEN_MAP.containsKey(token)){
				TOKEN_MAP.remove(token);
				flag = 3;
			} else if(!StringUtil.isNumeric(token)) {
				flag = 2;
			}
			flag = 1;
		}
		return flag;
	}
	
}
