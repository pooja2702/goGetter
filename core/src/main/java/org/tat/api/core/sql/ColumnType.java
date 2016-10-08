package org.tat.api.core.sql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Enumeration of the datatypes for a database column
 *
 */
public enum ColumnType {
	STRING("char","character","string"),LONG("long","int","integer"),DOUBLE("double","float"),TIMESTAMP("date","timestamp","datetime");
	
	private static final Map<ColumnType,List<String>> map = new HashMap<>();
	
	static{
		for(ColumnType col : ColumnType.values()){
			map.put(col, col.dataTypes);
		}
	}
	
	List<String> dataTypes;
	
	ColumnType(String... types){
		dataTypes = Arrays.asList(types);
	}
	
	public static ColumnType getColumnTypeForType(String type){
		for(Entry<ColumnType,List<String>> values : map.entrySet()){
			List<String> types = values.getValue();
			if(types.contains(type))
				return values.getKey();
		}
		return null;
		
	}
}


