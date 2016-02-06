package com.ficus.table;

import java.util.ArrayList;

public class ResultBean {
	private String draw;
	private int recordsTotal;
	private int recordsFiltered;
	private Object data[][];
	private String error;
	private ArrayList<AjaxQuerFilterHtmlItem> queryFilters;
	
	
	public ArrayList<AjaxQuerFilterHtmlItem> getQueryFilters() {
		return queryFilters;
	}
	public void setQueryFilters(ArrayList<AjaxQuerFilterHtmlItem> queryFilters) {
		this.queryFilters = queryFilters;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public Object[][] getData() {
		return data;
	}
	public void setData(Object[][] data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
