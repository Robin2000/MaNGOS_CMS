package com.ficus.table;

import java.util.List;

import com.ficus.db.Column;
import com.ficus.db.DBCon;
import com.ficus.db.QueryResult;
import com.ficus.util.Util;

public class ListBean {
	
	
			
	public ResultBean query(String table,String start,String length,ResultBean rb,String keyword,List<OrderBean> order) throws Exception{
		
		StringBuilder sql=new StringBuilder("select * from ").append(table);
		
		
		DBCon con=null;
		try{
			con=DBCon.getConnection("mangos");
			
			Column[] cols=con.getcolumns(table);
			if(keyword!=null&&keyword.trim().length()!=0)
				sql.append(" where ").append(searchClause(cols,keyword));
		
			
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<order.size();i++)
			{
				
				if(sb.length()==0)
					sb.append(" order by ");
				else
					sb.append(",");
				
				sb.append(cols[order.get(i).getOrderCol()].getName()).append(" ").append(order.get(i).getOrderDesc());	
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
			
			return rb;
			
		}finally{
			if(con!=null)
				con.close();
		}
		
		
	}
	private String searchClause(Column[] cols,String keyword){
		StringBuilder sb=new StringBuilder();
		for(Column c:cols){
			if (c.isNumeric())
			{
				if(Util.isNumeric(keyword))
				{
					if(sb.length()>0)
						sb.append(" or ");
					sb.append(c.getName()).append("=").append(keyword);
				}
			}
			else
			{
				if(sb.length()>0)
					sb.append(" or ");
				sb.append(c.getName()).append(" like '%").append(keyword.replaceAll("'", "\'")).append("%'");
			}
		}
		return sb.toString();
	}
}
