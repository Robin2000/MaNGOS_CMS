package com.ficus.query.quest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.dbc.DBCStore;
import com.ficus.dbc.SkillLine;
import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class RequiredSkill extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public RequiredSkill(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"不限"));
		List<SkillLine> list=DBCStore.me.getSkillLineList();
		for(SkillLine at:list)
			add(new KeyValue(at.id,at.toString()));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>技能需求：<select onchange='reload()' id='RequiredSkill'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.RequiredSkill = $('#RequiredSkill').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String RequiredSkill=request.getParameter("RequiredSkill");
		if(RequiredSkill==null||"-1".equals(RequiredSkill))
			return "";
		else 
			return "RequiredSkill="+RequiredSkill;
		
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated RequiredSkill stub
		return false;
	}
}
