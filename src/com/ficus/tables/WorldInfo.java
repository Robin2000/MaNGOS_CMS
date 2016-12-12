package com.ficus.tables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ficus.app.Container;
import com.ficus.db.DBCon;
import com.ficus.db.QueryResult;
import com.ficus.db.SQLParameter;
import com.ficus.db.TableInfo;

public final class WorldInfo {

	private static final Logger log = Logger.getLogger(Container.class);
	
	private ArrayList<TableInfo> tables;
	
	
	private synchronized void init() {
		
		if(tables!=null)
			return;
		
		DBCon con = null;
		try {
			con = DBCon.getConnection("mangos");
			tables=con.getTables();
			
			StringBuilder sb=new StringBuilder();
			for(TableInfo t:tables)
			{
				if(sb.length()>0)
					sb.append(" union ");
				sb.append("select '"+t.getName()+"' as t,count(*) as n from "+t.getName());
			}
			con.setSQL(sb.toString());
			QueryResult rs=con.query();
			for(int i=0;i<rs.size();i++)
			{
				
				for(TableInfo t:tables){
					if(t.getName().equals(rs.getObject(i, 0).toString()))
					{
						t.setCount(Integer.parseInt(rs.getObject(i, 1).toString()));
					}
				}
			}
			
		} catch (Exception e) {
			log.error("load mangos table info error!",e);
		} finally {
			if (con != null)
				con.close();
		}
	}

	public ArrayList<TableInfo> getTables() {
		if(tables==null)
			init();
		return tables;
	}
	
	public ArrayList<TableInfo> getTablesByFirstLetter(String letter){
		
		ArrayList<TableInfo> list=new ArrayList<TableInfo>();
		for(TableInfo t: getTables())
		{
			if(t.getName().startsWith(letter.toLowerCase()))
				list.add(t);
		}
		return list;
	}
	
	public ArrayList<ArrayList<TableInfo>> getTablesGroup(){
		
		HashMap<String,ArrayList<TableInfo>> map=new HashMap<String,ArrayList<TableInfo>>();
		for(TableInfo tb: getTables())
		{
			String key=String.valueOf(tb.getName().charAt(0));
			ArrayList<TableInfo> list=map.get(key);
			if(list==null)
			{
				list=new ArrayList<TableInfo>();
				map.put(key, list);
			}
			list.add(tb);
		}
		
		ArrayList<String> cl=new ArrayList<String>();
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext())
			cl.add(it.next());
		
		Collections.sort(cl,new Comparator<String>(){  /*key sort*/
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
			
		});

		ArrayList<ArrayList<TableInfo>> result=new ArrayList<ArrayList<TableInfo>>();
		ArrayList<TableInfo> gl;
		for(String c:cl)
		{
			gl=map.get(c);
			
			Collections.sort(gl,new Comparator<TableInfo>(){  /*value sort*/
				@Override
				public int compare(TableInfo o1, TableInfo o2) {
					return o1.getName().compareTo(o2.getName());
				}});
			
			result.add(gl);
		}
		
		return result;
	}
}
