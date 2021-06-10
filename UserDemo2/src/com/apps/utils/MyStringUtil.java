package com.apps.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStringUtil {
	/**
	 * 将配置的属性名称获取对应的set方法名称
	 * @param attr
	 * @return
	 */
	public static String retSetMethodName(String attr) {
		String firstLetter = attr.substring(0, 1);
		String upperLetter = firstLetter.toUpperCase();
		return "set" + upperLetter + attr.substring(1);
	}
	
	/**
	 * 将配置的属性名称获取对应的get方法名称
	 * @param attr
	 * @return
	 */
	public static String retGetMethodName(String attr) {
		String firstLetter = attr.substring(0, 1);
		String upperLetter = firstLetter.toUpperCase();
		return "get" + upperLetter + attr.substring(1);
	}
	
	// 通过正则表达式获取到#{}内的值
	public static String getContentInfo(String content) {
		Pattern regex = Pattern.compile("\\#\\{([^}]*)\\}");
		Matcher matcher = regex.matcher(content);
		StringBuilder sql = new StringBuilder();
		while(matcher.find()) {
			sql.append(matcher.group(1)+",");
		}
		if (sql.length() > 0) {
			sql.deleteCharAt(sql.length() - 1);
		}
		return sql.toString();
	}
}
