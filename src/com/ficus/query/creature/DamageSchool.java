package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class DamageSchool extends ArrayList<KeyValue> implements QueryItemInterface{
	public DamageSchool(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"SPELL_SCHOOL_NORMAL普通伤害"));
		add(new KeyValue(1,"SPELL_SCHOOL_HOLY神圣伤害"));
		add(new KeyValue(2,"SPELL_SCHOOL_FIRE火焰伤害"));
		add(new KeyValue(3,"SPELL_SCHOOL_NATURE自然伤害"));
		add(new KeyValue(4,"SPELL_SCHOOL_FROST霜冻伤害"));
		add(new KeyValue(5,"SPELL_SCHOOL_SHADOW暗影伤害"));
		add(new KeyValue(6,"SPELL_SCHOOL_ARCANE奥术伤害"));

			   
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>伤害类别：<select onchange='reload()' id='DamageSchool'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.DamageSchool = $('#DamageSchool').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String DamageSchool=request.getParameter("DamageSchool");
		if(DamageSchool!=null&&!"-1".equals(DamageSchool))
			return "DamageSchool="+DamageSchool;
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
