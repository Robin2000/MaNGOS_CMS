package com.ficus.util;

import java.util.HashMap;

public final class ShortUrl {

	public static String ShortText(String string) {
		return MD5Util.MD5_16(string);
	}
	public static void main(String argv[])
	{
		HashMap<String,String> testMap=new HashMap<String,String>();
		for(int i=0;i<100000000;i++)
		{
			String url="http://weibo.com/u/"+i;
			String key=MD5Util.MD5_16(url);
			System.out.println(key);
		   if(testMap.get(key)!=null)
			{
			   System.out.println(url+"="+testMap.get(key));
			   System.out.println(MD5Util.MD5_16(url)+"="+MD5Util.MD5_16(testMap.get(key)));
			   
			}else{
			   testMap.put(key, url);
			}
		   System.gc();
		}
	}
}
