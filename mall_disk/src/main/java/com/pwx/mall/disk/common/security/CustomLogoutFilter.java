package com.pwx.mall.disk.common.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Repository;

@Repository("customLogoutFilter")
public class CustomLogoutFilter extends LogoutFilter {
	
	public CustomLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
	
		super(logoutSuccessUrl, handlers);
		
	}
		   
	public CustomLogoutFilter(LogoutSuccessHandler logoutSuccessHandler,
			LogoutHandler[] handlers) {
	
		super(logoutSuccessHandler, handlers);
		
	}
}