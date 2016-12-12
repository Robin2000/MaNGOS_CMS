package com.ficus.query.quest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ficus.dbc.AreaTable;
import com.ficus.dbc.DBCStore;
import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class Zone extends ArrayList<KeyValue> implements QueryItemInterface{
	
	public Zone(){
		add(new KeyValue(-1,"全部"));

		List<AreaTable> list=DBCStore.me.getAreaTableList();
		for(AreaTable at:list)
			add(new KeyValue(at.id,at.toString()));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>地区：<select onchange='reload()' id='ZoneOrSort'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.ZoneOrSort = $('#ZoneOrSort').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String ZoneOrSort=request.getParameter("ZoneOrSort");
		if(ZoneOrSort==null||"-1".equals(ZoneOrSort))
			return "";
		else 
			return "ZoneOrSort="+ZoneOrSort;
		
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated ZoneOrSort stub
		return false;
	}
}
