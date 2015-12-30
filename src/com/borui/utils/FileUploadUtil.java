package com.borui.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.borui.third.alioss.api.AliOSS;
import com.borui.third.alioss.config.AliOSSConfig;
import com.borui.third.qiniu.api.QiNiuUploadAPI;
import com.borui.third.qiniu.util.QiNiuConfig;
import com.borui.utils.common.Constance;
import com.borui.utils.common.Constant;

public class FileUploadUtil {

	private static Logger log = Logger.getLogger(FileUploadUtil.class);

	private static final int FILE_SIZE = 16 * 1024;

	private static String image_mode = null;
	private static String video_mode = null;

	// 环境配置文件
	private static ResourceBundle resEv = ResourceBundle.getBundle("environment");
	
	// 环境配置变量
	private static final String ENVIRONMENT_MODE = "evvironment_mode";
	private static final String VIDEO_MODE= "video_mode";

	private static final String OSS = "oss"; //阿里OSS模式匹配串
	private static final String QINIU = "qiniu";

	// 日期转换
	public final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	private static String getIDName(String name){
		String uuId = UUID.randomUUID().toString();
		String extName = name.substring(
				name.lastIndexOf("."));
		// 上传后的文件名
		return uuId + extName;
	}
	
	private static boolean isImageFile(String name){
		for (String contans : Constance.UPLOAD_IMAGE_FILE) {
			if (name.indexOf(contans) > -1) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean isVideoFile(String name){
		for (String contans : Constance.UPLOAD_VIDEO_FILE) {
			if (name.indexOf(contans) > -1) {
				return true;
			}
		}
		
		return false;
	}
	
	private static ByteArrayOutputStream getImageOutputStream(FileItem item) throws IOException{
		String itemName = item.getName();
		String extName = itemName.substring(itemName.lastIndexOf("."));
		BufferedImage image = ImageIO.read(item.getInputStream());

		// 长度超过规定尺寸即压缩
//		if (image.getWidth() > Constant.getWidthAndHeight()[0]
//				|| image.getHeight() > Constant.getWidthAndHeight()[1])
//			// 等比例缩放图片
//			image = Thumbnails
//					.of(item.getInputStream())
//					.size(Constant.getWidthAndHeight()[0],
//							Constant.getWidthAndHeight()[1])
//					.keepAspectRatio(false).asBufferedImage();

		// 根据item流压缩图片文件
		if (image != null) {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ImageIO.write(image, extName.substring(1), byteOut);
			return byteOut;
		} else {
			return null;
		}
	}
	
	/*
	 * new file name will be something like: 20150413/7088757f-5cfd-4bc8-99ff-05bc409179ab.mp4
	 * location of the image:
	 *    baichuan: /userImages/
	 *    oss: "/" + AliOSSConfig.BASE_IMG_FOLDER, that is: /images/
	 *    native or aliyun: /data/appAppTemp/
	 */
	private static void uploadImageFile(FileItem item, String fileName, Map<String, Object> map) throws IOException, FileUploadException{
		Date date = new Date();
		String today = SIMPLE_DATE_FORMAT.format(date);
		map.put("createTime", date);
		String nameWithDate = today + File.separator + fileName;
		
		if(image_mode.equals(OSS)){//阿里OSS模式
//			ByteArrayOutputStream byteOut = getImageOutputStream(item);
//			if (byteOut != null) {
				try {
					
					
					//曲线救国，直接上传文件至会存在问题，需要采用copy的方式
					String url = "/data/" + File.separator + fileName;
					File fi = new File(url);
					createLocalFile(item, fi, true);
					AliOSS.uploadImg(nameWithDate, fi.getPath());
					
					
//					AliOSS.uploadImg(nameWithDate, byteOut);
					
					
					
					nameWithDate =  AliOSSConfig.OSS_HOST + AliOSSConfig.BASE_IMG_FOLDER + nameWithDate;
					map.put("smallImageFile", nameWithDate);
					fi.deleteOnExit();
				} catch (Exception e) {
					log.error("上传图片失败");
					throw new FileUploadException(e.getMessage());
				}
//			}

		}else {// 本地模式和阿里云模式
			map.put("smallImageFile", Constant.UPLOAD_PATH+Constant.UPLOAD_URL + nameWithDate);
			nameWithDate = Constant.UPLOAD_PATH+Constant.UPLOAD_URL + nameWithDate;
			// UPLOAD_PATH:"/data/appAppTemp/", is where to store image
			File fi = new File(nameWithDate);
			createLocalFile(item, fi, true);
		}
	}
	
	/*
	 * new file name will be something like: 20150413/7088757f-5cfd-4bc8-99ff-05bc409179ab.mp4
	 * location of the video:
	 *    oss: /video/
	 *    native or aliyun: /data/appAppTemp/
	 */
	private static void uploadVideoFile(FileItem item, String fileName, Map<String, Object> map) throws IOException, FileUploadException{
		// 文件按日期分隔，防止同一目录下文件过多，性能下降
		Date date = new Date();
		String today = SIMPLE_DATE_FORMAT.format(date);
		String nameWithDate = today + File.separator + fileName;
		
		if(video_mode.equals(OSS)){//阿里OSS模式
			try {
				AliOSS.uploadFile(nameWithDate, item.getOutputStream());
				nameWithDate = AliOSSConfig.OSS_HOST + AliOSSConfig.BASE_VIDEO_FOLDER + nameWithDate;
				map.put("smallVideoFile", nameWithDate);
				map.put("createTime", date);
			} catch (Exception e) {
				log.error("上传文件失败");
				throw new FileUploadException(e.getMessage());
			}
		}else if(video_mode.equals(QINIU)){
			nameWithDate = QiNiuConfig.address + nameWithDate;
			QiNiuUploadAPI.uploadFile(nameWithDate, item.get());
			map.put("smallVideoFile", nameWithDate);
		}else{
			nameWithDate = Constant.UPLOAD_PATH+Constant.UPLOAD_URL + nameWithDate;
			File fi = new File(Constant.UPLOAD_PATH, nameWithDate);
			createLocalFile(item, fi, false);
			map.put("smallVideoFile", nameWithDate);
		}
	}
	
	private static void createLocalFile(FileItem item, File fi, boolean imageFlag) throws IOException, FileUploadException{
		if (!fi.getParentFile().exists()) {
			fi.getParentFile().mkdirs();
		}
		if (!fi.exists()) {
			fi.createNewFile();
		}
		// 写入
		try {
//			if (imageFlag) {
//				BufferedImage image = ImageIO.read(item.getInputStream());
//				
////				 长度超过规定尺寸即压缩
//				if (image.getWidth() > Constant.getWidthAndHeight()[0]
//						|| image.getHeight() > Constant
//								.getWidthAndHeight()[1]){
//					// 等比例缩放图片
//					Thumbnails.of(item.getInputStream())
//					.forceSize(image.getWidth(), image.getHeight())
//					.toFile(fi);
//				}else{
//					item.write(fi);
//				}
//			}else{
				item.write(fi);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileUploadException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> upload(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException, FileUploadException {
		if (null == image_mode || null == video_mode) {
			image_mode = resEv.getString(ENVIRONMENT_MODE);
			video_mode = resEv.getString(VIDEO_MODE);
		}

		// 创建一个通用的多部分解析器
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// factory.setSizeThreshold(1024 * 1024);

		// 创建新的文件上传处理类
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = upload.parseRequest(request); // 处理请求
		Map<String, Object> map = new HashMap<String, Object>();

		String itemName = null;
		String fileName = null;

		for (FileItem item : list) {
			itemName = item.getName();
			if (null == itemName || "".equals(itemName)){
				String name = item.getFieldName();
				String fieldvalue = item.getString("UTF-8");
				map.put(name, fieldvalue);
				continue;
			}
			
			fileName = getIDName(itemName);// 上传后的文件名
			
			// 是否图片
			if (isImageFile(itemName.toLowerCase())) {
				if (item.getSize() > Integer.parseInt(Constant.UPLOAD_PIC_LENGTH) * 1024 * 1024){
					map.put("length", 0);
					return map;
				}
				uploadImageFile(item, fileName, map);
			} else if (isVideoFile(itemName.toLowerCase())){
				uploadVideoFile(item, fileName, map);
			} 
		}
		
		return map;
	}

	public static void upload(File source, File target) {
		OutputStream out = null;
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(source), FILE_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(target),
					FILE_SIZE);
			byte[] image = new byte[FILE_SIZE];
			while (in.read(image) > 0) {
				out.write(image);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException ex) {
			}
		}
	}

}
