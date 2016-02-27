package com.ficus.query.gameobject;

import java.util.ArrayList;

import com.ficus.query.QueryFilter;
import com.ficus.query.QueryItemInterface;

public class GameObjectTemplateClause implements QueryFilter {
	
	private ArrayList<QueryItemInterface> itemlist=new ArrayList<QueryItemInterface> ();
	public GameObjectTemplateClause(){
		itemlist.add(new GameObjectType());
		itemlist.add(new GameObjectFlags());
		itemlist.add(new GameObjectFaction());
		itemlist.add(new IconName());
	}


	@Override
	public ArrayList<QueryItemInterface> getFilterItems() {
		return itemlist;
	}
	@Override
	public boolean matchTable(String table) {
		return table.equals("gameobject_template");
		
	}
	
}
