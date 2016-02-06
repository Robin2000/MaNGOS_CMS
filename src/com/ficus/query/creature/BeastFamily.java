package com.ficus.query.creature;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.query.KeyValue;
import com.ficus.query.QueryItemInterface;

public class BeastFamily extends ArrayList<KeyValue> implements QueryItemInterface{
	public BeastFamily(){
		add(new KeyValue(-1,"全部"));
		add(new KeyValue(1,"Wolf"));
		add(new KeyValue(2,"Cat猫科动物"));
		add(new KeyValue(3,"Spider"));
		add(new KeyValue(4,"Bear"));
		add(new KeyValue(5,"Boar野猪"));
		add(new KeyValue(6,"Crocolisk鳄鱼"));
		add(new KeyValue(7,"Carrion Bird秃鹰"));
		add(new KeyValue(8,"Crab螃蟹"));
		add(new KeyValue(9,"Gorilla大猩猩"));
		add(new KeyValue(11,"Raptor迅猛龙"));
		add(new KeyValue(12,"Tallstrider陆行鸟"));
		add(new KeyValue(15,"Felhunter地狱犬"));
		add(new KeyValue(16,"Voidwalker虚空行者"));
		add(new KeyValue(17,"Succubus魅魔、女妖"));
		add(new KeyValue(19,"Doomguard末日守卫"));
		add(new KeyValue(20,"Scorpid蝎子"));
		add(new KeyValue(21,"Turtle乌龟"));
		add(new KeyValue(23,"Imp小恶魔"));
		add(new KeyValue(24,"Bat蝙蝠"));
		add(new KeyValue(25,"Hyena土狼"));
		add(new KeyValue(26,"Owl土狼"));
		
								//add(new KeyValue(21,"Bird of Prey食肉鸟"));
		add(new KeyValue(27,"Wind Serpent飞蛇"));
		add(new KeyValue(28,"Remote Control遥控器"));
		add(new KeyValue(29,"Felguard恶魔守卫"));
		add(new KeyValue(30,"Dragonhawk龙鹰"));
		add(new KeyValue(31,"Ravager劫掠者"));
		add(new KeyValue(32,"Warp Stalker森林巨蜥"));
		add(new KeyValue(33,"Sporebat孢子蝙蝠"));
		add(new KeyValue(34,"Nether Ray虚空鳐"));
		add(new KeyValue(35,"Serpent巨蛇"));
		add(new KeyValue(37,"Moth飞蛾"));
		add(new KeyValue(38,"Chimaera奇美拉"));
		add(new KeyValue(39,"Devilsaur魔暴龙"));
		add(new KeyValue(40,"Ghoul食尸鬼"));
		add(new KeyValue(41,"Silithid异种甲虫"));
		add(new KeyValue(42,"Worm蠕虫、蚯蚓"));
		add(new KeyValue(43,"Rhino犀牛"));
		add(new KeyValue(44,"Wasp黄蜂"));
		add(new KeyValue(45,"Core Hound熔火恶犬；熔核猎犬"));
		add(new KeyValue(46,"Spirit Beast灵魂兽"));
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		StringBuilder sb=new StringBuilder("<nobr>野兽家族：<select onchange='reload()' id='Family'>");
		
		for(KeyValue kv:this){
			sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript(){
		return "d.Family = $('#Family').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String Family=request.getParameter("Family");
		if(Family!=null&&!"-1".equals(Family))
			return "Family="+Family;
		else
			return "";
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
