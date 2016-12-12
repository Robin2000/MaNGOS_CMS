package com.ficus.query.quest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.dbc.DBCStore;
import com.ficus.dbc.Faction;
import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class RewRepFaction extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public RewRepFaction(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"不限"));
		List<Faction> list=DBCStore.me.getRewRepFactionList();
		for(Faction at:list)
			add(new KeyValue(at.id,at.toString()));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>回报声望：<select onchange='reload()' id='RewRepFaction'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.RewRepFaction = $('#RewRepFaction').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String RewRepFaction=request.getParameter("RewRepFaction");
		if(RewRepFaction==null||"-1".equals(RewRepFaction))
			return "";
		else 
			return "(RewRepFaction1="+RewRepFaction+" or RewRepFaction2="+RewRepFaction+" or RewRepFaction3="+RewRepFaction+" or RewRepFaction4="+RewRepFaction+")";
		
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated RewRepFaction stub
		return false;
	}
}
