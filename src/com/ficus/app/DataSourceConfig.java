package com.ficus.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public final class DataSourceConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(DataSourceConfig.class);

    public ArrayList<DBConItem> getDataSourceIni() {
    	
    	ArrayList<DBConItem> iniList=new ArrayList<DBConItem>();

        String config=loadConfig();
        if(config==null)
            return iniList;
        
        Document doc = Jsoup.parse(config);
        
		Elements list = doc.select("ds");
		for(Node elem:list)
			iniList.add(new DBConItem( elem.attr("name"),  elem.attr("driver"),  elem.attr("url"),  elem.attr("user"),  elem.attr("passwd"))) ;
		
		return iniList;
    }
    
    
    private String loadConfig(){
       try(InputStream in=this.getClass().getClassLoader().getResourceAsStream("datasource.xml");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));   
               ){
            StringBuilder txt = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) 
            	txt.append(line).append("\n");
            return txt.toString();
       }catch(Throwable e){
    	   log.error("config datasource error",e);
          return null;
       }
    }
 
}