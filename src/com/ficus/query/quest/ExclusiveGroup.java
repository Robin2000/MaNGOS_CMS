package com.ficus.query.quest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class ExclusiveGroup extends ArrayList<KeyValue> implements QueryItemInterface{
	public ExclusiveGroup(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,">0排它任务（ExclusiveGroup组中任务只能完成一个）"));
		add(new KeyValue(1,"<0同时任务（ExclusiveGroup组中任务必须全部完成）"));

	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>排它性：<select onchange='reload()' id='ExclusiveGroup'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.ExclusiveGroup = $('#ExclusiveGroup').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String ExclusiveGroup=request.getParameter("ExclusiveGroup");
		if(ExclusiveGroup==null||"-1".equals(ExclusiveGroup))
			return "";
		else if(ExclusiveGroup.equals("0"))	
			return "ExclusiveGroup>0";
		else 
			return "ExclusiveGroup<"+ExclusiveGroup;
	}


	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
