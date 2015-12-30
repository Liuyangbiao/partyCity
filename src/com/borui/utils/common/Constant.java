package com.borui.utils.common;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import cn.jpush.api.JPushClient;

import com.borui.utils.JPushUtil;

public class Constant {

	private static ResourceBundle res = ResourceBundle.getBundle("sysconfig");
	
	//每个约会可以参与兑换积分的人数
	public final static String PAPER_NUM = res.getString("paper_num");
	
	//苹果内购
	public final static String URL_VERIFY = res.getString("url_verify");
	
	//积分兑换规则
	public final static String PAPER_RULE = res.getString("paper_rule");

	//场地类型
	public final static String PALCE_TYPE = res.getString("palce_type");
	
	//审核通过提示信息
	public final static String INFO = res.getString("info");
	
	//苹果内购商品对应编号
	public final static String APPLE_PAPER_RULE = res.getString("apple_paper_rule");
	
	//环信前缀
	public final static String HX_PREFIX = res.getString("hx.prefix");
	
	//已消费后缀
	public final static String AREADY_CONSUME_SUFFIX = res.getString("consume_suffix");
	
	//来自博瑞平台的二维码
	public final static String CONSUME_PREX = res.getString("consume_prex");
	
	//代金券名称
	public final static String PAPER_NAME = res.getString("paper_name");
	
	//行业分类
	public final static String INDUSTRY_NAME = res.getString("industry");
	
	//规则
	public final static String TICKET_RULE = res.getString("ticket_rule");
	
	//屏蔽字
	public final static String CONTAION_KEYWORDS = res.getString("contaionKeywords");
	
	//是否开启趣味测试
	public final static String OPEN_TEST = res.getString("open_test");
	
	//从业人员可查询最近几天的记录
	public final static String DAY = res.getString("day");
	
	//完善信息期限
	public final static String THIRTY = res.getString("thirty");
	
	// 上传根目录地址 http://image.mn606.com/
	public static String UPLOAD_URL = res.getString("upload.url");
	
	public static String UPLOAD_IP = res.getString("upload.ip");

	public static String UPLOAD_PATH = res.getString("upload.path");

	public static String UPLOAD_PIC_SIZE = res.getString("upload.pic.size");
	
	public static String UPLOAD_PIC_LENGTH = res.getString("upload.pic.length");
	
	//推送消息实例化
	public static final JPushClient jpc = new JPushClient(JPushUtil.MASTER_KEY, JPushUtil.APP_KEY);
	
	//地球半径
	private static final double EARTH_RADIUS = 6378137;
	
	/**
	 * 超级管理员常量
	 * 
	 * @author lu
	 * 
	 */
	public static enum SuperAdmin {
		NO(0, "否"), YES(1, "是");
		public int key;
		public String value;

		private SuperAdmin(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static SuperAdmin get(int key) {
			SuperAdmin[] values = SuperAdmin.values();
			for (SuperAdmin object : values) {
				if (object.key == key) {
					return object;
				}
			}
			return null;
		}
	}
	
	/**
	 * 获取最大分辨率尺寸
	 * @author Dio
	 * @return
	 */
	public static int[] getWidthAndHeight(){
		int[] rel = new int[2];
		String[] max = UPLOAD_PIC_SIZE.split(",");
		rel[0] = Integer.parseInt(max[max.length-1].split("_")[0]);
		rel[1] = Integer.parseInt(max[max.length-1].split("_")[1]);
		return rel;
	}
	
	/**
	 * 
	 * 根据经纬度坐标计算两点之间的距离
	 * 
	 * @param lat				目标纬度
	 * @param lng				目标经度
	 * @param ls				结果集
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> getDistance(double lat, double lng, List<Map<String, Object>> ls) throws Exception {
		if(null != ls && ls.size() > 0){
			List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
			double distance = 0l;
			for (Map<String, Object> m : ls) {
				double mapLat = Double.parseDouble(m.get("lat").toString()), mapLng = Double.parseDouble(m.get("lng").toString());
				double getLat = rad(lat) - rad(mapLat), getLng = rad(lng) - rad(mapLng);
				distance = 2 *Math.asin(Math.sqrt(Math.pow(Math.sin(getLat/2), 2) + Math.cos(rad(lat) * Math.cos(rad(mapLat) * Math.pow(Math.sin(getLng/2), 2)))));
				distance = Math.round(distance * EARTH_RADIUS * 10000) / 10000;
				m.put("distance", distance);
				res.add(m);
			}
			return res;
		} else {
			return ls;
		}
	}
	
	/**
	 * 
	 * 根据经纬度坐标计算两点之间的距离
	 * 
	 * @param lat				目标纬度
	 * @param lng				目标经度
	 * @param map				结果集
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getDistanceByLatAndLng(double lat, double lng, Map<String, Object> map) throws Exception {
		if(null != map){
			double distance = 0l;
			double mapLat = Double.parseDouble(map.get("lat").toString()), mapLng = Double.parseDouble(map.get("lng").toString());
			double getLat = rad(lat) - rad(mapLat), getLng = rad(lng) - rad(mapLng);
			distance = 2 *Math.asin(Math.sqrt(Math.pow(Math.sin(getLat/2), 2) + Math.cos(rad(lat) * Math.cos(rad(mapLat) * Math.pow(Math.sin(getLng/2), 2)))));
			distance = Math.round(distance * EARTH_RADIUS * 10000) / 10000;
			map.put("distance", distance);
			return map;
		} else {
			return map;
		}
	}
	
	private static double rad(double d){
		return d*Math.PI/180.0;
	}
	
	/**
	 * 
	 * 检查屏蔽字
	 * 
	 * @param param
	 * @return
	 */
	public static boolean ContaionKeywords(String param){
		String[] contaionKeywords = CONTAION_KEYWORDS.split(",");
		boolean flag = true;
		for(String str: contaionKeywords){
			if(param.indexOf(str) != -1)
				flag = false;
		}
		return flag;
	}
	
	/**
	 * 
	 * 返回https
	 * 
	 * @return
	 */
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
	
	/**
	 * 
	 * 根据指定的参数获取list
	 * 
	 * @param type
	 * @return
	 */
	public static List<Map<String, Object>> getProperties(String[] type){
		List<Map<String, Object>> rel = new ArrayList<Map<String, Object>>();
		for(String str: type){
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", str.split("-")[0]);
			m.put("id", str.split("-")[1]);
			rel.add(m);
		}
		return rel;
	}
	
}