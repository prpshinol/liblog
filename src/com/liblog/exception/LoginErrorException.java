package com.liblog.exception;

/**
 * 登陆错误异常
 * @author Lenovo_pc
 *
 */
public class LoginErrorException extends Exception {

	public LoginErrorException() {
		// TODO Auto-generated constructor stub
	}

	public LoginErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public LoginErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginErrorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
