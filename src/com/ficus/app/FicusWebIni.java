package com.ficus.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.alibaba.druid.pool.DruidDataSource;

@WebListener
public final class FicusWebIni implements ServletContextListener {

	private static final Logger log = Logger.getLogger(FicusWebIni.class);
	
    public FicusWebIni() {
        
    }

	@Override
	public void contextInitialized(ServletContextEvent e) {
		
		loadAppProperties();
		
		Container.me.setSc(e.getServletContext());
		
		DOMConfigurator.configure(FicusWebIni.class.getResource("/log4j.xml"));
		
		HashMap<String,DruidDataSource> map=new HashMap<String,DruidDataSource>();
		ArrayList<DBConItem> list=new DataSourceConfig().getDataSourceIni();
		for(DBConItem ini:list)
		{
			DruidDataSource dds = new DruidDataSource();
			dds.setDriverClassName(ini.driver);
			dds.setUrl(ini.url);
			dds.setUsername(ini.user);
			dds.setPassword(ini.passwd);
			map.put(ini.ds,dds);
			log.info(new StringBuilder("datasource added ").append(ini.ds));
		}
		e.getServletContext().setAttribute("ficus_ds",map);
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		HashMap<String,DruidDataSource> map=(HashMap<String,DruidDataSource>)e.getServletContext().getAttribute("ficus_ds");
		Iterator<DruidDataSource> it=map.values().iterator();
		while(it.hasNext())
			close(it.next());
	}

	private void loadAppProperties(){
		Properties p=new Properties();
		try {
				p.load(FicusWebIni.class.getResourceAsStream("/app.properties"));
				Iterator<Entry<Object,Object>> it=p.entrySet().iterator();
				Entry<Object,Object> kv;
				while(it.hasNext())
				{
					kv=it.next();
					Container.me.setAppProperty(kv.getKey().toString().trim(),kv.getValue().toString().trim());
				}
		} catch (IOException e) {
			log.error("load app.properties fail!");
			Runtime.getRuntime().exit(1);
		}

	}
	private void close(DruidDataSource ds){
		try{ds.close();}catch(Exception e){
			
		}
		log.info(new StringBuilder("datasource closed ").append(ds.getName()));
	}
	
}
