package com.ficus.table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class TableDataServlet
 */
@WebServlet("/tabledata")
public final class TableDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TableDataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//printParameters(request);
		//printAttributes();

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=UTF-8");

		String start = request.getParameter("start");
		String length = request.getParameter("length");
		String table = request.getParameter("t");
		String draw = request.getParameter("draw");
		String keyword=request.getParameter("search[value]");
		
	
		String order;
		String orderDesc;
		
	
		ArrayList<OrderBean> orders=new ArrayList<OrderBean>();
		for(int i=0;i<Integer.MAX_VALUE;i++)
		{
			order=request.getParameter(new StringBuilder("order[").append(i).append("][column]").toString());
			if(order==null)
				break;
			orderDesc=request.getParameter(new StringBuilder("order[").append(i).append("][dir]").toString());
			
			int pos=Integer.parseInt(order);
			
			orders.add(new OrderBean(pos,orderDesc)); 

		}
		
		ResultBean rb = new ResultBean(); /*这是Jquery的DataTable要求的返回数据。draw是用于客户端区分不同的会话tokin*/
		rb.setDraw(draw);
		
		QueryInterface listBean=null;
		try{
			listBean=(QueryInterface)getServletContext().getAttribute(request.getParameter("bean"));
			if(listBean==null)
			{
				rb.setError("会话过期,请重新刷新页面!");
			}
		
		}catch(Exception e){
			rb.setError(e.getMessage());
		}
		try {
			if(listBean!=null)
			{
				String queryClause=listBean.parseQueryParameter(table, request);
				rb = listBean.query(table, start, length, rb,keyword,orders,queryClause);
			}
		} catch (Exception e) {
			rb.setError(e.getMessage());
		}

		
		response.getWriter().write(JSONObject.toJSONString(rb));

		// String search=request.getParameter("search");
		// String order=request.getParameter("order");
		// String columns=request.getParameter("columns");

	}
	
	@SuppressWarnings("unused")
	private void printAttributes(){
		Enumeration<String> e=getServletContext().getAttributeNames();
		while(e.hasMoreElements())
		{
			String key=e.nextElement();
			System.out.println(key+"="+getServletContext().getAttribute(key).getClass());
			
		}
		
		System.out.println("-------------");
	}
	
	@SuppressWarnings("unused")
	private void printParameters(HttpServletRequest request){
		Enumeration<String> p = request.getParameterNames();
		while (p.hasMoreElements()) {
			String k = p.nextElement();
			String v = request.getParameter(k);
			System.out.println(k + "=" + v);
		}
	}

}
