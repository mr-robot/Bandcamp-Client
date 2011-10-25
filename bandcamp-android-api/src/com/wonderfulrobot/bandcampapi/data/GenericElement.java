package com.wonderfulrobot.bandcampapi.data;

import java.util.HashMap;
import java.util.Map;

public class GenericElement {
	private Map<String, Object> attributes = new HashMap<String, Object>();
	

	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public Object getAttribute(String attr) {
		return attributes.get(attr);
	}
	
	public void setAttribute(String attr, Object val) {
		this.attributes.put(attr, val);
	}
	
	public boolean hasAttribute(String attr) {
		return this.attributes.containsKey(attr);
	}
}
