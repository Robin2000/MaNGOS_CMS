package com.ficus.translate;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ficus.db.Column;
import com.ficus.db.DBCon;
import com.ficus.table.TableDataBean;
import com.ficus.util.Util;

public class TranslateBean extends TableDataBean {

	private HashMap<String,String> sqlMap;
	private boolean inited=false;
	
	public ArrayList<Column> getColumns(DBCon con,String table) throws Exception{
		return con.getColumnsBySql(getSql(table).toString());
	}
	public StringBuilder getSql(String table) throws Exception{
		String sql=getSqlMap().get(table);
		if(sql==null)
		{
			throw new Exception("can not found "+table+",need check translate.xml");
		}
		return new StringBuilder(sql);
	}
	
	public HashMap<String,String> getSqlMap(){
		if(sqlMap!=null)
			return sqlMap;
		init();
		return sqlMap;
	}
	public synchronized void init(){
		if(inited)
			return;
		
		sqlMap=new HashMap<String,String>();
		String config=Util.loadConfig(this.getClass().getClassLoader(), "translate.xml");
		if(config==null)
            return;
		
		Document doc = Jsoup.parse(config);
        
		Elements rootElem = doc.select("root");
		for(Element root:rootElem){
			for(Element node:root.children())
				sqlMap.put(node.tagName(),node.text());
		}
		
		inited=true;
		
	}

}
