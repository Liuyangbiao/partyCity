package com.borui.third.h5;

import java.text.SimpleDateFormat;


public class H5Config {
	// construct test data for testing
	public static boolean testFlag = true;
	
	//
    public static final String H5_SERVER_IP_ADDR = "123.56.90.253";//"www.mumubar.com";服务器地址
    public static final int H5_SERVER_PORT = 12345;//服务器端口号
	
	//
	public static String resouceRelativePath = "../" + "resources" + "/"; 
	
	// new html file name
	public static String dateFlag = "_d_j_n_";// date joined number
	public static String splitter = "_";
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public static String suffix = ".html";
	
	//
	public static int sex_male = 1;// if not "1", means girl.
	public static int sex_female = 2;
	public static int bonus_type = 6;// support 6 kind of bonus by now.
	
	// max number of user' image to show
	public static int maxJoinedShowNum = 7;	
}
