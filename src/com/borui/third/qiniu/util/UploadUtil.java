package com.borui.third.qiniu.util;

import com.qiniu.util.Auth;

public class UploadUtil {
    public static final Auth auth = Auth.create(QiNiuConfig.accessKey, QiNiuConfig.secretKey);

    public static String uploadToken(){
    	return auth.uploadToken(QiNiuConfig.bucket);
    }
}
