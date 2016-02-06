package com.ficus.table;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.db.Column;
import com.ficus.db.DBCon;

public interface QueryInterface {
	
	public ArrayList<Column> getCols(String table);
	
	public ResultBean query(String table,String start,String length,ResultBean rb,String keyword,List<OrderBean> order,String queryClause) throws Exception;
	
	public ArrayList<Column> getColumns(DBCon con,String table) throws Exception;
	
	public StringBuilder getSql(String table) throws Exception;
	
	public String getQueryHtml(String table);
	
	public String getJavaScript(String table);
	
	public String parseQueryParameter(String table,HttpServletRequest request);
}
