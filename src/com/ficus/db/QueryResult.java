package com.ficus.db;

public final class QueryResult implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	boolean isDirty = false;/* 如果脏数据，则被删除,重新从数据库获得 */
	String[] tableSrc = null;/* 来源表，决定是否dirty的依据 */

	public int all_numrow = -1;
	public Column[] cols = new Column[] {};
	public String DBType = null;
	public String pageIdx = null;
	public String recNumPerPage = null; // 为null代表采用不分页的形式
	public String totalPageNum = null;

	public QueryResult() {
	}

	public int size() {
		if (cols.length == 0) {
			return 0;
		}
		return cols[0].value_vec.size();
	}

	public String getColType(int index) {
		return cols[index].getDataType();
	}

	public int getCol(String colName) throws Exception {
		for (int i = 0; i < cols.length; i++) {
			if (cols[i].getFullName().equalsIgnoreCase(colName) || cols[i].getName().equalsIgnoreCase(colName)) {
				return i;
			}
		}

		throw new Exception(new StringBuffer("查询中未指定:").append(colName).toString());
	}

	public Column[] getCols() {
		return cols;
	}

	public void setComment(String[] colname, String[] comment) {
		for (int i = 0; i < cols.length; i++)
			for (int j = 0; j < colname.length; j++) {
				if (cols[i].getName().equalsIgnoreCase(colname[j])) {
					cols[i].setAlias(comment[j]);
				}
			}
	}

	public Object getObject(int row_idx, int col_idx) {
		if (cols[col_idx].value_vec.size() == 0) {
			return "";
		}

		Object value = cols[col_idx].value_vec.get(row_idx);

		try {
			if (value == null)
				return "";
			if (value instanceof byte[]) {
				value = new String((byte[]) value);

			}
			else if (value != null && value instanceof String) {
				return value.toString().trim();
			}

		} catch (Exception e) {
			return "";
		}

		return value;

	}

	public Object getObject(int row_idx, String colName) throws Exception {
		return getObject(row_idx, getCol(colName));
	}

	public void setObject(int row_idx, int col_idx, Object value) {
		cols[col_idx].value_vec.set(row_idx, value);
	}

	public void setObject(int row_idx, String colName, Object value) throws Exception {
		setObject(row_idx, getCol(colName), value);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cols.length; i++) {
			sb.append(cols[i].getName()).append(",");

		}
		return sb.toString();
	}

}
