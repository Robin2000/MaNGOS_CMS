package com.ficus.query.item;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.ficus.app.AppKey;
import com.ficus.query.QueryItemInterface;
import com.ficus.util.SessionUtil;

public class SubClass extends ArrayList<SubClassKeyValue> implements QueryItemInterface{
	public SubClass(){
		add(new SubClassKeyValue(-1,-1,"全部"));
		add(new SubClassKeyValue(0,0,"Consumable可磨损"));
		add(new SubClassKeyValue(0,1,"Potion药水"));
		add(new SubClassKeyValue(0,2,"Elixir大药"));
		add(new SubClassKeyValue(0,3,"Flask烧瓶"));
		add(new SubClassKeyValue(0,4,"Scroll卷轴"));
		add(new SubClassKeyValue(0,5,"Food & Drink食物饮料"));
		add(new SubClassKeyValue(0,6,"Item Enhancement物品附魔"));
		add(new SubClassKeyValue(0,7,"Bandage绷带"));
		add(new SubClassKeyValue(0,8,"Other"));

		add(new SubClassKeyValue(1,0,"Bag包"));
		add(new SubClassKeyValue(1,1,"Soul Bag灵魂绑定包"));
		add(new SubClassKeyValue(1,2,"Herb Bag药包"));
		add(new SubClassKeyValue(1,3,"Enchanting Bag附魔包"));
		add(new SubClassKeyValue(1,4,"Engineering Bag工程包"));
		add(new SubClassKeyValue(1,5,"Gem Bag宝石袋"));
		add(new SubClassKeyValue(1,6,"Mining Bag挖矿包"));
		add(new SubClassKeyValue(1,7,"Leatherworking Bag制皮包"));
		add(new SubClassKeyValue(1,8,"Inscription Bag铭文包"));
		
		add(new SubClassKeyValue(2,0,"Axe One handed单手斧"));
		add(new SubClassKeyValue(2,1,"Axe Two handed双手斧"));
		add(new SubClassKeyValue(2,2,"Bow弓"));
		add(new SubClassKeyValue(2,3,"Gun枪"));
		add(new SubClassKeyValue(2,4,"Mace One handed单手狼牙棒"));
		add(new SubClassKeyValue(2,5,"Mace Two handed双狼牙棒"));
		add(new SubClassKeyValue(2,6,"Polearm长柄武器"));
		add(new SubClassKeyValue(2,7,"Sword	One handed单手剑"));	
		add(new SubClassKeyValue(2,8,"Sword	Two handed双手剑"));	
		add(new SubClassKeyValue(2,9,"Obsolete作废的"));	
		add(new SubClassKeyValue(2,10,"Staff法杖"));	
		add(new SubClassKeyValue(2,11,"Exotic外来物"));	
		add(new SubClassKeyValue(2,12,"Exotic外来物"));	
		add(new SubClassKeyValue(2,13,"Fist Weapon拳套"));	
		add(new SubClassKeyValue(2,14,"Miscellaneous杂项(Blacksmith Hammer, Mining Pick, etc.)"));	
		add(new SubClassKeyValue(2,15,"Dagger刺客匕首"));	
		add(new SubClassKeyValue(2,16,"Thrown投掷武器"));	
		add(new SubClassKeyValue(2,17,"Spear标枪"));	
		add(new SubClassKeyValue(2,18,"Crossbow十字弓"));	
		add(new SubClassKeyValue(2,19,"Wand魔杖"));	
		add(new SubClassKeyValue(2,20,"Fishing Pole鱼竿"));	
		
		add(new SubClassKeyValue(3,0,"Red红"));
		add(new SubClassKeyValue(3,1,"Blue蓝"));
		add(new SubClassKeyValue(3,2,"Yellow黄"));
		add(new SubClassKeyValue(3,3,"Purple紫"));
		add(new SubClassKeyValue(3,4,"Green绿"));
		add(new SubClassKeyValue(3,5,"Orange橙"));
		add(new SubClassKeyValue(3,6,"Meta多彩"));
		add(new SubClassKeyValue(3,7,"Simple简单"));		
		add(new SubClassKeyValue(3,8,"Prismatic棱光"));		
		
		add(new SubClassKeyValue(4,0,"Miscellaneous杂物"));
		add(new SubClassKeyValue(4,1,"Cloth布甲"));
		add(new SubClassKeyValue(4,2,"Leather皮革"));
		add(new SubClassKeyValue(4,3,"Mail链甲 "));
		add(new SubClassKeyValue(4,4,"Plate鳞甲"));
		add(new SubClassKeyValue(4,5,"Buckler小圆盾(OBSOLETE)"));
		add(new SubClassKeyValue(4,6,"Shield盾牌 "));
		add(new SubClassKeyValue(4,7,"Libram圣契"));		
		add(new SubClassKeyValue(4,8,"Idol幻象"));	
		add(new SubClassKeyValue(4,9,"Totem图腾"));	
		add(new SubClassKeyValue(4,10,"Sigil魔符"));	
		
		add(new SubClassKeyValue(5,0,"Reagent试剂"));
		
		add(new SubClassKeyValue(6,0,"Wand魔杖(OBSOLETE)"));
		add(new SubClassKeyValue(6,1,"Bolt弩箭(OBSOLETE)"));
		add(new SubClassKeyValue(6,2,"Arrow箭头"));
		add(new SubClassKeyValue(6,3,"Bullet子弹"));
		add(new SubClassKeyValue(6,4,"Thrown投掷武器(OBSOLETE)	"));
		
		add(new SubClassKeyValue(7,0,"Trade Goods贸易货物"));
		add(new SubClassKeyValue(7,1,"Parts零件"));
		add(new SubClassKeyValue(7,2,"Explosives炸药"));
		add(new SubClassKeyValue(7,3,"Devices装置"));
		add(new SubClassKeyValue(7,4,"Jewelcrafting珠宝"));
		add(new SubClassKeyValue(7,5,"Cloth布甲"));
		add(new SubClassKeyValue(7,6,"Leather皮甲"));
		add(new SubClassKeyValue(7,7,"Metal & Stone金属石头"));	
		add(new SubClassKeyValue(7,8,"Meat肉食"));	
		add(new SubClassKeyValue(7,9,"Herb药草"));	
		add(new SubClassKeyValue(7,10,"Elemental元素"));	
		add(new SubClassKeyValue(7,11,"Other"));	
		add(new SubClassKeyValue(7,12,"Enchanting附魔"));	
		add(new SubClassKeyValue(7,13,"Materials物料"));	
		add(new SubClassKeyValue(7,14,"Armor Enchantment护甲附魔"));	
		add(new SubClassKeyValue(7,15,"Weapon Enchantment武器附魔"));	
		
		add(new SubClassKeyValue(8,0,"Generic通用(OBSOLETE)"));
		
		add(new SubClassKeyValue(9,0,"Book书"));
		add(new SubClassKeyValue(9,1,"Leatherworking制皮图样"));
		add(new SubClassKeyValue(9,2,"Tailoring剪裁"));
		add(new SubClassKeyValue(9,3,"Engineering工程"));
		add(new SubClassKeyValue(9,4,"Blacksmithing铁匠"));
		add(new SubClassKeyValue(9,5,"Cooking烹饪"));
		add(new SubClassKeyValue(9,6,"Alchemy炼金术"));
		add(new SubClassKeyValue(9,7,"First Aid急救"));
		add(new SubClassKeyValue(9,8,"Enchanting附魔"));
		add(new SubClassKeyValue(9,9,"Fishing捕鱼"));
		add(new SubClassKeyValue(9,10,"Jewelcrafting珠宝学"));

		add(new SubClassKeyValue(10,0,"Recipe食谱"));
		
		add(new SubClassKeyValue(11,0,"Quiver(OBSOLETE)箭袋(已过时)"));
		add(new SubClassKeyValue(11,1,"Quiver(OBSOLETE)箭袋(已过时)"));
		add(new SubClassKeyValue(11,2,"Quiver-Can hold arrows箭袋-可装箭"));
		add(new SubClassKeyValue(11,3,"Ammo Pouch-Can hold bullets箭袋-可装子弹"));
		
		add(new SubClassKeyValue(12,0,"Quest任务"));
		
		add(new SubClassKeyValue(13,0,"Key钥匙"));
		add(new SubClassKeyValue(13,1,"Lockpick开锁"));
		
		add(new SubClassKeyValue(14,0,"Permanent永久"));
		
		add(new SubClassKeyValue(15,0,"Junk垃圾"));
		add(new SubClassKeyValue(15,1,"Reagent试剂"));
		add(new SubClassKeyValue(15,2,"Pet宠物"));
		add(new SubClassKeyValue(15,3,"Holiday节日"));
		add(new SubClassKeyValue(15,4,"Other其它"));
		add(new SubClassKeyValue(15,5,"Mount骑乘"));
		
		add(new SubClassKeyValue(16,0,"无"));
		add(new SubClassKeyValue(16,1,"Warrior战士"));
		add(new SubClassKeyValue(16,2,"Paladin圣骑士"));
		add(new SubClassKeyValue(16,3,"Hunter猎人"));
		add(new SubClassKeyValue(16,4,"Rogue流氓"));
		add(new SubClassKeyValue(16,5,"Priest牧师"));
		add(new SubClassKeyValue(16,6,"Death Knight死亡骑士"));
		add(new SubClassKeyValue(16,7,"Shaman萨满"));	
		add(new SubClassKeyValue(16,8,"Mage法师"));	
		add(new SubClassKeyValue(16,9,"Warlock术士"));		
		add(new SubClassKeyValue(16,11,"Druid德鲁伊"));	
	}

	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml(){
		
		HttpServletRequest request=(HttpServletRequest)SessionUtil.currentSession().get(AppKey.SERVLET_REQUEST);
		
		String type=request.getParameter("ItemClass");//提取上级大类的id
		if(type==null||"-1".equals(type)||"".equals(type))
			return "<nobr>子类别：<select ></select></nobr>";
		String SubClass=request.getParameter("SubClass");
		
		StringBuilder sb=new StringBuilder("<nobr>子类别：<select onchange='reload()' id='SubClass'>");
		
		for(SubClassKeyValue kv:this){
			if(kv.getParentClass().equals(Integer.parseInt(type))||kv.key.toString().equals("-1"))
			{
				if(kv.key.toString().equals(SubClass))
					sb.append("<option selected value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
				else
					sb.append("<option value='").append(kv.key).append("'>").append(kv.key).append(".").append(kv.value).append("</option>");
			}
		}
		sb.append("</select></nobr>");
		
		return sb.toString();
		
	}
	/*1、javascript从界面元素取值，产生POST参数。
	 * */
	public String getJavaScript(){
		return "d.SubClass = $('#SubClass').val();\n";
	}
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request){

		String type=request.getParameter("SubClass");
		if(type!=null&&!"-1".equals(type))
			return "subclass="+type;
		else
			return "";
	}
	public String getFilterTable(){
		return "item_template";
	}

	@Override
	public boolean useAjaxRetriveHtml() {
		return true;
	}
}
