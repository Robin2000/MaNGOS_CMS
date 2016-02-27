package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class SpellTrigger extends ArrayList<KeyValue> implements QueryItemInterface{
	public SpellTrigger(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"Use使用"));
		add(new KeyValue(1,"On Equip装备"));
		add(new KeyValue(2,"Chance on Hit命中"));
		add(new KeyValue(4,"Soulstone灵魂石"));
		add(new KeyValue(5,"Use with no delay使用无延时"));
		add(new KeyValue(6,"Learn spell如果spellid_1为55884学习技能，该法术叫学习"));
	
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>技能触发：<select onchange='reload()' id='spelltrigger'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.spelltrigger = $('#spelltrigger').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("spelltrigger");
		if(type!=null&&!"-1".equals(type))
		{
			StringBuilder sb=new StringBuilder("(");
			for(int i=1;i<=5;i++)
			{
				if(i>1)
					sb.append(" OR ");
				sb.append("spelltrigger_").append(i).append("=").append(type);
			}
			sb.append(")");
			return sb.toString();
		}
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
