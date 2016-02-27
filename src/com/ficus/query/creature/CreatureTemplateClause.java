package com.ficus.query.creature;

import java.util.ArrayList;

import com.ficus.query.QueryFilter;
import com.ficus.query.QueryItemInterface;
import com.ficus.query.gameobject.GameObjectFaction;

public class CreatureTemplateClause implements QueryFilter {
	
	private ArrayList<QueryItemInterface> list=new ArrayList<QueryItemInterface> ();
	public CreatureTemplateClause(){
		list.clear();
		list.add(new CreatureType());
		list.add(new CreatureTypeFlags());	
		list.add(new BeastFamily());
		list.add(new Rank());
		list.add(new InhabitType());	
		//list.add(new InhabitTypeTrinity());	
		list.add(new RacialLeader());
		list.add(new NpcFlags());
		list.add(new UnitFlags());
		list.add(new DynamicFlags());
		list.add(new ExtraFlags());
		list.add(new UnitClass());
		list.add(new DamageSchool());	
		list.add(new MechanicImmuneMask());
		list.add(new MovementType());
		list.add(new TrainerType());
		list.add(new AIName());
		list.add(new RegenerateStats());
		
		list.add(new CreatureTemplateFaction());
	}

	@Override
	public ArrayList<QueryItemInterface> getFilterItems() {
		return list;
	}
	@Override
	public boolean matchTable(String table) {
		return "creature_template".equals(table);
	}
	
}
