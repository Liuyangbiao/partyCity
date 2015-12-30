package com.borui.web.jpush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.borui.utils.common.Constant;
import com.borui.web.mapper.PartyMapper;


public class JPushUtils {

	public static Map<String, Object> getMap() {
		return new HashMap<String, Object>();
	}

	public static Map<String, Object> getPartyMap(Object id, Object title) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("pid", id);

		return map;
	}

	public static long partyAlertTime = 2 * 60 * 60 * 1000;// 2 hours before timeout
	public static long taskAlertTime = 30 * 60 * 1000;// half an hour before timeout

	public static String userTagPrefix = "pc_";

	public static ArrayList<String> industryList = null;

	public static ArrayList<String> getIndustryList() {
		if (industryList != null) {
			return industryList;
		}

		ArrayList<String> list = null;
		if (null != Constant.INDUSTRY_NAME) {
			list = new ArrayList<String>();

			for (String str : Constant.INDUSTRY_NAME.split("-")) {
				String[] temp = str.split(",");
				list.add(temp[0]);// temp[1] is name.
			}

		}

		return list;
	}

	public static String getPartyIndustries(String industryStr) {
		String industries = "";

		for (String str : industryStr.split(",")) {
			if (industries != "") {
				industries = industries + ",";
			}

			str.trim();
			String[] temp = str.split("-");
			industries = industries + temp[0];// temp[1] is count limit.
		}

		return industries;
	}

	public static ArrayList<String> getUserIdList(List<Map<String, Object>> rel, String userId) {
		if (null != rel && rel.size() > 0) {
			ArrayList<String> receiverList = new ArrayList<String>();
			String user = "";
			for (Map<String, Object> m : rel) {
				user = m.get("pid").toString();
				if(user.equals(userId)){
					continue;
				}
				receiverList.add(user);
			}

			return receiverList;
		}

		return null;
	}

	public static <T> ArrayList<String> getJoinedUserIdList(
			Map<String, Object> param, PartyMapper<T> partyMapper) {
		ArrayList<String> receiverList = null;

		List<Map<String, Object>> rel;
		try {
			rel = partyMapper.searchByFKUser(param);
			// 存在报名人员
			if (null != rel && rel.size() > 0) {
				receiverList = new ArrayList<String>();
				
				for (Map<String, Object> m : rel) {
					receiverList.add(JPushUtils.userTagPrefix
							+ m.get("userId").toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return receiverList;
	}

	
}
