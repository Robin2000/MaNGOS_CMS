package com.ficus.table;

public class SqlParser {
	
	/**
	 * 
	 * findPrevWord(find out previous word)<br/>
		E.g "select A.name as NAMEOK", field=NAMEOK, return  A.name
	
	 */
	public static String findPrevWord(String sql,String fieldOrAlis){
		
		String arr[]=sql.split("[\r\n,]+");
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i].equals(fieldOrAlis))
				if(arr[i-1].toUpperCase().equals("AS"))
					return arr[i-2]; 
		}
		
		
		return fieldOrAlis;
	}

}
