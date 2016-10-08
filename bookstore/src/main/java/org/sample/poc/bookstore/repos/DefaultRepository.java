package org.sample.poc.bookstore.repos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.sample.poc.bookstore.mapper.EntityResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.tat.api.core.sql.Field;
import org.tat.api.core.sql.Query;

public class DefaultRepository<E> extends AbstractRepository{
	
	private Class<E> entityClass;
	
	@Autowired
	private EntityResolver entityResolver;
	
	private Map<String,Field> metadataMap;
	
	public DefaultRepository(Class<E> entityClass,Map<String,Field> metadataMap){
		this.entityClass=entityClass;
		this.metadataMap=metadataMap;
	}
	
	public static String FIND_ALL_QUERY = "SELECT {column.list} FROM {entity.name} {filter.criteria} {sort.criteria}";
	public static String FIND_ONE_QUERY = "SELECT {column.list} FROM {entity.name} WHERE {lookup.id}=:lookupId";
	
	public List<E> findAll(int offset, int limit, String sorts,
			boolean all, Map<String, String> searchRequest) throws Exception{
		Query finalQueryObj = getAllEntities(offset, limit, sorts,
				all, searchRequest,metadataMap,FIND_ALL_QUERY);
		List<Map<String, Object>> entityMap = getNamedParameterJdbcTemplate().query(
				finalQueryObj.getFinalQuery(), finalQueryObj.getNamedParamsMap(), new ColumnMapRowMapper());
		System.out.println(entityMap);
		List<E> entities = entityResolver.getEntityList(entityMap,entityClass);
		return entities == null ? new ArrayList<E>() : entities;
	}
	
	public Optional<E> save(E e){
		//TODO: To be implemented
		return null;
		
	}

	public E findOne(Object lookupKey) throws Exception {
		Map<String,Object> lookupMap = entityResolver.getLookUpCriteria(lookupKey, entityClass);
		SqlParameterSource namedParameters = new MapSqlParameterSource("lookupId", lookupMap.get("value"));
		String finalQuery = populateFields(FIND_ONE_QUERY, metadataMap);
		finalQuery = populateLookUpId(finalQuery,lookupMap.get("column"));
		List<Map<String, Object>> entityMap = getNamedParameterJdbcTemplate().query(
				finalQuery, namedParameters, new ColumnMapRowMapper());
		List<E> entities = entityResolver.getEntityList(entityMap,entityClass);
		return entities.get(0);
	}
}
