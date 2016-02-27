package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

/**
 * 
 * <b>该类是用于：</b>按下Z键取下武器的动作<br/>
 * <b>@author：</b> 29698868@qq.com <br/>
 * <b>@since JDK1.7</b><br/>
 * <b>@history </b>2016年2月6日 <br/>
 */
public class Sheath extends ArrayList<KeyValue> implements QueryItemInterface{
	public Sheath(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"Two Handed Weapon双手武器,斜穿过背朝下"));
		add(new KeyValue(2,"Staff权杖,穿过了背朝上"));
		add(new KeyValue(3,"One Handed单手武器,到以边"));
		add(new KeyValue(4,"Shield盾牌,背面中间"));
		add(new KeyValue(5,"Enchanter’s  Rod附魔师的杆"));
		add(new KeyValue(6,"Off hand副手，另一边手"));
	
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>z键取放动作：<select onchange='reload()' id='Sheath'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.Sheath = $('#Sheath').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("Sheath");
		if(type!=null&&!"-1".equals(type))
			return "Sheath="+type;
		else
			return "";
	}
	public String getFilterTable(){
		return "item_template";
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
