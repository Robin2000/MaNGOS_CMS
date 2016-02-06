package com.ficus.query.item;

import java.util.ArrayList;

import com.ficus.query.QueryFilter;
import com.ficus.query.QueryItemInterface;

public class ItemTemplateClause implements QueryFilter {
	
	private ArrayList<QueryItemInterface> list=new ArrayList<QueryItemInterface> ();
	public ItemTemplateClause(){
		list.add(new ItemClass());
		list.add(new SubClass());
	}
	public String getFilterTable(){
		return "item_template";
	}

	@Override
	public ArrayList<QueryItemInterface> getFilterItems() {
		return list;
	}
	
}
