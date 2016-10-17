package org.tat.api.core.sql;

import java.util.Map;

import org.tat.api.core.exceptions.InvalidFieldNameException;
import org.tat.api.core.exceptions.InvalidSearchQueryException;

public interface Query {

	void formQueryObject(int offset, int limit, String sorts,
			boolean all, Map<String, String> searchRequest,
			Map<String, Field> entityFieldMap, String baseQuery) throws InvalidSearchQueryException, InvalidFieldNameException;

	String getFinalQuery();

	String getColumnsList(Map<String, Field> entityFieldMap) throws InvalidFieldNameException;
	
	Map<String, Object> getNamedParamsMap();

}
