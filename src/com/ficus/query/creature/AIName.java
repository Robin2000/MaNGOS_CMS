package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class AIName extends ArrayList<KeyValue> implements QueryItemInterface{
	public AIName(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue("NullAI","无AI"));
		add(new KeyValue("AggressorAI","生物攻击只要是仇恨范围中的东西"));
		add(new KeyValue("ReactorAI","生物被法术等攻击时反抗攻击"));
		add(new KeyValue("GuardAI","卫兵AI主动攻击"));
		add(new KeyValue("PetAI","宠物AI"));
		add(new KeyValue("TotemAI","从字段spell1施放技能，否则空AI"));
		add(new KeyValue("EventAI","基于事件的AI"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>人工智能：<select onchange='reload()' id='AIName'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.AIName = $('#AIName').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String AIName=request.getParameter("AIName");
		if("NullAI".equals(AIName))
			return "(AIName is null or AIName='')";
		else if(AIName!=null&&!"-1".equals(AIName))
			return "AIName='"+AIName+"'";
		else
			return "";
	}
	public String getFilterTable(){
		return "creature_template";
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
