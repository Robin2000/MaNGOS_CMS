package com.ficus.query.quest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class Method extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public Method(){
	/*标志位：eg: 2^(ID-1).*/
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"autocompleted自动完成"));
		add(new KeyValue(1,"disabled禁用"));
		add(new KeyValue(2,"普通"));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>处理方法：<select onchange='reload()' id='Method'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.Method = $('#Method').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String Method=request.getParameter("Method");
		if(Method==null||"-1".equals(Method))
			return "";
		else 
			return "Method="+Method;
		
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
