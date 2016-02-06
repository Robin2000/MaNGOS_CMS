package com.ficus.quest;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ficus.util.Util;

public class QuestQueryXmlConfig {
	
	private HashMap<String,String> sqlMap;
	private boolean inited=false;	
	public static QuestQueryXmlConfig me=new QuestQueryXmlConfig();
	
	private QuestQueryXmlConfig(){
		init();
	}
	public String getSql(String table){
		return me.getSqlMap().get(table);
	}
	public HashMap<String,String> getSqlMap(){
		if(sqlMap!=null)
			return sqlMap;
		init();
		return sqlMap;
	}
	private synchronized void init(){
		if(inited)
			return;
		
		sqlMap=new HashMap<String,String>();
		String config=Util.loadConfig(this.getClass().getClassLoader(), "quest.xml");
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
