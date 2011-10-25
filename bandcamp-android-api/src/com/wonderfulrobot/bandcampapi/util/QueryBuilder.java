package com.wonderfulrobot.bandcampapi.util;

import java.util.HashMap;

public class QueryBuilder {
	

	private HashMap<String, Object> fields = new HashMap<String, Object>();

	public QueryBuilder addQuery(String key, Object value){
		this.fields.put(key, value);
		return this;
	}
	
	public QueryBuilder addAggregatedQuery(String key, long[] value){
		StringBuilder sb = new StringBuilder();
		for(long i : value){
			sb.append(""+i+",");
		}
		
		this.fields.put(key, sb.toString());
		return this;
	}
	
	public String[] addAggregatedQueryObject(String key, long[] value){
		if(value.length > 0){
			StringBuilder sb = new StringBuilder();
			sb.append("&");
			sb.append(key);
			sb.append("=");
			for(long i : value){
				sb.append(""+i+",");
			}		
			return new String[]{sb.toString()};
		}
		else
			return new String[]{};
	}
	
	
	public HashMap<String, Object> build(){
		return fields;
	}
}
