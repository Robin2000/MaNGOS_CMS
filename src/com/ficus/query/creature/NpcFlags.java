package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class NpcFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public NpcFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"Gossip对话"));
		add(new KeyValue(2,"Quest Giver任务给予者"));
		add(new KeyValue(16,"Trainer训练师"));
		add(new KeyValue(32,"Trainer_Class职业训练师"));
		add(new KeyValue(64,"Trainer_Profession专业训练师"));
		add(new KeyValue(128,"Vendor供应商"));
		add(new KeyValue(256,"Vendor_Ammo护甲供应商"));
		add(new KeyValue(512,"Vendor_Food食品供应商"));
		add(new KeyValue(1024,"Vendor_Poison药剂供应商"));
		add(new KeyValue(2048,"Vendor_Reagent试剂供应商"));
		add(new KeyValue(4096,"Repairer修理工"));
		add(new KeyValue(8192,"Flight Master飞行大师"));
		add(new KeyValue(16384,"Spirit Healer灵魂治疗师"));
		add(new KeyValue(32768,"Spirit Guide灵魂向导"));
		add(new KeyValue(65536,"Innkeeper客栈老板"));
		add(new KeyValue(131072,"Banker银行家"));
		add(new KeyValue(262144,"Petitioner呈请人"));
		add(new KeyValue(524288,"Tabard Designer工会徽章设计者"));
		add(new KeyValue(1048576,"Battlemaster战斗大师"));
		add(new KeyValue(2097152,"Auctioneer拍卖师"));
		add(new KeyValue(4194304,"Stable Master兽栏管理员"));
		add(new KeyValue(8388608,"Guild Banker工会银行"));
		add(new KeyValue(16777216,"Spellclick需要表npc_spellclick_spells数据"));
		//add(new KeyValue(16777216,"Instantloot即时爆落"));
		add(new KeyValue(33554432,"Player Vehicle运载工具"));
		add(new KeyValue(268435456,"Guard守卫"));
		add(new KeyValue(67108864,"Mailbox邮箱"));
		
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>NPC标志：<select onchange='reload()' id='NpcFlags'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.NpcFlags = $('#NpcFlags').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String NpcFlags=request.getParameter("NpcFlags");
		if(NpcFlags==null||"-1".equals(NpcFlags))
			return "";
		else if(NpcFlags.equals("0"))	
			return "NpcFlags=0";
		else 
			return "NpcFlags&"+NpcFlags;
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
