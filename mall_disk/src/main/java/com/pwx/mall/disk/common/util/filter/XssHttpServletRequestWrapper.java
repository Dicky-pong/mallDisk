/**
 * Copyright © 2014 Winit Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of Winit Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Winit Corp or an authorized sublicensor.
 */
package com.pwx.mall.disk.common.util.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import com.itextpdf.text.html.HtmlUtilities;

/**
 * <描述这个类>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * bianwc 	1.0  		2016年3月21日 	Created
 *
 * </pre>
 * @since 1.
 */

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (null == values) {
			return null;
		}

		String[] encodedValues = new String[values.length];

		for (int i = 0; i < values.length; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}

		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);

		if (null == value) {
			return null;
		}

		return cleanXSS(value);
	}

	@Override
	public String getHeader(String header) {
		String value = super.getHeader(header);

		if (null == value) {
			return null;
		}

		return cleanXSS(value);
	}

	private String cleanXSS(String value) {
		return HtmlUtils.htmlEscape(value);
	}
}
