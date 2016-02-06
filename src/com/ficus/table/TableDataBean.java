package com.ficus.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ficus.db.Column;
import com.ficus.db.DBCon;
import com.ficus.db.QueryResult;
import com.ficus.query.QueryFilter;
import com.ficus.query.QueryItemInterface;
import com.ficus.query.creature.CreatureTemplateClause;
import com.ficus.query.gameobject.GameObjectTemplateClause;
import com.ficus.query.item.ItemTemplateClause;
import com.ficus.util.Util;

public class TableDataBean implements QueryInterface{
	
	private static final Logger log = Logger.getLogger(TableDataBean.class);
	
	private HashMap<String,ArrayList<Column>> colMap=new HashMap<String,ArrayList<Column>>(); //not static,so sub class can use different map.
	
	private ArrayList<QueryFilter> filters=new ArrayList<QueryFilter>();
	
	public TableDataBean(){
		filters.add(new GameObjectTemplateClause());//这里扩展
		filters.add(new CreatureTemplateClause());//这里扩展
		filters.add(new ItemTemplateClause());//这里扩展
	}
	
	public ArrayList<Column> getCols(String table){
		
		ArrayList<Column> cols=colMap.get(table);
		if(cols==null)
		{
			cols=init(table);
		}
		
		return cols;
	}
	
