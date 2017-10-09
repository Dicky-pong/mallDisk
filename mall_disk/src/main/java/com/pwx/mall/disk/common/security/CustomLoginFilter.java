package com.pwx.mall.disk.common.security;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ReflectionUtils;


public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	MyTokenBasedRememberMeServices myTokenBasedRememberMeServices = new MyTokenBasedRememberMeServices();

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

		// if (!request.getMethod().equals("POST")) {
		// throw new AuthenticationServiceException(
		// "Authentication method not supported: "
		// + request.getMethod());
		// }

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		/*
		 * String regStatus = request.getParameter("register_Return_Status");
		 * String loginName = request.getParameter("loginName");
		 */
		String rememberMe = "1";
		rememberMe = request.getParameter("rememberMe");
		/*
		 * if(null != regStatus && !"".equals(regStatus)){ SendEmail sendEmail =
		 * new SendEmail(false); // 16进制转2进制 byte[] encrytByte =
		 * sendEmail.parseHexStr2Byte(password); byte[] encrytByteName =
		 * sendEmail.parseHexStr2Byte(loginName); // 解密 try { String result =
		 * sendEmail.deCrypt(encrytByte, "123456789"); String name =
		 * sendEmail.deCrypt(encrytByteName, "123456789"); password = result;
		 * username = name; } catch (Exception e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } }
		 */

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		setDetails(request, authRequest);
		if ("0".equals(rememberMe)) {
			request.setAttribute("password", password);
			myTokenBasedRememberMeServices.onLoginSuccess(request, response,
					this.getAuthenticationManager().authenticate(authRequest));
		}

		return this.getAuthenticationManager().authenticate(authRequest);
	}

}
