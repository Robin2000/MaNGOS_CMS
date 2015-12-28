package com.ficus.app;

public final class DBConItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public String ds = null;
	public String driver = null;
	public String url = null;
	public String user = null;
	public String passwd = null;

	public DBConItem(String ds, String driver, String url, String user, String password) {
		this.ds = ds;
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.passwd = password;
	}
}