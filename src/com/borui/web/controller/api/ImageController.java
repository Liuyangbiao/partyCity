package com.borui.web.controller.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.borui.utils.FileUploadUtil;
import com.borui.utils.JsonPUtil;
import com.borui.utils.common.Constance;
import com.borui.utils.common.ResponseMessageUtil;
import com.borui.web.controller.BaseController;

/**
 * 
 * 上传图片控制器
 * 
 * @author Dio
 *
 */
@SuppressWarnings({"unused", "finally"})
@Controller
@RequestMapping(value="/api/image")
public class ImageController extends BaseController {
	
	private Logger log = Logger.getLogger(ImageController.class);
	
	/**
	 * 
	 * 图片上传异步
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value="/uploadImageAsync")
	@ResponseBody
	public Map<String, Object> uploadImageAsync(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> msg = new HashMap<String, Object>();
		try{
			Map<String, Object> map = FileUploadUtil.upload(request, response);
		} catch(Exception e){
			log.error("api/image/uploadImageAsync.do is error. info:"+e+"\t now is : "+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	/**
	 * 
	 * 图片上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/uploadImage")
	@ResponseBody
	public Map<String, Object> uploadImage(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> msg = new HashMap<String, Object>();
		try{
			Map<String, Object> map = FileUploadUtil.upload(request, response);
			if(!map.isEmpty()){
				if(null == map.get("length")){
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SUCCESS_CODE, "true", map);
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(IOException e){
			log.error("api/image/uploadImage.do is error. info:"+e+"\t now is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.IOEXCEPTION, "false");
		} catch(FileUploadException e){
			log.error("api/image/uploadImage.do is error. info:"+e+"\t now is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.FILEUPLOADEXCEPTION, "false");
		} catch(Exception e){
			log.error("api/image/uploadImage.do is error. info:"+e+"\t now is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 获取图片列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getImageList")
	@ResponseBody
	public Map<String, Object> getImageList(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> msg = new HashMap<String, Object>();
		try{
//			Map<String, Object> map = 
		} catch(Exception e) {
			log.error("api/image/getImageList.do is error. info:"+e+"\t now is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
	
	/**
	 * 
	 * 删除图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteImages")
	@ResponseBody
	public Map<String, Object> deleteImages(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> msg = new HashMap<String, Object>();
		try{
			Map<String, Object> map = JsonPUtil.pToMap(request);
			if(!map.isEmpty()){
				if(null != map.get("type")){
					msg = ResponseMessageUtil.respMsg(Constance.BASE_SUCCESS_CODE, "true");
				} else {
					msg = ResponseMessageUtil.respMsg(Constance.IMAGE_TYPE_IS_NULL, "false");
				}
			} else {
				msg = ResponseMessageUtil.respMsg(Constance.PARAMETERS_ERROR, "false");
			}
		} catch(Exception e){
			log.error("api/images/deleteImages.do is error. info:"+e+"\t now is :"+new Date());
			msg = ResponseMessageUtil.respMsg(Constance.BASE_SERVER_WRONG_CODE, "false");
		}
		finally{
			return msg;
		}
	}
}
