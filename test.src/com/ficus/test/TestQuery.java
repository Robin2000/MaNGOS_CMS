package com.ficus.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ficus.db.DBCon;
import com.ficus.db.QueryResult;
import com.ficus.db.RecodDealer;

public class TestQuery {

	public static void main(String[] args) throws SQLException {
		DBCon con=null;
		try{
			con=DBCon.getConnection("mangos");
			con.setSQL("select * from creature_template");
			con.Query(new RecodDealer(){

				@Override
				public boolean deal(ResultSet rs) {
					
					
					
					
					return true;
				}});
		}finally{
			con.close();
		}

	}

}
