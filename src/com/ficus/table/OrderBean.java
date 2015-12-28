package com.ficus.table;

public final class OrderBean {
	private int orderCol;
	private String orderDesc;
	
	public OrderBean(int orderCol,String orderDesc){
		this.orderCol=orderCol;
		this.orderDesc=orderDesc;
	}
	public int getOrderCol() {
		return orderCol;
	}
	public void setOrderCol(int orderCol) {
		this.orderCol = orderCol;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	
}
