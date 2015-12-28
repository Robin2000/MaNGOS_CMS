package com.ficus.db;

public final class TableInfo {
	private String DSN;
	private String name;
	private int count;
	private String comment;
	public TableInfo(String DSN,String name){
		this.DSN=DSN;
		this.name=name;
	}

	public String getDSN() {
		return DSN;
	}

	public void setDSN(String dSN) {
		DSN = dSN;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
