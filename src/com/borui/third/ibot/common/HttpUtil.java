package com.borui.third.ibot.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http操作类
 * 
 * @ClassName: HttpUtil
 *
 */
public class HttpUtil {

	/**
	 * post请求
	 * 
	 * @function: httpPost
	 * @param url
	 * @param paramMap
	 * @param headerMap
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public static String httpPost2(String url, Map<String, Object> paramMap,
			Map<String, Object> headerMap) throws Exception {

		String result = "";
		String params = "";

		PrintWriter out = null;
		BufferedReader in = null;

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();

			// paramMap 处理
			if (paramMap != null && !paramMap.isEmpty()) {
				for (String key : paramMap.keySet()) {
					params += (key + "=" + paramMap.get(key) + "&");
				}
				params = params.substring(0, params.length() - 1);
			}

			// headerMap 处理
			if (headerMap != null && !headerMap.isEmpty()) {
				for (String key : headerMap.keySet()) {
					conn.setRequestProperty(key, (String) headerMap.get(key));
				}
			}

			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 设置 HttpURLConnection的字符编码
			// conn.setRequestProperty("Accept-Charset", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new PrintWriter(conn.getOutputStream());// 获取URLConnection对象对应的输出流

			out.print(params);// 发送请求参数

			out.flush();// flush输出流的缓冲

			in = new BufferedReader(// 定义BufferedReader输入流来读取URL的响应
					new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

			if (result != null && result != "") {
				// String pattern="[\u4e00-\u9fa5]+";
				// Pattern p=Pattern.compile(pattern);
				// Matcher m=p.matcher(result);
				// if(!m.find()){
				// 编码转换，解决中文乱码
				// result = new String(result.getBytes("gbk"),"utf-8");
				// }
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("发送 POST 请求出现异常！" + e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return result;
	}

	/**
	 * post请求
	 * 
	 * @function: httpPost
	 * @param url
	 * @param paramMap
	 * @param headerMap
	 * @return
	 * @throws Exception
	 * @return String
	 */
	public static String httpPost(String url, Map<String, Object> paramMap,
			Map<String, Object> headerMap) throws Exception {

		String result = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			// paramMap 处理
			if (paramMap != null && !paramMap.isEmpty()) {
				for (String key : paramMap.keySet()) {
					nameValuePairs.add(new BasicNameValuePair(key,
							(String) paramMap.get(key)));
				}
			}

			// headerMap 处理
			if (headerMap != null && !headerMap.isEmpty()) {
				for (String key : headerMap.keySet()) {
					httppost.addHeader(key, (String) headerMap.get(key));
				}
			}

			httppost.addHeader("Content-type",
					"application/x-www-form-urlencoded");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			HttpResponse response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				result = EntityUtils.toString(response.getEntity());
			} else {
				String err = response.getStatusLine().getStatusCode() + "";
				throw new Exception("发送 POST 请求失败！" + err);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("发送 POST 请求出现异常！" + e);
		}

		return result;
	}
}
