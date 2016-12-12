package com.ficus.dbc;

public class AreaTable {
	public long id;
	public String name_en;
	public String name_cn;
	public long mapid;
	public long areaid;
	public int arealevel;
	public String toString(){
		return new StringBuilder().append(name_en).append(name_cn).toString();
	}
}
