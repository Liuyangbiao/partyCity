package com.borui.third.ibot.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置类
 * @ClassName: IBotConfig
 *
 */
public class IBotConfig {
	public static final String APP_KEY = "zF7AHNHDNPgX";
	public static final String APP_SECRET = "S7dI18sr9iyUWt97c3y3";
	
	//调用接口
	public static final String IBOT_URL = "http://nlp.xiaoi.com/ask.do";
	
	// to calculate sleep time
	public static int INPUT_SPEED = 1;// 1 character per second.
	public static int ROBOT_QUERY_DURATION = 2;// it takes 2 second for robot to get feedback
	public static int IM_MESSAGE_HANDLE_DURATION = 2;// it take 2 second for IM server to receive the message then send back to client.
	public static double SPEED_RATIO = 0.7;// adjust real effect, make it fater or slower
	
	// to filter some fixed feedback from robot
	public static ArrayList<String> FilterList = new ArrayList<String>();
	static
	{
		FilterList.add("主人还没有给我设置");
		FilterList.add("如果您想查看搜索情况");
		FilterList.add("位置查询服务升级中");
		FilterList.add("http://");
		FilterList.add("暂时无法为您");
	}
	
	// some fixed QA.
	public static String WhoAreYou1 = "你是谁";
	public static String WhoAreYou2 = "你的名字";
	public static String WhoAreYou3 = "你哪位";
	public static Map<String, String> QAMap = new HashMap<String, String>();
	static
	{
		QAMap.put("你好", "你好");
		QAMap.put("hi", "hi");
		QAMap.put("hello", "how are you");
		QAMap.put("你干嘛呢", "没干什么");
		QAMap.put("呵呵", "呵呵");
		QAMap.put("傻瓜", "你才傻");
		QAMap.put("呵呵", "呵呵");
		QAMap.put(WhoAreYou1, "我是");
		QAMap.put(WhoAreYou2, "我是");
		QAMap.put(WhoAreYou3, "我是");
		QAMap.put("忙什么呢", "忙着和你聊天:)");
	}
	
	// default answers
	public static ArrayList<String> AnswerList = new ArrayList<String>();
	static
	{
		AnswerList.add("无语");
		AnswerList.add("现在忙，有空聊");
		AnswerList.add("等会...");
		AnswerList.add("说什么呢");
		AnswerList.add("");
	}
}
