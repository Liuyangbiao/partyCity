package com.borui.third.qiniu.api;


import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import com.borui.third.qiniu.util.UploadUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

public class QiNiuUploadAPI {
	private static Logger log = Logger.getLogger(QiNiuUploadAPI.class);
	
    @SuppressWarnings("finally")
	public static void uploadFile(String key, byte[] data) throws FileUploadException {
        assert (data != null && key != null);

        String token = UploadUtil.uploadToken();
        Response resp = null;
        try {
        	UploadManager uploadManager = new UploadManager();
        	
        	log.info("start to upload file:" + token);
        	resp = uploadManager.put(data, key, token);
        	
        	if (!resp.isOK()){
        		log.info("resp:" + resp + " body:" + resp.bodyString());
        		throw new FileUploadException("upload file failed");
        	}
        	
        } catch (QiniuException e) {
        	resp = e.response;
            log.info("upload file exception:" + resp.toString());
            String str = null;
            try {
            	str = resp.bodyString();
			} catch (QiniuException e1) {
				str = e1.getMessage();
			}finally{
				throw new FileUploadException(str);
			}
        }
    }
}
