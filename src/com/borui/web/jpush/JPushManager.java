package com.borui.web.jpush;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.borui.utils.StringUtil;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushManager {

	private static Logger log = Logger.getLogger(JPushManager.class);

	private static final String APP_KEY = "8707d73aeecbb73d855caf4b";
	private static final String MASTER_SECRET = "c75a42b7e7a326e64980cf9b";
	public static JPushClient jpushClient = new JPushClient(MASTER_SECRET,
			APP_KEY);

	/*
	 * requirement 新建派对推送：对于“通讯录”或者“行业”用户发送通知。
	 * 
	 * 派对修改推送：派对时间、地点、费用，修改后，推送给报名用户。
	 * 
	 * 派对取消：推送给报名用户。
	 * 
	 * 派对开始前推送：活动开始前推送：派对还有2小时就要开始了，推送给报名用户 事项开始前推送：事项还有半小时就要开始了，通知该用户
	 * 
	 * from system to user
	 * 
	 * party: create, modify(time, location, fee), cancel, alert task: alert
	 */

	public static String createMsg = "create";
	public static String modifyMsg = "modify";
	public static String cancelMsg = "cancel";
	public static String partyAlertMsg = "partyAlert";
	public static String taskAlertMsg = "taskAlert";
	public static String msgMsg = "msg";

	enum MESSAGE_TYPE {
		CREATE, MODIFY, CANCEL, PARTYALERT, TASKALERT, MSG
	}

	public static String createContentPrefix = "有一个适合您的派对\"";
	public static String createContentSuffix = "\"，请前往查看。";

	public static String modifyContentPrefix = "您参与的派对\"";
	public static String modifyContentSuffix = "\"有所变动，请尽快查看，以免耽误您的行程。";

	public static String cancelContentPrefix = modifyContentPrefix;
	public static String cancelContentSuffix = "\"，已经取消，请重新安排您的时间。";

	public static String alertContentPrefix = modifyContentPrefix;
	public static String alertContentSuffix = "\"还有2小时就要开始了，请按时参加。";

	public static String taskAlertContentPrefix = "您的事项\"";
	public static String taskAlertContentSuffix = "\"还有半小时就要开始了，请注意您的时间安排。";
	
	public static String msgContentPrefix = "";
	public static String msgContentSuffix = "";

	private static String contentPrefix[] = { createContentPrefix,
			modifyContentPrefix, cancelContentPrefix, alertContentPrefix,
			taskAlertContentPrefix, msgContentPrefix };

	private static String contentSuffix[] = { createContentSuffix,
			modifyContentSuffix, cancelContentSuffix, alertContentSuffix,
			taskAlertContentSuffix, msgContentSuffix };

	private static String getMessageContent(int msgType, String title) {
		StringBuffer buffer = new StringBuffer();

		String prefix = contentPrefix[msgType];
		String suffix = contentSuffix[msgType];

		buffer.append(prefix).append(title).append(suffix);

		return buffer.toString();
	}

	/**
	 * 
	 * <b>根据用户ID推送消息</b>
	 * 
	 * 
	 * <ol>
	 * 必须参数
	 * </ol>
	 * <ol>
	 * content 推送内容
	 * </ol>
	 * <ol>
	 * receiverId 推送给谁
	 * </ol>
	 * 
	 * @param map
	 * @return
	 */

	@SuppressWarnings({ "null", "unchecked" })
	public static boolean pushMessage(Map<String, Object> map) {

		ArrayList<String> receiverList = (ArrayList<String>) map
				.get("receivers");
		if (receiverList == null && receiverList.size() == 0) {
			log.info("no target user to send msg.");
			return false;
		}

		int msgType = Integer.parseInt(map.get("msgType").toString());// 消息类型
		String title = map.get("title").toString();
		String pid = map.get("pid").toString();// 消息或事项id
		if (msgType < 1 || msgType > MESSAGE_TYPE.values().length
				|| StringUtil.strIsObjNull(pid)
				|| StringUtil.strIsObjNull(title)) {
			log.info("Data is not correct when sending out message.");
			return false;
		}

		String msgContent = getMessageContent(msgType - 1, title);// 消息内容
		Long createDate = System.currentTimeMillis();// 发送日期统一由服务器端生成
			
		PushPayload payload = getPayload(msgContent, receiverList, pid, msgType, createDate);

		doSendMsg(payload);

		return true;
	}
	
	private static PushPayload getPayload(String msgContent, ArrayList<String> receiverList, String pid, int msgType, Long createDate){
		PushPayload payload = PushPayload
				.newBuilder()
				.setOptions(
						Options.newBuilder().setApnsProduction(true)
								.build())
				.setPlatform(Platform.all())
				.setAudience(
						Audience.newBuilder()
								.addAudienceTarget(
										AudienceTarget.tag(receiverList)).build())									
				//.setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(msgContent)
                                .setBadge(5)
                                .setSound("sound.caf")
                                .addExtra("createDate", createDate)
								.addExtra("pid", pid)
								.addExtra("msgType", msgType)
                                .build())
                        .build())
				.build();
		
		return payload;
	}
	
	private static boolean doSendMsg(PushPayload payload){
		try {
			PushResult result = jpushClient.sendPush(payload);
			log.info("Got result - " + result);
			return true;

		} catch (APIConnectionException e) {
			log.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			log.error("Should review the error, and fix the request", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}
		
		return false;
	}

}
