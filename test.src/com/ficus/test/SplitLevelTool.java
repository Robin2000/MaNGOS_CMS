package com.ficus.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ficus.db.DBCon;
import com.ficus.db.QueryResult;
import com.ficus.db.RecodDealer;
import com.ficus.util.Util;

public class SplitLevelTool {
	 String RARE="精英";
	 String ELITE="稀有";
	 String RAREELITE="稀有精英";
	 String HEROIC="英雄"; 
	 String LEADER="首领";	
	 

		//                                1精2稀有精英3boss4稀有      0不是1是
	 int id,herominlevel=0,heromaxlevel=0,rank=0,                   isleader=0;
	 String minlevel,maxlevel,oldcn,cn,tw,subname,npc_type,area,npc_desc=null;
	 int _minLevel,_maxLevel;
	 
	public static void main(String[] args) throws SQLException {
		new SplitLevelTool().init();
	}
	public void init() throws SQLException{	
		DBCon con=null;
		final DBCon updatecon=DBCon.getConnection("hh");
		try{
			con=DBCon.getConnection("hh");
			//				   1    2   3  4    5      6       7         8          9           10         11     12   13     14
			con.setSQL("select id,oldcn,cn,tw,tw2cn,subname,minlevel,maxlevel,herominlevel,heromaxlevel,npc_type,area,rank,isleader from npctwcn order by id");
			con.Query(new RecodDealer(){
				@Override
				public boolean deal(ResultSet rs) throws SQLException {
					//rank:1=精2=稀有精英3=boss4=稀有      isleader:0不是1是
					 //String RARE="精英";
					 //String ELITE="稀有";
					 //String RAREELITE="稀有精英";
					 //String BOSS="BOSS"; 
					 //String LEADER="首领";
					 
					 herominlevel=0;
					 heromaxlevel=0;
					 rank=0;
					 isleader=0;
					 minlevel=null;
					 maxlevel=null;
					 oldcn=null;
					 cn=null;
					 tw=null;
					 subname=null;
					 npc_type=null;
					 area=null;
					 npc_desc=null;
					 _minLevel=0;
					 _maxLevel=0;
					 
					id=rs.getInt(1);
					minlevel=rs.getString(7);
					maxlevel=rs.getString(8);
					subname=rs.getString(6);
					if(subname!=null&&!subname.startsWith("<"))
					{
						npc_desc=Util.replace(subname,"'","\\'");
					}
					
					
					readFlag(minlevel);
					readFlag(maxlevel);
					readHeroic(minlevel,false);
					readHeroic(maxlevel,true);
					
					 int pos=minlevel.indexOf(" ");
					 if(pos>0)
						 _minLevel=Integer.parseInt(minlevel.substring(0, pos));
					 else
						 _minLevel=Integer.parseInt(minlevel);
					 pos=maxlevel.indexOf(" ");
					 if(pos>0)
						 _maxLevel=Integer.parseInt(maxlevel.substring(0, pos));
					 else
						 _maxLevel=Integer.parseInt(maxlevel);
						 
					StringBuilder sql=new StringBuilder("update npctwcn set herominlevel=").append(herominlevel).append(",heromaxlevel=").append(heromaxlevel);
						sql.append(",").append("_minLevel=").append(_minLevel).append(",").append("_maxLevel=").append(_maxLevel);
						sql.append(",").append("npc_desc='").append(npc_desc).append("'");
						sql.append(",").append("rank=").append(rank).append(",").append("isleader=").append(isleader).append(" where id=").append(id);
						
					updatecon.setSQL(sql.toString());
					updatecon.executeUpdate();
						
					return true;
				}});
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			con.close();
			updatecon.close();
		}
	}
	private void readFlag(String minOrMaxLevel){
		if(minOrMaxLevel.contains(LEADER))
			isleader=1;
		if(minOrMaxLevel.contains(RAREELITE))
			rank=2;
		else if(minOrMaxLevel.contains(RARE))
			rank=4;
		else if(minOrMaxLevel.contains(ELITE))
			rank=1;
		else
			rank=0;
	}
	private void readHeroic(String minOrMaxLevel,boolean isMax){
		if(!minOrMaxLevel.contains(HEROIC))
		{
			if(isMax)
				heromaxlevel=_maxLevel;
			else
				herominlevel=_minLevel;
			return;		
		}
		int pos1=minOrMaxLevel.indexOf("(");
		if(pos1==-1)
			return;
		int pos2=minOrMaxLevel.indexOf(" 英雄)");
		if(pos2==-1)
			return;
		
		String hlevel=minOrMaxLevel.substring(pos1+1,pos2);
		if(hlevel.contains("–")){
			String[] la=hlevel.split("–");
			herominlevel=Integer.parseInt(la[0]);
			heromaxlevel=Integer.parseInt(la[1]);
		}else{
			herominlevel=Integer.parseInt(hlevel);
			heromaxlevel=herominlevel;
		}
		
	}
}
