package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class BagFamily extends ArrayList<KeyValue> implements QueryItemInterface{
	public BagFamily(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"None无"));
		add(new KeyValue(1,"Arrows箭头"));
		add(new KeyValue(2,"Bullets子弹"));
		add(new KeyValue(4,"Soul Shards灵魂碎片"));
		add(new KeyValue(8,"Leatherworking Supplies皮革用品"));
		add(new KeyValue(16,"Inscription Supplies铭文用品"));
		add(new KeyValue(32,"Herbs草药"));
		add(new KeyValue(64,"Enchanting Supplies附魔用品"));
		add(new KeyValue(128,"Engineering Supplies工程用品"));
		add(new KeyValue(256,"Keys钥匙"));
		add(new KeyValue(512,"Gems宝石"));
		add(new KeyValue(1024,"Mining Supplies挖矿用品"));
		
		add(new KeyValue(2048,"Soulbound Equipment灵魂绑定装备"));
		add(new KeyValue(4096,"Vanity Pets虚荣的宠物"));
		add(new KeyValue(8192,"Currency Tokens货币符号"));
		add(new KeyValue(16384,"Quest Items任务物品"));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>包族：<select onchange='reload()' id='BagFamily'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.BagFamily = $('#BagFamily').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String flags=request.getParameter("BagFamily");
		if(flags==null||"-1".equals(flags))
			return "";
		else if(flags.equals("0"))	
			return "flags=0";
		else 
			return "flags&"+flags;
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
