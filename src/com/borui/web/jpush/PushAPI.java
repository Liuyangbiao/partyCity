package com.borui.web.jpush;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.borui.utils.StringUtil;
import com.borui.web.mapper.AddressMapper;
import com.borui.web.mapper.MessageMapper;
import com.borui.web.mapper.ScheduleMapper;
import com.borui.web.mapper.UserMapper;

public class PushAPI {
	private Logger log = Logger.getLogger(PushAPI.class);

	private static PushAPI instance = null;

	public static PushAPI getInstance() {
		if (instance == null) {
			instance = new PushAPI();
		}

		return instance;
	}

	/*
	 * to push party-create message to target users
	 */
	public <T> void pushCreationMessage(Map<String, Object> param,
			UserMapper<T> userMapper, AddressMapper addressMapper,
			ScheduleMapper<T> scheduleMapper, MessageMapper messageMapper) throws Exception {
		// if start time is 0-8 in the morning, will not push msg.
		Long start = Long.parseLong(param.get("start").toString());

		// get the clock data from start
		Date date = new Date(start);
		int startClockHour = date.getHours();

		if (startClockHour >= 0 && startClockHour < 8) {
			log.info("morning time, no need to push message");
			return;
		}

		String userId = param.get("owner").toString();// owner

		String inviteStr = param.get("inviteobject").toString();
		if (StringUtil.strIsObjNotNull(inviteStr)) {
			Map<String, Object> pushMap = JPushUtils.getMap();

			Object title = param.get("name");
			// fill easily accessed data
			pushMap.put("msgType", 1);
			pushMap.put("title", title);
			pushMap.put("pid", param.get("pid"));

			// need to get contact list
			List<String> receiverList = null;
			if (inviteStr.equalsIgnoreCase("SB")) {
				// contacts--find all contacts of the user

				List<Map<String, Object>> rel = addressMapper
						.getContactList(userId);

				if (null != rel && rel.size() > 0) {
					receiverList = new ArrayList<String>();

					for (Map<String, Object> m : rel) {
						receiverList.add(m.get("who").toString());
					}
				}

			} else {
				// industry--find all contacts with the same industries
				// first get industries' id from format of "1-10, 2-20, 3-10".

				Map<String, Object> tmp = new HashMap<String, Object>();
				String industries = JPushUtils.getPartyIndustries(inviteStr);
				tmp.put("industries", industries);

				// get user info
				List<Map<String, Object>> rel = userMapper
						.searchByIndustry(tmp);
				receiverList = JPushUtils.getUserIdList(rel, userId);
			}

			if (receiverList == null || receiverList.size() < 1) {
				log.info("no target user when push message");
				return;
			}

			// filter users who are busy.
			// if the start time of the party is inside one of the scheduler of
			// the user
			// then should not push party creation message

			List<String> filteredReceiverList = new ArrayList<String>();
			for (int i = 0; i < receiverList.size(); i++) {
				String targetUser = receiverList.get(i);
				if (checkUserFreeAccordingSchedule(targetUser, date,
						start, scheduleMapper)) {
					filteredReceiverList.add(JPushUtils.userTagPrefix
							+ targetUser);
				} else {
					continue;
				}
			}

			if (filteredReceiverList != null && filteredReceiverList.size() > 0) {
				// first
				pushMap.put("receivers", filteredReceiverList);
				JPushManager.pushMessage(pushMap);
				
				for(String str: filteredReceiverList){

					param = new HashMap<String, Object>();
					param.put("userId", str);
					param.put("info", title);
					param.put("type", 1);
					
					messageMapper.insert(param);
				}
			}

		}
	}

	private <T> boolean checkUserFreeAccordingSchedule(String targetUser, Date date, Long start,
			ScheduleMapper<T> scheduleMapper) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		boolean findFreeTime = false;

		param.put("owner", targetUser);
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String now = sdf.format(date);
			now = now.split(" ")[0];
			long startTime = sdf.parse(now + " 08:00:00").getTime();
			long endTime = sdf.parse(now + " 23:59:59").getTime();
			param.put("start", startTime);
			param.put("end", endTime);
		}

		List<Map<String, Object>> rel = scheduleMapper.search(param);

		if (null != rel && rel.size() > 0) {
			Long startTmp, endTmp;

			for (Map<String, Object> m : rel) {
				startTmp = Long.parseLong(m.get("start").toString());
				endTmp = Long.parseLong(m.get("end").toString());
				if (start >= startTmp && start < endTmp) {
					findFreeTime = false;
					break;
				} else {
					findFreeTime = true;
					continue;
				}
			}
		}

		return findFreeTime;
	}

	/*
	 * to push party-modify message to target users
	 */
	public void pushModifyMessage(Map<String, Object> pushMap) {
		pushMap.put("msgType", 2);
		JPushManager.pushMessage(pushMap);
	}

	/*
	 * to push party-cancel message to target users
	 */
	public void pushCancelMessage(Map<String, Object> pushMap) {
		pushMap.put("msgType", 3);
		JPushManager.pushMessage(pushMap);
	}

	/*
	 * to push party-alert message to target users
	 */
	public void pushPartyAlertMessage(Map<String, Object> pushMap) {
		pushMap.put("msgType", 4);
		JPushManager.pushMessage(pushMap);
	}

	/*
	 * to push task-alert message to target users
	 */
	public void pushTaskAlertMessage(Map<String, Object> pushMap) {
		pushMap.put("msgType", 5);
		JPushManager.pushMessage(pushMap);
	}
	
	/*
	 * to push verifyUserInfo message to target users
	 */
	public void verifyUserInfo(Map<String, Object> pushMap) {
		pushMap.put("msgType", 6);
		JPushManager.pushMessage(pushMap);
	}
}
