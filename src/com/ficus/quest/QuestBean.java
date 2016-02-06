package com.ficus.quest;

import java.util.ArrayList;

import com.ficus.db.Column;
import com.ficus.db.DBCon;
import com.ficus.table.TableDataBean;

public class QuestBean extends TableDataBean {

	public ArrayList<Column> getColumns(DBCon con,String table) throws Exception{
		return con.getColumnsBySql(getSql(table).toString());
	}
	public StringBuilder getSql(String table) throws Exception{
		String sql=QuestQueryXmlConfig.me.getSql(table);
		if(sql==null)
		{
			throw new Exception("can not found "+table+",need check quest.xml");
		}
		return new StringBuilder(sql);
	}
}
