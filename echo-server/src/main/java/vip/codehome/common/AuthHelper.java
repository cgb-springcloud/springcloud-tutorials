package vip.codehome.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthHelper {
	public static String getToken() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
		Cookie[] cookies=request.getCookies();
		for(Cookie cookie:cookies) {
			if(Constants.TOKEN_KEY.equals(cookie.getName())) return cookie.getValue();
		}
		return "";
	}
}
