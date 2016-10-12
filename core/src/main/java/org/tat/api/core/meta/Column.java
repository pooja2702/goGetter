package org.tat.api.core.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to configure a member of a POJO to a database table
 * column. This annotation is applicable for only member variable of a POJO.
 * 
 * <pre>
 * <code>
 * 	&#64;Column(dbColumn = "O_ID")
 *	private String orderId;
 * </code>
 * </pre>
 * 
 * @author pooja
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	String dbColumn();
}
