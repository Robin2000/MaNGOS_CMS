package com.ficus.query;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <b>该类是用于：</b>构造界面查询的一个元素项。<br/>
 * <b>@author：</b> 29698868@qq.com <br/>
 * <b>@since JDK1.7</b><br/>
 * <b>@history </b>2016年1月30日 <br/>
 */
public interface QueryItemInterface {
	/*生成界面html供最终用户使用，交互构造查询条件*/
	public String getQueryHtml();
	
	/*javascript从界面元素取值，产生POST参数*/
	public String getJavaScript();
	
	/*服务端从HttpServletRequest取提交的值，产生查询条件*/
	public String parseQueryParameter(HttpServletRequest request);
	
	/*是否通过ajax刷新html，支持动态生成的html*/
	public boolean useAjaxRetriveHtml();
	
}
