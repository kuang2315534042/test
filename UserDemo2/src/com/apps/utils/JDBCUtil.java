package com.apps.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * 通过解析给定的XML配置文件，可以获取数据源对象
 * @author tancheng
 *
 */
public class JDBCUtil {
	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;
	private static Object dbSource;
	private static DataSource ds;
	
	/**
	 * 解析XML
	 * @param path
	 * @throws Exception
	 */
	private static void parseXML(String path) throws Exception {
		//创建DOM4J解析器
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(path));
		// 获取根节点
		Element root = document.getRootElement();
		// 获取datasource标签
		Element datasource = root.element("datasource");
		// 获取到datasource标签的class属性
		Attribute classAttr = datasource.attribute("class");
		String className = classAttr.getValue();
		// 获取字节码文件
		Class clazz = Class.forName(className);
		// 实例化对象
		dbSource = clazz.newInstance();
		// 获取datasource标签下的所有子元素
		List pros = datasource.elements();
		// 遍历每个property
		for(Object o : pros) {
			Element el = (Element) o;
			// 获取name属性
			Attribute nameAttr = el.attribute("name");
			Attribute valueAttr = el.attribute("value");
			// 获取name属性的值
			String nameValue = nameAttr.getValue();
			/// 
			String methodName = MyStringUtil.retSetMethodName(nameValue);
			// 获取set方法后
			Method method = clazz.getMethod(methodName, String.class);
			// 将value属性的值注入给set方法
			String value = valueAttr.getValue();
			method.invoke(dbSource, value);
		}
	}
	
	
	
	
	public static DataSource getDataSource(String path) {
		try {
			parseXML(path);
		} catch (Exception e) {
			System.err.println("属性不存在:" + e.getMessage());
			return null;
		}
		if(ds == null) {
			ds = (DataSource) dbSource;
		}
		return ds;
	}
}
