package com.ficus.dbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.ficus.db.DB;
import com.ficus.db.DBCon;
import com.ficus.db.DBI;
import com.ficus.db.QueryResult;

public class DBCStore {
	private static final Logger log = Logger.getLogger(DBCStore.class);
	public static DBCStore me=new DBCStore();
	private List<AreaTable> areaTableList;
	private List<QuestSort> questSortList;
	private List<SkillLine> skillLineList;
	private List<QuestInfo> questInfoList;
	private List<Faction> repObjectiveFactionList;//任务目标Faction声望
	private List<Faction> requiredMinRepFactionList;//任务需求最小Faction声望
	private List<Faction> requiredMaxRepFactionList;//任务需求最大Faction声望
	private List<Faction> rewRepFactionList;//任务回报Faction声望
	
	private List<FactionTemplate> factionTemplateList;
	
	private List<CharTitles> charTitlesList;//任务完成获得头衔
	
	private List<Faction> factionList;//全部faction
	private HashMap<Integer,Faction> allFactionMap;
	private DBCStore(){
	}
	public List<Faction> getRewRepFactionList(){
		if(rewRepFactionList==null)
			initRewRepFactionList();
		return rewRepFactionList;
	}
	public List<FactionTemplate> getFactionTemplateList(){
		if(factionTemplateList==null)
			initFactionTemplateList();
		return factionTemplateList;
	}
	public List<CharTitles> getCharTitlesList(){
		if(charTitlesList==null)
			initCharTitlesList();
		return charTitlesList;
	}
	public List<Faction> getRequiredMinRepFactionList(){
		if(requiredMinRepFactionList==null)
			initRequiredMinRepFactionList();
		return requiredMinRepFactionList;
	}
	public List<Faction> getRequiredMaxRepFactionList(){
		if(requiredMaxRepFactionList==null)
			initRequiredMaxRepFactionList();
		return requiredMaxRepFactionList;
	}
	public List<Faction> getRepObjectiveFactionList(){
		if(repObjectiveFactionList==null)
			initRepObjectiveFactionList();
		return repObjectiveFactionList;
	}
	public List<Faction> getFactionList(){
		if(factionList==null)
			initFactionList();
		return factionList;
	}
	public HashMap<Integer,Faction> getAllFactionMap(){
		if(allFactionMap==null)
			initFactionList();
		return allFactionMap;
	}

	public List<AreaTable> getAreaTableList(){
		if(areaTableList==null)
			initAreaTableList();
		return areaTableList;
	}
	public List<QuestSort> getQuestSortList(){
		if(questSortList==null)
			initQuestSortList();
		return questSortList;
	}
	public List<SkillLine> getSkillLineList(){
		if(skillLineList==null)
			initSkillLineList();
		return skillLineList;
	}
	public List<QuestInfo> getQuestInfoList(){
		if(questInfoList==null)
			initQuestInfoList();
		return questInfoList;
	}	

