package com.borui.third.h5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class H5PageManager {
	/* test purpose
	 * 
	 * */
	public static void main(String args[]){
		H5Config.testFlag = true;
		
		H5PageManager.setUpTestMap();
		try {
			// test generateActivityPage
			//H5PageManager.generateActivityPage(H5PageManager.getTestMap());
			
			// test updateActivityPage
			getTestMap().put("sharePage", "http://192.168.0.102/20150706/1_d_j_n_2_.html");
			H5PageManager.isUpdateRequired(getTestMap());
			H5PageManager.updateActivityPage(getTestMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static Map<String, Object> testMap = null;
	public static Map<String, Object> getTestMap(){
		return testMap;
	}
	public static void setUpTestMap(){
		if (testMap != null){
			return;
		}
		
		String imagePath1 = "http://www.people.com.cn/mediafile/pic/20150702/14/6644703604721031146.jpg";
		String imagePath2 = "http://www.people.com.cn/mediafile/pic/20150630/8/17014255007855165432.jpg";
		String imagePath3 = "http://www.sol.net.cn/data/attachment/portal/201506/15/140156a7u69j994448j9t6.jpg";
		
		testMap = new HashMap<String, Object>();
		testMap.put("pid", 1);
		
		testMap.put("avatar", imagePath1);
		testMap.put("nickName", "我就是好男人");
		testMap.put("ownerSex", H5Config.sex_male);
		testMap.put("constellation", "10");//摩羯座
		testMap.put("age", "30");
		
		testMap.put("bonusNum", "2");
		
		testMap.put("barName", "Baby face Club");
		
		//
		testMap.put("title", "带你狂奔，带你飞");
		testMap.put("sex", H5Config.sex_female);// "1": boy
		
		testMap.put("feeType", "1");//"我请"
		testMap.put("time", "2015-5-12 23:00");
		
		
		List<Map<String, Object>> personJoinedList = new ArrayList<Map<String, Object>>();
		Map<String, Object> item = null;
		int personJoinedNum = 5;
		testMap.put("personJoinedNum", personJoinedNum);
		for (int i = 0; i < personJoinedNum; i++) {
			item = new HashMap<String, Object>();
			
			if (i == 0){
				item.put("avatar", imagePath2);
			}else{
				item.put("avatar", imagePath3);
			}
			
			personJoinedList.add(item);
		}
		testMap.put("personJoinedList", personJoinedList);
		
		testMap.put("desc", "风萧萧兮易水寒，壮士自挂东南枝。两只黄鹂鸣翠柳，白鹭自挂东南枝。"
				+ "人生在世不如意，不如自挂东南枝。天生我材必有用，各种自挂东南枝。"
				+ "约会约得不如意，还是去挂东南枝。约起来，约起来，我就是帅哥才怪。");
	}

	public static boolean generateActivityPage(Map<String, Object> map){
    	H5Page page = new H5Page();
    	page.fillData(true, map);
    	page.setAction(1);
    
    	return implementRequest(page, map);
	}

	public static boolean updateActivityPage(Map<String, Object> map) throws Exception {		
		boolean needUpdate = isUpdateRequired(map);
		if (!needUpdate){
			return false;
		}

		H5Page page = new H5Page();
    	page.fillData(false, map);
    	page.setAction(2);
    
    	return implementRequest(page, map);
	}
	
	private static boolean implementRequest(H5Page page, Map<String, Object> map){
		String data = JSON.toJSONString(page);
    	
		String sharePage = H5ServiceAPI.sendRequest(data);
		if(sharePage == null || sharePage.isEmpty()){
			return false;
		}else{
			map.put("sharePage", sharePage);
			return true;
		}
	}

	/*
	 * map contains info: current joined User, and h5 Page(the link contains
	 * joined user Number)
	 */
	public static boolean isUpdateRequired(Map<String, Object> map) {
		// first check whether the content of 'sharePage' is not null or empty.
		String str = getH5Page(map);
		if(str == null || str.isEmpty()){
			return true;
		}
		
		// check num
		int num1 = 0;
		int num2 = 0;
		num1 = getJoinedNum(map);

		if (num1 == 0) {
			return false;
		}

		// parse file
		if (str != null && !str.isEmpty()) {
			num2 = H5Utils.parseH5FileName(str);
		}

		if (num1 == num2) {
			return false;
		} else {
			return true;
		}

	}


	private static String getH5Page(Map<String, Object> map) {
		return (String) map.get("sharePage");
	}

	private static int getJoinedNum(Map<String, Object> map) {
		return (Integer) map.get("personJoinedNum");
	}

}
