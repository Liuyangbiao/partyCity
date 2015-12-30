package com.borui.third.alipay.config;

import com.borui.third.alipay.sign.Base64;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088711729468081";
	// 商户的私钥
	public static String private_key = "ud2v5bjn582645m2qjsjuu2b5luml1e0";
	
	//收款单位
	public static String SELLER = "borui2014@boruibrothers.com";
	
	public static String ios_private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMUZJgT/I7Lzm2pbvF35PzExKwTW1i0uK94vUAENt+UeY2ausMqTHFkMyMOj6Qt8pzr6adZb4YTHYgN7Hce+Ok3sBDTOfpIq7TCyTO77OsAf6SJfKuJhjlksnu1IaPMzEXE03LNrxH6HUTnIHD3DrnhLVKjy/kwtAuJnTxVeWDf7AgMBAAECgYEAhifX+Q6w/d1Sq9JFJLHCkXkmDuUsYxRKR7Fsg0sTIKDOh9XgAAKiCPrqrD+lIGx4Z2yLHZzso7QSwsvKOWsBw1UX/J4xGcCRkshAQSV2sKso16sftWVMEwKWcms5B9+AJZwdqPfU7eROWPiKhJxE1bzHLHTI3J49IhEdy4feHgECQQD14Y2vox9J7BwlNEw5F2EgaznjuABDj4ebllLsJ4CySMORx0dXQ3S+WMpsjYV9aSJYIKpGsjsAtI8tcQdFGfUlAkEAzTWmcTwXgaC9Wwkn3hlTyPybPLEiuy+2TZT4YGnsTVFHvGhmcoq9D4LrGCPa8b2E9COY9RTfu2aQez7a1FI+nwJBAPDhQzkqCWEWGoyoK3RS3ygvY8sfW8LUPfnCzwHjwUTn3BBYth9bSmef/M9T5c7yzF6hwa74tK0ANrRB4uljgLECQQCqXmJjHGq/mj3bOMy6nfhrox0W1FFrav9Fhep30Tj4MAUIrPxxGDJCkISyNAJwNNIPBwbUYpIlOc+2Isb3A5ktAkAmotiwGNHSM7nMtbtTw87c5/SP6EVa2wmpsYlmG6k18yd+xpoBavPiZAyQ4EUGoxm2si3rFXxAgbtARn/UolsP";
	

	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "/root/";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	public static String refund_sign_type = "MD5";
	
	public static String ENCODE_PRIVATEKEY = new String(Base64.encode(AlipayConfig.ios_private_key.getBytes()));
	
	public static String ENCODE_SELLER = new String(Base64.encode(AlipayConfig.SELLER.getBytes()));
	
	public static String ENCODE_PARTNER = new String(Base64.encode(AlipayConfig.partner.getBytes()));
}
