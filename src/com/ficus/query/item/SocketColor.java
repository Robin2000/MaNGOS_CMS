package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class SocketColor extends ArrayList<KeyValue> implements QueryItemInterface{
	public SocketColor(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"Meta"));
		add(new KeyValue(2,"Red"));
		add(new KeyValue(4,"Yellow"));
		add(new KeyValue(8,"Blue"));
	
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>插孔支持：<select onchange='reload()' id='SocketColor'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.SocketColor = $('#SocketColor').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("SocketColor");
		if(type!=null&&!"-1".equals(type))
			return "(SocketColor_1="+type+" or SocketColor_2="+type+" or SocketColor_3="+type+")";
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
