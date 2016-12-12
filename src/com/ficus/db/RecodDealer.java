package com.ficus.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RecodDealer {
	public boolean deal(ResultSet rs) throws SQLException;
}
