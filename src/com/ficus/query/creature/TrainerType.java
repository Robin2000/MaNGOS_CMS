package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class TrainerType extends ArrayList<KeyValue> implements QueryItemInterface{
	public TrainerType(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"TRAINER_TYPE_CLASS职业训练"));
		add(new KeyValue(1,"TRAINER_TYPE_MOUNTS骑乘训练"));
		add(new KeyValue(2,"TRAINER_TYPE_TRADESKILLS商业技能训练"));
		add(new KeyValue(3,"TRAINER_TYPE_PETS宠物训练"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>训练类型：<select onchange='reload()' id='TrainerType'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.TrainerType = $('#TrainerType').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String TrainerType=request.getParameter("TrainerType");
		if(TrainerType!=null&&!"-1".equals(TrainerType))
			return "TrainerType="+TrainerType;
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
