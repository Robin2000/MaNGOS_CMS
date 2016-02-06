package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class UnitFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public UnitFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"UNIT_FLAG_SERVER_CONTROLLED"));
		add(new KeyValue(2,"UNIT_FLAG_NON_ATTACKABLE"));
		add(new KeyValue(4,"UNIT_FLAG_DISABLE_MOVE禁止移动"));
		add(new KeyValue(8,"UNIT_FLAG_PVP_ATTACKABLE应用阵营PVP规则"));
		add(new KeyValue(16,"UNIT_FLAG_RENAME"));
		add(new KeyValue(32,"UNIT_FLAG_RESTING能回复")); //SPELL_ATTR_EX5_NO_REAGENT_WHILE_PREP
		add(new KeyValue(64,"UNIT_FLAG_UNK_6"));
		add(new KeyValue(128,"UNIT_FLAG_NOT_ATTACKABLE_1"));
		add(new KeyValue(256,"UNIT_FLAG_IMMUNE_TO_PC禁止玩家战斗"));
		add(new KeyValue(512,"UNIT_FLAG_IMMUNE_TO_NPC禁止非玩家战斗"));
		add(new KeyValue(1024,"UNIT_FLAG_LOOTING爆落动画"));
		add(new KeyValue(2048,"UNIT_FLAG_PET_IN_COMBAT宠物战斗"));
		add(new KeyValue(4096,"UNIT_FLAG_PVP允许法术施放在其上"));
		add(new KeyValue(8192,"UNIT_FLAG_SILENCED不能施法"));
		add(new KeyValue(16384,"UNIT_FLAG_UNK_14"));
		add(new KeyValue(32768,"UNIT_FLAG_UNK_15"));
		add(new KeyValue(65536,"UNIT_FLAG_UNK_16"));
		add(new KeyValue(131072,"UNIT_FLAG_PACIFIED生物不攻击"));
		add(new KeyValue(262144,"UNIT_FLAG_STUNNED惊呆了"));
		add(new KeyValue(524288,"UNIT_FLAG_IN_COMBAT"));
		add(new KeyValue(1048576,"UNIT_FLAG_TAXI_FLIGHT禁止骑乘"));
		add(new KeyValue(2097152,"UNIT_FLAG_DISARMED禁止近战法术"));
		add(new KeyValue(4194304,"UNIT_FLAG_CONFUSED"));
		add(new KeyValue(8388608,"UNIT_FLAG_FLEEING"));
		add(new KeyValue(16777216,"UNIT_FLAG_PLAYER_CONTROLLED玩家控制宠物看东西包括车辆"));
		add(new KeyValue(33554432,"UNIT_FLAG_NOT_SELECTABLE鼠标不可选"));
		add(new KeyValue(67108864,"UNIT_FLAG_SKINNABLE"));
		add(new KeyValue(134217728,"UNIT_FLAG_MOUNT也用于定制坐骑"));
		add(new KeyValue(268435456,"UNIT_FLAG_UNK_28"));
		add(new KeyValue(536870912,"UNIT_FLAG_UNK_29用于装死或者做死亡时动作"));
		add(new KeyValue(1073741824,"UNIT_FLAG_SHEATHE"));
		add(new KeyValue(2147483648L,"UNIT_FLAG_UNK_31改变皮肤颜色"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>Unit标志：<select onchange='reload()' id='UnitFlags'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.UnitFlags = $('#UnitFlags').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String UnitFlags=request.getParameter("UnitFlags");
		if(UnitFlags==null||"-1".equals(UnitFlags))
			return "";
		else if(UnitFlags.equals("0"))	
			return "UnitFlags=0";
		else 
			return "UnitFlags&"+UnitFlags;
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
