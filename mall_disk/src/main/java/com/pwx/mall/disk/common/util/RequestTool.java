package com.pwx.mall.disk.common.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 项目名称：ej 	<br><br>
 * 
 * 类名称：RequestTool 		<br><br>
 * 
 * 创建人：LinApex@163.com 	<br><br>
 * 
 * 创建时间：2014-2-26 下午12:45:04 	<br><br>
 * 
 * 版本：1.0					<br><br>
 * 
 * 功能描述：请求工具
 */
public class RequestTool
{

	private static final String GET = "GET";
	private static final String POST = "POST";

//	private static Log log = Log.getInstance();

	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	private RequestTool()
	{
	}

	public static RequestTool getInstance()
	{
		return LazyHolder.singletonInstatnce;
	}

	public static HttpServletRequest getRequest()
	{
		return requestLocal.get();
	}

	public static void setRequest(HttpServletRequest request)
	{
		requestLocal.set(request);
	}

	public static HttpSession getSession()
	{
		return getRequest().getSession();
	}

	public static String getParameter(String key)
	{
		return getRequest().getParameter(key);
	}

	public static String[] getParameterValues(String key)
	{
		return getRequest().getParameterValues(key);
	}

	public static String getURI()
	{
		return getRequest().getRequestURI();
	}

	public static String getContextPath()
	{
		return getRequest().getContextPath();
	}

	public static void redirect(String uri) throws IOException
	{
		getResponse().sendRedirect(uri);
	}

	public void forward(String uri) throws ServletException, IOException
	{
		//		RequestDispatcher rd = getRequest().getServletContext().getRequestDispatcher(uri);
		RequestDispatcher rd = getRequest().getSession().getServletContext().getRequestDispatcher(uri);
		rd.forward(getRequest(), getResponse());
	}


	public static HttpServletResponse getResponse()
	{
		return responseLocal.get();
	}

	public static void setResponse(HttpServletResponse response)
	{
		responseLocal.set(response);
	}

	/**
	 * 
	 * TODO 输出Json.
	 * @param o
	 */
	public static void writeJson(Object o)
	{
		try
		{
			String json = JsonTool.toJson(o);
//			log.info("输出JSON：:0", json);
			RequestTool.getResponse().setContentType("text/html;charset=UTF-8");
			if (isAjaxRequest())
			{
				RequestTool.getResponse().setContentType("application/json;charset=UTF-8");
			}
			RequestTool.getResponse().getWriter().write(json);
			RequestTool.getResponse().getWriter().flush();
		} catch (Exception e)
		{
//			log.error(e.getMessage(), e);
		}
	}
	
	public static void writeJsonList(Object o)
    {
        try
        {
            String json = JSONObject.toJSONString(o);
//          log.info("输出JSON：:0", json);
            RequestTool.getResponse().setContentType("text/html;charset=UTF-8");
            if (isAjaxRequest())
            {
                RequestTool.getResponse().setContentType("application/json;charset=UTF-8");
            }
            RequestTool.getResponse().getWriter().write(json);
            RequestTool.getResponse().getWriter().flush();
        } catch (Exception e)
        {
            e.printStackTrace();
//          log.error(e.getMessage(), e);
        }
    }

	/**
	 * 
	 * TODO 功能描述：输出内容.
	 * @param o
	 */
	public static void write(String o)
	{
		try
		{
			RequestTool.getResponse().setContentType("text/html;charset=UTF-8");
			if (isAjaxRequest())
			{
				RequestTool.getResponse().setContentType("application/json;charset=UTF-8");
			}
			RequestTool.getResponse().getWriter().write(o);
			RequestTool.getResponse().getWriter().flush();
		} catch (Exception e)
		{
//			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * TODO 当前请求是否是ajax请求.
	 * @return
	 */
	public static boolean isAjaxRequest()
	{
		String header = getRequest().getHeader("X-Requested-With");

		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

	public static boolean isPostRequest()
	{
		return getRequest().getMethod().equals(POST);
	}

	public static boolean isGetRequest()
	{
		return getRequest().getMethod().equals(GET);
	}

	public static boolean isAjaxRequest(HttpServletRequest request)
	{
		String header = request.getHeader("X-Requested-With");

		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}

	public static String getIpAddr()
	{
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1)
		{
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		//如果是本机，则取局域网地址
		if (ip == null || ip.trim().equals("0:0:0:0:0:0:0:1") || ip.trim().equals("127.0.0.1"))
		{
			try
			{
				InetAddress inet = InetAddress.getLocalHost();
				ip = inet.getHostAddress();
			} catch (Exception e)
			{
//				log.error(e.getMessage(), e);
				ip = "127.0.0.1";
			}
		}
		return ip;
	}

	public static RequestTool setAttribute(String key, Object value)
	{
		getRequest().setAttribute(key, value);
		return getInstance();
	}

	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(String key)
	{
		Object o = getRequest().getAttribute(key);
		return o == null ? null : (T) o;
	}

	public static RequestTool removeAttribute(String key)
	{
		getRequest().removeAttribute(key);
		return getInstance();
	}

	public static Object getRequestController()
	{
		return getAttribute("ej_controller");
	}

	public static Method getRequestMethod()
	{
		Method method = (Method) getRequest().getAttribute("ej_method");
		return method == null ? null : method;
	}

	//	public static MapBean getMapBean(Object... objects)
	//	{
	//		MapBean mapBean = getAttribute("mapBean");
	//		if (mapBean == null)
	//			mapBean = new MapBean(objects);
	//		
	//		return pagedTool;
	//	}

	@SuppressWarnings("unchecked")
	public static <T> T getBySession(String name)
	{
		Object o = getRequest().getSession().getAttribute(name);
		return o == null ? null : (T) o;
	}

	public static void setBySession(String name, Object value)
	{
		getRequest().getSession().setAttribute(name, value);
	}

	public static void addCookie(String name, String value, int maxAge)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		getResponse().addCookie(cookie);
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(String name)
	{
		Map<String, Cookie> cookieMap = ReadCookieMap(getRequest());
		if (cookieMap.containsKey(name))
		{
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else
		{
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request)
	{
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies)
		{
			for (Cookie cookie : cookies)
			{
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	public static void clearAll()
	{
		RequestTool.setRequest(null);
		RequestTool.setResponse(null);
	}

	private static class LazyHolder
	{
		private static final RequestTool singletonInstatnce = new RequestTool();
	}

}
