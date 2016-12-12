package com.ficus.query.quest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.dbc.CharTitles;
import com.ficus.dbc.DBCStore;
import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class CharTitlesFilter extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public CharTitlesFilter(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"无"));
		List<CharTitles> list=DBCStore.me.getCharTitlesList();
		for(CharTitles at:list)
			add(new KeyValue(at.id,at.toString()));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>获得称号：<select onchange='reload()' id='CharTitles'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.CharTitles = $('#CharTitles').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String CharTitles=request.getParameter("CharTitles");
		if(CharTitles==null||"-1".equals(CharTitles))
			return "";
		else 
			return "CharTitleId="+CharTitles;
		
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated CharTitles stub
		return false;
	}
}
