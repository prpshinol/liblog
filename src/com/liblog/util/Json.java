package com.liblog.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Json implements Serializable{
	private Object data;
	private boolean status;
	private String msg;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
