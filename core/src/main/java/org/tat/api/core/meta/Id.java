package org.tat.api.core.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to configure a member of a POJO to identifier. This will
 * be helpful to identify a entity with a unique value.
 * 
 * <pre>
 * <code>
 *	&#64;Id
 *	private String orderId;
 * </code>
 * </pre>
 * 
 * @author pooja
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {

}
