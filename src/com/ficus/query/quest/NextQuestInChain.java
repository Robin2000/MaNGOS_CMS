package com.ficus.query.quest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class NextQuestInChain extends ArrayList<KeyValue> implements QueryItemInterface{
	public NextQuestInChain(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"=0后置任务不出现"));
		add(new KeyValue(1,">0后置任务立刻出现"));

	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>后置任务链：<select onchange='reload()' id='NextQuestInChain'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.NextQuestInChain = $('#NextQuestInChain').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String NextQuestInChain=request.getParameter("NextQuestInChain");
		if(NextQuestInChain==null||"-1".equals(NextQuestInChain))
			return "";
		else if(NextQuestInChain.equals("0"))	
			return "NextQuestInChain=0";
		else 
			return "NextQuestInChain>0";
	}


	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
