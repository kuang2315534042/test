package com.apps.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SQLUtil {
	
	private static Map<String, SQLInfo> sqlMap = new HashMap<>();
	
	public static Map<String,SQLInfo> getSQLMap(String path) {
		//创建DOM4J解析器
		SAXReader reader = new SAXReader();
		
		try {
			Document document = reader.read(new File(path));
			// 获取根节点
			Element root = document.getRootElement();
			// 获取所有的sqlinfo标签
			Element sqlinfo = root.element("sqlinfo");
			List childs = sqlinfo.elements();
			for (Object o : childs) {
				Element el = (Element) o;
				String name = el.getName();
				Attribute idAttr = el.attribute("id");
				// 获取id属性对应的值：方法名称
				String methodName = idAttr.getValue();
				Class rtTypclazz = null;
				Class paramclazz = null;
				if(name == "select") {
					Attribute rstpAttr = el.attribute("resultType");
					if(rstpAttr != null) {
						String pojoName = rstpAttr.getValue();
						// 获取到要封装的实体类
						rtTypclazz = Class.forName(pojoName);
					}
				} else if(name == "update") {
					Attribute paramAttr = el.attribute("parameterType");
					if(paramAttr != null) {
						String pojoName = paramAttr.getValue();
						// 获取到要封装的实体类
						paramclazz = Class.forName(pojoName);
					}
				}
				// 获取SQL语句
				String sql = el.getText();
				// 将信息封装到Map中
				sqlMap.put(methodName, new SQLInfo(name, rtTypclazz, paramclazz, sql));		
			}
			return sqlMap;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
}