	private synchronized ArrayList<Column> init(String table){
		
		ArrayList<Column> columns=colMap.get(table);
		
		if(columns!=null)
			return columns;
		
		DBCon con=null;
		try{
			con=DBCon.getConnection("mangos");
			columns=getColumns(con,table);
			colMap.put(table, columns);

		}catch(Exception e){
			log.error("database error",e);
		}finally{
			if(con!=null)con.close();
		}
		
		return columns;
		
	}
	public ResultBean query(String table,String start,String length,ResultBean rb,String keyword,List<OrderBean> order,String queryClause) throws Exception{
		
		StringBuilder sql=getSql(table);
		
		
		DBCon con=null;
		try{
			con=DBCon.getConnection("mangos");
			
			ArrayList<Column> cols=getCols(table);
			String keywordClause="";
			if(keyword!=null&&keyword.trim().length()!=0)
				keywordClause=searchClause(table,cols,keyword);
			
			if(!keywordClause.equals("") ||!queryClause.equals(""))	
			{
				sql.append(" where ");
				boolean added=false; //标志变量，如果为真，后续子句需要添加and
				
				if(!keywordClause.equals(""))
				{
					sql.append("(").append(keywordClause).append(")");
					added=true;
				}
				if(!queryClause.equals("")&&added)	
					sql.append("and (").append(queryClause).append(")");
				else if(!queryClause.equals("")&&!added)	
					sql.append(queryClause);
				
			}
			
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<order.size();i++)
			{
				
				if(sb.length()==0)
					sb.append(" order by ");
				else
					sb.append(",");
				
				Column c=cols.get(order.get(i).getOrderCol());
				if(c.getAlias()==null)
					sb.append(c.getName()).append(" ").append(order.get(i).getOrderDesc());	
				else
					sb.append(c.getAlias()).append(" ").append(order.get(i).getOrderDesc());	
			}
			
			sql.append(sb.toString());
				
			con.setSQL(sql.toString());
			
			QueryResult rs= con.query(Integer.parseInt(start), Integer.parseInt(length));
			Object[][] data=new Object[rs.size()][rs.getCols().length];
			for(int i=0;i<rs.size();i++)
				for(int j=0;j<rs.getCols().length;j++)
				{
					data[i][j]=rs.getObject(i, j);
				}
			
			rb.setData(data);
			rb.setRecordsTotal(rs.all_numrow);
			rb.setRecordsFiltered(rs.all_numrow);
			
			rb.setQueryFilters(getAjaxQueryHtml(table));
			return rb;
			
		}finally{
			if(con!=null)
				con.close();
		}
		
		
	}
	private String searchClause(String table,ArrayList<Column> cols,String keyword){
		StringBuilder sb=new StringBuilder();
		for(Column c:cols){
			if (c.isNumeric())
			{
				if(Util.isNumeric(keyword))
				{
					if(sb.length()>0)
						sb.append(" or ");
					sb.append(changeColName(table,c)).append("=").append(keyword); //must be table alias Fully qualified name,E.g WHERE A.entry 
				}
			}
			else
			{
				if(sb.length()>0)
					sb.append(" or ");
				sb.append(changeColName(table,c)).append(" like '%").append(keyword.replaceAll("'", "\'")).append("%'"); 
			}
		}
		return sb.toString();
	}
	
	public String changeColName(String table,Column c){
		//D.name_loc4 as itemname_loc4,D.name_loc4 as sender_loc4
		if(table.equals("achievement_reward"))
		{
			if(c.getAlias().equals("itemname_loc4"))
				return "C.name_loc4";
			if(c.getAlias().equals("sender_loc4"))
				return "D.name_loc4";
			if(c.getAlias().equals("itemname_loc5"))
				return "C.name_loc5";
			if(c.getAlias().equals("sender_loc5"))
				return "D.name_loc5";
		}else if(c.getAlias().equals("entrya"))
			return "A.entry";

		
		return c.getName();
	}
	public ArrayList<Column> getColumns(DBCon con,String table) throws Exception{
		return con.getColumns(table);
	}
	public StringBuilder getSql(String table) throws Exception{
		return new StringBuilder("select * from ").append(table);
	}

	private ArrayList<AjaxQuerFilterHtmlItem> getAjaxQueryHtml(String table){
		
		ArrayList<AjaxQuerFilterHtmlItem> arr=new ArrayList<AjaxQuerFilterHtmlItem>();
		for(QueryFilter filter:filters)//遍历所有filters，找到表名相同的
		{
			if(filter.getFilterTable().equals(table)){
				ArrayList<QueryItemInterface> items=filter.getFilterItems();
				for(QueryItemInterface item:items)//遍历所有的过滤元素，生成过滤条件
				{
					if(item.useAjaxRetriveHtml()) //只生成ajax刷新的部分
						arr.add(new AjaxQuerFilterHtmlItem(item));
				}
				
			}
		}
		
		return arr;
	}

	public String getQueryHtml(String table){
		
		StringBuilder sb=new StringBuilder();
		String s;
		for(QueryFilter filter:filters)//遍历所有filters，找到表名相同的
		{
			if(filter.getFilterTable().equals(table))
			{
				ArrayList<QueryItemInterface> items=filter.getFilterItems();
				
				for(QueryItemInterface item:items)//遍历所有的过滤元素，生成过滤条件
				{
					s=item.getQueryHtml();
					if(item.useAjaxRetriveHtml())
						sb.append("<span id='ajax_"+item.getClass().getSimpleName()+"'>").append(s).append("</span>").append("\n"); //生成页面上的spanid，为ajax刷新提供目标
					else if(!s.equals(""))
						sb.append(s).append("\n");					
				}
				
			}
		}
		
		return sb.toString();
	}
	public String getJavaScript(String table){
		
		StringBuilder sb=new StringBuilder();
		String s;
		for(QueryFilter filter:filters)//遍历所有filters，找到表名相同的
		{
			if(filter.getFilterTable().equals(table))
			{
				ArrayList<QueryItemInterface> items=filter.getFilterItems();
				
				for(QueryItemInterface item:items)//遍历所有的过滤元素，生成过滤条件
				{
					s=item.getJavaScript();
					if(s.equals(""))
						continue;
					sb.append(s).append("\n");
				}
				
			}
		}
		
		return sb.toString();
	}
	
	public String parseQueryParameter(String table,HttpServletRequest request){
		
		StringBuilder sb=new StringBuilder();
		String s;
		
		for(QueryFilter filter:filters)//遍历所有filters，找到表名相同的
		{
			if(filter.getFilterTable().equals(table))
			{
				ArrayList<QueryItemInterface> items=filter.getFilterItems();
				
				for(QueryItemInterface item:items)//遍历所有的过滤元素，生成过滤条件
				{
					s=item.parseQueryParameter(request);
					if(s.equals(""))
						continue;
					if(sb.length()>0)
						sb.append(" and ");
					sb.append(s);
				}
				
			}
		}
		
		return sb.toString();
	}
}
