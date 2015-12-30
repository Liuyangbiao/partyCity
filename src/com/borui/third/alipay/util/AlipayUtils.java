package com.borui.third.alipay.util;

import java.util.Map;

public class AlipayUtils {
	

	/**
	 * 验证请求来源
	 * 
	 * @author Dio
	 * @param map
	 * @return
	 */
	public static int checkAlipay(Map<String, String> map, boolean isPay) {
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		if (isPay)
			if (AlipayNotify.verifyByRefund(map)) {// 验证成功
				return 1;
			} else {// 验证失败
				return -1;
			}
		else if (AlipayNotify.verify(map)) {// 验证成功RSA
			return 1;
		} else {// 验证失败
			return -1;
		}
	}

}
