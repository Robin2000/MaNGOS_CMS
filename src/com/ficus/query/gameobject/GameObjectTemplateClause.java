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
	public String getFilterTable(){
		return "gameobject_template";
	}

	@Override
	public ArrayList<QueryItemInterface> getFilterItems() {
		return itemlist;
	}
	
}
