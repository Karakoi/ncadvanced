package com.overseer.caching;

import lombok.val;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Proxy;
import java.util.*;


/**
 * Provide functionality to {@link CacheableData} annotation.
 */
@Component
public class DataCachingAnnotationHandlerBeanPostProcessor implements BeanPostProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DataCachingAnnotationHandlerBeanPostProcessor.class);
    private Map<String, Class> map = new HashMap<>();
    private static final long DEFAULT_CACHE_CLEAN_TIME = 120;
    private SimpleInMemoryCache<Triplet<Object, String, List>, Object>
            simpleInMemoryCache = new SimpleInMemoryCache<>(DEFAULT_CACHE_CLEAN_TIME);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Repository.class)) {
            map.put(name, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        Class beanClass = map.get(name);
        if (beanClass == null) {
            return bean;
        }

            LOG.debug("Checking bean {} with annotation Repository on containing cache annotation", bean);
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                System.out.println(method.getName());
                System.out.println(bean.toString());
                if (method.getName().contains("save") || method.getName().contains("delete")) {
                    System.out.println("cleaning on save or delete");
                    simpleInMemoryCache.cleanup();
                }
                if (method.isAnnotationPresent(CacheableData.class)) {
                    Triplet<Object, String, List> key = Triplet.with(bean, method.getName(), Arrays.asList(args));
                    LOG.debug("Method {} has annotation, starting checking cache", method);
                    if (simpleInMemoryCache.contains(key)) {
                        return simpleInMemoryCache.getData(key);
                    } else {
                        val result = method.invoke(bean, args);
                        simpleInMemoryCache.put(key, result);
                        return result;
                    }
                } else {
                    return method.invoke(bean, args);
                }
            });
    }

}
