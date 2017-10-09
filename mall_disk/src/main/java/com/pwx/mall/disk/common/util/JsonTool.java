package com.pwx.mall.disk.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * <Json 工具类>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年6月18日 	Created
 *
 * </pre>
 * @since 1.
 */
public class JsonTool
{

	private static final SerializeConfig config;

	static
	{
		config = new SerializeConfig();
		config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
	}

	private static final SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
	};

	//	public static String toJson(Object o)
	//	{
	//		if (o == null)
	//			return "";
	//		return JSON.toJSONString(o, config, features);
	//	}

	public static String toJson(Object o, Object... objects)
	{
		String resultJsonStr = "";

		if (o == null)
			return resultJsonStr;

		String format = "yyyy-MM-dd HH:mm:ss";
		if (objects.length == 1)
		{
			format = objects[0].toString();
		}
		return JSON.toJSONStringWithDateFormat(o, format, features);
	}

	//	public static String toJsonDate(Object o, Object... objects)
	//	{
	//		String resultJsonStr = "";
	//		if (o != null)
	//		{
	//			String format = "yyyy-MM-dd HH:mm:ss";
	//			if (objects.length == 1)
	//			{
	//				format = objects[0].toString();
	//			}
	//			resultJsonStr = JSON.toJSONStringWithDateFormat(o, format, features);
	//		}
	//		return resultJsonStr;
	//	}

}
