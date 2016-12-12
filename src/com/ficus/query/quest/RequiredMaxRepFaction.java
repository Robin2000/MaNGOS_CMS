package com.ficus.query.quest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.dbc.DBCStore;
import com.ficus.dbc.Faction;
import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class RequiredMaxRepFaction extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public RequiredMaxRepFaction(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"不限"));
		List<Faction> list=DBCStore.me.getRequiredMaxRepFactionList();
		for(Faction at:list)
			add(new KeyValue(at.id,at.toString()));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>需求最大声望：<select onchange='reload()' id='RequiredMaxRepFaction'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.RequiredMaxRepFaction = $('#RequiredMaxRepFaction').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String RequiredMaxRepFaction=request.getParameter("RequiredMaxRepFaction");
		if(RequiredMaxRepFaction==null||"-1".equals(RequiredMaxRepFaction))
			return "";
		else 
			return "RequiredMaxRepFaction="+RequiredMaxRepFaction;
		
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated RequiredMaxRepFaction stub
		return false;
	}
}
