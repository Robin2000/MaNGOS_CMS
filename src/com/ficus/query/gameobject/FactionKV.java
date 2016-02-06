package com.ficus.query.gameobject;

import com.ficus.query.KeyValue;

public class FactionKV extends KeyValue {
	
	public int ourMask;/*自己阵营遮罩*/
	public int friendMask;
	public int hostileMask;
	
	public FactionKV(Object k,Object v,int ourMask,int friendMask,int hostileMask){
		super(k,v);
		
		this.ourMask=ourMask;
		this.friendMask=friendMask;
		this.hostileMask=hostileMask;
	}
}
