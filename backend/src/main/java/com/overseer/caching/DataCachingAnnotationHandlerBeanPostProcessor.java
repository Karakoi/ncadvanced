package com.overseer.caching;

import com.overseer.caching.annotation.CacheChanger;
import com.overseer.caching.annotation.CacheableData;
import com.overseer.caching.impl.SimpleInMemoryCacheImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Triplet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.lang.reflect.*;
import java.util.*;


/**
 * Provide functionality to {@link CacheableData} annotation.
 */
@Slf4j
@Component
public class DataCachingAnnotationHandlerBeanPostProcessor implements BeanPostProcessor, Ordered {
    private Map<String, Class> repositoryMap = new HashMap<>();
    private static final long DEFAULT_CACHE_CLEAN_SECONDS_TIME = 120;
    private SimpleInMemoryCache<Triplet<Object, String, List>, Object>
            simpleInMemoryCache = new SimpleInMemoryCacheImpl<>(DEFAULT_CACHE_CLEAN_SECONDS_TIME);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Repository.class)) {
            repositoryMap.put(name, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        Class beanClass = repositoryMap.get(name);
        if (beanClass == null) {
            return bean;
        }
        if (bean.getClass().getInterfaces() == null) {
            log.debug("Method has no interface, creating via cglib");
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> cacheHandler(bean, method, objects));
            return enhancer.create();
        } else {
            log.debug("Checking bean {} with annotation Repository on containing cache annotation", bean);
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    (proxy, method, args) -> cacheHandler(bean, method, args));
        }
    }

    /**
     * Method interceptor for dynamic proxy or MethodInterceptor for cglib. Method encapsulate logic of access to cache
     * and method invoking for two different class {@link MethodInterceptor} and {@link InvocationHandler}
     * @param bean   Object which method will be intercepted.
     * @param method target method.
     * @param args   arguments of target method.
     * @return result of method invoking.
     */
    @SneakyThrows
    private Object cacheHandler(Object bean, Method method, Object[] args) {
        if (method.isAnnotationPresent(CacheChanger.class)) {
            log.debug("Method {} has annotation CacheChanger, start cleaning cache");
            simpleInMemoryCache.cleanup();
        }
        if (method.isAnnotationPresent(CacheableData.class)) {
            Triplet<Object, String, List> key = Triplet.with(bean, method.getName(), null);
            if (args != null) {                                  //for methods which has arguments
                log.debug("Method has arguments. Added to key");
                key = key.setAt2(Arrays.asList(args));
            }
            log.debug("Method {} has annotation, starting checking cache", method);
            if (simpleInMemoryCache.contains(key)) {
                log.debug("Getting date from cache with key: {}", key);
                return simpleInMemoryCache.getData(key);
            } else {
                log.debug("Adding date to cache with key: {}", key);
                simpleInMemoryCache.put(key, method.invoke(bean, args));
                return simpleInMemoryCache.getData(key);
            }
        } else {
            return method.invoke(bean, args);
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
