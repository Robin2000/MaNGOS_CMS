package com.ficus.dbc;

public class QuestInfo {
	public int id;
	public String en;
	public String cn;
	public String toString(){
		return new StringBuilder().append(en).append(cn).toString();
	}
}
