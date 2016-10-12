package org.sample.poc.bookstore.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.tat.api.core.meta.Id;
import org.tat.api.core.meta.Table;

public class EntityResolver {
	
	public <E> Map<String,Object> getLookUpCriteria(Object lookupKey,Class<E> entityClass){
		Map<String,Object> lookupMap = null;
		if(entityClass.isAnnotationPresent(Table.class)){
			Table table = entityClass.getAnnotation(Table.class);
			String tableName = table.name();
			Field[] fields = entityClass.getDeclaredFields();
			Field idField = null;
			for(Field field:fields){
				if(field.isAnnotationPresent(Id.class)){
					idField = field;
					break;
				}
			}
			lookupMap =  new HashMap<>();
			org.tat.api.core.meta.Column column = idField.getAnnotation(org.tat.api.core.meta.Column.class);
			lookupMap.put("column", getColumnName(tableName, column));
			lookupMap.put("value", idField.getType().cast(lookupKey));
		}
		return lookupMap;
	}

	public <E> List<E> getEntityList(List<Map<String, Object>> entityMapList, Class<E> entityClass) {
		List<E> outputList = null;
		for(Map<String,Object> entityMap: entityMapList){
			if(entityClass.isAnnotationPresent(Table.class)){
				Table table = entityClass.getAnnotation(Table.class);
				String tableName = table.name();
				if(tableName==null || tableName.isEmpty())
            		tableName = entityClass.getSimpleName().toUpperCase();
				try {
					E instance = (E)entityClass.newInstance();
					Field[] fields = entityClass.getDeclaredFields();
					for(Field field:fields){
						if(field.isAnnotationPresent(org.tat.api.core.meta.Column.class)){
							org.tat.api.core.meta.Column column = field.getAnnotation(org.tat.api.core.meta.Column.class);
							String columnName = getColumnAlias(tableName,column);
							if(entityMap.containsKey(columnName)){
								Object value = entityMap.get(columnName);
								if(value!=null){
									BeanUtils.setProperty(instance, field.getName(), value);
								}
							}
						}
					}
					if(outputList==null){
						outputList = new ArrayList<>();
					}
					outputList.add(instance);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return outputList;
	}

	private static String getColumnAlias(String tableName, org.tat.api.core.meta.Column column) {
		StringBuffer sb = new StringBuffer(tableName);
		sb.append("_").append(column.dbColumn());
		return sb.toString();
	}
	
	private static String getColumnName(String tableName, org.tat.api.core.meta.Column column) {
		StringBuffer sb = new StringBuffer(tableName);
		sb.append(".").append(column.dbColumn());
		return sb.toString();
	}



}
