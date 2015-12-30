package com.borui.third.h5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class H5Utils {


	public static int parseH5FileName(String fileName) {
		H5Utils.h5Trace("parseH5FileName. file name:" + fileName);
		String str = null;
		int pos = fileName.indexOf(H5Config.dateFlag)
				+ H5Config.dateFlag.length();
		if (pos > 0) {
			int startIndex = pos;
			int size = fileName.length();
			char c;
			for (; pos < size; pos++) {
				c = fileName.charAt(pos);
				if (c == '_') {
					break;
				}
			}

			str = fileName.substring(startIndex, pos);
		}

		if (str != null && !str.isEmpty()) {
			return Integer.parseInt(str);
		} else {
			return -1;// failed
		}

	}
	
	public static String formatType = "yyyy-MM-dd HH:mm a";
	
	public static String transformDate(String time)
			throws ParseException {
		String longTime = time;
		
		int pos = time.indexOf(".");
		if (pos > 0){
			longTime = time.substring(0, pos);
		}
		
		long currentTime = Long.parseLong(longTime) * 1000;
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = new SimpleDateFormat(formatType).format(dateOld); // 把date类型的时间转换为string
		return sDateTime;
	}
	
	public static void h5Trace(String str){
		if (H5Config.testFlag){
			System.out.println(str);
		}
	}
	
	public static void main(String args[]){
		String str = null;
		try {
			str = H5Utils.transformDate("1436427120.000000");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(str);
	}
}
