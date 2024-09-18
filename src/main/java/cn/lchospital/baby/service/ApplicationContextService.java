package cn.lchospital.baby.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ApplicationContextService implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public List<Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(annotationType);
        return new ArrayList<>(beansWithAnnotation.values());
    }
}