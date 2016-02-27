package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class TotemCategory extends ArrayList<KeyValue> implements QueryItemInterface{
	public TotemCategory(){
		add(new KeyValue(-1,"全部"));

		add(new KeyValue(1,"Skinning Knife (OLD)"));
		add(new KeyValue(2,"Earth Totem"));
		add(new KeyValue(3,"Air Totem"));
		add(new KeyValue(4,"Fire Totem"));
		add(new KeyValue(5,"Water Totem"));
		add(new KeyValue(6,"Runed Copper Rod"));
		add(new KeyValue(7,"Runed Silver Rod"));
		add(new KeyValue(8,"Runed Golden Rod"));
		add(new KeyValue(9,"Runed Truesilver Rod"));
		add(new KeyValue(10,"Runed Arcanite Rod"));
		
		add(new KeyValue(11,"Mining Pick (OLD)"));
		add(new KeyValue(12,"Philosopher's Stone"));
		add(new KeyValue(13,"Blacksmith Hammer (OLD)"));
		add(new KeyValue(14,"Arclight Spanner"));
		add(new KeyValue(15,"Gyromatic Micro-Adjustor"));
		
		add(new KeyValue(21,"Master Totem"));		
		add(new KeyValue(41,"Runed Fel Iron Rod"));		
		add(new KeyValue(62,"Runed Adamantite Rod"));		
		add(new KeyValue(63,"Runed Eternium Rod"));		
		add(new KeyValue(81,"Hollow Quill"));		
		add(new KeyValue(101,"Runed Azurite Rod"));	
		add(new KeyValue(121,"Virtuoso Inking Set"));	
		add(new KeyValue(141,"Drums"));	
		add(new KeyValue(161,"Gnomish Army Knife"));	
		add(new KeyValue(162,"Blacksmith Hammer"));	
		add(new KeyValue(165,"Mining Pick"));	
		add(new KeyValue(166,"Skinning Knife"));	
		add(new KeyValue(167,"Hammer Pick"));		
		add(new KeyValue(168,"Bladed Pickaxe"));	
		add(new KeyValue(169,"Flint and Tinder"));	
		add(new KeyValue(189,"Runed Cobalt Rod"));	
		add(new KeyValue(190,"Runed Titanium Rod"));	
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>图腾类：<select onchange='reload()' id='TotemCategory'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.TotemCategory = $('#TotemCategory').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("TotemCategory");
		if(type!=null&&!"-1".equals(type))
			return "TotemCategory="+type;
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
