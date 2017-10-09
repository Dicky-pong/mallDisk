/**
 * Copyright © 2014 Winit Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of Winit Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Winit Corp or an authorized sublicensor.
 */
package com.pwx.mall.disk.common.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <描述这个类>
 * xss攻击拦截
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * bianwc 	1.0  		2016年3月21日 	Created
 *
 * </pre>
 * @since 1.
 */

public class XSSFilter implements Filter {
	private FilterConfig filterConfig = null;
	
	@Override
	public void destroy() {
		this.setFilterConfig(null);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new XssHttpServletRequestWrapper( (HttpServletRequest) request), response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.setFilterConfig(filterConfig);
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

}
