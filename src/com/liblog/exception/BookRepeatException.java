package com.liblog.exception;
/**
 * 书（isbn）重复异常
 * @author Lenovo_pc
 *
 */
@SuppressWarnings("serial")
public class BookRepeatException extends Exception {

	public BookRepeatException() {
		super();
	}

	public BookRepeatException(String message, Throwable cause,
                               boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BookRepeatException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookRepeatException(String message) {
		super(message);
	}

	public BookRepeatException(Throwable cause) {
		super(cause);
	}
	
}
