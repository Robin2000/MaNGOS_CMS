package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class InhabitTypeTrinity extends ArrayList<KeyValue> implements QueryItemInterface{
	public InhabitTypeTrinity(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"未设置"));
		add(new KeyValue(1,"Ground"));
		add(new KeyValue(2,"Water"));
		add(new KeyValue(4,"Flying"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>移动媒介：<select onchange='reload()' id='InhabitType'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.InhabitType = $('#InhabitType').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String InhabitType=request.getParameter("InhabitType");
		if(InhabitType==null||"-1".equals(InhabitType))
			return "";
		else if(InhabitType.equals("0"))	
			return "InhabitType=0";
		else 
			return "InhabitType&"+InhabitType;
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
