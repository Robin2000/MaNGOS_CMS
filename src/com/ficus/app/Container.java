package com.ficus.app;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.ficus.db.Column;

public final class Container {

	public static Container me = new Container();
	private static final Logger log = Logger.getLogger(Container.class);
	
	private ServletContext sc;
	public  HashMap<String, Column[]> tableInfoPool = new HashMap<String, Column[]>();
	private HashMap<String,String> appProperties=new HashMap<String,String>();

	public ServletContext getSc() {
		return sc;
	}

	public void setSc(ServletContext sc) {
		this.sc = sc;
	}

	
	public HashMap<String, Column[]> getTableInfoPool() {
		return tableInfoPool;
	}

	@SuppressWarnings("unchecked")
	public Connection getConnection(String dsName) throws SQLException {
		if (sc != null)
			return ((HashMap<String, DruidDataSource>) sc.getAttribute("ficus_ds")).get(dsName).getConnection();
		else {
			log.info("ServletContext not found,for unit test.");
			List<DBConItem> list = new DataSourceConfig().getDataSourceIni();
			for (DBConItem ini : list) {
				if(!ini.ds.equals(dsName))
					continue;
				Object o = null;
				try {
					o = Class.forName(ini.driver).newInstance();
				} catch (Exception e) {
					log.error("", e);

				}
				DriverManager.registerDriver((Driver) o);
				Connection conn = DriverManager.getConnection(ini.url, ini.user, ini.passwd);
				return conn;

			}
			
			Runtime.getRuntime().exit(1);
			return null;
		}
	}
	public String getAppProperty(String key){
		return appProperties.get(key);
	}
	public void setAppProperty(String key,String value){
		appProperties.put(key,value);
	}
}
