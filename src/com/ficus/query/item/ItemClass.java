package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class ItemClass extends ArrayList<KeyValue> implements QueryItemInterface{
	public ItemClass(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"Consumable可磨损"));
		add(new KeyValue(1,"Container容器"));
		add(new KeyValue(2,"Weapon武器"));
		add(new KeyValue(3,"Gem宝石"));
		add(new KeyValue(4,"Armor护甲"));
		add(new KeyValue(5,"Reagent试剂"));
		add(new KeyValue(6,"Projectile弹丸"));
		add(new KeyValue(7,"Trade Goods贸易货物"));
		add(new KeyValue(8,"Generic(OBSOLETE)通用(已过时)"));
		add(new KeyValue(9,"Recipe书谱"));
		add(new KeyValue(10,"Money(OBSOLETE)钱(已过时)"));
		add(new KeyValue(11,"Quiver给予任务"));
		add(new KeyValue(12,"Quest任务"));
		add(new KeyValue(13,"Key钥匙"));
		add(new KeyValue(14,"Permanent(OBSOLETE)永恒 (已过时)"));
		add(new KeyValue(15,"Miscellaneous杂项"));
		add(new KeyValue(16,"Glyph标志符号"));			   
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>类别：<select onchange='reload()' id='ItemClass'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.ItemClass = $('#ItemClass').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("ItemClass");
		if(type!=null&&!"-1".equals(type))
			return "class="+type;
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
