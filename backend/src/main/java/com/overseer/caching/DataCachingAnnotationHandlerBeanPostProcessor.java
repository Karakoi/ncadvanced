package com.overseer.caching;

import com.overseer.caching.annotation.CacheChanger;
import com.overseer.caching.annotation.CacheableData;
import com.overseer.caching.impl.SimpleInMemoryCacheImpl;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.lang.reflect.*;
import java.util.*;


/**
 * Provide functionality to {@link CacheableData} annotation.
 */
@Component
public class DataCachingAnnotationHandlerBeanPostProcessor implements BeanPostProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DataCachingAnnotationHandlerBeanPostProcessor.class);
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
            LOG.debug("Method has no interface, creating via cglib");
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> cacheHandler(bean, method, objects));
            return enhancer.create();
        } else {
            LOG.debug("Checking bean {} with annotation Repository on containing cache annotation", bean);
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
     * @throws Throwable  the exception to throw from the method invocation on the proxy instance. The exception's type must be assignable either to any of the exception types declared in the throws clause of the interface method or to the unchecked exception types java.lang.RuntimeException or java.lang.Error. If a checked exception is thrown by this method that is not assignable to any of the exception types declared in the throws clause of the interface method, then an UndeclaredThrowableException containing the exception that was thrown by this method ill be thrown by the method invocation on the proxy instance.
     */
    public Object cacheHandler(Object bean, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(CacheChanger.class)) {
            LOG.debug("Method {} has annotation CacheChanger, start cleaning cache");
            simpleInMemoryCache.cleanup();
        }
        if (method.isAnnotationPresent(CacheableData.class)) {
            Triplet<Object, String, List> key = Triplet.with(bean, method.getName(), null);
            if (args != null) {                                  //for methods which has arguments
                LOG.debug("Method has arguments. Added to key");
                key = key.setAt2(Arrays.asList(args));
            }
            LOG.debug("Method {} has annotation, starting checking cache", method);
            if (simpleInMemoryCache.contains(key)) {
                LOG.debug("Getting date from cache with key: {}", key);
                return simpleInMemoryCache.getData(key);
            } else {
                LOG.debug("Adding date to cache with key: {}", key);
                simpleInMemoryCache.put(key, method.invoke(bean, args));
                return simpleInMemoryCache.getData(key);
            }
        } else {
            return method.invoke(bean, args);
        }
    }
}
