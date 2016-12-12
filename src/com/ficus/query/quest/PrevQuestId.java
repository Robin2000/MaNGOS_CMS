package com.ficus.query.quest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class PrevQuestId extends ArrayList<KeyValue> implements QueryItemInterface{
	public PrevQuestId(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,">0前置任务必须完成"));
		add(new KeyValue(1,"<0前置任务必须激活"));

	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>前置任务：<select onchange='reload()' id='PrevQuestId'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.PrevQuestId = $('#PrevQuestId').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String PrevQuestId=request.getParameter("PrevQuestId");
		if(PrevQuestId==null||"-1".equals(PrevQuestId))
			return "";
		else if(PrevQuestId.equals("0"))	
			return "PrevQuestId>0";
		else 
			return "PrevQuestId<"+PrevQuestId;
	}


	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
