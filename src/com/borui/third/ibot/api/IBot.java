package com.borui.third.ibot.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.borui.third.ibot.common.HttpUtil;
import com.borui.third.ibot.config.IBotConfig;

/**
 * 小i机器人api
 * 
 * @ClassName: IBot
 *
 */
public class IBot {
	private static Logger log = Logger.getLogger(IBot.class);

	/**
	 * 询问小i机器人，参数:question,userId,platform,type
	 * 
	 * @function: askRobot
	 * @param map
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public static String askRobot(Map<String, Object> map) throws Exception {
		// first ask internal robot
		int leastTime = IBotConfig.IM_MESSAGE_HANDLE_DURATION;
		String result = askInternalRobot(map);
		if (result == null) {
			result = askExternalRobot(map);
			leastTime += IBotConfig.ROBOT_QUERY_DURATION;
		}

		// delay to send back
		int length = result.length();
		int delayTime = length * IBotConfig.INPUT_SPEED - leastTime;
		
		// at least IBotConfig.ROBOT_QUERY_DURATION to respond
		delayTime = (delayTime > IBotConfig.ROBOT_QUERY_DURATION) ? delayTime: IBotConfig.ROBOT_QUERY_DURATION;
		
		if (delayTime > 0){
			delayTime = (int)(1000 * delayTime * IBotConfig.SPEED_RATIO);
			log.info("sleep time:" + delayTime);
			Thread.sleep(delayTime);
		}

		return result;
	}

	private static boolean filterAnswer(String result) {
		if (result == null || result.isEmpty()) {
			return true;
		}

		ArrayList<String> list = IBotConfig.FilterList;
		String str;

		for (int i = 0; i < list.size(); i++) {
			str = list.get(i);
			if (result.contains(str)) {
				log.info("filter the answer:" + result);
				return true;
			}
		}

		return false;
	}

	private static String getRandomAnswer() {
		ArrayList<String> list = IBotConfig.AnswerList;
		int size = list.size();
		if (size < 1) {
			return null;
		}

		int index = new Random().nextInt(size);
		return (String) list.get(index);
	}

	private static String askInternalRobot(Map<String, Object> requestMap) {
		String result = null;

		String question = (String) requestMap.get("question");
		if (question == null || question.isEmpty()) {
			return "";
		}

		Map<String, String> map = IBotConfig.QAMap;
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			if (question.contains(key)) {
				result = (String) entry.getValue();
				
				if (key == IBotConfig.WhoAreYou1 || key == IBotConfig.WhoAreYou2 || key == IBotConfig.WhoAreYou3){
					result = result + requestMap.get("nickname");
				}
				
				log.info("internal robot got the anser:" + result);
				break;
			}
		}

		return result;
	}
	
	private static String askExternalRobot(Map<String, Object> map) throws Exception {
		String result;
		
		map.put("platform", "custom"); // 平台
		map.put("type", "0"); // type,0-普通，1-高级

		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("X-Auth", getXAuth()); // 签名

		result = HttpUtil.httpPost(IBotConfig.IBOT_URL, map, headerMap);

		// filter answer
		if (filterAnswer(result)) {
			result = getRandomAnswer();
		}
		
		return result;
	}

	// 获取签名
	private static String getXAuth() {
		String realm = "xiaoi.com";
		String method = "POST";
		String uri = "/ask.do";
		byte[] b = new byte[20];
		new Random().nextBytes(b);
		String nonce = new String(Hex.encodeHex(b));
		String HA1 = DigestUtils.shaHex(StringUtils.join(new String[] {
				IBotConfig.APP_KEY, realm, IBotConfig.APP_SECRET }, ":"));
		String HA2 = DigestUtils.shaHex(StringUtils.join(new String[] { method,
				uri }, ":"));
		String sign = DigestUtils.shaHex(StringUtils.join(new String[] { HA1,
				nonce, HA2 }, ":"));

		String XAuthStr = "app_key=\"" + IBotConfig.APP_KEY + "\", nonce=\""
				+ nonce + "\", signature=\"" + sign + "\"";

		return XAuthStr;
	}
}
