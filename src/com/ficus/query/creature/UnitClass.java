package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class UnitClass extends ArrayList<KeyValue> implements QueryItemInterface{
	public UnitClass(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"CLASS_WARRIOR战士"));
		add(new KeyValue(2,"CLASS_PALADIN圣骑士"));
		add(new KeyValue(4,"CLASS_ROGUE潜行者"));
		add(new KeyValue(8,"CLASS_MAGE法师"));

			   
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>单位职业：<select onchange='reload()' id='UnitClass'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.UnitClass = $('#UnitClass').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String UnitClass=request.getParameter("UnitClass");
		if(UnitClass!=null&&!"-1".equals(UnitClass))
			return "UnitClass="+UnitClass;
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
