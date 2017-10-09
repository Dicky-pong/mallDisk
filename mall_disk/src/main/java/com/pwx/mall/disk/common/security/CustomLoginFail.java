package com.pwx.mall.disk.common.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.pwx.mall.disk.common.util.MD5Util;
import com.pwx.mall.disk.user.entity.Users;
import com.pwx.mall.disk.user.service.IUserService;


public class CustomLoginFail implements AuthenticationFailureHandler {

	@Resource
	private IUserService userService;

	Logger logger = Logger.getLogger(CustomLoginFail.class);

	private String defaultFailureUrl;
	private boolean forwardToDestination = false;
	private boolean allowSessionCreation = true;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@SuppressWarnings("deprecation")
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		// 获取当前失败账户
		Authentication authen = exception.getAuthentication();

		// 获取登录明文密码
		String credentials = authen.getCredentials().toString();
		String pwd = "";
		try {
			pwd = MD5Util.md5Encode(credentials);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 查询用户信息
		Users user = userService.findUserByuserName(authen.getPrincipal()
				.toString(), pwd);
		try {
			/*
			 * if(null != user){ if ("1".equals(user.getState())) { SendEmail
			 * sendEmail = new SendEmail(false); // 加密密码 byte[] credentialsByte
			 * = sendEmail.enCrypt(credentials, "123456789"); String
			 * credentialsEncrypt = sendEmail.parseByte2HexStr(credentialsByte);
			 * 
			 * // 修改跳转路径
			 * setDefaultFailureUrl("/reg/method/toRegStep3.do?loginName=" +
			 * authen.getPrincipal().toString() + "&credentials=" +
			 * credentialsEncrypt); } }else{
			 * setDefaultFailureUrl("/login?error"); }
			 */
			setDefaultFailureUrl("/login/login.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// defaultFailureUrl 默认的认证失败回调URL
		if (defaultFailureUrl == null) {
			logger.debug("No failure URL set, sending 401 Unauthorized error");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Authentication Failed: " + exception.getMessage());
		} else {
			saveException(request, exception);
			if (forwardToDestination) {
				logger.debug("Forwarding to " + defaultFailureUrl);
				request.getRequestDispatcher(defaultFailureUrl).forward(
						request, response);
			} else {
				logger.debug("Redirecting to " + defaultFailureUrl);
				redirectStrategy.sendRedirect(request, response,
						defaultFailureUrl);
			}
		}
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), "'"
				+ defaultFailureUrl + "' is not a valid redirect URL");
		this.defaultFailureUrl = defaultFailureUrl;
	}

	protected final void saveException(HttpServletRequest request,
			AuthenticationException exception) {
		if (forwardToDestination) {
			request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
					exception);
		} else {
			HttpSession session = request.getSession(false);

			if (session != null || allowSessionCreation) {
				request.getSession().setAttribute(
						WebAttributes.AUTHENTICATION_EXCEPTION, exception);
			}
		}
	}

	protected boolean isUseForward() {
		return forwardToDestination;
	}

	/**
	 * If set to <tt>true</tt>, performs a forward to the failure destination
	 * URL instead of a redirect. Defaults to <tt>false</tt>.
	 */
	public void setUseForward(boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

	/**
	 * Allows overriding of the behaviour when redirecting to a target URL.
	 */
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	protected boolean isAllowSessionCreation() {
		return allowSessionCreation;
	}

	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}

}
