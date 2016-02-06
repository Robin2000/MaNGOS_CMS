package com.ficus.util;

import java.util.Calendar;
import java.util.Date;

public final class DateTool
{
  Calendar can = null ; //日历类
  String FORMAT = "YYYY-MM-DD" ;
  /**
   * 用系统时间构造。<br>
   */

  public DateTool ()
  {
	can = Calendar.getInstance () ;
  }
  /**
   * 从long型数据构造SADate
   */
  public DateTool (long l)
  {
	this () ;
	can.setTime (new Date (l)) ;
  }
  /**
   * 用给定时间对象构造。<br>
   */

  public DateTool (Date d)
  {
	this () ;
	can.setTime (d) ;
  }
  /**
   * 得到某月的第几号<br>
	 创建日期: (2000-10)<br>
   * @param s java.lang.String
   */

  public String getDay ()
  {
	if (can.get (Calendar.DAY_OF_MONTH) < 10)
	{
	  return new StringBuffer("0").append(can.get (Calendar.DAY_OF_MONTH)).toString() ;
	}
	else
	{
	  return String.valueOf(can.get (Calendar.DAY_OF_MONTH)) ;
	}
  }
  /**
   * 得到小时<br>
	 创建日期: (2000-10)<br>
   * @param s java.lang.String
   */

  public String getHour ()
  {
	if (can.get (Calendar.HOUR_OF_DAY) < 10)
	{
	  return new StringBuffer("0").append( can.get (Calendar.HOUR_OF_DAY) ).toString();
	}
	else
	{
	  return String.valueOf( can.get (Calendar.HOUR_OF_DAY) );
	}
  }
  /**
   * 得到分钟数<br>
	 创建日期: (2000-10)<br>
   * @param s java.lang.String
   */
  public String getMinute ()
  {
	if (can.get (Calendar.MINUTE) < 10)
	{
	  return new StringBuffer("0").append(can.get (Calendar.MINUTE) ).toString();
	}
	else
	{
	  return String.valueOf(can.get (Calendar.MINUTE)) ;
	}
  }
  /**
   * 得到月份<br>
	 创建日期: (2000-10)<br>
   * @param s java.lang.String
   */

  public String getMonth ()
  {
	if ( (can.get (Calendar.MONTH) + 1) < 10)
	{
	  return new StringBuffer("0").append((can.get (Calendar.MONTH) + 1)).toString() ;
	}
	else
	{
	  return String.valueOf(can.get(Calendar.MONTH) + 1) ;
	}
  }
  /**
   * 得到秒数<br>
	 创建日期: (2000-10)<br>
   * @param s java.lang.String
   */

  public String getSecond ()
  {
	if (can.get (Calendar.SECOND) < 10)
	{
	  return new StringBuffer("0").append(can.get (Calendar.SECOND)).toString() ;
	}
	else
	{
	  return String.valueOf(can.get (Calendar.SECOND)) ;
	}
  }
  /**
   * 得到年分<br>
	 创建日期: (2000-10)<br>
   * @param s java.lang.String
   */
  public String getYear ()
  {
	return String.valueOf(can.get (Calendar.YEAR)) ;
  }
  /**
   * 将日期型数据转化为字符串,格式为YYYY-MM-DD<br>
	 创建日期: (2000-10)<br>

   * @param s java.lang.String
   * return java.lang.String
   */
  public String toDateString ()
  {
	return toDateString (toDateString (FORMAT)) ;
  }
  /**
   * 将日期型数据转化为字符串,格式为YYYY-MM-DD<br>
	 创建日期: (2000-10)<br>

   * @param format java.lang.String
   * return java.lang.String
   */
  public String toDateString (String format)
  {
	String ret = format ;
        if(ret.indexOf("YYYY")>-1)
 	 ret = Util.replace (ret , "YYYY" , String.valueOf(getYear ())) ;

        if(ret.indexOf("YY")>-1)
          ret = Util.replace (ret , "YY" , getYear ().substring(2)) ;

        if(ret.indexOf("MM")>-1)
	  ret = Util.replace (ret , "MM" , String.valueOf(getMonth ())) ;

        if(ret.indexOf("DD")>-1)
	  ret = Util.replace (ret , "DD" , String.valueOf(getDay ())) ;

        if(ret.indexOf("HH")>-1)
	  ret = Util.replace (ret , "HH" , String.valueOf(getHour () )) ;

        if(ret.indexOf("MI")>-1)
          ret = Util.replace (ret , "MI" , String.valueOf(getMinute ())) ;

        if(ret.indexOf("SS")>-1)
          ret = Util.replace (ret , "SS" , String.valueOf(getSecond ())) ;

	return ret ;
  }
  /**
   * 将日期型数据转化为字符串,格式为YYYY-MM-DD<br>
	 创建日期: (2000-10)<br>

   * return java.lang.String
   */

  public String toString ()
  {
	return toDateString () ;
  }
  /**
   * 将日期型数据转化为时间,格式为hh:mm:ss<br>
	 创建日期: (2000-10)<br>

   * return java.lang.String
   */

  public String toTimeString ()
  {
	return new StringBuffer().append(getHour ()).append(":").append(getMinute ()).append(":").append(getSecond ()).toString() ;
  }
  /**
   * 得到月份<br>
   */

  public String getMonthStart ()
  {
	String ret = "YYYY-MM-DD" ;
	ret = Util.replace (ret , "YYYY" ,String.valueOf( getYear ())) ;
	ret = Util.replace (ret , "MM" , String.valueOf(getMonth ())) ;
	ret = Util.replace (ret , "DD" , "01") ;
	return ret ;
  }
public String getYearStart ()
  {
	String ret = "YYYY-MM-DD" ;
	ret = Util.replace (ret , "YYYY" , String.valueOf(getYear ())) ;
	ret = Util.replace (ret , "MM" , "01") ;
	ret = Util.replace (ret , "DD" , "01") ;
	return ret ;
  }
   public static long parseLong (String YYYYMMDDHHMISS)
   {
	   return parse(YYYYMMDDHHMISS).getTime();
   }
   /**
   * insert method comment here。
   * create time：(2002-5-12 23:26:37)
   * @param YYYYMMDDHHMISS java.lang.String
   */
  public static Date parse (String YYYYMMDDHHMISS)
  {
	Calendar can = Calendar.getInstance () ;

	java.util.StringTokenizer st = new java.util.StringTokenizer (
		YYYYMMDDHHMISS
		, " ") ;
	String YMD = st.nextElement ().toString () ;
	java.util.StringTokenizer stYMD = new java.util.StringTokenizer (YMD , "-") ;

	int year = Integer.parseInt (stYMD.nextElement ().toString ()) ;
	int month = Integer.parseInt (stYMD.nextElement ().toString ()) - 1 ;
	int day = Integer.parseInt (stYMD.nextElement ().toString ()) ;

	can.set (year , month , day) ;

	if (st.hasMoreElements ())
	{
	  String HMS = st.nextElement ().toString () ;
	  java.util.StringTokenizer stHMS = new java.util.StringTokenizer (HMS
		  , ":") ;
	  int hour = Integer.parseInt (stHMS.nextElement ().toString ()) ;
	  int minute = Integer.parseInt (stHMS.nextElement ().toString ()) ;
	  can.set (year , month , day , hour , minute) ;

	  if (stHMS.hasMoreElements ())
	  {
		int second = Integer.parseInt (stHMS.nextElement ().toString ()) ;
		can.set (year , month , day , hour , minute , second) ;
	  }
	}

	return can.getTime () ;
  }

}
