package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class ItemQuality extends ArrayList<KeyValue> implements QueryItemInterface{
	public ItemQuality(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"Grey灰色差Poor"));
		add(new KeyValue(1,"White白色普通Common"));
		add(new KeyValue(2,"Green绿色不不同Uncommon"));
		add(new KeyValue(3,"Blue蓝色稀有Rare"));
		add(new KeyValue(4,"Purple紫色史诗"));
		add(new KeyValue(5,"Orange橙色传奇Legendary"));
		add(new KeyValue(6,"Red红色神器Artifact"));	
		add(new KeyValue(7,"Gold金色绑定到账号Bind to Account"));		   
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>质量：<select onchange='reload()' id='ItemQuality'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.ItemQuality = $('#ItemQuality').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("ItemQuality");
		if(type!=null&&!"-1".equals(type))
			return "Quality="+type;
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
