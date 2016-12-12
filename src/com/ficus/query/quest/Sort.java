package com.ficus.query.quest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.dbc.DBCStore;
import com.ficus.dbc.QuestSort;
import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class Sort extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public Sort(){
		add(new KeyValue(-2,"全部"));

		List<QuestSort> list=DBCStore.me.getQuestSortList();
		for(QuestSort qs:list)
			add(new KeyValue(-qs.id,qs.toString()));//id为负数表示的是任务类别
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>分类：<select onchange='reload()' id='ZoneOrSort2'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.ZoneOrSort2 = $('#ZoneOrSort2').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String ZoneOrSort=request.getParameter("ZoneOrSort2");
		if(ZoneOrSort==null||"-2".equals(ZoneOrSort))
			return "";
		else 
			return "ZoneOrSort="+ZoneOrSort;
		
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated ZoneOrSort stub
		return false;
	}
}
