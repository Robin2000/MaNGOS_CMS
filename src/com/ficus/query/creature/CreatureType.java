package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class CreatureType extends ArrayList<KeyValue> implements QueryItemInterface{
	public CreatureType(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"None"));
		add(new KeyValue(1,"Beast野兽"));
		add(new KeyValue(2,"Dragonkin龙族"));
		add(new KeyValue(3,"Demon恶魔"));
		add(new KeyValue(4,"Elemental元素精灵"));
		add(new KeyValue(5,"Giant巨人"));
		add(new KeyValue(6,"Undead不死族"));
		add(new KeyValue(7,"Humanoid类人生物"));
		add(new KeyValue(8,"Critter小动物"));
		add(new KeyValue(9,"Mechanical机械"));
		add(new KeyValue(10,"Not specified不指定"));
		add(new KeyValue(11,"Totem图腾"));
		add(new KeyValue(12,"Non-combat Pet非战斗宠物"));
		add(new KeyValue(13,"Gas Cloud气云"));
			   
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>生物类别：<select onchange='reload()' id='creature_type'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.creatureType = $('#creature_type').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String creatureType=request.getParameter("creatureType");
		if(creatureType!=null&&!"-1".equals(creatureType))
			return "creatureType="+creatureType;
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
