package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class Material extends ArrayList<KeyValue> implements QueryItemInterface{
	public Material(){
		add(new KeyValue(-2,"全部"));
		add(new KeyValue(-1,"Consumables消耗品，食物等"));
		add(new KeyValue(0,"Not Defined未定义"));
		add(new KeyValue(1,"Metal金属"));
		add(new KeyValue(2,"Wood木头"));
		add(new KeyValue(3,"Liquid液体"));
		add(new KeyValue(4,"Jewelry珠宝"));
		add(new KeyValue(5,"Chain锁甲"));
		add(new KeyValue(6,"Plate"));
		add(new KeyValue(7,"Cloth布甲"));
		add(new KeyValue(8,"Leather皮革"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>材料：<select onchange='reload()' id='Material'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.Material = $('#Material').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("Material");
		if(type!=null&&!"-2".equals(type))
			return "Material="+type;
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
