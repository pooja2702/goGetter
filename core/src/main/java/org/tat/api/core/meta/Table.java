package org.tat.api.core.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to configure a POJO to a database table. The whole point
 * is to map a relational database object to a java object.
 * 
 * <pre>
 * <code>
 * @Table(name="ORDERS")
 * public class Order {
 * }
 * </code>
 * </pre>
 * 
 * @author pooja
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	String name() default "";
}
