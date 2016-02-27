package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class FoodType extends ArrayList<KeyValue> implements QueryItemInterface{
	public FoodType(){
		add(new KeyValue(-1,"全部"));

		add(new KeyValue(1,"Meat"));
		add(new KeyValue(2,"Fish"));
		add(new KeyValue(3,"Cheese"));
		add(new KeyValue(4,"Bread"));
		add(new KeyValue(5,"Fungus菌类"));
		add(new KeyValue(6,"Fruit"));
		add(new KeyValue(7,"Raw Meat生肉"));
		add(new KeyValue(8,"Raw Fish生鱼"));
	
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>食物类别：<select onchange='reload()' id='FoodType'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.FoodType = $('#FoodType').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("FoodType");
		if(type!=null&&!"-1".equals(type))
			return "FoodType="+type;
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
