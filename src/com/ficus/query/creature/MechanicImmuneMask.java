package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class MechanicImmuneMask extends ArrayList<KeyValue> implements QueryItemInterface{
	public MechanicImmuneMask(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"不免疫"));
		add(new KeyValue(1,"MECHANIC_CHARM被魅惑"));
		add(new KeyValue(2,"MECHANIC_DISORIENTED迷失了方向"));
		add(new KeyValue(4,"MECHANIC_DISARM被缴械"));
		add(new KeyValue(8,"MECHANIC_DISTRACT被吸引"));
		add(new KeyValue(16,"MECHANIC_FEAR恐惧"));
		add(new KeyValue(32,"MECHANIC_GRIP死亡之握和类似的效果"));
		add(new KeyValue(64,"MECHANIC_ROOT被定身"));
		add(new KeyValue(128,"MECHANIC_PACIFY安抚，无用"));
		add(new KeyValue(256,"MECHANIC_SILENCE沉默"));
		add(new KeyValue(512,"MECHANIC_SLEEP睡眠"));
		add(new KeyValue(1024,"MECHANIC_SNARE诱捕"));
		add(new KeyValue(2048,"MECHANIC_STUN昏迷"));
		add(new KeyValue(4096,"MECHANIC_FREEZE冻结"));
		add(new KeyValue(8192,"MECHANIC_KNOCKOUT瘫痪效果如 Repetance (圣骑士)"));
		add(new KeyValue(16384,"MECHANIC_BLEED流血"));
		add(new KeyValue(32768,"MECHANIC_BANDAGE愈合等。"));
		add(new KeyValue(65536,"MECHANIC_POLYMORPH被变形"));
		add(new KeyValue(131072,"MECHANIC_BANISH被放逐"));
		add(new KeyValue(262144,"MECHANIC_SHIELD被防护"));
		add(new KeyValue(524288,"MECHANIC_SHACKLE只束缚亡灵"));
		add(new KeyValue(1048576,"MECHANIC_MOUNT召唤坐骑任何效果"));
		add(new KeyValue(2097152,"MECHANIC_INFECTED冰霜热疫，血瘟疫等。"));
		add(new KeyValue(4194304,"MECHANIC_TURN如超度邪恶"));
		add(new KeyValue(8388608,"MECHANIC_HORROR如死亡缠绕 (术士)"));
		add(new KeyValue(16777216,"MECHANIC_INVULNERABILITY无敌免伤只有忍耐，虚空防护，免"));
		add(new KeyValue(33554432,"MECHANIC_INTERRUPT被打断"));
		add(new KeyValue(67108864,"MECHANIC_DAZE眩晕"));
		add(new KeyValue(134217728,"MECHANIC_DISCOVERY被发现，任何创建物品的效果"));
		add(new KeyValue(268435456,"MECHANIC_IMMUNE_SHIELD防护免疫，神圣之盾，冰块，手的保护..."));
		add(new KeyValue(536870912,"MECHANIC_SAPPED被闷棍"));
		add(new KeyValue(1073741824,"MECHANIC_ENRAGED激怒免疫"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>免疫遮罩：<select onchange='reload()' id='MechanicImmuneMask'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.MechanicImmuneMask = $('#MechanicImmuneMask').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String MechanicImmuneMask=request.getParameter("MechanicImmuneMask");
		if(MechanicImmuneMask==null||"-1".equals(MechanicImmuneMask))
			return "";
		else if(MechanicImmuneMask.equals("0"))	
			return "MechanicImmuneMask=0";
		else 
			return "MechanicImmuneMask&"+MechanicImmuneMask;
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
