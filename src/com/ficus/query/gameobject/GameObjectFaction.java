package com.ficus.query.gameobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.dbc.DBCStore;
import com.ficus.dbc.FactionTemplate;
import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;
import com.ficus.util.Util;

public class GameObjectFaction extends ArrayList<KeyValue> implements QueryItemInterface{

	protected HashMap<Integer,FactionKV> allFaction;
	public GameObjectFaction(){
		initAllFaction();
		
		add(new FactionKV(0,"全部",0,0,0));
		add(new FactionKV(1,"PLAYER, Human人类",3,2,12));
		add(new FactionKV(2,"PLAYER, Orc兽人",5,4,10));
		add(new FactionKV(3,"PLAYER, Dwarf小矮人",3,2,12));
		add(new FactionKV(4,"PLAYER, Night Elf暗夜精灵",3,2,12));
		add(new FactionKV(5,"PLAYER, Undead不死族",5,4,10));
		add(new FactionKV(6,"PLAYER, Tauren牛头人",5,4,10));
		add(new FactionKV(115,"PLAYER, Gnome侏儒",3,2,12));
		add(new FactionKV(116,"PLAYER, Troll巨魔",5,4,10));
		add(new FactionKV(1610,"PLAYER, Blood Elf血精灵",5,4,10));
		add(new FactionKV(1629,"PLAYER, Draenei德莱尼",3,2,12));
		add(new FactionKV(2050,"PLAYER, Knights of the Ebon Blade黑锋骑士",1,0,0)); //敌对1098
//		add(new FactionKV(2051,"PLAYER, Knights of the Ebon Blade黑锋骑士"),1,0,0);
//		add(new FactionKV(2144,"PLAYER, Knights of the Ebon Blade黑锋骑士"),1,0,0);
//		add(new FactionKV(2214,"PLAYER, Knights of the Ebon Blade黑锋骑士"),1,6,0);
		add(new FactionKV(2226,"PLAYER, Knights of the Ebon Blade黑锋骑士",1,6,0));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>阵营友好：<select onchange='reload()' id='friendlyID'>");
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select>\n");

		sb.append("阵营敌视：<select onchange='reload()' id='hostileID'>");
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>\n");

		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		String s= "d.friendlyID = $('#friendlyID').val();\n";
		s+="d.hostileID = $('#hostileID').val();\n";
		return s;
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		StringBuilder sb=new StringBuilder();
		String friendlyID=request.getParameter("friendlyID");
		boolean added=false;
		if(friendlyID!=null&&!"0".equals(friendlyID))
		{
			FactionKV selFactionKV=allFaction.get(Integer.parseInt(friendlyID));
			sb.append("friendlyMask&"+selFactionKV.ourMask);
			added=true;
		}
		String hostileID=request.getParameter("hostileID");

		if(hostileID!=null&&!"0".equals(hostileID))
		{
			FactionKV selFactionKV=allFaction.get(Integer.parseInt(hostileID));
			if(added)
				sb.append(" and hostileMask&"+selFactionKV.ourMask);
			else
				sb.append("hostileMask&"+selFactionKV.ourMask);
			added=true;
		}
		
		return sb.toString();
	}
	public String getFilterTable(){
		return "gameobject_template";
	}
	private void initAllFaction(){
		allFaction=new HashMap<Integer,FactionKV>();
		List<FactionTemplate> list=DBCStore.me.getFactionTemplateList();
		for(FactionTemplate f:list)
		allFaction.put(f.id, new FactionKV(f.id,"",f.ourMask,f.friendlyMask,f.hostileMask));
	}
	private void initAllFactionOld(){
		allFaction=new HashMap<Integer,FactionKV>();
		
		String s=Util.loadConfig(GameObjectFaction.class.getClassLoader(), "ExportFactionTemplate.txt");
		for(String line:s.split("@@@")){
			if(!line.trim().contains(" | "))
				continue;
		
			String cols[]=line.trim().split(" \\| ");
			allFaction.put(Integer.parseInt(cols[0]), new FactionKV(Integer.parseInt(cols[0]),"",Integer.parseInt(cols[3]),Integer.parseInt(cols[4]),Integer.parseInt(cols[5])));
		}
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
