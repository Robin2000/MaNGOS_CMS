package com.ficus.query.item;

import com.ficus.query.KeyValue;

public class SubClassKeyValue extends KeyValue {

	private Object parentClass;
	public SubClassKeyValue(Object parentClass,Object key, Object value) {
		super(key, value);
		this.parentClass=parentClass;
	}
	public Object getParentClass() {
		return parentClass;
	}
	public void setParentClass(Object parentClass) {
		this.parentClass = parentClass;
	}
	
}
