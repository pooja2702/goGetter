package org.tat.api.core.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to inject dynamic DAO object to the service layer.
 * 
 * <pre>
 * <code>
 *	@DataAccess(entity=Book.class)
 *	private DefaultRepository<Book> bookDefaultRepository;
 * </code>
 * </pre>
 * 
 * @author pooja
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Documented
public @interface DataAccess {
	Class<?> entity();
}
