package com.ficus.query.quest;

import java.util.ArrayList;

import com.ficus.query.QueryFilter;
import com.ficus.query.QueryItemInterface;

public class QuestTemplateClause implements QueryFilter {
	private String tables[]="quest_template,basic,accept,complete,reward,chain,emote".split(",");
	private ArrayList<QueryItemInterface> list=new ArrayList<QueryItemInterface> ();
	public QuestTemplateClause(){
		list.clear();
		list.add(new RequiredClasses());
		list.add(new RequiredRaces());
		list.add(new Method());
		list.add(new RequiredSkill());
		list.add(new Zone());
		list.add(new Sort());
		list.add(new Type());
		list.add(new RepObjectiveFaction());
		list.add(new RequiredMaxRepFaction());
		list.add(new RequiredMinRepFaction());
		list.add(new QuestFlags());
		list.add(new SpecialFlags());
		list.add(new CharTitlesFilter());
		list.add(new PrevQuestId());
		list.add(new NextQuestId());
		list.add(new ExclusiveGroup());
		list.add(new NextQuestInChain());
		list.add(new RewRepFaction());
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
