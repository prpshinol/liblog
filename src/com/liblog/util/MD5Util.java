package com.liblog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具栏
 * 
 * @author Lenovo_pc
 * 
 */
public class MD5Util {

	public static String getMD5(String value) {
		String result = null;

		try {

			byte[] valueByte = value.getBytes();

			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(valueByte);

			result = toHex(md.digest());
			return result;

		} catch (NoSuchAlgorithmException e2) {
			throw new RuntimeException("MD5加密出错！");
		}
		
	}

	// 将传递进来的字节数组转换成十六进制的字符串形式并返回
	private static String toHex(byte[] buffer) {

		StringBuffer sb = new StringBuffer(buffer.length * 2);

		for (int i = 0; i < buffer.length; i++) {

			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));

			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));

		}

		return sb.toString();

	}

}
