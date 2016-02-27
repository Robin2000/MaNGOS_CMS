package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class RequiredReputationRank extends ArrayList<KeyValue> implements QueryItemInterface{
	public RequiredReputationRank(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"Hated讨厌"));
		add(new KeyValue(1,"Hostile敌对"));
		add(new KeyValue(2,"Unfriendly不友好"));
		add(new KeyValue(3,"Neutral中性"));
		add(new KeyValue(4,"Friendly友好"));
		add(new KeyValue(5,"Honored荣幸"));
		add(new KeyValue(6,"Revered崇敬"));
		add(new KeyValue(7,"Exalted尊贵"));
			
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>拥有荣誉等级：<select onchange='reload()' id='RequiredReputationRank'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.RequiredReputationRank = $('#RequiredReputationRank').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("RequiredReputationRank");
		if(type!=null&&!"-1".equals(type))
			return "RequiredReputationRank="+type;
		else
			return "";
	}
	public String getFilterTable(){
		return "item_template";
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
