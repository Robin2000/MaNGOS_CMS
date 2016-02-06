package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class CreatureTypeFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public CreatureTypeFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"CREATURE_TYPEFLAGS_TAMEABLE让野兽驯服 (必须是野兽有family)"));
		add(new KeyValue(2,"CREATURE_TYPEFLAGS_GHOST_VISIBLE设置玩家灵魂状态可见"));
		add(new KeyValue(4,"CREATURE_TYPEFLAGS_BOSS更改到生物的可见级别概览中生物的画像-免疫击退。"));
		add(new KeyValue(8,"CREATURE_TYPEFLAGS_DO_NOT_PLAY_WOUND_PARRY_ANIMATION"));
		add(new KeyValue(16,"CREATURE_TYPEFLAGS_HIDE_FACTION_TOOLTIP隐藏鼠标tooltip生物阵营"));
		add(new KeyValue(32,"CREATURE_TYPEFLAGS_UNK6声音相关"));
		add(new KeyValue(64,"CREATURE_TYPEFLAGS_SPELL_ATTACKABLE使生物可否法术攻击"));
		add(new KeyValue(128,"CREATURE_TYPEFLAGS_DEAD_INTERACT死掉生物玩家可交互)"));
		add(new KeyValue(136,"CREATURE_TYPEFLAGS_NON_PVP_PLAYER)"));
		add(new KeyValue(256,"CREATURE_TYPEFLAGS_HERBLOOT使怪物尸体可采药-剥皮字段"));
		add(new KeyValue(512,"CREATURE_TYPEFLAGS_MININGLOOT使怪物尸体可挖矿-剥皮字段"));
		add(new KeyValue(1024,"CREATURE_TYPEFLAGS_DONT_LOG_DEATH不记录死亡"));
		add(new KeyValue(2048,"CREATURE_TYPEFLAGS_MOUNTED_COMBAT进入战斗可继续骑乘。"));
		add(new KeyValue(4096,"CREATURE_TYPEFLAGS_AID_PLAYERS可在战斗中帮助玩家。如护送对象。"));
		add(new KeyValue(8192,"CREATURE_TYPEFLAGS_IS_PET_BAR_USED宠物动作条检查。"));
		add(new KeyValue(16384,"CREATURE_TYPEFLAGS_MASK_UID如果有，客户端设置生物guid_low &= 0xFF000000。"));
		add(new KeyValue(32768,"CREATURE_TYPEFLAGS_ENGINEERLOOT使怪物尸体可工程师战利品-剥皮字段。"));

		add(new KeyValue(65536,"CREATURE_TYPEFLAGS_EXOTIC作为外来宠物驯服。此外必须设置正常驯服的标志。"));
		add(new KeyValue(131072,"CREATURE_TYPEFLAGS_USE_DEFAULT_COLLISION_BOX碰撞相关。(总是使用默认碰撞框吗?)"));
		add(new KeyValue(262144,"CREATURE_TYPEFLAGS_IS_SIEGE_WEAPON是攻城武器。"));
		add(new KeyValue(524288,"CREATURE_TYPEFLAGS_PROJECTILE_COLLISION子弹头可以碰撞与这种生物-交互与 TARGET_DEST_TRAJ"));
		add(new KeyValue(1048576,"CREATURE_TYPEFLAGS_HIDE_NAMEPLATE隐藏铭牌。"));
		add(new KeyValue(2097152,"CREATURE_TYPEFLAGS_DO_NOT_PLAY_MOUNTED_ANIMATIONS不播放骑乘的动画。"));
		add(new KeyValue(4194304,"CREATURE_TYPEFLAGS_IS_LINK_ALL"));
		add(new KeyValue(8388608,"CREATURE_TYPEFLAGS_INTERACT_ONLY_WITH_CREATOR只可以与它的创造者交互。"));
		add(new KeyValue(134217728,"CREATURE_TYPEFLAGS_FORCE_GOSSIP允许这种生物可以显示单个对话选项"));

	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>分类标志：<select onchange='reload()' id='CreatureTypeFlags'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.CreatureTypeFlags = $('#CreatureTypeFlags').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String CreatureTypeFlags=request.getParameter("CreatureTypeFlags");
		if(CreatureTypeFlags==null||"-1".equals(CreatureTypeFlags))
			return "";
		else if(CreatureTypeFlags.equals("0"))	
			return "CreatureTypeFlags=0";
		else 
			return "CreatureTypeFlags&"+CreatureTypeFlags;
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
