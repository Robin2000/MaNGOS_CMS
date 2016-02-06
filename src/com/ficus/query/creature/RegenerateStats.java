package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class RegenerateStats extends ArrayList<KeyValue> implements QueryItemInterface{
	public RegenerateStats(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"NPC离开战斗不恢复状态"));
		add(new KeyValue(1,"NPC离开战斗回血Regenerate Health"));
		add(new KeyValue(2,"NPC离开战斗回蓝Regenerate Power (Mana)"));
		add(new KeyValue(3,"NPC离开战斗回血回蓝"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>生物恢复：<select onchange='reload()' id='RegenerateStats'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.RegenerateStats = $('#RegenerateStats').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String RegenerateStats=request.getParameter("RegenerateStats");
		if(RegenerateStats!=null&&!"-1".equals(RegenerateStats))
			return "RegenerateStats="+RegenerateStats;
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
