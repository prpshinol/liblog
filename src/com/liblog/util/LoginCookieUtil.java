package com.liblog.util;

import com.liblog.entity.User;
import com.liblog.service.IUserService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 自动登陆cookie工具类
 * 
 * @author Lenovo_pc
 * 
 */
public class LoginCookieUtil {

	// 保存cookie时的cookieName
	private final static String LOGIN_COOKIE_KEY = "userLogin";
	// 加密cookie时的网站自定码
	private final static String WEB_KEY = "abc123";
	// 设置cookie有效期,以秒为单位,默认一周
	private final static long COOKIE_MAX_AGE = 60 * 60 * 24 * 7;

	/**
	 * 保存Cookie
	 * 
	 * @param user
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public static void saveCookie(User user, HttpServletResponse response) {
		
			try {
				// cookie的有效期
				long validTime = System.currentTimeMillis()
						+ (COOKIE_MAX_AGE * 1000);
				// MD5加密用户详细信息
				String cookieValueWithMd5 = MD5Util.getMD5(user.getUsername() + ":"
						+ user.getPassword()

						+ ":" + validTime + ":" + WEB_KEY);
				// 将要被保存的完整的Cookie值
				System.out.println(user.getUsername());
				String cookieValue = user.getUsername() + ":" + validTime + ":"
						+ cookieValueWithMd5;
				// 再一次对Cookie的值进行BASE64编码

				String cookieValueBase64 = Base64.encode(cookieValue.getBytes());
				String value2=new String(Base64.decode(cookieValueBase64));
				// 开始保存Cookie
				Cookie cookie = new Cookie(LOGIN_COOKIE_KEY, URLEncoder.encode(cookieValueBase64,"UTF-8"));
				// 存两年(这个值应该大于或等于validTime)
				cookie.setMaxAge((int) COOKIE_MAX_AGE);

				// cookie有效路径是网站根目录

				cookie.setPath("/");

				// 向客户端写入
				response.addCookie(cookie);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException("设置cookie出错！");
			}
		

	}

	public static boolean readCookieAndLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 根据cookieName取cookieValue
			Cookie cookies[] = request.getCookies();
			String cookieValue = null;
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (LOGIN_COOKIE_KEY.equals(cookies[i].getName())) {
						cookieValue = URLDecoder.decode(cookies[i].getValue(),"utf-8");
						break;
					}

				}

			}
			// 如果cookieValue为空,返回,
			if (cookieValue == null) {
				return false;
			}
			// 如果cookieValue不为空,才执行下面的代码
			// 先得到的CookieValue进行Base64解码
			String cookieValueAfterDecode = new String(
					Base64.decode(cookieValue));
			// 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
			String cookieValues[] = cookieValueAfterDecode.split(":");
			if (cookieValues.length != 3) {
				return false;
			}
			// 判断是否在有效期内,过期就删除Cookie
			long validTimeInCookie = new Long(cookieValues[1]);
			if (validTimeInCookie < System.currentTimeMillis()) {
				// 删除Cookie
				clearCookie(response);
				return false;
			}
			// 取出cookie中的用户名,并到数据库中检查这个用户名,
			String username = cookieValues[0];

			// 根据用户名到数据库中检查用户是否存在
			IUserService userService = ServiceUtil.getUserService();
			User user = userService.findByUsername(username);

			// 如果user返回不为空,就取出密码,使用用户名+密码+有效时间+ webSiteKey进行MD5加密
			if (user != null) {
				String md5ValueInCookie = cookieValues[2];
				String md5ValueFromUser = MD5Util.getMD5(user.getUsername()
						+ ":" + user.getPassword() + ":" + validTimeInCookie
						+ ":" + WEB_KEY);
				// 将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
				if (md5ValueFromUser.equals(md5ValueInCookie)) {
					HttpSession session = request.getSession(true);
					session.setAttribute("loginInfo", user);
					return true;
				} else {
					return false;
				}

			} else {

				return false;

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自动登陆异常！");
		}

	}

	// 用户注销时,清除Cookie,在需要时可随时调用-----------------------------------------------------
	public static void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(LOGIN_COOKIE_KEY, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
