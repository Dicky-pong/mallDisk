package com.pwx.mall.disk.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.util.StringUtils;

import com.pwx.mall.disk.common.util.MD5Util;
import com.pwx.mall.disk.user.entity.Roles;
import com.pwx.mall.disk.user.entity.Users;
import com.pwx.mall.disk.user.service.IUserService;

public class MyTokenBasedRememberMeServices extends AbstractRememberMeServices {

	@Resource
	private IUserService userService;

	public static final int TOKEN_LIFETI_ME = 86400;

	public MyTokenBasedRememberMeServices() {

	}

	public MyTokenBasedRememberMeServices(String key,
			UserDetailsService userDetailsService) {
		super(key, userDetailsService);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication successfulAuthentication) {
		String username = retrieveUserName(successfulAuthentication);
		String password = retrievePassword(successfulAuthentication);

		// If unable to find a username and password, just abort as
		// TokenBasedRememberMeServices is
		// unable to construct a valid token in this case.
		if (!StringUtils.hasLength(username)) {
			logger.debug("Unable to retrieve username");
			return;
		}

		if (!StringUtils.hasLength(password)) {
			UserDetails user = null;
			/*
			 * Users users = userService.findUserByuserName(username);
			 * 
			 * if (users != null) { user = new
			 * org.springframework.security.core.userdetails.User( username,
			 * users.getPassword(), true, true, true, true,
			 * findUserAuthorities(users)); }
			 */

			password = String.valueOf(request.getAttribute("password"));
			try {
				password = MD5Util.md5Encode(password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!StringUtils.hasLength(password)) {
				logger.debug("Unable to obtain password for user: " + username);
				return;
			}
		}

		int tokenLifetime = TOKEN_LIFETI_ME;
		long expiryTime = System.currentTimeMillis();
		// SEC-949
		expiryTime += 1000L * (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);

		String signatureValue = makeTokenSignature(expiryTime, username,
				password);

		setCookie(new String[] { username, Long.toString(expiryTime),
				signatureValue }, tokenLifetime, request, response);

		if (logger.isDebugEnabled()) {
			logger.debug("Added remember-me cookie for user '" + username
					+ "', expiry: '" + new Date(expiryTime) + "'");
		}
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response)
			throws RememberMeAuthenticationException, UsernameNotFoundException {

		if (cookieTokens.length != 3) {
			throw new InvalidCookieException("Cookie token did not contain 3"
					+ " tokens, but contained '" + Arrays.asList(cookieTokens)
					+ "'");
		}

		long tokenExpiryTime;

		try {
			tokenExpiryTime = new Long(cookieTokens[1]).longValue();
		} catch (NumberFormatException nfe) {
			throw new InvalidCookieException(
					"Cookie token[1] did not contain a valid number (contained '"
							+ cookieTokens[1] + "')");
		}

		if (isTokenExpired(tokenExpiryTime)) {
			throw new InvalidCookieException(
					"Cookie token[1] has expired (expired on '"
							+ new Date(tokenExpiryTime)
							+ "'; current time is '" + new Date() + "')");
		}

		// Check the user exists.
		// Defer lookup until after expiry time checked, to possibly avoid
		// expensive database call.
		Users users = userService.findUserByuserName(cookieTokens[0]);
		UserDetails userDetails = null;
		if (users != null) {
			userDetails = new org.springframework.security.core.userdetails.User(
					cookieTokens[0], users.getPassword(), true, true, true,
					true, findUserAuthorities(users));
		}
		request.getSession().setAttribute("ZHLT_USER_INFO", users);

		// Check signature of token matches remaining details.
		// Must do this after user lookup, as we need the DAO-derived password.
		// If efficiency was a major issue, just add in a UserCache
		// implementation,
		// but recall that this method is usually only called once per
		// HttpSession - if the token is valid,
		// it will cause SecurityContextHolder population, whilst if invalid,
		// will cause the cookie to be cancelled.
		String expectedTokenSignature = makeTokenSignature(tokenExpiryTime,
				userDetails.getUsername(), userDetails.getPassword());

		if (!equals(expectedTokenSignature, cookieTokens[2])) {
			throw new InvalidCookieException(
					"Cookie token[2] contained signature '" + cookieTokens[2]
							+ "' but expected '" + expectedTokenSignature + "'");
		}
		return userDetails;
	}

	protected void cancelCookies(HttpServletRequest request,
			HttpServletResponse response) {
		cancelCookie(request, response);
	}

	protected boolean isTokenExpired(long tokenExpiryTime) {
		return tokenExpiryTime < System.currentTimeMillis();
	}

	/**
	 * 
	 * TODO 获取当前用户登录权限信息.
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Collection<GrantedAuthority> findUserAuthorities(Users user) {
		List<GrantedAuthority> autthorities = new ArrayList<GrantedAuthority>();
		List<Roles> roles = user.getRoles();
		for (Roles role : roles) {
			autthorities.add(new GrantedAuthorityImpl(role.getRoleCode()));
		}
		return autthorities;
	}

	protected String makeTokenSignature(long tokenExpiryTime, String username,
			String password) {
		String key = getKey();
		if (key != null && !"".equals(key))
			key = key;
		else
			key = "BUSINESS_UNION_KEY";
		String data = username + ":" + tokenExpiryTime + ":" + password + ":"
				+ key;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(data.getBytes())));
	}

	/**
	 * Constant time comparison to prevent against timing attacks.
	 */
	private static boolean equals(String expected, String actual) {
		byte[] expectedBytes = bytesUtf8(expected);
		byte[] actualBytes = bytesUtf8(actual);
		if (expectedBytes.length != actualBytes.length) {
			return false;
		}

		int result = 0;
		for (int i = 0; i < expectedBytes.length; i++) {
			result |= expectedBytes[i] ^ actualBytes[i];
		}
		return result == 0;
	}

	private static byte[] bytesUtf8(String s) {
		if (s == null) {
			return null;
		}
		return Utf8.encode(s);
	}

	protected String retrieveUserName(Authentication authentication) {
		if (isInstanceOfUserDetails(authentication)) {
			return ((UserDetails) authentication.getPrincipal()).getUsername();
		} else {
			return authentication.getPrincipal().toString();
		}
	}

	protected String retrievePassword(Authentication authentication) {
		if (isInstanceOfUserDetails(authentication)) {
			return ((UserDetails) authentication.getPrincipal()).getPassword();
		} else {
			if (authentication.getCredentials() == null) {
				return null;
			}
			return authentication.getCredentials().toString();
		}
	}

	private boolean isInstanceOfUserDetails(Authentication authentication) {
		return authentication.getPrincipal() instanceof UserDetails;
	}

	protected int calculateLoginLifetime(HttpServletRequest request,
			Authentication authentication) {
		return getTokenValiditySeconds();
	}

}
