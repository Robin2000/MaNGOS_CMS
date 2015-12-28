package com.ficus.db;

import java.util.HashMap;

public final class KeyUpperMap extends HashMap<String, Object> {
	@Override
	public Object put(String key, Object value) {
		return super.put(key.toUpperCase(), value);
	}

	private static final long serialVersionUID = 1L;
	
}
