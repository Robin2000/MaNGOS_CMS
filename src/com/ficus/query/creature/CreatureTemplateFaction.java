package com.ficus.query.creature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;
import com.ficus.query.gameobject.FactionKV;
import com.ficus.query.gameobject.GameObjectFaction;
import com.ficus.util.Util;

public class CreatureTemplateFaction extends GameObjectFaction implements QueryItemInterface{

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>在联盟时阵营友好：<select onchange='reload()' id='friendlyID'>");
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select>\n");

		sb.append("在联盟时阵营敌视：<select onchange='reload()' id='hostileID'>");
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>\n");

		return sb.toString();
		
	}

	public String getFilterTable(){
		return "creature_template";
	}

}
