package com.ficus.quest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.ficus.db.Column;
import com.ficus.db.DB;
import com.ficus.db.QueryResult;

public class QuestChain {

	private static final Logger log = Logger.getLogger(QuestChain.class);
	private ArrayList<Object[]> list ;
	private Column[] cols;
	private QueryResult rs;
	
	public Column[] getCols() {
		return cols;
	}

	private HashSet<Object> hashset;
	private String questid;
	private String sql;

	public String getQuestid() {
		return questid;
	}

	public void setQuestid(String questid) {
		this.questid = questid;

		refresh();
	}

	public ArrayList<Object[]> getList() {
		return list;
	}

	private void refresh() {
		sql = QuestQueryXmlConfig.me.getSql("chain")
				+ " WHERE A.entry=:ID OR PrevQuestId=:PREVID OR NextQuestId=:NEXTID OR NextQuestInChain=:NEXTCHAIN";
		list=new ArrayList<Object[]>();
		hashset=new HashSet<Object>();
		query(questid);

	}

	private void query(Object curid) {
		try {
				DB.me.execute(con -> {
						con.setSQL(sql);
						con.setParameter("PREVID", curid);
						con.setParameter("NEXTID", curid);
						con.setParameter("NEXTCHAIN", curid);
						con.setParameter("ID", curid);
						rs = con.query();
				});
		} catch (SQLException e) {
			log.error("查询失败!");
		}
		
		cols=rs.cols;
		
		ArrayList<Object> entryAdded=new ArrayList<Object>(); /*避免递归出现重复查询，将需要待查询的实体添加到本列表*/
		for (int i = 0; i < rs.size(); i++) {
			Object entry = rs.getObject(i, 0);
			if (!hashset.contains(entry)) {
				hashset.add(entry);// 确保不重复添加
				entryAdded.add(entry);
				
				Object row[]=new Object[cols.length];
				for(int c=0;c<cols.length;c++)
					row[c]=rs.getObject(i, c);
				list.add(row);
			}
		}
		
		if (entryAdded.size() == 0)
			return;
		for(Object entry:entryAdded)
			query(entry); //继续查询其上下级		
		
		
		
		
		
	}
}
