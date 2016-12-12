package com.ficus.query.quest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class QuestFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public QuestFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"QuestFlags_QuestFlags_NONE无"));
		add(new KeyValue(1,"QuestFlags_QuestFlags_STAY_ALIVE不能死"));
		add(new KeyValue(2,"QuestFlags_QuestFlags_EVENT护送任务或任何其他事件驱动的任务，玩家将收到确认框接受。"));
		add(new KeyValue(4,"QuestFlags_QuestFlags_EXPLORATION涉及到地区触发器的激活"));
		add(new KeyValue(8,"QuestFlags_QuestFlags_SHARABLE允许与其他玩家共同"));
		add(new KeyValue(16,"QuestFlags_QuestFlags_NONE2"));
		add(new KeyValue(32,"QuestFlags_QuestFlags_EPIC史诗级任务 (猎人)"));
		add(new KeyValue(64,"QuestFlags_QuestFlags_RAID类似玩家组或Raid的任务。"));
		add(new KeyValue(128,"QuestFlags_QuestFlags_TBC是TBC后添加"));
		add(new KeyValue(256,"QuestFlags_QuestFlags_UNK2任务需要额外物品，非目标物non-objective"));
		add(new KeyValue(512,"QuestFlags_QuestFlags_HIDDEN_REWARDS报酬只出现一次，日志中不显示"));
		add(new KeyValue(1024,"QuestFlags_QuestFlags_AUTO_REWARDED任务都是自动奖励"));
		
		add(new KeyValue(2048,"QuestFlags_QuestFlags_TBC_RACES血精灵/德莱尼新手区任务"));
		add(new KeyValue(4096,"QuestFlags_QuestFlags_DAILY每日可重复任务"));
		add(new KeyValue(8192,"QuestFlags_QuestFlags_REPEATABLE可重复任务"));
		
		//trinity
		add(new KeyValue(16384,"QuestFlags_QuestFlags_UNAVAILABLE使用上不是一般可用的任务【trinity】"));
		add(new KeyValue(32768,"QuestFlags_QuestFlags_WEEKLY每周可重复任务【trinity】 "));
		add(new KeyValue(65536,"QuestFlags_QuestFlags_AUTOCOMPLETE可自动完成任务【trinity】"));
		add(new KeyValue(131072,"QuestFlags_QuestFlags_SPECIAL_ITEM需要RequiredItemId 和 SourceItemId做【trinity】"));
		add(new KeyValue(262144,"QuestFlags_QuestFlags_OBJ_TEXT使用Objective text作为Complete text【trinity】"));
		add(new KeyValue(524288,"QuestFlags_QuestFlags_AUTO_ACCEPT在新手区域任务【trinity】"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>标志：<select onchange='reload()' id='QuestFlags'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.QuestFlags = $('#QuestFlags').val();\n";
	}
	
	/*服务端从HttpServletReQuestFlags取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String QuestFlags=request.getParameter("QuestFlags");
		if(QuestFlags==null||"-1".equals(QuestFlags))
			return "";
		else if(QuestFlags.equals("0"))	
			return "QuestFlags=0";
		else 
			return "QuestFlags&"+QuestFlags;
	}


	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
