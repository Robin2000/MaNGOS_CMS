package com.ficus.dbc;

public class FactionTemplate {
	public int id;
	public int ourMask;
	public int friendlyMask;
	public int hostileMask;
	public String toString(){
		return new StringBuilder().append(id).toString();
	}
}
