package com.ficus.table;

import com.ficus.query.QueryItemInterface;

/**
 * 
 * <b>该类是用于：</b>通过Ajax请求过滤组件<br/>
 * <b>@author：</b> 29698868@qq.com <br/>
 * <b>@since JDK1.7</b><br/>
 * <b>@history </b>2016年2月6日 <br/>
 */
public class AjaxQuerFilterHtmlItem {
	private String id;
	private String html;
	
	public AjaxQuerFilterHtmlItem(QueryItemInterface queryItem){
		id=new StringBuilder("ajax_").append(queryItem.getClass().getSimpleName()).toString();
		html=new StringBuilder("<span id='").append(this.id).append("'>").append(queryItem.getQueryHtml()).append("</span>").append("\n").toString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
}
