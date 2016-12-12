package com.ficus.query.quest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class SpecialFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public SpecialFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"No extra无额外标志"));
		add(new KeyValue(1,"QUEST_SPECIAL_FLAG_REPEATABLE让任务可重复"));
		add(new KeyValue(2,"QUEST_SPECIAL_FLAG_EXPLORATION_OR_EVENT外部事件如areatrigger_involvedrelation.entry或spell_scripts.entry(command=7)"));
		add(new KeyValue(4,"QUEST_SPECIAL_FLAG_MONTHLY月任务"));
		add(new KeyValue(8,"QUEST_SPECIAL_FLAG_DELIVER递送任务"));
		add(new KeyValue(16,"QUEST_SPECIAL_FLAG_SPEAKTO讲话"));
		add(new KeyValue(20,"QUEST_SPECIAL_FLAG_KILL_OR_CAST杀死或施法"));
		add(new KeyValue(20,"QUEST_SPECIAL_FLAG_TIMED时间"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>额外标志：<select onchange='reload()' id='SpecialFlags'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.SpecialFlags = $('#SpecialFlags').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String SpecialFlags=request.getParameter("SpecialFlags");
		if(SpecialFlags==null||"-1".equals(SpecialFlags))
			return "";
		else if(SpecialFlags.equals("0"))	
			return "SpecialFlags=0";
		else 
			return "SpecialFlags&"+SpecialFlags;
	}


	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
