package com.apps.utils;

import java.util.Arrays;
import java.util.List;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mysql.fabric.xmlrpc.base.Array;
/**
 * 代理工具类：动态代理产生Dao层接口的实现对象
 * @author tancheng
 *
 */
public class ProxyDaoFactory {
	
	private String path;
	private QueryRunner qr;
	private Map<String, SQLInfo> sqlMap;
	
	public ProxyDaoFactory(String path) {
		this.path = path;
		qr = new QueryRunner(JDBCUtil.getDataSource(path));
		sqlMap = SQLUtil.getSQLMap(path);
	}
	
	/**
	 * 根据传入的接口的Class对象产生实现对象
	 * @param clazz
	 * @return
	 */
	public Object getProxy(Class clazz) {
		return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				// 打印参数
				String argsStr = Arrays.toString(args);
				System.out.println(argsStr);	
				// 获取方法名称
				String methodName = method.getName();
				System.out.println(methodName);
				// 获取方法的返回值类型
				Class rtType = method.getReturnType();
				// 从sqlMap中取出SQL配置信息
				SQLInfo sqlInfo = sqlMap.get(methodName);
				// 取出标签名称
				String tagName = sqlInfo.getTagName();
				// 取出SQL语句
				String sql = sqlInfo.getSql();
				
				// 判断是什么数据库操作
				if("select".equals(tagName)) {
					// 取出实体字节码对象
					Class rsType = sqlInfo.getResultType();
					ResultSetHandler rshd = new BeanHandler<>(rsType);
					if(rtType == List.class) {
						rshd = new BeanListHandler<>(rsType);
					} 
					return qr.query(sql, rshd, args);
				} else {
					// 执行增删该方法时，对args进行处理
					// 如果update标签有配置parameterType属性指定数据封装的实体类
					// 就对Object[] args参数数组进行处理，否则不处理
					Class<?> paramType = sqlInfo.getParamType();
					if(paramType != null) {
						// 将SQL语句中 #{属性}按照顺序获取到并获取到属性名
						String attrStr = MyStringUtil.getContentInfo(sql);
						String[] attrs = attrStr.split(","); // {"uname", "age", "address"}
						// 通过反射获取到数据实体Bean的对应的get方法，并将
						// 数据存入数组
						Object[] params = new Object[attrs.length];
						for(int i=0; i<attrs.length; i++) {
							String getMethodName = MyStringUtil.retGetMethodName(attrs[i]);
							// 找实体类中的中的get方法
							Method getMethod = paramType.getMethod(getMethodName, null);
							Object result = getMethod.invoke(args[0], null);
							params[i] = result;
						}
						// 将占位符替换为 ?
						sql = sql.replaceAll("\\#\\{([^}]*)\\}", "?");
						return qr.update(sql, params);
					}

					return qr.update(sql, args);
				}
				
			}
		});
	}
	
}
