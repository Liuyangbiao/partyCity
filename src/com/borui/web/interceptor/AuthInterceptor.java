package com.borui.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.borui.utils.SessionUtils;

/**
 * 权限拦截器
 *
 */
@SuppressWarnings({"static-access"})
public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	private final static Logger log= Logger.getLogger(AuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getServletPath();
		log.info("访问接口"+url);
		if(url.substring(0,5).toLowerCase().equals("/api/")){//来自于客户端的请求，直接放行
			return super.preHandle(request, response, handler);
		} else {
			HttpSession session = request.getSession();
			if(null == session && !SessionUtils.getInstance().checkSession(session)){
				response.setStatus(response.SC_GATEWAY_TIMEOUT);
				response.sendRedirect(request.getContextPath()+"/login.shtml");
				return false;
			}
			return super.preHandle(request, response, handler);
		}
	}
}