	private synchronized void initFactionTemplateList(){
		if(factionTemplateList!=null)
			return;
		try {
			
			DB.me.execute("dbc",new DBI()
			{
				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select id,ourMask,friendlyMask,hostileMask  FROM factiontemplate");
					QueryResult rs = con.query();
					factionTemplateList=new ArrayList<FactionTemplate>(rs.size());
					FactionTemplate f;
					for(int i=0;i<rs.size();i++)
					{
						f=new FactionTemplate();
						f.id=Integer.parseInt(rs.getObject(i, 0).toString());
						f.ourMask=Integer.parseInt(rs.getObject(i, 1).toString());
						f.friendlyMask=Integer.parseInt(rs.getObject(i, 2).toString());
						f.hostileMask=Integer.parseInt(rs.getObject(i, 3).toString());
						factionTemplateList.add(f);
					}
					
				}});

		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	private synchronized void initRequiredMaxRepFactionList(){
		if(requiredMaxRepFactionList!=null)
			return;
		try {
			DB.me.execute(new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select requiredMaxRepFaction,count(*) as cnt  FROM quest_template group by requiredMaxRepFaction having cnt>0 and requiredMaxRepFaction>0");
					QueryResult rs = con.query();
					requiredMaxRepFactionList=new ArrayList<Faction>(rs.size());
					if(factionList==null)
						initFactionList();
						
					Faction f;
					for(int i=0;i<rs.size();i++)
					{
						f=allFactionMap.get(Integer.parseInt(rs.getObject(i, 0).toString()));
						if(f==null)
							log.error("quest表无效faction:"+rs.getObject(i, 0));
						else
							requiredMaxRepFactionList.add(f);
					}
					
				}});

		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	
	private synchronized void initRequiredMinRepFactionList(){
		if(requiredMinRepFactionList!=null)
			return;
		try {
			DB.me.execute(new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select requiredMinRepFaction,count(*) as cnt  FROM quest_template group by requiredMinRepFaction having cnt>0 and requiredMinRepFaction>0");
					QueryResult rs = con.query();
					requiredMinRepFactionList=new ArrayList<Faction>(rs.size());
					if(factionList==null)
						initFactionList();
						
					Faction f;
					for(int i=0;i<rs.size();i++)
					{
						f=allFactionMap.get(Integer.parseInt(rs.getObject(i, 0).toString()));
						if(f==null)
							log.error("quest表无效faction:"+rs.getObject(i, 0));
						else
							requiredMinRepFactionList.add(f);
					}
					
				}});
			
		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	
	private synchronized void initRewRepFactionList(){
		if(rewRepFactionList!=null)
			return;
		try {
			DB.me.execute(new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					StringBuilder sb=new StringBuilder();
					sb.append("select RewRepFaction1,count(*) as cnt  FROM quest_template group by RewRepFaction1 having cnt>0 and RewRepFaction1>0");
					sb.append(" union select RewRepFaction2,count(*) as cnt  FROM quest_template group by RewRepFaction2 having cnt>0 and RewRepFaction2>0");
					sb.append(" union select RewRepFaction3,count(*) as cnt  FROM quest_template group by RewRepFaction3 having cnt>0 and RewRepFaction3>0");
					sb.append(" union select RewRepFaction4,count(*) as cnt  FROM quest_template group by RewRepFaction4 having cnt>0 and RewRepFaction4>0");
					con.setSQL(sb.toString());
					QueryResult rs = con.query();
					rewRepFactionList=new ArrayList<Faction>(rs.size());
					if(factionList==null)
						initFactionList();
						
					Faction f;
					HashSet<Integer> set=new HashSet<Integer>();
					for(int i=0;i<rs.size();i++)
					{
						f=allFactionMap.get(Integer.parseInt(rs.getObject(i, 0).toString()));
						if(f==null)
							log.error("quest表无效faction:"+rs.getObject(i, 0));
						else
						{
							if(set.contains(f.id))
								continue;
							rewRepFactionList.add(f);
							set.add(f.id);
						}
					}
					
				}});
			
		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	private synchronized void initRepObjectiveFactionList(){
		if(repObjectiveFactionList!=null)
			return;
		try {
			DB.me.execute(new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select RepObjectiveFaction,count(*) as cnt  FROM quest_template group by RepObjectiveFaction having cnt>0 and RepObjectiveFaction>0");
					QueryResult rs = con.query();
					repObjectiveFactionList=new ArrayList<Faction>(rs.size());
					if(factionList==null)
						initFactionList();
						
					Faction f;
					for(int i=0;i<rs.size();i++)
					{
						f=allFactionMap.get(Integer.parseInt(rs.getObject(i, 0).toString()));
						if(f==null)
							log.error("quest表无效faction:"+rs.getObject(i, 0));
						else
							repObjectiveFactionList.add(f);
					}
					
					
				}});
		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	private synchronized void  initCharTitlesList(){
		if(charTitlesList!=null)
			return;
		try {
			DB.me.execute("dbc",new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select id,en,cn from CharTitles");
					QueryResult rs = con.query();
					charTitlesList=new ArrayList<CharTitles>(rs.size());
					CharTitles qs;
					for(int i=0;i<rs.size();i++)
					{
						qs=new CharTitles();
						qs.id=Integer.parseInt(rs.getObject(i, 0).toString());
						qs.en=rs.getObject(i, 1).toString();
						qs.cn=rs.getObject(i, 2).toString();
						charTitlesList.add(qs);
					}
					
				}});

		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	private synchronized void initFactionList(){
		if(factionList!=null)
			return;
		try {
			
			DB.me.execute("dbc",new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select id,enname,cnname from Faction");
					QueryResult rs = con.query();
					factionList=new ArrayList<Faction>(rs.size());
					Faction qs;
					for(int i=0;i<rs.size();i++)
					{
						qs=new Faction();
						qs.id=Integer.parseInt(rs.getObject(i, 0).toString());
						qs.en=rs.getObject(i, 1).toString();
						qs.cn=rs.getObject(i, 2).toString();
						factionList.add(qs);
					}
					allFactionMap=new HashMap<Integer,Faction>();
					for(Faction f:factionList)
						allFactionMap.put(f.id, f);
					
				}});
			
		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	private synchronized void initQuestInfoList(){
		if(questInfoList!=null)
			return;
		try {
			DB.me.execute("dbc",new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select id,name_en,name_cn from QuestInfo");
					QueryResult rs = con.query();
					questInfoList=new ArrayList<QuestInfo>(rs.size());
					QuestInfo qs;
					for(int i=0;i<rs.size();i++)
					{
						qs=new QuestInfo();
						qs.id=Integer.parseInt(rs.getObject(i, 0).toString());
						qs.en=rs.getObject(i, 1).toString();
						qs.cn=rs.getObject(i, 2).toString();
						questInfoList.add(qs);
					}
					
				}});
			
		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	private synchronized void initSkillLineList(){
		if(skillLineList!=null)
			return;
		try {
			DB.me.execute("dbc",new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select id,en,cn from skillLine");
					QueryResult rs = con.query();
					skillLineList=new ArrayList<SkillLine>(rs.size());
					SkillLine qs;
					for(int i=0;i<rs.size();i++)
					{
						qs=new SkillLine();
						qs.id=Integer.parseInt(rs.getObject(i, 0).toString());
						qs.en=rs.getObject(i, 1).toString();
						qs.cn=rs.getObject(i, 2).toString();
						skillLineList.add(qs);
					}
					
				}});

		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	private synchronized void initQuestSortList(){
		if(questSortList!=null)
			return;
		try {
			DB.me.execute("dbc",new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select id,en,cn from questsort");
					QueryResult rs = con.query();
					questSortList=new ArrayList<QuestSort>(rs.size());
					QuestSort qs;
					for(int i=0;i<rs.size();i++)
					{
						qs=new QuestSort();
						qs.id=Integer.parseInt(rs.getObject(i, 0).toString());
						qs.en=rs.getObject(i, 1).toString();
						qs.cn=rs.getObject(i, 2).toString();
						questSortList.add(qs);
					}
					
				}});
			
		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
	private synchronized void initAreaTableList(){
		if(areaTableList!=null)
			return;
		try {
			DB.me.execute("dbc",new DBI(){

				@Override
				public void onConnection(DBCon con) throws SQLException {
					con.setSQL("select id,name_en,name_cn,map,parentid,arealevel from areatable");
					QueryResult rs = con.query();
					areaTableList=new ArrayList<AreaTable>();
					AreaTable at;
					for(int i=0;i<rs.size();i++)
					{
						at=new AreaTable();
						at.id=Long.parseLong(rs.getObject(i, 0).toString());
						at.name_en=rs.getObject(i, 1).toString();
						at.name_cn=rs.getObject(i, 2).toString();
						at.mapid=Long.parseLong(rs.getObject(i, 3).toString());
						at.areaid=Long.parseLong(rs.getObject(i, 4).toString());
						at.arealevel=Integer.parseInt(rs.getObject(i, 5).toString());
						
						areaTableList.add(at);
					}
					
				}});
			
		} catch (SQLException e) {
			log.error("查询失败!");
		}
	}
}
