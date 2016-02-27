package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class Bonding extends ArrayList<KeyValue> implements QueryItemInterface{
	public Bonding(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"拾取绑定"));
		add(new KeyValue(2,"装备绑定"));
		add(new KeyValue(3,"使用绑定"));
		add(new KeyValue(4,"任务物品"));
		add(new KeyValue(5,"任务物品2"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>绑定类别：<select onchange='reload()' id='Bonding'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.Bonding = $('#Bonding').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("Bonding");
		if(type!=null&&!"-1".equals(type))
			return "Bonding="+type;
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
