package com.ficus.db;

import java.io.FileInputStream;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import com.ficus.app.Container;
import com.ficus.util.Util;

public final class DBCon {

	public java.sql.Connection con = null;
	public static boolean debug_sql = true;
	private String sql = null;

	public String DSN = null;
	private ArrayList<SQLParameter> para_vec = new ArrayList<SQLParameter>();
	private String schema = null;
	boolean useStrPara = false;
	private PreparedStatement psmt;

	public DBCon(java.sql.Connection con) {
		this.con = con;
	}

	public void close() {
		try {
			psmt.close();
		} catch (Exception e) {

		}
		try {
			if (con != null)
				con.close();
			con = null;
		} catch (Exception e) {

		}
	}

	public void commit() throws SQLException {
		con.commit();
	}

	public void rollback() {
		try {
			con.rollback();
		} catch (Exception e) {
		}
	}

	public boolean getAutoCommit() throws SQLException {
		return con.getAutoCommit();
	}

	public void setAutoCommit(boolean commit) {
		try {
			con.setAutoCommit(commit);
		} catch (Exception e) {
		}
	}

	public boolean execute() throws SQLException {
		try {
			commitParameter();
			psmt = con.prepareStatement(sql);
			for (int i = 0; i < para_vec.size(); i++) {
				setP1(i + 1, ((SQLParameter) para_vec.get(i)).value);

			}
			boolean i = psmt.execute();

			/* 清空缓存数据 */
			return i;
		} catch (SQLException e) {
			if (e.getMessage() != null && e.getMessage().toUpperCase().indexOf("DUPLICATE") != -1) {
				throw new SQLException("记录已经存在!");
			} else {
				throw e;
			}
		}
	}
	public QueryResult query() throws SQLException {
		return realQuery();
	}
	public QueryResult queryOLD() throws SQLException {

		return queryOLD(0, 999999999);
	}

	// private String getKey(int begin, int end) {
	// StringBuilder keyBuf = new StringBuilder(sql);
	// for (int i = 0; i < para_vec.size(); i++) {
	// keyBuf.append(':').append(((SQLParameter) para_vec.get(i)).toString());
	// }
	//
	// keyBuf.append(':').append(begin).append(':').append(end);
	// return keyBuf.reverse().toString();
	// }

	public QueryResult realQuery() throws SQLException {
		
		commitParameter();
		psmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 只用于查询，提高next效率

		for (int i = 0; i < para_vec.size(); i++)
			setP1(i + 1, ((SQLParameter) para_vec.get(i)).value);

		return makeResultMySql(psmt);
	}
	public QueryResult queryOLD(int begin, int size) throws SQLException {
		commitParameter();
		/* 至此，SQL为标准带占位符的SQL */
		psmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 只用于查询，提高next效率

		for (int i = 0; i < para_vec.size(); i++)
			setP1(i + 1, ((SQLParameter) para_vec.get(i)).value);

		return makeResultMySqlOld(psmt, begin, begin + size);
	}

	public void setSQL(String sql) {
		if (debug_sql)
			System.out.println(sql);
		this.sql = sql;

		this.para_vec = new ArrayList<SQLParameter>();

		clearStatment();

		this.useStrPara = false;
	}

