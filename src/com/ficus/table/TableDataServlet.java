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

	ListBean listBean = new ListBean();

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

		Enumeration<String> p = request.getParameterNames();
		while (p.hasMoreElements()) {
			String k = p.nextElement();
			String v = request.getParameter(k);
			System.out.println(k + "=" + v);
		}

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
		
		ResultBean rb = new ResultBean();
		try {
			
			rb = listBean.query(table, start, length, rb,keyword,orders);
		} catch (Exception e) {
			rb.setError(e.getMessage());
		}

		rb.setDraw(draw);
		response.getWriter().write(JSONObject.toJSONString(rb));

		// String search=request.getParameter("search");
		// String order=request.getParameter("order");
		// String columns=request.getParameter("columns");

	}

}
