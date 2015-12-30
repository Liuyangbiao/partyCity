package com.borui.web.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.borui.utils.common.Constance;
import com.borui.utils.common.Constant;
import com.borui.utils.common.ResponseMessageUtil;
import com.borui.web.controller.BaseController;

@SuppressWarnings("finally")
@Controller
@RequestMapping(value = "/api/common")
public class GlobalController extends BaseController {

	
	private Logger log = Logger.getLogger(GlobalController.class);
	
	@RequestMapping(value="/getCities")
	@ResponseBody
	public Map<String, Object> getCities(HttpServletRequest request){
		Map<String, Object> msg = new HashMap<String, Object>();
		try{
			msg.put("code", Constance.BASE_SUCCESS_CODE);
			msg.put("status", "true");
			
			List<Map<String, Object>> cityList = getCityList();
			msg.put("result", cityList);
		} catch(Exception e){
			log.error("api/city/getCities.do is error. info:"+e+"\tnow is ："+ new Date()+"\n");
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally {
			return msg;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPlaceType")
	public Map<String, Object> getPlaceType(HttpServletRequest request){
		List<Map<String, Object>> rel = new ArrayList<Map<String, Object>>();
		if(null != Constant.PALCE_TYPE)
			for(String str : Constant.PALCE_TYPE.split(";")){
				String[] place = str.split("-");
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("id", place[0]);
				m.put("name", place[1]);
				rel.add(m);
			}
		return ResponseMessageUtil.listMsg(Constance.BASE_SUCCESS_CODE, "true", rel);
	}
	
	
	private ArrayList<Map<String, Object>> getCityList(){
		ArrayList<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> city = new HashMap<String, Object>();
		
		city.put("code", "010");
		city.put("name", "北京市");
		
		cityList.add(city);
		
		city = new HashMap<String, Object>();
		city.put("code", "020");
		city.put("name", "广州市");
		
		cityList.add(city);
		
		
		city = new HashMap<String, Object>();
		city.put("code", "0755");
		city.put("name", "深圳市");
		
		cityList.add(city);

		
		city = new HashMap<String, Object>();
		city.put("code", "027");
		city.put("name", "武汉市");
		
		cityList.add(city);
		

		
		city = new HashMap<String, Object>();
		city.put("code", "0512");
		city.put("name", "苏州市");
		
		cityList.add(city);
		

		
		city = new HashMap<String, Object>();
		city.put("code", "0411");
		city.put("name", "大连市");
		 
		cityList.add(city);
		

		
		city = new HashMap<String, Object>();
		city.put("code", "025");
		city.put("name", "南京市");
		
		cityList.add(city);
		
		return cityList;
	}
	
}
