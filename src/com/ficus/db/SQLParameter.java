package com.ficus.db;

public final class SQLParameter
{
  String name ;
  Object value ;
  int pos = -999 ;
  public SQLParameter (String name , Object value , int pos)
  {
	super () ;
	this.name = name ;
	this.value = value ;
	this.pos = pos ;
  }
  public String toString()
  {
    return new StringBuffer(this.pos).append(".").append(this.name).append("=").append(this.value).toString();
  }
}