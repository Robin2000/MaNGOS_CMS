package com.ficus.db;

import java.util.ArrayList;

public final class Column implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// 列所在的表名
	private String tableName = null;

	// 列名
	private String name = null;

	// 列的注释
	private String alias = null;

	// 列的数据类型
	private String dataType = null;
	
	private Boolean dataTypeIsNumber = null;
	
	// 数据长度
	private int length = 0;

	// 是否可空
	private boolean isNullable = true;
	private boolean autoIncrement=false;
	
	
	
	// 在赋值时是否校验值的合法性
	boolean isCheck = false;

	// 显示长度
	private int dispLength = 0; // 列所在的数据源
	private String DSN = null;
	private boolean isReadOnly = false;
	private int scale = 0; // 小数位数
	
	public ArrayList<Object> value_vec = new ArrayList<Object>();//数据
	
	/**
	 * 复制该列的对象
	 */

	public Column cloneMe() {
		Column c = new Column();
		c.setTableName(getTableName());
		c.setName(getName());
		c.setAlias(getAlias());
		c.setDataType(getDataType());
		c.setIsNullable(isNullable());
		c.setLength(getLength());
		return c;
	}

	/**
	 * 得到列的别名
	 */

	public String getAlias() {
		if(alias==null)
			return name;
		return alias;
	}

	/**
	 * 获取列的数据类型
	 */

	public String getDataType() {
		return dataType;
	}

	/**
	 * 得到列的全名
	 */

	public String getFullName() {
		if (tableName.trim().length()==0)
			return name;
		else
			return new StringBuilder(tableName).append('.').append(name).toString();
	}

	/**
	 * 得到数据长度
	 */

	public int getLength() {
		return length;
	}

	/**
	 * 得到列的名称
	 */

	public String getName() {
		return name;
	}

	/**
	 * 得到表名
	 */

	public String getTableName() {
		return tableName;
	}

	/**
	 * 该列是否可空
	 */

	public boolean isNullable() {
		return isNullable;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	/**
	 * 在为列赋值时是否检查值的合法性
	 */

	public void setCheckValue(boolean b) {
		isCheck = b;
	}

	/**
	 * 设置列的别名
	 */

	public void setAlias(String n) {
		alias = n;
	}

	/**
	 * 设置列的数据类型
	 */

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 设置列值是否可空
	 */

	public void setIsNullable(boolean b) {
		isNullable = b;
	}

	/**
	 * 设置列的数据长度
	 */

	public void setLength(int l) {
		length = l;
	}

	/**
	 * 设置列名
	 */

	public void setName(String n) {
		if (n != null)
			name = n.toUpperCase();
	}

	/**
	 * 设置表名
	 */

	public void setTableName(String tableName) {
		if (tableName != null)
			this.tableName = tableName.toUpperCase();
	}

	/**
	 * 字符串表示
	 */

	public String toString() {
		return new StringBuilder(getTableName()).append('.').append(getName()).toString();
	}



	/**
	 * 检查列值的有效性,若列值无效则抛出异常
	 */

	public void checkValue(int row) throws java.sql.SQLException {
		return;
	}

	/**
	 * 设置列的数据类型
	 */

	public int getDispLength() {
		return this.dispLength;
	}

	/**
	 * 设置列的数据类型
	 */

	public String getDSN() {
		return this.DSN;
	}

	/**
	 * 得到小数位数
	 */

	public int getScale() {
		return scale;
	}

	/**
	 * 得到列值
	 */

	public Object getValue(int row) {
		Object value = value_vec.get(row);
		if (value == null)
			return null;
		else if (value instanceof String)
			return value.toString().trim();
		else
			return value;
	}

	/**
	 * 该列是否可修改
	 */

	public boolean isReadOnly() {
		return isReadOnly;
	}

	/**
	 * 设置列的数据类型
	 */

	public void setDispLength(int length) {
		this.dispLength = length;
	}

	/**
	 * 设置列的数据类型
	 */

	public void setDSN(String DSN) {
		this.DSN = DSN;
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	/**
	 * 得到小数位数
	 */

	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * 设置列值
	 */

	public void setValue(int row, Object obj) throws java.sql.SQLException {
		value_vec.set(row, obj);

		if (isCheck)
			checkValue(row);

	}
	public boolean isNumeric(){
		
		if(dataTypeIsNumber==null)
			dataTypeIsNumber= (dataType.indexOf("NUM") > -1 || dataType.indexOf("INT") > -1|| dataType.indexOf("DEC") > -1 || dataType.indexOf("FLOAT") > -1);
		
		return dataTypeIsNumber;
	}
}
