package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class DynamicFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public DynamicFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"UNIT_DYNFLAG_NONE"));
		add(new KeyValue(1,"UNIT_DYNFLAG_LOOTABLE"));
		add(new KeyValue(2,"UNIT_DYNFLAG_TRACK_UNIT怪物的位置在小地图显示"));
		add(new KeyValue(4,"UNIT_DYNFLAG_TAPPED怪物名字灰色"));
		add(new KeyValue(8,"UNIT_DYNFLAG_TAPPED_BY_PLAYER玩家控制飞行器PCV"));
		add(new KeyValue(16,"UNIT_DYNFLAG_SPECIALINFO"));
		add(new KeyValue(32,"UNIT_DYNFLAG_DEAD让怪物外观是死亡"));
		add(new KeyValue(64,"UNIT_DYNFLAG_REFER_A_FRIEND"));
		add(new KeyValue(128,"UNIT_DYNFLAG_TAPPED_BY_ALL_THREAT_LIST"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>动态标志：<select onchange='reload()' id='DynamicFlags'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.DynamicFlags = $('#DynamicFlags').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String DynamicFlags=request.getParameter("DynamicFlags");
		if(DynamicFlags==null||"-1".equals(DynamicFlags))
			return "";
		else if(DynamicFlags.equals("0"))	
			return "DynamicFlags=0";
		else 
			return "DynamicFlags&"+DynamicFlags;
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
