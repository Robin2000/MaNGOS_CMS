package com.ficus.query.quest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class RequiredRaces extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public RequiredRaces(){
	/*标志位：eg: 2^(ID-1).*/
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"不限职业"));
		add(new KeyValue(1,"Human人类"));
		add(new KeyValue(2,"Orc兽人"));
		add(new KeyValue(3,"Dwarf小矮人"));
		add(new KeyValue(4,"Night Elf暗夜精灵"));
		add(new KeyValue(5,"Undead不死族"));
		add(new KeyValue(6,"Tauren牛头人"));
		add(new KeyValue(7,"Gnome侏儒"));
		add(new KeyValue(8,"巨魔法师"));
		add(new KeyValue(9,"Goblin小妖精"));
		add(new KeyValue(10,"Blood Elf血精灵"));
		
		add(new KeyValue(11,"Draenei德莱尼"));
		add(new KeyValue(12,"Fel Orc邪兽人"));
		add(new KeyValue(13,"Naga娜迦"));
		add(new KeyValue(14,"Broken破碎"));
		add(new KeyValue(15,"Skeleton骨架"));
		add(new KeyValue(16,"Vrykul维京人维库人"));
		add(new KeyValue(17,"Tuskarr海象"));
		add(new KeyValue(18,"Forest Troll森林巨魔"));
		add(new KeyValue(19,"Taunka远古牛头人"));
		add(new KeyValue(20,"Northrend Skeleton诺森德骨架"));
		add(new KeyValue(21,"Ice Troll冰巨魔"));
		add(new KeyValue(22,"Worgen狼人"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>种族：<select onchange='reload()' id='RequiredRaces'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.RequiredRaces = $('#RequiredRaces').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String RequiredRaces=request.getParameter("RequiredRaces");
		if(RequiredRaces==null||"-1".equals(RequiredRaces))
			return "";
		else if(RequiredRaces.equals("0"))	
			return "RequiredRaces=0";
		else 
			return "RequiredRaces&"+Math.pow(2,Integer.parseInt(RequiredRaces)-1);
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
