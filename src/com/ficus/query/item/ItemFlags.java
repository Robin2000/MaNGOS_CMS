package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class ItemFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public ItemFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"Soulbound灵魂绑定"));
		add(new KeyValue(2,"Conjured召唤物"));
		add(new KeyValue(4,"Openable  可右键点开"));
		add(new KeyValue(8,"带有绿色Heroic文本在物品上"));
		add(new KeyValue(16,"Deprecated废弃"));
		add(new KeyValue(32,"不可摧毁，技能使用Totem图腾"));
		add(new KeyValue(64,"Activatable 活动，可右键点"));
		add(new KeyValue(128,"没有缺省30秒冷却"));
		add(new KeyValue(512,"Wrapper可以包装其它物品"));
		add(new KeyValue(1024,"Gifts礼物"));
		
		add(new KeyValue(2048,"Item团队爆落可被抢"));
		add(new KeyValue(4096,"Itemrefundable物品可退还"));
		add(new KeyValue(8192,"Charter (Arena or Guild)宪章（竞技场工会）"));
		add(new KeyValue(32768,"PvP reward item奖励的PvP物品"));
		add(new KeyValue(262144,"Item can be prospected可以勘探的物品"));
		add(new KeyValue(524288,"Unique equipped唯一的装备 (玩家同时只能装备1个)"));
		add(new KeyValue(2097152,"Item can be used during arena match物品用于竞技场比赛"));
		add(new KeyValue(4194304,"Throwable 可抛出(为tooltip ingame)游戏对象已被摧毁"));
		add(new KeyValue(8388608,"shapeshift forms变形形式"));
		
		add(new KeyValue(33554432," Profession recipes: 专业秘谱仅在你达到需求还没有学会的时候出现"));
		add(new KeyValue(67108864," 物品可用于竞技场"));
		add(new KeyValue(134217728,"Bind to Account 绑定到帐户(需要 Quality = 7 设置)"));
		add(new KeyValue(268435456," Spell is cast with triggered flag带有触发技能标志"));
		add(new KeyValue(536870912," Millable 混炼型"));
		add(new KeyValue(2147483648L,"Bind on Pickup tradeable可交易的拾取绑定"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>标志：<select onchange='reload()' id='flag_type'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.flags = $('#flag_type').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String flags=request.getParameter("flags");
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
