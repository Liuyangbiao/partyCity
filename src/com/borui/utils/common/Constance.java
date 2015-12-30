package com.borui.utils.common;

/**
 * 基础响应的操作编码
 * @author yuandong lin
 *
 */
public interface Constance {

	/**--------------------客户端返回状态码----------------------------*/
	
	//操作成功
	public final static String BASE_SUCCESS_CODE = "200";
	
	//操作失败
	public final static String BASE_SERVER_WRONG_CODE = "500";
	
	//暂无数据
	public final static String DATA_IS_EMPTY = "700";
	
	//用户名不存在
	public final static String USERNAME_IS_NOT_EXIST = "1001";

	// 密码错误
	public final static String PASSWORD_IS_WRONG = "1002";

	// 用户名和密码为空
	public final static String PASSWORD_OR_USERNAME_IS_EMPTY = "1003";
	
	//用户已存在
	public final static String USER_IS_EXIST = "1004";
	
	//非法操作
	public final static String ILLEGAL_OPERATE = "1005";
	
	//超出设置图片
	public final static String PHOTO_IS_OUT_OF_RANGE = "1006";
	
	//用户组已存在
	public final static String USER_GROUP_IS_EXIST = "1007";
	
	//该动态已被您点赞
	public final static String YOU_HAS_APPOINT = "1008";

	// 参数错误(转json错误或为空)
	public final static String PARAMETERS_ERROR = "1009";

	// 参数格式错误
	public final static String PARAMETERS_PATTERN_ERROR = "1010";
	
	// 已关注
	public final static String USER_IS_ATTENTION = "1011";
	
	//手机号为空
	public final static String USER_PHONE_IS_NULL = "1012";
	
	//用户不存在
	public final static String USER_IS_NOT_EXIST = "1013";
	
	//必传参数为空
	public final static String PRIMARY_KEY_IS_NULL = "1014";
	
	//读取文件异常
	public final static String IOEXCEPTION = "1015";
	
	//文件上传异常
	public final static String FILEUPLOADEXCEPTION = "1016";
	
	//图片类型为空
	public final static String IMAGE_TYPE_IS_NULL = "1017";
	
	//执行数据库语句出错
	public final static String SQL_IS_ERROR = "1018";
	
	//酒吧不存在
	public final static String BAR_IS_NOT_EXIST = "1019";
	
	//pageNo or pageSize is empty
	public final static String PAGENUMBER_OR_PAGESIZE_IS_NOT_NULL = "1020";

	//已加入该约会
	public final static String IS_JOINED = "1021";
	
	//用户已绑定酒吧
	public final static String USER_IS_BINDED = "1022";
	
	//含有屏蔽字
	public final static String CONTAIN_KEYWORDS = "1023";
	
	//数据为空或者不是所有者
	public final static String DATA_IS_EMPTY_OR_IS_NOT_OWNER = "1024";
	
	//无操作权限
	public final static String NO_PERMISSION = "1025";
	
	//参数类型不能为空
	public final static String TYPE_IS_EMPTY = "1026";
	
	//此人以创建过酒吧
	public final static String BAR_ALREADY_EXIST = "1027";
	
	//报名已截至
	public final static String HAS_BEEN_CUT_OFF ="1028";
	
	//兑换商品不存在
	public static final String COMMODITY_IS_NOT_EXIST = "1029";
	
	//兑换积分不够
	public static final String POINTS_IS_NOT_HAVE = "1030";
	
	//苹果内购验证失败
	public static final String CHECK_APPLE_ERROR = "1031";
	
	//环信修改密码失败
	public static final String MODIFY_HX_ERROR = "1032";
	
	//红包数量不够
	public static final String PAPER_COUNT_CAL = "1033";
	
	//报名次数已达上线
	public static final String JOINED_DATE_IS_FULL = "1034";
	
	//与约会期望对象不符
	public static final String IS_NOT_MEET = "1035";
	
	//密码相同，不可修改
	public static final String PASSWORD_IS_SAME = "1036";
	
	//场地名已存在
	public static final String FILED_NAME_IS_EXIST = "1037";
	
	//已经注销
	public static final String AREADY_OUT = "1038";
	
	//派对不存在
	public static final String PARTY_IS_NOT_EXIST = "1039";
	
	//报名失败
	public static final String JOINED_IS_ERROR = "1040";
	
	//派对修改push到报名人员消息出错
	public static final String PUSH_MSG_ERROR = "1041";
	
	//创建日程失败
	public static final String CREATE_SCHEDULE_ERROR = "1042";
	
	/**
	 * Below is for upload
	 */
	// 上传图片格式
	public final static String[] UPLOAD_IMAGE_FILE = { ".jpg", ".png", ".gif", ".jpeg", ".bmp" };
	
	// 上传视频格式
	public final static String[] UPLOAD_VIDEO_FILE = { ".mp4", ".avi", ".rm",
			".3gp", ".wmv", ".rmvb", ".asf", ".divx", ".mpg", ".mpeg", ".mpe", ".wmv", ".mkv", ".vob" };
	
	// 上传音频格式
	public final static String[] UPLOAD_AUDIO_FILE = { ".mp3", ".wma", ".mid", ".mmf", ".aif", ".aiff", ".au", ".mpa",
		".ra", ".wav"};
	
	/*
	 * Below is for Alipay
	 */
	//支付宝支付成功
	public final static String ALIPAY_FINISHED_CODE = "TRADE_FINISHED";
	
	//支付宝支付失败
	public final static String ALIPAY_SUCCESS_CODE = "TRADE_SUCCESS";
	
	//支付宝取消支付
	public final static String ALIPAY_QUIT_CODE = "6001";
	
	//支付宝消息已处理
	public final static String ALIPAY_HANDLE_CODE = "5000";
	
	//支付宝验证失败
	public final static String ALIPAY_CHECK_WRONG_CODE ="3000";
	
	//不存在纪录
	public final static String NO_PAY_CODE = "4000";
	
	//已消费
	public final static String ALREADY_PAY_CODE = "2000";
	
	//消费码是否是属于该酒吧
	public final static String BAR_IS_NOT_HAVE_CODE = "2500";

}
