package com.apps.utils;

public class SQLInfo {
	private Class resultType;
	private Class paramType;
	private String sql;
	private String tagName;

	
	public SQLInfo() {
		super();
	}
	public SQLInfo(String tagName, Class resultType, Class paramType, String sql) {
		super();
		this.tagName = tagName;
		this.resultType = resultType;
		this.paramType = paramType;
		this.sql = sql;
	}

	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Class getResultType() {
		return resultType;
	}
	public void setResultType(Class resultType) {
		this.resultType = resultType;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
	public Class getParamType() {
		return paramType;
	}
	public void setParamType(Class paramType) {
		this.paramType = paramType;
	}
	@Override
	public String toString() {
		return "SQLInfo [resultType=" + resultType + ", paramType=" + paramType
				+ ", sql=" + sql + ", tagName=" + tagName + "]";
	}
	
}
