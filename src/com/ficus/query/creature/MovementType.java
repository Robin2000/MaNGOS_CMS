package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class MovementType extends ArrayList<KeyValue> implements QueryItemInterface{
	public MovementType(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"Stand Still禁止不动"));
		add(new KeyValue(1,"Random Movement随机移动"));
		add(new KeyValue(2,"Waypoint Movement按路径移动"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>移动类型：<select onchange='reload()' id='MovementType'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.MovementType = $('#MovementType').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String MovementType=request.getParameter("MovementType");
		if(MovementType!=null&&!"-1".equals(MovementType))
			return "MovementType="+MovementType;
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
