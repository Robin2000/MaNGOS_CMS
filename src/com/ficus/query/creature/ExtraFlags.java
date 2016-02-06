package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class ExtraFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public ExtraFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"CREATURE_FLAG_EXTRA_INSTANCE_BIND副本绑定"));
		add(new KeyValue(2,"CREATURE_FLAG_EXTRA_CIVILIAN忽略仇恨"));
		add(new KeyValue(4,"CREATURE_FLAG_EXTRA_NO_PARRY禁止格挡"));
		add(new KeyValue(8,"CREATURE_FLAG_EXTRA_NO_PARRY_HASTEN生物招架不会加速其下一次攻击"));
		add(new KeyValue(16,"CREATURE_FLAG_EXTRA_NO_BLOCK禁止生物打断"));
		add(new KeyValue(32,"CREATURE_FLAG_EXTRA_NO_CRUSH禁止生物处理毁灭性的打击"));
		add(new KeyValue(64,"CREATURE_FLAG_EXTRA_NO_XP_AT_KILL使生物击杀奖励没有 XP"));
		add(new KeyValue(128,"CREATURE_FLAG_EXTRA_TRIGGER生物是触发 NPC (仅玩家可见)"));
		add(new KeyValue(256,"CREATURE_FLAG_EXTRA_NO_TAUNT生物是免疫嘲讽的光环且影响攻击我"));
		add(new KeyValue(512,"CREATURE_FLAG_EXTRA_AGGRO_ZONE生物本身设置战斗带仇恨"));
		add(new KeyValue(1024,"CREATURE_FLAG_EXTRA_GUARD生物是守卫，如果守卫死亡公告受到攻击"));
		add(new KeyValue(2048,"CREATURE_FLAG_EXTRA_NO_TALKTO_CREDIT生物对话时不给任务荣誉，(临时标志)。"));
		add(new KeyValue(4096,"CREATURE_FLAG_EXTRA_ACTIVE在服务器启动时加载，不需要玩家激活网格。"));
		
		add(new KeyValue(16384,"CREATURE_FLAG_EXTRA_WORLDEVENT自定义标志世界事件 (合并左房)-trinity"));
		add(new KeyValue(32768,"CREATURE_FLAG_EXTRA_GUARD生物是一名警卫 (将忽略假死和消失)-trinity"));
		add(new KeyValue(131072,"CREATURE_FLAG_EXTRA_NO_CRIT生物并不致命一击-trinity"));
		add(new KeyValue(262144,"CREATURE_FLAG_EXTRA_NO_SKILLGAIN生物不会增加武器技能-trinity"));
		add(new KeyValue(524288,"CREATURE_FLAG_EXTRA_TAUNT_DIMINISH生物嘲讽是收益递减-trinity"));
		add(new KeyValue(1048576,"CREATURE_FLAG_EXTRA_ALL_DIMINISH生物是所有收益递减-trinity"));
		add(new KeyValue(2097152,"CREATURE_FLAG_EXTRA_CREATURE_FLAG_EXTRA_NO_PLAYER_DAMAGE_REQ(建议)Npc 可以帮助杀此怪物。玩家通用会的荣誉，如果他标记生物-trinity"));
		add(new KeyValue(268435456,"CREATURE_FLAG_EXTRA_DUNGEON_BOSS程序设置为地下城boss。数据库不设置。-trinity"));
		add(new KeyValue(536870912,"CREATURE_FLAG_EXTRA_IGNORE_PATHFINDING生物将忽略寻路。这就像禁用 Mmaps，只为一种生物。-trinity"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>额外标志：<select onchange='reload()' id='ExtraFlags'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.ExtraFlags = $('#ExtraFlags').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String ExtraFlags=request.getParameter("ExtraFlags");
		if(ExtraFlags==null||"-1".equals(ExtraFlags))
			return "";
		else if(ExtraFlags.equals("0"))	
			return "ExtraFlags=0";
		else 
			return "ExtraFlags&"+ExtraFlags;
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
