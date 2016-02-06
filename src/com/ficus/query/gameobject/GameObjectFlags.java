package com.ficus.query.gameobject;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class GameObjectFlags extends ArrayList<KeyValue> implements QueryItemInterface{
	public GameObjectFlags(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"GO_FLAG_IN_USE不能使用"));//Gameobject in use - Disables interaction while being animated
		add(new KeyValue(2,"GO_FLAG_LOCKED要钥匙或技能"));//Makes the Gameobject Locked. Requires a key, spell, or event to be opened. "Locked" appears in tooltip
		add(new KeyValue(4,"GO_FLAG_INTERACT_COND被指定，不能进行交互"));//Untargetable, cannot interact
		add(new KeyValue(8,"GO_FLAG_TRANSPORT游戏物体可以运输 (船、 电梯、 汽车)"));//Gameobject can transport (boat, elevator, car)
		add(new KeyValue(16,"GO_FLAG_NOT_SELECTABLE不可选 (甚至不在GM模式)"));//Not selectable (Not even in GM-mode)
		add(new KeyValue(32,"GO_FLAG_NODESPAWN永远不会 despawns。典型的游戏物体与开/关状态 (例如门)"));//Never despawns. Typical for gameobjects with on/off state (doors for example)
		add(new KeyValue(64,"GO_FLAG_TRIGGERED通常情况下，传唤对象。由法术或其他事件触发"));//typically, summoned objects. Triggered by spell or other events
		add(new KeyValue(512,"GO_FLAG_DAMAGED游戏对象已损坏"));//Gameobject has been siege damaged
		add(new KeyValue(1024,"GO_FLAG_DESTROYED游戏对象已被摧毁"));//Gameobject has been destroyed
		
			   
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
		return "gameobject_template";
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		// TODO Auto-generated method stub
		return false;
	}
}
