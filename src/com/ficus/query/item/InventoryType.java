package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class InventoryType extends ArrayList<KeyValue> implements QueryItemInterface{
	public InventoryType(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"Non equipable不可装备"));
		add(new KeyValue(1,"Head头"));
		add(new KeyValue(2,"Neck脖子"));
		add(new KeyValue(3,"Shoulder肩膀"));
		add(new KeyValue(4,"Shirt衬衫"));
		add(new KeyValue(5,"Chest胸部"));
		add(new KeyValue(6,"Waist腰"));
		add(new KeyValue(7,"Legs腿"));
		add(new KeyValue(8,"Feet双脚"));
		add(new KeyValue(9,"Wrists手腕"));
		add(new KeyValue(10,"Hands手"));
		add(new KeyValue(11,"Finger手指"));
		add(new KeyValue(12,"Trinket小饰品"));
		add(new KeyValue(13,"Weapon武器"));
		add(new KeyValue(14,"Shield盾牌"));
		add(new KeyValue(15,"Ranged (Bows)远程 (弓)"));
		add(new KeyValue(16,"Back背"));		
		add(new KeyValue(17,"Two-Hand双手"));		
		add(new KeyValue(18,"Bag包"));		
		add(new KeyValue(19,"Tabard战袍"));		
		add(new KeyValue(20,"Robe连衣裙"));		
		add(new KeyValue(21,"Main hand主手"));	
		add(new KeyValue(22,"Off hand副手"));	
		add(new KeyValue(23,"Holdable (Tome)可持有（卷册）"));	
		add(new KeyValue(24,"Ammo弹药"));	
		add(new KeyValue(25,"Thrown投掷类"));	
		add(new KeyValue(26,"Ranged right (Wands, Guns)远程权 (魔杖，枪)"));	
		add(new KeyValue(27,"Quiver箭袋"));	
		add(new KeyValue(28,"Relic圣物"));		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>库存位置：<select onchange='reload()' id='InventoryType'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.InventoryType = $('#InventoryType').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("InventoryType");
		if(type!=null&&!"-1".equals(type))
			return "InventoryType="+type;
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
