package com.borui.web.exception;

/**
 * 自定义异常类，显示用户已存在
 *
 */
public class UserIsExistException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserIsExistException(String err){
		super(err);
	}
	
	public UserIsExistException(Exception e){
		super(e);
	}
}
