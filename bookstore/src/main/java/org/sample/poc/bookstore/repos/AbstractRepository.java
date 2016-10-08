package org.sample.poc.bookstore.repos;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.tat.api.core.sql.Field;
import org.tat.api.core.sql.Query;
import org.tat.api.core.sql.QueryImpl;

public class AbstractRepository {

	private static final Logger logger = Logger
			.getLogger(AbstractRepository.class);

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired 
	public void setDatasource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	protected Query getAllEntities(int offset, int limit, String sorts,
			boolean all, Map<String, String> searchRequest,
			Map<String, Field> entityFieldMap, String baseQuery)
					throws Exception {
		Query query = new QueryImpl();
		query.formQueryObject(offset, limit, sorts, all, searchRequest,
				entityFieldMap, populateEntityName(baseQuery,entityFieldMap));
		logger.debug("Final Query : " + query.getFinalQuery());
		return query;
	}

	protected String populateFields(String queryStr, Map<String, Field> entityFieldMap) throws Exception {
		Query query = new QueryImpl();
		queryStr = populateEntityName(queryStr,entityFieldMap);
		queryStr = queryStr.replace("{column.list}",
				query.getColumnsList(entityFieldMap));
		return queryStr;
	}
	
	protected String populateLookUpId(String query,Object columnName){
		query = query.replace("{lookup.id}", columnName.toString());
		return query;
	}
	
	private String populateEntityName(String query,Map<String, Field> entityFieldMap) {
		String tableName=null;
		for(Field f: entityFieldMap.values()){
			 tableName = f.getTableName();
			break;
		}
		String queryStr = query.replace("{entity.name}",tableName);
		return queryStr;
	}
}