	public ResultSet getResultSet() throws Exception {
		try {
			return psmt.getResultSet();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void setParameter(int index, Object value, int length) throws Exception {

		if (debug_sql) {
			System.out.print(index);
			System.out.print("=");
			System.out.println(value);
		}
		try {
			if (value instanceof FileInputStream) {
				psmt.setBinaryStream(index, (FileInputStream) value, length);
			} else {
				throw new Exception("未知的数据类型！");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	private void commitParameter() {
		if (this.useStrPara == false) {
			return;
		}

		para_vec.sort(new Comparator<SQLParameter>(){
			@Override
			public int compare(SQLParameter o1, SQLParameter o2) {
				return o1.pos-o2.pos;
			}});


		for (int i = 0; i < para_vec.size(); i++)
			sql = Util.replace(sql, new StringBuilder(":").append(((SQLParameter) para_vec.get(i)).name).toString(),
					"?");
	}

	/**
	 * 执行insert语句,参数cs中包含要更新的列及列值<br>
	 * cs中含有更新的列名和更新的数值的信息<br>
	 * 创建日期: (2000-12)<br>
	 * 
	 * @author: micro <br>
	 * @param tableName
	 *            String
	 * @param cs
	 *            Column[]
	 */

	public void doInsert(String tableName, Column cs[]) throws SQLException {
		try {

			StringBuilder sqlBuf = new StringBuilder("insert into ");

			if (cs == null) {
				return;
			}

			if (cs.length == 0)
				throw new SQLException("错误的列参数");
			sqlBuf.append(tableName);
			sqlBuf.append('(');
			for (int i = 0; i < cs.length; i++) {
				sqlBuf.append(cs[i].getName());
				sqlBuf.append((i == cs.length - 1) ? ')' : ',');
			}

			sqlBuf.append(" values(");
			for (int i = 0; i < cs.length; i++) {
				sqlBuf.append('?');
				sqlBuf.append((i == cs.length - 1) ? ')' : ',');
			}

			for (int row = 0; row < cs[0].value_vec.size(); row++) {
				/* System.out.println(sqlBuf); */
				clearStatment();
				psmt = con.prepareStatement(sqlBuf.toString());
				for (int i = 0; i < cs.length; i++) {
					setP1(i + 1, cs[i].getValue(row));
					/* System.out.println(cs[i].getValue(row)); */
				}

				if (debug_sql) {
					System.out.println(sqlBuf.toString());
				}
				psmt.execute();
			}

			/* 将缓冲数据清空 */
		} catch (SQLException e) {
			if (e.getErrorCode() == 1) {
				throw new SQLException("记录已经存在");
			}
			if (e.getMessage() != null && e.getMessage().toUpperCase().indexOf("DUPLICATE") != -1) {
				throw new SQLException("记录已经存在");
			} else {
				throw e;
			}
		}
	}

	public String getTableNameByPstm() throws SQLException {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= psmt.getMetaData().getColumnCount(); i++) {
			String tName = psmt.getMetaData().getTableName(i);
			if (sb.indexOf(tName) == -1) {
				if (sb.length() > 0)
					sb.append(',');
				sb.append(tName);
			}
		}
		return sb.toString();
	}

	public String getTableNameByQueryResult(QueryResult rs) throws SQLException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rs.getCols().length; i++) {
			String tName = rs.getCols()[i].getTableName();
			if (sb.indexOf(tName) == -1) {
				if (sb.length() > 0)
					sb.append(',');
				sb.append(tName);
			}
		}
		return sb.toString();
	}

	public void excuteNoWarningPk() throws SQLException {
		try {
			execute();
		} catch (SQLException e) {
			if (e.getMessage() != null && e.getMessage().toUpperCase().indexOf("记录已经存在") == -1) {
				throw e;
			}
		}

	}

	public static DBCon getConnection(String DSN) throws SQLException {
		return new DBCon(Container.me.getConnection(DSN));
	}

	public ArrayList<TableInfo> getTables() throws Exception {
		ArrayList<TableInfo> list = new ArrayList<TableInfo>();
		ResultSet rs = null;
		try {
			DatabaseMetaData dmd = con.getMetaData();
			rs = dmd.getTables(null, schema, null, null);

			while (rs.next()) {
				TableInfo info = new TableInfo(DSN, rs.getString(3).trim().toLowerCase());
				list.add(info);
			}

			return list;
		} finally {
			if (rs != null) {
				rs.close();
			}

		}
	}

	public boolean isClosed() throws Exception {
		try {
			if (con != null) {
				return con.isClosed();
			} else {
				return true;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public QueryResult query(String recNumPerPage, String pageIdx) throws SQLException {

		int begin = Integer.parseInt(recNumPerPage) * (Integer.parseInt(pageIdx) - 1);
		int end = Integer.parseInt(recNumPerPage) + begin;
		QueryResult rs=query( begin, end-begin);
		rs.recNumPerPage = recNumPerPage;
		rs.pageIdx = pageIdx;
		rs.totalPageNum = String.valueOf((int) Math.ceil(.5 + rs.all_numrow / Integer.parseInt(recNumPerPage)));
		return rs;
	}
	public QueryResult query(int begin, int size) throws SQLException {

		String oldsql=sql;
		
		sql=new StringBuilder("select count(*) from(").append(sql).append(") T").toString();
		QueryResult rsCount = query();
		
		sql=new StringBuilder(oldsql).append(" limit ").append(begin).append(",").append(size).toString();
		QueryResult rs = query();
		rs.all_numrow=Integer.parseInt(rsCount.getObject(0,0).toString());/*覆盖总记录数*/

		rs.recNumPerPage = String.valueOf(size);
		if(size>0)
			rs.pageIdx = String.valueOf((int)(begin/size)+1);
		else
			rs.pageIdx="1";
		return rs;
	}
	public QueryResult queryOLD(String recNumPerPage, String pageIdx) throws SQLException {

		int begin = Integer.parseInt(recNumPerPage) * (Integer.parseInt(pageIdx) - 1);
		int end = Integer.parseInt(recNumPerPage) + begin;

		QueryResult rs = queryOLD(begin, end);
		rs.recNumPerPage = recNumPerPage;
		rs.pageIdx = pageIdx;

		rs.totalPageNum = String.valueOf((int) Math.ceil(.5 + rs.all_numrow / Integer.parseInt(recNumPerPage)));

		return rs;
	}
	private void setP1(int index, Object value) throws SQLException {
		setP1(this.psmt, index, value);
	}

	public void setParameter(String para_name, Object para_value) throws SQLException {
		if (debug_sql)
			System.out.println(new StringBuilder(para_name).append('=').append(para_value));

		this.useStrPara = true;
		try {
			int pos = 0;
			int idx = -1;

			while (true) {
				idx = sql.indexOf(new StringBuilder(":").append(para_name).toString(), idx + 1);
				if (idx < 0) {
					break;
				}

				pos = idx;

				para_vec.add(new SQLParameter(para_name, para_value, idx));
			}

			if (pos == 0) {
				throw new Exception(para_name);
			}

		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	/*
	 * 根据给定表名将缓冲数据置为dirty
	 */
	public ArrayList<Column> getColumns(String tableName) throws SQLException {

		ArrayList<Column> v = new ArrayList<Column>();

		DatabaseMetaData dmd = con.getMetaData();
		ResultSet rs = dmd.getColumns(null, null, tableName, null);
		if(rs==null)
			throw new SQLException("table name now found!"+tableName);
		while (rs.next()) {
			String str = rs.getString("COLUMN_NAME").trim(); // 列名
			Column c = new Column();
			c.setName(str.toLowerCase());
			c.setTableName(tableName.toUpperCase());
			str = rs.getString("TYPE_NAME").trim(); // 数据类型
			c.setDataType(str.toUpperCase());
			c.setIsNullable(rs.getString("IS_NULLABLE").equals("YES"));
			c.setLength(rs.getInt("COLUMN_SIZE"));
			v.add(c);
		}

		return v;
	}

	public ArrayList<Column> getColumnsBySql(String sql) throws SQLException {
		
		String sqlDummy;
		if(!sql.toLowerCase().contains(" limit "))
			sqlDummy=new StringBuilder(sql).append(" limit 1").toString();
		else
			sqlDummy=sql;
		
		try (PreparedStatement ps = con.prepareStatement(sqlDummy); ResultSet rs = ps.executeQuery();) {

			ArrayList<Column> v = new ArrayList<Column>();

			ResultSetMetaData rmd = rs.getMetaData();
			
			int count = rmd.getColumnCount();

			for (int i = 0; i < count; i++) {

				Column c = new Column();
				c.setName(rmd.getColumnName(i + 1).toLowerCase());
				c.setTableName(rmd.getTableName(i + 1).toUpperCase());
				c.setDataType(rmd.getColumnTypeName(i + 1).trim().toUpperCase());
				c.setIsNullable(rmd.isNullable(i + 1) == 1);
				c.setLength(rmd.getPrecision(i + 1));
				c.setDispLength(rmd.getColumnDisplaySize(i + 1));
				c.setScale(rmd.getScale(i + 1));
				c.setAlias(rmd.getColumnLabel(i + 1).toLowerCase());
				v.add(c);
				
			}
			
			return v;
		} catch(Exception e){
			throw new SQLException("sql error!"+sql,e);
		}
		
	}

	/**
	 * 从SQL语句解析出查询的表。 select * from a,b union select * from b,c create
	 * time：(2002-5-13 15:49:10)
	 * 
	 * @return java.lang.String[]
	 * @param sql
	 *            java.lang.String
	 */
	public String[] getTables(String SQLStr) {

		String s = SQLStr.toLowerCase();
		StringTokenizer st = new StringTokenizer(s, " ");
		ArrayList<String> v = new ArrayList<String>();
		while (st.hasMoreElements()) {
			String tmp = st.nextElement().toString().trim();
			if (!tmp.equals("from")) {
				continue;
			}

			tmp = st.nextElement().toString().trim();
			StringTokenizer st_tmp = new StringTokenizer(tmp, ",");
			while (st_tmp.hasMoreElements()) {
				String name = st_tmp.nextElement().toString().trim();
				if (v.indexOf(name) == -1) {
					v.add(name);
				}
			}
		}

		String tableName[] = new String[v.size()];
		v.toArray(tableName);

		return tableName;
	}

	public List<String> getPkCol(String table) throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		DatabaseMetaData dm = con.getMetaData();
		ResultSet rs = null;
		try {
			rs = dm.getExportedKeys("", "", table);
			while (rs.next())
				list.add(rs.getString("PKCOLUMN_NAME"));
		} finally {
			rs.close();
		}
		return list;
	}
	private QueryResult makeResultMySql(PreparedStatement psmt) throws SQLException {

		ResultSet rs = null;
		try {
			QueryResult qr = new QueryResult();

			rs = psmt.executeQuery();
			ResultSetMetaData rsm = rs.getMetaData();
			qr.cols = new Column[rsm.getColumnCount()];

			Column col = new Column();
			for (int i = 0; i < rsm.getColumnCount(); i++) {
				col = new Column();
				qr.cols[i] = col;
				col.setDSN(DSN);
				col.setTableName(rsm.getTableName(i + 1));
				col.setName(rsm.getColumnName(i + 1));
				col.setDataType(rsm.getColumnTypeName(i + 1).toUpperCase());
				col.setLength(rsm.getPrecision(i + 1));
				col.setDispLength(rsm.getColumnDisplaySize(i + 1));
				col.setScale(rsm.getScale(i + 1));
				col.setAlias(rsm.getColumnLabel(i + 1));
				col.setAutoIncrement(rsm.isAutoIncrement(i + 1));
				col.setIsNullable((rsm.isNullable(i + 1) == 0) ? false : true);
				col.setScale(rsm.getScale(i + 1));
			}

			int numrows=0;
			while (rs.next()) {
				numrows++;
				for (int i = 0; i < qr.cols.length; i++) {
					qr.cols[i].value_vec.add(rs.getObject(i + 1));
				}
			}
			
			qr.all_numrow=numrows;/*更改结果集记录数，若为分页查询，请在本方法返回后覆盖all_numrow*/
			
			return qr;

		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	private QueryResult makeResultMySqlOld(PreparedStatement psmt, int begin, int end) throws SQLException {

		ResultSet rs = null;
		try {
			QueryResult qr = new QueryResult();

			rs = psmt.executeQuery();
			ResultSetMetaData rsm = rs.getMetaData();
			qr.cols = new Column[rsm.getColumnCount()];

			Column col = new Column();
			for (int i = 0; i < rsm.getColumnCount(); i++) {
				col = new Column();
				qr.cols[i] = col;
				col.setDSN(DSN);
				col.setTableName(rsm.getTableName(i + 1));
				col.setName(rsm.getColumnName(i + 1));
				col.setDataType(rsm.getColumnTypeName(i + 1).toUpperCase());
				col.setLength(rsm.getPrecision(i + 1));
				col.setDispLength(rsm.getColumnDisplaySize(i + 1));
				col.setScale(rsm.getScale(i + 1));
				col.setAlias(rsm.getColumnLabel(i + 1));
				// col.setIsPk(rsm.); see getPkCol
				col.setAutoIncrement(rsm.isAutoIncrement(i + 1));

				col.setIsNullable((rsm.isNullable(i + 1) == 0) ? false : true);
				// qr.cols[i].setIsReadOnly(rsm.isReadOnly(i + 1));
				col.setScale(rsm.getScale(i + 1));
				// qr.cols[i].isCheck= false;
				/*
				 * System.out.println("-----------" + qr.cols[i].getName() +
				 * "-----------"); System.out.println("isDefinitelyWritable=" +
				 * rsm.isDefinitelyWritable(i + 1));
				 * System.out.println("isReadOnly=" + rsm.isReadOnly(i + 1));
				 * System.out.println("isWritable=" + rsm.isWritable(i + 1));
				 * System.out.println("isCurrency=" + rsm.isCurrency(i + 1));
				 * System.out.println("isCaseSensitive=" + rsm.isCaseSensitive(i
				 * + 1)); System.out.println("isAutoIncrement=" +
				 * rsm.isAutoIncrement(i + 1));
				 * System.out.println("getSchemaName=" + rsm.getSchemaName(i +
				 * 1)); System.out.println("getCatalogName=" +
				 * rsm.getCatalogName(i + 1)); System.out.println("getScale=" +
				 * rsm.getScale(i + 1)); System.out.println("getPrecision=" +
				 * rsm.getPrecision(i + 1));
				 */
			}

			if (begin > 0) {
				rs.absolute(begin);

			}
			boolean no_rec = true;
			while (rs.next()) {
				no_rec = false;
				for (int i = 0; i < qr.cols.length; i++) {
					qr.cols[i].value_vec.add(rs.getObject(i + 1));

				}
				if (rs.getRow() == end) {
					break;
				}
			}

			rs.last();
			if (begin == 0 && no_rec) {
				qr.all_numrow = 0;
			} else {
				qr.all_numrow = rs.getRow();

				// System.out.println("结果集makeResult读取花费时间" +
				// (System.currentTimeMillis() - l));
			}
			return qr;

		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	private void setP1(PreparedStatement psmt, int index, Object value) throws SQLException {

		if (value == null) {
			psmt.setNull(index, Types.CHAR);
		} else if (value instanceof String && value.toString().getBytes().length < 2000) {
			psmt.setString(index, (String) value);

		} else if (value instanceof String) {
			psmt.setObject(index, (String) value);

		} else if (value instanceof StringBuilder) {
			psmt.setObject(index, ((StringBuilder) value).toString());

		}

		else if (value instanceof java.sql.Date) {
			psmt.setDate(index, (java.sql.Date) value);
		} else if (value instanceof Integer) {
			Integer i = (Integer) value;
			int ii = i.intValue();
			psmt.setInt(index, ii);
		} else if (value instanceof Float) {
			Float f = (Float) value;
			float ff = f.floatValue();
			psmt.setFloat(index, ff);
		} else if (value instanceof Double) {
			Double d = (Double) value;
			double dd = d.doubleValue();
			psmt.setDouble(index, dd);
		} else if (value instanceof Long) {
			Long d = (Long) value;
			long dd = d.longValue();
			psmt.setDouble(index, dd);
		}

		else if (value instanceof byte[]) {
			psmt.setBytes(index, (byte[]) value);
		} else {
			psmt.setObject(index, value);
		}
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-12-26 16:44:39)
	 */
	public void clearStatment() {
		try {
			if (this.psmt != null) {
				this.psmt.clearParameters();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}

		try {
			if (this.psmt != null) {
				this.psmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int doUpdate(String tableName, Column[] updateCol, Column[] whereCol) throws SQLException {

		StringBuilder sqlBuf = new StringBuilder("update ");
		sqlBuf.append(tableName);
		sqlBuf.append(" set ");
		for (int i = 0; i < updateCol.length; i++) {
			sqlBuf.append(updateCol[i].getName());
			sqlBuf.append("=?");
			sqlBuf.append((i == updateCol.length - 1) ? " " : ",");
		}

		sqlBuf.append(" where ");
		for (int i = 0; i < whereCol.length; i++) {
			sqlBuf.append(whereCol[i].getName());
			sqlBuf.append("=?");
			sqlBuf.append((i == whereCol.length - 1) ? " " : " and ");
		}

		int count = 0;
		for (int row = 0; row < updateCol[0].value_vec.size(); row++) {
			clearStatment();
			psmt = con.prepareStatement(sqlBuf.toString());
			for (int i = 0; i < updateCol.length; i++) {
				// System.out.println(i+" p:"+updateCol[i].getValue(row));
				setP1((i + 1), updateCol[i].getValue(row));

			}

			for (int i = 0; i < whereCol.length; i++) {
				// System.out.println(i+" p:"+whereCol[i].getValue(row));
				setP1((i + 1) + updateCol.length, whereCol[i].getValue(row));
			}

			if (debug_sql) {
				System.out.println(sqlBuf.toString());
			}
			count += psmt.executeUpdate();

		}
		/* 将数据请空 */
		return count;
	}

	public int doUpdate(String tableName, String pk[], Column[] col) throws SQLException {
		ArrayList<Column> v = new ArrayList<Column>();
		for (int j = 0; j < pk.length; j++) {
			for (int i = 0; i < col.length; i++) {
				if (col[i].getName().toUpperCase().equals(pk[j].toUpperCase())
						|| col[i].getFullName().toUpperCase().equals(pk[j].toUpperCase())) {
					v.add(col[i]);
					break;
				}
			}
		}

		Column c[] = new Column[v.size()];
		v.toArray(c);
		return doUpdate(tableName, col, c);

	}

	public int executeUpdate() throws SQLException {
		try {
			commitParameter();

			psmt = con.prepareStatement(sql);
			for (int i = 0; i < para_vec.size(); i++) {
				setP1(i + 1, ((SQLParameter) para_vec.get(i)).value);

			}
			int i = psmt.executeUpdate();

			/* 清空缓存数据 */
			return i;
		} catch (SQLException e) {
			if (e.getMessage() != null && e.getMessage().toUpperCase().indexOf("DUPLICATE") != -1) {
				throw new SQLException("记录已经存在!");
			} else {
				throw e;
			}
		}
	}
}
