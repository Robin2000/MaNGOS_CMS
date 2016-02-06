package com.ficus.db;

import com.ficus.util.DateTool;
import com.ficus.util.Util;

public final class XMLCreator {
	
	QueryResult rs;

	/**
	 * XMLCreator 构造子注解。
	 */
	public XMLCreator(QueryResult rs) {
		super();
		this.rs = rs;
	}

	/**
	 * %替换为%25 ,替换为%2C 保证s中不再有,
	 */
	public String decode(String s) {
		s = Util.replace(s, "%40", "@");
		s = Util.replace(s, "%24", "$");
		s = Util.replace(s, "%25", "%");

		return s;
	}

	/**
	 * %替换为%25 ,替换为%2C 保证s中不再有,
	 */
	public String encode(String s) {
		s = Util.replace(s, "%", "%25");
		s = Util.replace(s, "@", "%40"); // 列分割
		s = Util.replace(s, "$", "%24"); // 行分割
		return s;
	}

	public StringBuffer ge() throws Exception {
		StringBuffer xml = new StringBuffer();
		for (int i = 1; i <= rs.getCols().length; i++) {
			xml.append(rs.getCols()[i - 1].getName()).append(",");
		}
		return xml;
	}

	public String getDate(int row_idx, String format, int col_idx) throws Exception {
		Object o = rs.getObject(row_idx, col_idx);
		if (o == null) {
			return "";
		}
		o.getClass();
		DateTool d = new DateTool((java.sql.Timestamp) o);
		return d.toDateString(format);
	}

	public String getDate(int row_idx, String format, String colName) throws Exception {
		Object o = rs.getObject(row_idx, colName);
		if (o == null) {
			return "";
		}
		o.getClass();
		DateTool d = new DateTool((java.sql.Timestamp) o);
		return d.toDateString(format);
	}

	public StringBuffer getXML() throws Exception {
		StringBuffer xml = new StringBuffer();
		xml.append("<result>\n");

		xml.append("<dbtype>");
		xml.append(rs.DBType);
		xml.append("</dbtype>");

		xml.append("<pageIdx>");
		xml.append(rs.pageIdx);
		xml.append("</pageIdx>\n");

		xml.append("<recNumPerPage>");
		xml.append(rs.recNumPerPage);
		xml.append("</recNumPerPage>\n");

		xml.append("<all_numrow>");
		xml.append(rs.totalPageNum);
		xml.append("</all_numrow>\n");

		xml.append("<size>");
		xml.append(rs.size());
		xml.append("</size>\n");

		xml.append(getXMLColumnInfo());
		xml.append(getXMLRecordset());
		xml.append("</result>\n");

		// System.out.println(xml);
		return xml;
	}

	public StringBuffer getXMLColumnInfo() {
		StringBuffer xml = new StringBuffer();
		xml.append("<columns>\n");

		for (int i = 0; i < rs.getCols().length; i++) {
			xml.append("<col>");
			xml.append("<name>");
			xml.append(rs.getCols()[i].getFullName());
			xml.append("</name>");

			xml.append("<data_type>");
			xml.append(rs.getCols()[i].getDataType());
			xml.append("</data_type>");

			xml.append("<length>");
			xml.append(rs.getCols()[i].getLength());
			xml.append("</length>");

			xml.append("<scale>");
			xml.append(rs.getCols()[i].getScale());
			xml.append("</scale>");

			xml.append("<dispLength>");
			xml.append(rs.getCols()[i].getDispLength());
			xml.append("</dispLength>");

			xml.append("<comment>");
			xml.append(rs.getCols()[i].getAlias());
			xml.append("</comment>");

			xml.append("<nullAble>");
			xml.append(rs.getCols()[i].isNullable());
			xml.append("</nullAble>");

			xml.append("</col>\n");
		}
		xml.append("</columns>\n");

		return xml;
	}

	public StringBuffer getXMLRecordset() throws Exception {
		StringBuffer xml = new StringBuffer();
		xml.append("<recordset>\n");

		for (int r = 0; r < rs.size(); r++) {
			if (r > 0) {
				xml.append("@\n");
				// xml.append("<" + rs.getCols()[c].getFullName() + ">");

			}
			for (int c = 0; c < rs.getCols().length; c++) {

				if (c > 0) {
					xml.append("$");

					// xml.append("<rec>");
				}
				Object value = rs.getObject(r, c);
				if (rs.getColType(c) != null && rs.getColType(c).equals("timestamp")) {
					xml.append((value == null) ? "" : getDate(r, "YYYY-MM-DD", c));
				} else {
					xml.append((value == null) ? "" : replace(value.toString()));

					// xml.append("</" + rs.getCols()[c].getFullName() + ">");
				}
			}
			// xml.append("</rec>\n");
		}
		xml.append("</recordset>\n");

		return xml;
	}

	private String replace(String colvalue) {

		colvalue = Util.replace(colvalue, "&", "&amp;");
		colvalue = Util.replace(colvalue, "<", "&lt;");
		colvalue = encode(colvalue);

		return colvalue;
	}
}
