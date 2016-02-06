package com.ficus.query.gameobject;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class GameObjectType extends ArrayList<KeyValue> implements QueryItemInterface{
	public GameObjectType(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(0,"GAMEOBJECT_TYPE_DOOR门"));
		add(new KeyValue(1,"GAMEOBJECT_TYPE_BUTTON按钮"));
		add(new KeyValue(2,"GAMEOBJECT_TYPE_QUESTGIVER任务"));
		add(new KeyValue(3,"GAMEOBJECT_TYPE_CHEST宝箱"));
		add(new KeyValue(4,"GAMEOBJECT_TYPE_BINDER粘结剂"));
		add(new KeyValue(5,"GAMEOBJECT_TYPE_GENERIC通用"));
		add(new KeyValue(6,"GAMEOBJECT_TYPE_TRAP陷阱"));
		add(new KeyValue(7,"GAMEOBJECT_TYPE_CHAIR椅子"));
		add(new KeyValue(8,"GAMEOBJECT_TYPE_SPELL_FOCUS法术焦点"));
		add(new KeyValue(9,"GAMEOBJECT_TYPE_TEXT文本"));
		add(new KeyValue(10,"GAMEOBJECT_TYPE_GOOBER花生"));
		add(new KeyValue(11,"GAMEOBJECT_TYPE_TRANSPORT运输"));
		add(new KeyValue(12,"GAMEOBJECT_TYPE_AREADAMAGE区域损伤"));
		add(new KeyValue(13,"GAMEOBJECT_TYPE_CAMERA相机"));
		add(new KeyValue(14,"GAMEOBJECT_TYPE_MAP_OBJECT地图对象"));
		add(new KeyValue(15,"GAMEOBJECT_TYPE_MO_TRANSPORT运输"));
		add(new KeyValue(16,"GAMEOBJECT_TYPE_DUEL_ARBITER决斗仲裁员"));
		add(new KeyValue(17,"GAMEOBJECT_TYPE_FISHINGNODE捕鱼节点"));
		add(new KeyValue(18,"GAMEOBJECT_TYPE_RITUAL仪式"));
		add(new KeyValue(19,"GAMEOBJECT_TYPE_MAILBOX邮箱"));
		add(new KeyValue(20,"GAMEOBJECT_TYPE_AUCTIONHOUSE拍卖行"));
		add(new KeyValue(21,"GAMEOBJECT_TYPE_GUARDPOST哨所"));
		add(new KeyValue(22,"GAMEOBJECT_TYPE_SPELLCASTER施法者"));
		add(new KeyValue(23,"GAMEOBJECT_TYPE_MEETINGSTONE会议石头"));
		add(new KeyValue(24,"GAMEOBJECT_TYPE_FLAGSTAND旗子的支架"));
		add(new KeyValue(25,"GAMEOBJECT_TYPE_FISHINGHOLE渔洞"));
		add(new KeyValue(26,"GAMEOBJECT_TYPE_FLAGDROP旗降"));
		add(new KeyValue(27,"GAMEOBJECT_TYPE_MINI_GAME迷你游戏"));
		add(new KeyValue(28,"GAMEOBJECT_TYPE_LOTTERY_KIOSK彩票亭"));
		add(new KeyValue(29,"GAMEOBJECT_TYPE_CAPTURE_POINT捕获点"));
		add(new KeyValue(30,"GAMEOBJECT_TYPE_AURA_GENERATOR光环发电机"));
		add(new KeyValue(31,"GAMEOBJECT_TYPE_DUNGEON_DIFFICULTY地下城难度"));
		add(new KeyValue(32,"GAMEOBJECT_TYPE_BARBER_CHAIR理发椅"));
		add(new KeyValue(33,"GAMEOBJECT_TYPE_DESTRUCTIBLE_BUILDING可破坏建筑"));
		add(new KeyValue(34,"GAMEOBJECT_TYPE_GUILD_BANK公会银行"));
		add(new KeyValue(35,"GAMEOBJECT_TYPE_TRAPDOOR活板门"));
			   
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>类别：<select onchange='reload()' id='go_type'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.type = $('#go_type').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("type");
		if(type!=null&&!"-1".equals(type))
			return "type="+type;
		else
			return "";
	}
	public String getFilterTable(){
		return "gameobject_template";
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
