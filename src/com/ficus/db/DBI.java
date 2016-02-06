package com.ficus.db;

import java.sql.SQLException;

public interface DBI {
	public void onConnection(DBCon con) throws SQLException;
}
