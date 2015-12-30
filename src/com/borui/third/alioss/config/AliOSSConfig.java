package com.borui.third.alioss.config;

/**
 * 阿里云OSS配置文件
 * 
 * @ClassName: AliOSSConfig
 */
public class AliOSSConfig {

	public static final String ACCESS_ID = "ZMufdzopGteKF3QZ"; // OSS用户签名id

	public static final String ACCESS_KEY = "l66EyRsyIAQerRcjfWzWP5g2G9l0iU"; // OSS用户签名秘钥

	public static final String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com/"; // bucket数据中心，为北京
	
	public static final String OSS_HOST = "http://mumubar.cn/"; //阿里云OSS域名地址
	
	public static final String BASE_BUCKET = "mumu-storage"; // 基本bucket

	public static final String BASE_IMG_FOLDER = "images/"; // 图片存储根文件夹

	public static final String BASE_AUDIO_FOLDER = "audio/"; // 音频存储根文件夹
	
	public static final String BASE_VIDEO_FOLDER = "video/"; // 音频存储根文件夹

	public static final String BASE_FILE_FOLDER = "files/"; // 其他文件存储根文件夹

}
