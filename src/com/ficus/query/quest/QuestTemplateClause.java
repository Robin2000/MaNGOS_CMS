package com.ficus.query.quest;

import java.util.ArrayList;

import com.ficus.query.QueryFilter;
import com.ficus.query.QueryItemInterface;
import com.ficus.query.gameobject.GameObjectFaction;

public class QuestTemplateClause implements QueryFilter {
	private String tables[]="quest_template,basic,accept,complete,reward,chain,emote".split(",");
	private ArrayList<QueryItemInterface> list=new ArrayList<QueryItemInterface> ();
	public QuestTemplateClause(){
		list.clear();
		list.add(new RequiredClasses());

	}

	@Override
	public ArrayList<QueryItemInterface> getFilterItems() {
		return list;
	}
	@Override
	public boolean matchTable(String table) {
		for(String t:tables)
			if(t.equals(table))
				return true;
		return false;
	}
	
}
