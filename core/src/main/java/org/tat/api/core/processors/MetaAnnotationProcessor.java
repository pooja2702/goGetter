package org.tat.api.core.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

@Component
public class MetaAnnotationProcessor implements BeanPostProcessor{
	
	 private ConfigurableListableBeanFactory configurableListableBeanFactory;

	    @Autowired
	    public MetaAnnotationProcessor(ConfigurableListableBeanFactory bf) {
	        configurableListableBeanFactory = bf;
	    }


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		 scanDataAccessAnnotation(bean, beanName);
        return bean;
	}
	
	protected void scanDataAccessAnnotation(Object bean, String beanName) {
        Class<?> managedBeanClass = bean.getClass();
        FieldCallback fcb = new DataAccessFieldCallback(configurableListableBeanFactory, bean);
        ReflectionUtils.doWithFields(managedBeanClass, fcb);
    }

}
