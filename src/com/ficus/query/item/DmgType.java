package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class DmgType extends ArrayList<KeyValue> implements QueryItemInterface{
	public DmgType(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"Physical物理"));
		add(new KeyValue(1,"Holy圣神"));
		add(new KeyValue(2,"Fire火焰"));
		add(new KeyValue(3,"Nature自然"));
		add(new KeyValue(4,"Frost冰霜"));
		add(new KeyValue(5,"Shadow暗影"));
		add(new KeyValue(6,"Arcane奥术"));
	
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>伤害类别：<select onchange='reload()' id='DmgType'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.DmgType = $('#DmgType').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("DmgType");
		if(type!=null&&!"-1".equals(type))
			return "(Dmg_Type1="+type+" OR Dmg_Type2="+type+")";
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
