package com.borui.test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;

import com.borui.utils.StringUtil;

public class Party {

	String[] character = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z", "#" };

	/**
	 * 1、 没有任何修饰词，所有的都可以访问 2、 public 所有的都可以访问 3、 private 私有的，只有自己和子类可以访问 4、
	 * protected 受保护的，只有自己和指定子类可以调用
	 * 
	 * 
	 * 
	 * 
	 */
	@Test
	public void testEnum() throws ParseException {

		Map<String, List<Map<String, Object>>> charMap = new HashMap<String, List<Map<String, Object>>>();

		for (String str : character) {
			charMap.put(str, new ArrayList<Map<String, Object>>());
		}

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("name", "大白白");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "白白");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "吖吖");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "白白");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "陈醋");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "大大");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "饿饿");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "方法");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "杠杆");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "好好");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "经济界");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "休闲");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "用于");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "zz");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "cz");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "ez");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "周四");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "oo");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "ll");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "qq");

		datas.add(param);

		param = new HashMap<String, Object>();
		param.put("name", "rr");

		datas.add(param);

		for (Map<String, Object> m : datas) {
			if (StringUtil.isEnglish(m.get("name").toString())) {// 英文
				String tempStr = m.get("name").toString().substring(0, 1);// 获取首字母
				if (charMap.containsKey(tempStr.toUpperCase())) {
					List<Map<String, Object>> enLs = charMap.get(tempStr
							.toUpperCase());
					enLs.add(m);
					charMap.put(tempStr.toUpperCase(), enLs);
				} else {
					List<Map<String, Object>> enLs = charMap
							.get(character[character.length]);
					enLs.add(m);
					charMap.put(character[character.length], enLs);
				}
			} else {// 中文
				String tempStr = m.get("name").toString();
				if (charMap.containsKey(converterToFirstSpell(tempStr)
						.substring(0, 1).toUpperCase())) {
					List<Map<String, Object>> enLs = charMap
							.get(converterToFirstSpell(tempStr).substring(0, 1)
									.toUpperCase());
					enLs.add(m);
					charMap.put(converterToFirstSpell(tempStr).substring(0, 1)
							.toUpperCase(), enLs);
				}
			}
		}

		List<List<Map<String, Object>>> ls = new ArrayList<List<Map<String, Object>>>();

		for (String str : character) {
			if (charMap.containsKey(str))
				ls.add(charMap.get(str));
		}

		// 有序输出结果

		System.out
				.println("****************************有序输出结果：****************************");
		for (int i = 0; i < 26; i++) {
			System.out.print("此模块为：" + character[i]);
			if (null != ls.get(i)) {
				for (Map<String, Object> m : ls.get(i)) {
					System.out.println("\t\t\t\t" + m.keySet() + "\t\t\t\t\t\t"
							+ m.get("name"));
				}
			}
			System.out.println("\n");
		}

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		Date tt = new Date(1442419200000l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String now = sdf.format(tt);

		System.out.println(now);

		System.out.println(now.split(" ")[0]);

		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		List<Map<String, Object>> lss = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < 5; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", i);
			m.put("name", "name" + i);
			lss.add(m);
		}

		List<Map<String, Object>> lsss = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hooo", 1);
		map.put("yeaaaa", 0);

		lsss.add(map);

		System.out.println(lss.contains(map.get("hooo")) + "\t"
				+ lss.containsAll(lsss));

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		System.out.println("===============last:" + last);

		for (int i = 0; i < 10; i++) {
			System.out.println(UUID.randomUUID().toString().replace("-", "")
					.substring(0, 10));
		}

	}

	@Test
	public void calVipCode() {
		List<String> rel = new ArrayList<String>();
		for (int i = 1; i < 201; i++) {
			String sql = "INSERT INTO `party`.`vipCode`(`vipCode`) VALUES (";
			String tmp = "'QCPD";
			if(String.valueOf(i).length() == 1){
				tmp += "00"+i+"');";
				sql += tmp;
				rel.add(sql);
			} else if(String.valueOf(i).length() == 2){
				tmp += "0"+i+"');";
				sql += tmp;
				rel.add(sql);
			} else if(String.valueOf(i).length() == 3){
				tmp += i+"');";
				sql += tmp;
				rel.add(sql);
			}
		}
		
		for(String str : rel){
			System.out.println(str);
		}
	}

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变 YjMyYWRmNDJkY2YzZjU1MQ==
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	@Test
	public void cal() {
		String[] abc = { "", "", "", "" };
		System.out.println(abc.length);
	}

	class User {
		int age;
		String name;

		public static final String parent = "mother";

		public User() {

		}

		public User(String name, int age) {
			this.age = age;
			this.name = name;
		}

		public void cal(String now, String birthday) {

		}
	}
}
