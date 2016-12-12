package com.ficus.query.quest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class RequiredClasses extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public RequiredClasses(){
	/*标志位：eg: 2^(ID-1).*/
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"不限职业"));
		add(new KeyValue(1,"Warrior战士"));
		add(new KeyValue(2,"Paladin圣骑士"));
		add(new KeyValue(3,"Hunter猎人"));
		add(new KeyValue(4,"Rogue潜行者"));
		add(new KeyValue(5,"Priest牧师"));
		add(new KeyValue(6,"Death Knight黑暗骑士"));
		add(new KeyValue(7,"Shaman萨满"));
		add(new KeyValue(8,"Mage法师"));
		add(new KeyValue(9,"Warlock术士"));
		add(new KeyValue(11,"Druid德鲁伊"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>职业：<select onchange='reload()' id='RequiredClasses'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.RequiredClasses = $('#RequiredClasses').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String RequiredClasses=request.getParameter("RequiredClasses");
		if(RequiredClasses==null||"-1".equals(RequiredClasses))
			return "";
		else if(RequiredClasses.equals("0"))	
			return "RequiredClasses=0";
		else 
			return "RequiredClasses&"+Math.pow(2,Integer.parseInt(RequiredClasses)-1);
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
