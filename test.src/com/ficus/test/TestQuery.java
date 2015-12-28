package com.ficus.test;

import java.sql.SQLException;

import com.ficus.db.DBCon;
import com.ficus.db.QueryResult;

public class TestQuery {

	public static void main(String[] args) throws SQLException {
		DBCon con=null;
		try{
			con=DBCon.getConnection("mangos");
			con.setSQL("select * from creature_template");
			QueryResult rs=con.query(1000,12);
			for(int i=0;i<rs.size();i++)
				System.out.println(rs.getObject(i,1));
		}finally{
			con.close();
		}

	}

}
