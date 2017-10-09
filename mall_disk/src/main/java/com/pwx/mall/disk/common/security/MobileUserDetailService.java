package com.pwx.mall.disk.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.pwx.mall.disk.common.util.MD5Util;
import com.pwx.mall.disk.common.util.MsgJson;
import com.pwx.mall.disk.user.entity.Roles;
import com.pwx.mall.disk.user.entity.Users;
import com.pwx.mall.disk.user.service.IUserService;

@Repository("MobileUserDetailService")
public class MobileUserDetailService implements UserDetailsService {
	@Resource
	private IUserService userService;

//	@Resource
//	private IMobileFreeLoginService mobileFreeLoginService;

	// @Resource
	// private IChildAccountService childAccountService;

	@Resource
	private HttpServletRequest request;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		UserDetails user = null;
		String rootPath = request.getParameter("rootPath");
		String password = request.getParameter("password");
		String password1 = request.getParameter("password");
		String loginName = request.getParameter("loginName");
		// 注册成功直接登录效验
		String md5Username = username;
		String registerStatus = (String) request
				.getParameter("register_Return_Status");
		// 密码加密验证
		try {
			if (registerStatus != null && registerStatus.equals("fast")) {

			} else {
				password = MD5Util.md5Encode(password);
			}
			md5Username = MD5Util.md5Encode(username);

		} catch (Exception e) {
			request.getSession().setAttribute("ErrorMessage", "用户名或密码不存在");
			return user;
		}

		String rootPathUrl = "";

//		if ("1".equals(rootPath)) {
//			ApplyPaychannel applyPayChannel = new ApplyPaychannel();
//
//			applyPayChannel.setLoginName(username);
//			applyPayChannel.setPaychannelId("2");
//			applyPayChannel.setChannelPaytypeId("2");
//			MsgJson msgJson = payChannelService
//					.checkPayChannelStatus(applyPayChannel);
//			String msgCode = msgJson.getMsgCode();
//			String path = request.getContextPath();
//			if (msgCode == "0")
//				rootPathUrl = path + "/users/toOpenQrcodePay.do";
//			else if (msgCode == "1")
//				rootPathUrl = path + "/users/successForWait.do";
//			else if (msgCode == "2")
//				rootPathUrl = path + "/main/toQrcodePay.do";
//			else if (msgCode == "3")
//				rootPathUrl = path + "/users/successForWait.do";
//		}

		// ChildAccount childAccount =
		// childAccountService.findChildAccountById(null,username);
		// 获取用户
		// Users users = userService.findUserByuserName(username,password);
		Users users = userService.findUserByuserName(username);

		/*
		 * if(childAccount!=null){ if("1".equals(childAccount.getStatus())){
		 * request.getSession().setAttribute("ErrorMessage", "该账号已被锁定"); return
		 * null; }else{ request.getSession().setAttribute("CHILD_ACCOUNT_INFO",
		 * childAccount); } }
		 */
		if (users != null) {
			if (password.equals(users.getPassword())) {
				request.getSession().setAttribute("user", users);
				try {
					user = new org.springframework.security.core.userdetails.User(
							username, users.getPassword(), true, true, true,
							true, findUserAuthorities(users));
					if (user.getAuthorities().size() > 0) {
						request.getSession().setAttribute("rootPathUrl",
								rootPathUrl);
						request.getSession().removeAttribute("ErrorMessage");
					} else {
						request.getSession().setAttribute("ErrorMessage",
								"抱歉,您没有权限进入");
					}
					Cookie[] cookie = request.getCookies();
					String openId = null;
					if (cookie != null) {
						for (int i = 0; i < cookie.length; i++) {
							Cookie cook = cookie[i];
							if (cook.getName().equalsIgnoreCase("LOGIN_ID")) { // 获取键
								openId = cook.getValue().toString();
							}
						}
					}
					// 得到openid和 用户名及密码
//					if (StringUtils.isNotBlank(openId)) {
//						MobileFreeLogin mobileFreeLogin = mobileFreeLoginService
//								.getFreeLogin(null, users.getId());
//						if (null == mobileFreeLogin) {
//							MobileFreeLogin mfl = new MobileFreeLogin();
//							mfl.setPassword(password1);
//							mfl.setUsername(username);
//							mfl.setCreateDate(new Date());
//							mfl.setUserId(users.getId());
//							mfl.setOpenId(openId);
//							mobileFreeLoginService.saveFreeLogin(mfl);
//						} else if (!mobileFreeLogin.getOpenId().equals(openId)) {
//							mobileFreeLogin.setOpenId(openId);
//							mobileFreeLogin.setUserId(users.getId());
//							mobileFreeLoginService
//									.updateFreeLogin(mobileFreeLogin);
//						}
//					}
				} catch (Exception e) {
					request.getSession().setAttribute("ErrorMessage", "系统异常");
					e.printStackTrace();
				}
			} else if (registerStatus != null && registerStatus.equals("fast")) {
				request.getSession().setAttribute("user", users);
				try {
					user = new org.springframework.security.core.userdetails.User(
							username, users.getPassword(), true, true, true,
							true, findUserAuthorities(users));
					if (user.getAuthorities().size() > 0) {
						request.getSession().setAttribute("rootPathUrl",
								rootPathUrl);
						request.getSession().removeAttribute("ErrorMessage");
					} else {
						request.getSession().setAttribute("ErrorMessage",
								"抱歉,您没有权限进入");
					}
				} catch (Exception e) {
					request.getSession().setAttribute("ErrorMessage", "系统异常");
					e.printStackTrace();
				}
			} else {
				request.getSession().setAttribute("ErrorMessage", "用户名或密码有误");
			}
		} else {
			request.getSession().setAttribute("ErrorMessage", "该号码尚未注册,请先注册");
		}
		return user;
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
		/*
		 * if(autthorities.size()==1){ //最低权限, 为刚注册时的权限 String role =
		 * "ROLE_COMM".toUpperCase();
		 * if(role.equals(autthorities.get(0).toString())){
		 * request.getSession().setAttribute("UserState", "false"); } }else{
		 * request.getSession().setAttribute("UserState", "true"); }
		 */
		return autthorities;
	}

	public static boolean verifyCodeIsTrue(HttpServletRequest request) {
		// 验证码生成时 存放的session -->validateCode
		String validateC = (String) request.getSession().getAttribute(
				"validateCode");
		String veryCode = request.getParameter("veryCode");
		validateC = validateC.toUpperCase();
		if (veryCode != null)
			veryCode = veryCode.toUpperCase();

		if (veryCode == null || "".equals(veryCode)) {
			request.getSession().setAttribute("ErrorMessage", "验证码为空");
			return false;
		} else {
			if (validateC.equals(veryCode)) {
				return true;
			} else {
				request.getSession().setAttribute("ErrorMessage", "验证码错误");
				return false;
			}
		}
	}

}
