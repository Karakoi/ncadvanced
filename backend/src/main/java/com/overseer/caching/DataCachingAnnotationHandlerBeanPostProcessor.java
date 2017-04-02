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
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            enhancer.setCallback(getCacheInterceptor(bean));
            return enhancer.create();
        } else {
            LOG.debug("Checking bean {} with annotation Repository on containing cache annotation", bean);
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), getInvocationHandler(bean));
        }
    }


    /**
     * awdwadwa.
     *
     * @param bean awdwadwda.
     * @return dawdadawdwadawd.
     */
    private MethodInterceptor getCacheInterceptor(Object bean) {
        return (object, method, args, proxy) -> {
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
                    simpleInMemoryCache.put(key, proxy.invoke(bean, args));
                    return simpleInMemoryCache.getData(key);
                }
            } else {
                return proxy.invoke(bean, args);
            }
        };
    }

    /**
     * wadwadwad.
     *
     * @param bean awdawdawd.
     * @return wadwadawdawd.
     */
    private InvocationHandler getInvocationHandler(Object bean) {
        return (proxy, method, args) -> {
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
                    simpleInMemoryCache.put(key, method.invoke(bean, args));
                    return simpleInMemoryCache.getData(key);
                }
            } else {
                return method.invoke(bean, args);
            }
        };
    }
}
