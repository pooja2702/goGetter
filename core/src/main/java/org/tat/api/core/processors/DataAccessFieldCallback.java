package org.tat.api.core.processors;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.tat.api.core.meta.DataAccess;
import org.tat.api.core.meta.Table;
import org.tat.api.core.sql.ColumnType;

public class DataAccessFieldCallback implements FieldCallback{

    private static Logger logger = Logger.getLogger(DataAccessFieldCallback.class);
    private static int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;

    private static String ERROR_ENTITY_VALUE_NOT_SAME = "@DataAccess(entity) " + "value should have same type with injected generic type.";
    private static String WARN_NON_GENERIC_VALUE = "@DataAccess annotation assigned " + "to raw (non-generic) declaration. This will make your code less type-safe.";
    private static String ERROR_CREATE_INSTANCE = "Cannot create instance of " + "type '{}' or instance creation is failed because: {}";

    private ConfigurableListableBeanFactory configurableListableBeanFactory;
    private Object bean;

    public DataAccessFieldCallback(final ConfigurableListableBeanFactory bf, final Object bean) {
        configurableListableBeanFactory = bf;
        this.bean = bean;
    }

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(DataAccess.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        final Type fieldGenericType = field.getGenericType();
        // In this example, get actual "GenericDAO' type.
        final Class<?> generic = field.getType();
        final Class<?> classValue = field.getDeclaredAnnotation(DataAccess.class).entity();

        if (genericTypeIsValid(classValue, fieldGenericType)) {
            final String beanName = classValue.getSimpleName() + generic.getSimpleName();
            final Object beanInstance = getBeanInstance(beanName, generic, classValue);
            field.set(bean, beanInstance);
        } else {
            throw new IllegalArgumentException(ERROR_ENTITY_VALUE_NOT_SAME);
        }
    }

    /**
     * For example, if user write:
     * <pre>
     * &#064;DataAccess(entity=Person.class) 
     * private GenericDAO&lt;Account&gt; personGenericDAO;
     * </pre>
     * then this is should be failed.
     */
    public boolean genericTypeIsValid(final Class<?> clazz, final Type field) {
        if (field instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) field;
            final Type type = parameterizedType.getActualTypeArguments()[0];

            return type.equals(clazz);
        } else {
            logger.warn(WARN_NON_GENERIC_VALUE);
            return true;
        }
    }

    public final Object getBeanInstance(final String beanName, final Class<?> genericClass, final Class<?> paramClass) {
        Object daoInstance = null;
        if (!configurableListableBeanFactory.containsBean(beanName)) {
            logger.info("Creating new DataAccess bean named '{"+beanName+"}'");

            Object toRegister = null;
            try {
                final Constructor<?> ctr = genericClass.getConstructor(Class.class,Map.class);
                if (paramClass.isAnnotationPresent(Table.class)) {
                	Map<String,org.tat.api.core.sql.Field> fieldMap = new HashMap<>();
                	Table table = paramClass.getAnnotation(Table.class);
                	String tableName= table.name();
                	logger.debug("::Table Name:: "+tableName);
                	if(tableName==null || tableName.isEmpty())
                		tableName = paramClass.getSimpleName().toUpperCase();
                	Field[] fields = paramClass.getDeclaredFields();
                	for(Field f : fields){
                		if (f.isAnnotationPresent(org.tat.api.core.meta.Column.class)) {
                			org.tat.api.core.meta.Column memberField = f.getAnnotation(org.tat.api.core.meta.Column.class);
                			logger.debug("::dbColumnName:: "+ memberField.dbColumn());
                			fieldMap.put(f.getName(), new org.tat.api.core.sql.Field(memberField.dbColumn(), f.getName(), ColumnType.getColumnTypeForType(f.getType().getSimpleName().toLowerCase()), tableName));
                		}
                	}
                	toRegister = ctr.newInstance(paramClass,fieldMap);
                }
            } catch (final Exception e) {
                logger.error(ERROR_CREATE_INSTANCE, e);
                throw new RuntimeException(e);
            }

            daoInstance = configurableListableBeanFactory.initializeBean(toRegister, beanName);
            configurableListableBeanFactory.autowireBeanProperties(daoInstance, AUTOWIRE_MODE, true);
            configurableListableBeanFactory.registerSingleton(beanName, daoInstance);
            logger.info("Bean named '{"+beanName+"}' created successfully.");
        } else {
            daoInstance = configurableListableBeanFactory.getBean(beanName);
            logger.info("Bean named '{"+beanName+"}' already exist used as current bean reference.");
        }
        return daoInstance;
    }
}
