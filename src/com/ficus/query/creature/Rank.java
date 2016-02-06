package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class Rank extends ArrayList<KeyValue> implements QueryItemInterface{
	public Rank(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"Normal"));
		add(new KeyValue(1,"Elite更高伤害，更多血，更好的爆落。"));
		add(new KeyValue(2,"Rare Elite稀有，精英伤害与血量。"));
		add(new KeyValue(3,"World Boss最高等级，最好的战利品，重生时间最长"));
		add(new KeyValue(4,"Rare稍好的爆落重生时间长"));

			   
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>生物评级：<select onchange='reload()' id='Rank'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.Rank = $('#Rank').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String Rank=request.getParameter("Rank");
		if(Rank!=null&&!"-1".equals(Rank))
			return "Rank="+Rank;
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
