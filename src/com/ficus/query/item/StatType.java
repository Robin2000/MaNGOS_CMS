package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class StatType extends ArrayList<KeyValue> implements QueryItemInterface{
	public StatType(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"ITEM_MOD_MANA法力"));
		add(new KeyValue(1,"ITEM_MOD_HEALTH生命"));
		add(new KeyValue(3,"ITEM_MOD_AGILITY敏捷"));
		add(new KeyValue(4,"ITEM_MOD_STRENGTH力量"));
		add(new KeyValue(5,"ITEM_MOD_INTELLECT智力"));
		add(new KeyValue(6,"ITEM_MOD_SPIRIT精神"));
		add(new KeyValue(7,"ITEM_MOD_STAMINA耐力"));
		add(new KeyValue(12,"ITEM_MOD_DEFENSE_SKILL_RATING防御加分"));
		add(new KeyValue(13,"ITEM_MOD_DODGE_RATING闪避加分"));
		add(new KeyValue(14,"ITEM_MOD_PARRY_RATING格挡加分"));
		add(new KeyValue(15,"ITEM_MOD_BLOCK_RATING打断加分"));
		add(new KeyValue(16,"ITEM_MOD_HIT_MELEE_RATING近战命中加分"));		
		add(new KeyValue(17,"ITEM_MOD_HIT_RANGED_RATING远战命中加分"));		
		add(new KeyValue(18,"ITEM_MOD_HIT_SPELL_RATING法术命中加分"));		
		add(new KeyValue(19,"ITEM_MOD_CRIT_MELEE_RATING近战暴击加分"));		
		add(new KeyValue(20,"ITEM_MOD_CRIT_RANGED_RATING远战暴击加分"));		
		add(new KeyValue(21,"ITEM_MOD_CRIT_SPELL_RATING法术暴击加分"));	
		add(new KeyValue(22,"ITEM_MOD_HIT_TAKEN_MELEE_RATING近战命中躲闪加分"));	
		add(new KeyValue(23,"ITEM_MOD_HIT_TAKEN_RANGED_RATING远战命中躲闪加分"));	
		add(new KeyValue(24,"ITEM_MOD_HIT_TAKEN_SPELL_RATING法术命中躲闪加分"));	
		add(new KeyValue(25,"ITEM_MOD_CRIT_TAKEN_MELEE_RATING近战暴击躲闪加分"));	
		add(new KeyValue(26,"ITEM_MOD_CRIT_TAKEN_RANGED_RATING远战暴击躲闪加分"));	
		add(new KeyValue(27,"ITEM_MOD_CRIT_TAKEN_SPELL_RATING法术暴击躲闪加分"));	
		add(new KeyValue(28,"ITEM_MOD_HASTE_MELEE_RATING近战加速加分"));
		
		add(new KeyValue(29,"ITEM_MOD_HASTE_RANGED_RATING远战加速加分"));		
		add(new KeyValue(30,"ITEM_MOD_HASTE_SPELL_RATING法术加速加分"));		
		add(new KeyValue(31,"ITEM_MOD_HIT_RATING命中加分"));		
		add(new KeyValue(32,"ITEM_MOD_CRIT_RATING暴击加分"));		
		add(new KeyValue(33,"ITEM_MOD_HIT_TAKEN_RATING命中闪躲加分"));	
		add(new KeyValue(34,"ITEM_MOD_CRIT_TAKEN_RATING暴击闪躲加分"));	
		add(new KeyValue(35,"ITEM_MOD_RESILIENCE_RATING恢复力加分"));	
		add(new KeyValue(36,"ITEM_MOD_HASTE_RATING加速加分"));	
		add(new KeyValue(37,"ITEM_MOD_EXPERTISE_RATING专业加分"));	
		add(new KeyValue(38,"ITEM_MOD_ATTACK_POWER攻击力"));	
		add(new KeyValue(39,"ITEM_MOD_RANGED_ATTACK_POWER远攻力"));	
		add(new KeyValue(40,"ITEM_MOD_FERAL_ATTACK_POWER野性攻击力"));
		
		add(new KeyValue(41,"ITEM_MOD_SPELL_HEALING_DONE法术治疗效果"));
		add(new KeyValue(42,"ITEM_MOD_SPELL_DAMAGE_DONE法术伤害效果"));
		add(new KeyValue(43,"ITEM_MOD_MANA_REGENERATION魔法再生"));
		add(new KeyValue(44,"ITEM_MOD_ARMOR_PENETRATION_RATING护甲穿透等级"));
		add(new KeyValue(45,"ITEM_MOD_SPELL_POWER法力"));
		add(new KeyValue(46,"ITEM_MOD_HEALTH_REGEN生命恢复力"));
		add(new KeyValue(47,"ITEM_MOD_SPELL_PENETRATION法术穿透力"));
		add(new KeyValue(48,"ITEM_MOD_BLOCK_VALUE打断值"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>附加的状态类别：<select onchange='reload()' id='StatType'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.StatType = $('#StatType').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("StatType");
		if(type!=null&&!"-1".equals(type))
		{
			StringBuilder sb=new StringBuilder("(");
			for(int i=1;i<=10;i++)
			{
				if(i>1)
					sb.append(" OR ");
				sb.append("STAT_TYPE").append(i).append("=").append(type);
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
