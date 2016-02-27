package com.ficus.query.item;

import java.util.ArrayList;

import com.ficus.query.QueryFilter;
import com.ficus.query.QueryItemInterface;

public class ItemTemplateClause implements QueryFilter {
	
	private ArrayList<QueryItemInterface> list=new ArrayList<QueryItemInterface> ();
	public ItemTemplateClause(){
		list.add(new ItemClass());
		list.add(new SubClass());
		list.add(new ItemFlags());
		list.add(new ItemQuality());
		list.add(new InventoryType());
		list.add(new RequiredReputationRank());
		list.add(new StatType());
		list.add(new DmgType());
		list.add(new SpellTrigger());
		list.add(new Bonding());
		list.add(new Material());
		list.add(new Sheath());
		list.add(new BagFamily());
		list.add(new TotemCategory());
		list.add(new SocketColor());
		list.add(new FoodType());	
		list.add(new ExtraFlags());	
	}


	@Override
	public ArrayList<QueryItemInterface> getFilterItems() {
		return list;
	}
	@Override
	public boolean matchTable(String table) {
		return table.equals("item_template");
	}
	
}
