package com.ficus.db;

import java.util.ArrayList;

import com.ficus.app.Container;

public final class TableBean {

	private TableBean(){
		
	}
	public static TableBean me=new TableBean();
	
	public Column[] getColumns(String DSN, String tableName) throws Exception {
		
		String key=new StringBuilder(DSN).append('.').append(tableName).toString().toUpperCase();
		Column[] c = (Column[]) Container.me.getTableInfoPool().get(key);
		if(c!=null)
			return c;
		
		synchronized(key){
			c = (Column[]) Container.me.getTableInfoPool().get(key);
			if(c!=null)	
				return c;//check again
			
			DBCon con = null;
			try {
				con = DBCon.getConnection(DSN);
				con.setSQL(new StringBuilder("SELECT * FROM ").append(tableName).append(" WHERE 1=0").toString());
				QueryResult rs = con.query();
				c = rs.getCols();
				Container.me.getTableInfoPool().put(key, c);
				return c;
			} finally {
				if (con != null)
					con.close();
			}
		}
	}

	public boolean insert(String DSN, String tableName, Column c[]) throws java.sql.SQLException {
		
		DBCon con = null;
		try {
			con=DBCon.getConnection(DSN);
			con.doInsert(tableName, c);
		} catch (java.sql.SQLException e) {
			if (e.getMessage().toLowerCase().indexOf("duplicate entry") != -1) {
				throw new java.sql.SQLException("记录已经存在！");
			}
			throw e;
		}finally{
			if(con!=null)
				con.close();
		}
		return true;

	}

	public boolean insert(String DSN, String tableName, KeyUpperMap values) throws Exception {
		
		Column c[] = makeCommitColumn(DSN, tableName, values);
		
		insert(DSN, tableName, c);

		return true;
	}


	public QueryResult query(String DSN, String sql) throws Exception {
		return query(DSN, sql, null, null, "999999999", "1");
	}


	public QueryResult query(String DSN, String sql, String[] paraName, Object[] paraValue) throws Exception {
		return query(DSN, sql, paraName, paraValue, "999999999", "1");
	}

	public QueryResult query(String DSN, String sql, String[] paraName, Object[] paraValue, String pageSize,String pageIdx) throws Exception {

		DBCon con = null;
		QueryResult rs = null;
		try {
			con = DBCon.getConnection(DSN);
			con.setSQL(sql);

			if (paraName != null) {
				for (int i = 0; i < paraName.length; i++) {
					con.setParameter(paraName[i], paraValue[i]);

				}
			}
			rs = con.query(pageSize, pageIdx);

			return rs;
		} catch (Exception e) {
			throw e;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public QueryResult query(String DSN, String sql, String pageSize, String pageIdx) throws Exception {
		return query(DSN, sql, null, null, pageSize, pageIdx);
	}

	public Column[] makeCommitColumn(String DSN, String tableName,KeyUpperMap values) throws Exception {
		
		Column[] tableColumn = getColumns(DSN, tableName);
		ArrayList<Column> commitV = new ArrayList<Column>();
		
		Object value;
		Object val[];
		for (int i = 0; i < tableColumn.length; i++) {

			value = values.get(tableColumn[i].getName());

			if (value instanceof Object[]) {/*support multiple record*/
				val = ((Object[]) value);
				tableColumn[i].setCheckValue(true);
				tableColumn[i].value_vec = new ArrayList<Object>(tableColumn.length);
				for (int j = 0; j < val.length; j++) 
				{
					if (val[j]!=null&&val[j].toString().length()==0)
						if (tableColumn[i].isNumeric()) 
							val[j] = null;
					
					tableColumn[i].setValue(j, val[j]);
				}
			} else {
				tableColumn[i].value_vec = new ArrayList<Object>(tableColumn.length);

				if (value!=null&&value.toString().length()==0)
					if (tableColumn[i].isNumeric()) 
						value = null;

				tableColumn[i].setValue(0, value);
				
			}
			
			commitV.add(tableColumn[i]);
		}

		tableColumn = new Column[commitV.size()];
		commitV.toArray(tableColumn);

		return tableColumn;
	}

	public int update(String DSN, String tableName, Column c[], String pk[]) throws Exception {
		DBCon con=null;
		try{
			con=DBCon.getConnection(DSN);
			return con.doUpdate(tableName, pk, c);
		}finally{
			if(con!=null)
				con.close();
		}
	}

	public int update(String DSN, String tableName, KeyUpperMap values, String pk[]) throws Exception {
		Column c[] = makeCommitColumn(DSN, tableName, values);
		return update(DSN, tableName, c, pk);
	}
}
