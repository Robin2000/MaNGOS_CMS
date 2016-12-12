package com.ficus.quest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.ficus.db.Column;
import com.ficus.db.DB;
import com.ficus.db.DBCon;
import com.ficus.db.DBI;
import com.ficus.db.QueryResult;

public class QuestChain {

	private static final Logger log = Logger.getLogger(QuestChain.class);
	private ArrayList<Object[]> list ;
	private HashMap<String,Integer> entry2pos;/*每个任务id对应list的下标位置*/
	
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
		//0        1         2          3           4              5            6
		//entrya,A.title,B.title_loc4,PrevQuestId,NextQuestId,ExclusiveGroup,NextQuestInChain
		sql = QuestQueryXmlConfig.me.getSql("chain")
				+ " WHERE A.entry=:ID OR PrevQuestId=:PREVID OR NextQuestId=:NEXTID OR NextQuestInChain=:NEXTCHAIN";
		list=new ArrayList<Object[]>();
		hashset=new HashSet<Object>();
		query(questid);
		entry2pos=new HashMap<String,Integer>();
		for(int i=0;i<list.size();i++)
			entry2pos.put(list.get(i)[0].toString(), i);
	}
	/*根据任务id取得其在list中位置*/
	public int getQuestPos(String questid){
		Integer pos=entry2pos.get(questid);
		if(pos==null)
			setQuestid(questid);
		return entry2pos.get(questid);
	}
	private void query(final Object curid) {
		try {
			DB.me.execute(new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL(sql);
					con.setParameter("PREVID", curid);
					con.setParameter("NEXTID", curid);
					con.setParameter("NEXTCHAIN", curid);
					con.setParameter("ID", curid);
					rs = con.query();
					
				}});

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
