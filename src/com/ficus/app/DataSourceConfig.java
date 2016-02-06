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

    private String defaultDSN;
    private ArrayList<DBConItem> iniList;
    public static DataSourceConfig me=new DataSourceConfig();
    private DataSourceConfig()
    {
    	init();
    }
    private void init(){
    	
    	iniList=new ArrayList<DBConItem>();

        String config=loadConfig();

        Document doc = Jsoup.parse(config);
        
		Elements list = doc.select("ds");
		for(Node elem:list)
			iniList.add(new DBConItem( elem.attr("name"),  elem.attr("driver"),  elem.attr("url"),  elem.attr("user"),  elem.attr("passwd"))) ;
		
		defaultDSN=doc.select("defaultDSN").text();
    }
    public ArrayList<DBConItem> getDataSourceIni() {
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


	public String getDefaultDSN() {
		return defaultDSN;
	}


	public void setDefaultDSN(String defaultDSN) {
		this.defaultDSN = defaultDSN;
	}
    
    
 
}