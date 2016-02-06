package com.ficus.query;

import java.util.ArrayList;

public interface QueryFilter {
	
	/*提供若干过滤元素项，每个元素对应一个列*/
	public ArrayList<QueryItemInterface> getFilterItems();
	
	/*提供过滤的表名*/
	public String getFilterTable();
	
}
