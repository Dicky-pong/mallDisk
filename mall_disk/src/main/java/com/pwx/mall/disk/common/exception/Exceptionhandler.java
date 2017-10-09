/**
 * Copyright © 2014 Winit Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of Winit Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from Winit Corp or an authorized sublicensor.
 */
package com.pwx.mall.disk.common.exception;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.pwx.mall.disk.common.util.JsonUtil;
import com.pwx.mall.disk.common.util.MsgJson;


/**
 * <描述这个类>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * bianwc 	1.0  		2016年11月15日 	Created
 *
 * </pre>
 * @since 1.
 */

public class Exceptionhandler implements HandlerExceptionResolver{
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		String message = exception.getMessage();
		if (log.isTraceEnabled()) {
			log.error(exception, exception);
		} else {
			log.error(exception);
		}
		
		if(exception instanceof IllegalStateException) {
			return null;
		}
		
		if (isAjax(request)) {
			addPromptMessageWithAjaxException(request, response, message);
			return null;
		}
		
		return new ModelAndView("forward:/errorpages/error.jsp");
	}
	
	private void addPromptMessageWithAjaxException(HttpServletRequest request, HttpServletResponse response, String message) {
		Writer writer = null;
		MsgJson msg = new MsgJson();
		
		msg.setMsg(message);
		msg.setMsgCode("0");
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.toJSONString(msg));
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log.error("io close exception", e);
		} catch (Exception e) {
			log.error("io close exception", e);
		}finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
			}
		}
	}
	
	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

}
