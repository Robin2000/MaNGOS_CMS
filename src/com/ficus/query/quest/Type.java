package com.ficus.query.quest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.dbc.DBCStore;
import com.ficus.dbc.QuestInfo;
import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

/**
 * 
 * <b>该类是用于：QuestInfo.dbc</b><br/>
 */
public class Type extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public Type(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"不限"));
		List<QuestInfo> list=DBCStore.me.getQuestInfoList();
		for(QuestInfo at:list)
			add(new KeyValue(at.id,at.toString()));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>类别：<select onchange='reload()' id='Type'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.Type = $('#Type').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String Type=request.getParameter("Type");
		if(Type==null||"-1".equals(Type))
			return "";
		else 
			return "Type="+Type;
		
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated Type stub
		return false;
	}
}
