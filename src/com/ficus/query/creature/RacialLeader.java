package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class RacialLeader extends ArrayList<KeyValue> implements QueryItemInterface{
	public RacialLeader(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"NOT Racial Leader不是种族领袖"));
		add(new KeyValue(1,"IS Racial Leader是种族领袖"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>种族领袖：<select onchange='reload()' id='RacialLeader'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.RacialLeader = $('#RacialLeader').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String RacialLeader=request.getParameter("RacialLeader");
		if(RacialLeader!=null&&!"-1".equals(RacialLeader))
			return "RacialLeader="+RacialLeader;
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
