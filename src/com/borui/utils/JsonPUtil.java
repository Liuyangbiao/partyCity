package com.borui.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class JsonPUtil {
	
	private static Logger log = Logger.getLogger(JsonPUtil.class);
	
	public static Map<String, Object> pToMap(HttpServletRequest request){
		try{
			String p = request.getParameter("p");
			JSONObject jsonObject = JSON.parseObject(p);
			Map<String,Object> map = jsonObject;
			if(map!=null){
				return map;
			}else{
				return new HashMap<String, Object>();
			}
		}catch(JSONException e){
			log.error("p参数json转换失败，"+e.getMessage());
			return new HashMap<String, Object>();
		}
	}
	
	/**
	 * 获取post方式提交的参数
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getPostParams(HttpServletRequest request){
		Map<String, Object> params = new TreeMap<String, Object>();

		// post方式参数
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] values = request.getParameterValues(paramName);
			if (values == null || values.length == 0) {
				// Do nothing, no values found at all.
			} else if (values.length > 1) {
				params.put(paramName, values);
			} else {
				String val = StringUtils.trim(values[0]);
				if (val.length() != 0) {
					params.put(paramName, val);
				}
			}
		}
		
		if (params.isEmpty()) {
			// Get方式获取参数
            String queryString = request.getQueryString();
            if (StringUtils.isEmpty(queryString)) { 
            	return params; 
            }
            String encoding =
                    StringUtils.defaultString(request.getCharacterEncoding(),
                            "UTF-8");
            try {
                queryString = URLDecoder.decode(queryString, encoding);
                List<String> list1 = toList(queryString, '&');
                List<String> buff = new ArrayList<String>(2);
                for (String str : list1) {
                    toList(str, '=', buff);
                    if (buff.size() > 1) {
                        params.put(buff.get(0), buff.get(1));
                    }
                }
            } catch (UnsupportedEncodingException e) {
            }
        
		}
		return params;
	}
	
	private static List<String> toList(String str, char sep) {
		if (StringUtils.isEmpty(str)) {
			return Collections.emptyList();
		}
		List<String> result = new ArrayList<String>();
		toList(str, sep, result);
		return result;
	}
	
	private static void toList(String str, char sep, List<String> list) {
		StringBuilder sb = new StringBuilder();
		list.clear();
		for (int idx = 0; idx < str.length(); ++idx) {
			char c = str.charAt(idx);
			if (c == sep) {
				list.add(sb.toString().trim());
				sb.setLength(0);
			} else {
				sb.append(c);
			}
		}
		if (sb.length() > 0) {
			list.add(sb.toString().trim());
			sb.setLength(0);
		}
	}
	
	/**
	 * 
	 * 构建map对象
	 * 
	 * @author Dio
	 * @param request
	 * @return
	 */
	public static Map<String, String> buildMap(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = (String[]) map.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}
}
