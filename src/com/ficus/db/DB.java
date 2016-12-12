package com.ficus.db;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ficus.app.DataSourceConfig;


public final class DB {
	private static final Logger log = Logger.getLogger(DB.class);
	public static DB me=new DB();
	
	public void execute(DBI action) throws SQLException {
		execute(DataSourceConfig.me.getDefaultDSN(), action);
	}

	public void executeTransaction(DBI action) throws SQLException {
		 executeTransaction(DataSourceConfig.me.getDefaultDSN(), action);
	}


	public   void executeTransaction(String DSN, DBI action) throws SQLException {
		DBCon con=null;
		try {
			con=DBCon.getConnection(DSN);
			con.setAutoCommit(false);
			action.onConnection(con);
			con.commit();

		} catch (SQLException e) {
			con.rollback();
			log.warn(e.getMessage());
			throw e;
		} finally {
			con.close();
		}
	}

	public  void execute(String DSN, DBI action) throws SQLException {
		DBCon con=null;
		try {
			con=DBCon.getConnection(DSN);
			action.onConnection(con);
		} finally {
			con.close();
		}
	}
}
